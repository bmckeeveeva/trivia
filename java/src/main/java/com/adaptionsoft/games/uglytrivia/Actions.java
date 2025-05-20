package com.adaptionsoft.games.uglytrivia;

interface Actions {
    void roll(ActionChanger actionChanger, Printer printer, int roll);
    void wasCorrectlyAnswered(ActionChanger actionChanger, Printer printer);
    void wrongAnswer(ActionChanger actionChanger, Printer printer);

    boolean shouldAskQuestion();
    boolean shouldIncrementPlace();
    boolean shouldIncrementPurse();
}
