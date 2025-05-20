package com.adaptionsoft.games.uglytrivia;

class GettingOutOfPenaltyBoxActions implements Actions {
    @Override
    public void roll(Changer changer, Printer printer, int roll) {
        throw new IllegalStateException("Cannot be 'GettingOutOfPenaltyBoxActions'");
    }

    @Override
    public void wasCorrectlyAnswered(Changer changer, Printer printer) {
        changer.normal();
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
        throw new IllegalStateException("Cannot be 'GettingOutOfPenaltyBoxActions'");
    }
}
