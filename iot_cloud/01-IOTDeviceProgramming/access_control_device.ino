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
}

void loop()
{
    bool statePir = digitalRead(pir);
    bool stateBtnLeft = digitalRead(btnLeft);
    bool stateBtnRight = digitalRead(btnRight);

    currentMillis = millis();
    // if (!statePir && (currentMillis - startMillis >= period))
    // {
    //     countBtnLeft, countBtnRight = 0;
    //     digitalWrite(ledRed, HIGH);
    //     digitalWrite(ledYellow, LOW);
    //     digitalWrite(ledGreen, LOW);
    // }

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

    if (countBtnLeft + countBtnRight == 2)
    {
        state = 3;
    }
}

void checkState()
{
    if (countBtnLeft == 1 && countBtnRight == 1)
    {
        digitalWrite(ledGreen, HIGH);
        state = 4;
    }
    else
    {
        blinkRed();
        countBtnLeft, countBtnRight = 0;
        state = 2;
    }
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

void unlock(bool stateBtnLeft, bool stateBtnRight)
{
    digitalWrite(ledRed, LOW);
    digitalWrite(ledYellow, LOW);
    digitalWrite(ledGreen, HIGH);
    delay(5000);
    digitalWrite(ledRed, HIGH);

    countBtnLeft, countBtnRight = 0;
    state = 1;
}

void lock(bool statePir)
{
    digitalWrite(ledRed, HIGH);
    digitalWrite(ledGreen, LOW);
    if (statePir)
        state = 1;
}