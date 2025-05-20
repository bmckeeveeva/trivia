package com.adaptionsoft.games.uglytrivia;

class NormalActions implements Actions {
    @Override
    public void roll(Changer changer, Printer printer, int roll) {

    }

    @Override
    public void wasCorrectlyAnswered(Changer changer, Printer printer) {

    }

    @Override
    public void wrongAnswer(Changer changer, Printer printer) {
        changer.inPenaltyBox();
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
