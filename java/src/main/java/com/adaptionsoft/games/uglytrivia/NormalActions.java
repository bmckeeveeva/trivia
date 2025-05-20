package com.adaptionsoft.games.uglytrivia;

class NormalActions implements Actions {
    @Override
    public void roll(ActionChanger actionChanger, Printer printer, int roll) {

    }

    @Override
    public void wasCorrectlyAnswered(ActionChanger actionChanger, Printer printer) {

    }

    @Override
    public void wrongAnswer(ActionChanger actionChanger, Printer printer) {
        actionChanger.inPenaltyBox();
    }

    @Override
    public boolean shouldAskQuestion() {
        return true;
    }

    @Override
    public boolean shouldIncrementPlace() {
        return true;
    }

    @Override
    public boolean shouldIncrementPurse() {
        return true;
    }
}
