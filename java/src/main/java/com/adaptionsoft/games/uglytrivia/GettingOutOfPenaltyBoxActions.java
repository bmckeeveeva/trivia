package com.adaptionsoft.games.uglytrivia;

class GettingOutOfPenaltyBoxActions implements Actions {
    @Override
    public void roll(ActionChanger actionChanger, Printer printer, int roll) {
        throw new IllegalStateException("Cannot be 'GettingOutOfPenaltyBoxActions'");
    }

    @Override
    public void wasCorrectlyAnswered(ActionChanger actionChanger, Printer printer) {
        actionChanger.normal();
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
        throw new IllegalStateException("Cannot be 'GettingOutOfPenaltyBoxActions'");
    }
}
