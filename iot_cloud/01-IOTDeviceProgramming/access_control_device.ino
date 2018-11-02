/*
  This program blinks pin 13 of the Arduino (the
  built-in LED)
*/

int ledRed = 13;
int ledYellow = 12;
int ledGreen = 11;
int btnLeft = 4;
int btnRight = 2;
int pir = 3;

int state = 1;
int countBtnLeft = 0;
int countBtnRight = 0;

void setup()
{
    pinMode(ledRed, OUTPUT);
    pinMode(ledYellow, OUTPUT);
    pinMode(ledGreen, OUTPUT);
    pinMode(btnLeft, INPUT);
    pinMode(btnRight, INPUT);
    pinMode(pir, INPUT);
}

void loop()
{
    bool statePir = digitalRead(pir);
    bool stateBtnLeft = digitalRead(btnLeft);
    bool stateBtnRight = digitalRead(btnRight);

    switch (state)
    {
    case 1:
        detectMotion(statePir);
        break;
    case 2:
        waitForInput(stateBtnLeft, stateBtnRight);
        break;
    case 3:
        pressKeySequence();
        break;
    case 4:
        checkState();
        break;
    case 5:
        unlock();
        break;
    }
}

void detectMotion(bool statePir)
{
    digitalWrite(ledRed, HIGH);

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
        digitalWrite(ledYellow, HIGH);
        state = 3;
    }
}

void pressKeySequence()
{
    if (countBtnLeft == 1 && countBtnRight == 1)
    {
        digitalWrite(ledGreen, HIGH);
        state = 4;
    }
}

void checkState()
{
    // Check state and validate sequence
}

void unlock()
{
}