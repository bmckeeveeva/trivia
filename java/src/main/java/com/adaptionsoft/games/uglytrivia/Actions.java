package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;

interface Actions {
    void roll(Changer changer, Printer printer, int roll);
    void wasCorrectlyAnswered(Changer changer, Printer printer);
    void wrongAnswer(Changer changer, Printer printer);

    boolean shouldAskQuestion();
    boolean shouldIncrementPlace();
    boolean shouldIncrementPurse();

    interface Changer {
        void normal();
        void inPenaltyBox();
        void gettingOutOfPenaltyBox();
    }

    interface Printer {
        void println(String template);
    }
}
