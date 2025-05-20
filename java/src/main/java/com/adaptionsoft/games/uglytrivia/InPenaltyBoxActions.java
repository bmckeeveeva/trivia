package com.adaptionsoft.games.uglytrivia;

class InPenaltyBoxActions implements Actions {
    @Override
    public void roll(ActionChanger actionChanger, Printer printer, int roll) {
        if (roll % 2 != 0) {
            printer.println("{player} is getting out of the penalty box");
            actionChanger.gettingOutOfPenaltyBox();
        } else {
            printer.println("{player} is not getting out of the penalty box");
        }
    }

    @Override
    public void wasCorrectlyAnswered(ActionChanger actionChanger, Printer printer) {

    }

    @Override
    public void wrongAnswer(ActionChanger actionChanger, Printer printer) {

    }

    @Override
    public boolean shouldAskQuestion() {
        return false;
    }

    @Override
    public boolean shouldIncrementPlace() {
        return false;
    }

    @Override
    public boolean shouldIncrementPurse() {
        return false;
    }
}
