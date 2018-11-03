int ledRed = 13;
int ledYellow = 12;
int ledGreen = 11;
int btnLeft = 4;
int btnRight = 2;
int pir = 3;

int state = 0;
int countBtnLeft = 0;
int countBtnRight = 0;

unsigned long startMillis;
unsigned long currentMillis;
const unsigned long period = 5000;

void setup()
{
    pinMode(ledRed, OUTPUT);
    pinMode(ledYellow, OUTPUT);
    pinMode(ledGreen, OUTPUT);
    pinMode(btnLeft, INPUT);
    pinMode(btnRight, INPUT);
    pinMode(pir, INPUT);
    startMillis = millis();
    Serial.begin(9600);
}

void loop()
{
    bool statePir = digitalRead(pir);
    bool stateBtnLeft = digitalRead(btnLeft);
    bool stateBtnRight = digitalRead(btnRight);

    currentMillis = millis();
    if (!statePir && (currentMillis - startMillis >= period))
    {
        Serial.println((String) "Timeout system for " + period + " ms locking");
        startMillis = currentMillis;
        lock(statePir);
    }

    switch (state)
    {
    case 1:
        detectMotion(statePir);
        break;
    case 2:
        waitForInput(stateBtnLeft, stateBtnRight);
        break;
    case 3:
        checkState();
        break;
    case 4:
        unlock(stateBtnLeft, stateBtnRight);
        break;
    default:
        lock(statePir);
        break;
    }
}

void detectMotion(bool statePir)
{
    if (statePir)
    {
        Serial.println((String) "Detecting motion, state: " + state);
        delay(200);
        digitalWrite(ledRed, LOW);
        state = 2;
    }
}

void waitForInput(bool stateBtnLeft, bool stateBtnRight)
{
    digitalWrite(ledYellow, HIGH);

    if (stateBtnLeft)
        countBtnLeft++;

    if (stateBtnRight)
        countBtnRight++;

    if (stateBtnLeft || stateBtnRight)
    {
        digitalWrite(ledYellow, LOW);
        delay(300);
    }

    if (countBtnLeft + countBtnRight >= 2)
    {
        Serial.println((String) "waitForInput state: " + state);
        delay(200);
        state = 3;
    }
}

void checkState()
{
    if (countBtnLeft == 1 && countBtnRight == 1)
    {
        Serial.println((String) "checkState state: " + state + " countBtnLeft: " + countBtnLeft + " countBtnRight: " + countBtnRight);
        delay(200);
        digitalWrite(ledGreen, HIGH);
        state = 4;
    }
    else
    {
        Serial.println((String) "checkState (blinkred) state: " + state + " countBtnLeft: " + countBtnLeft + " countBtnRight: " + countBtnRight);
        delay(200);
        blinkRed();
        lock(1);
    }
}

void unlock(bool stateBtnLeft, bool stateBtnRight)
{
    Serial.println((String) "Unlock state: " + state);
    delay(200);
    digitalWrite(ledRed, LOW);
    digitalWrite(ledYellow, LOW);
    digitalWrite(ledGreen, HIGH);
    delay(5000);
    digitalWrite(ledRed, HIGH);
    digitalWrite(ledGreen, LOW);

    countBtnLeft = 0;
    countBtnRight = 0;
    Serial.println((String) "Reset count: " + stateBtnLeft + ", " + stateBtnRight);
    delay(200);
    state = 0;
}

void lock(bool statePir)
{
    Serial.println((String) "lock state: " + state + " statePir: " + statePir);
    delay(200);
    digitalWrite(ledRed, HIGH);
    digitalWrite(ledGreen, LOW);
    if (statePir)
        state = 1;
}

void blinkRed()
{
    for (int i = 0; i < 4; i++)
    {
        digitalWrite(ledRed, HIGH);
        delay(200);
        digitalWrite(ledRed, LOW);
        delay(200);
    }
}