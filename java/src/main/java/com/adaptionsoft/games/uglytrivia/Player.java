package com.adaptionsoft.games.uglytrivia;

class Player implements Actions {
    private final String name;
    private Actions actions;
    private int place;
    private int purse;

    public Player(String name, Actions actions) {
        this.name = name;
        this.actions = actions;
        this.place = 0;
        this.purse = 0;
    }

    void setActions(Actions actions) {
        this.actions = actions;
    }

    void incrementPlace(int increment) {
        this.place = (place + increment) % 12;
    }

    void incrementPurse() {
        purse++;
    }

    String getName() {
        return name;
    }

    int getPlace() {
        return place;
    }

    int getPurse() {
        return purse;
    }

    boolean playerWon() {
        return purse == 6;
    }

    @Override
    public void roll(ActionChanger actionChanger, Printer printer, int roll) {
        actions.roll(actionChanger, printer, roll);
    }

    @Override
    public void wasCorrectlyAnswered(ActionChanger actionChanger, Printer printer) {
        actions.wasCorrectlyAnswered(actionChanger, printer);
    }

    @Override
    public void wrongAnswer(ActionChanger actionChanger, Printer printer) {
        actions.wrongAnswer(actionChanger, printer);
    }

    @Override
    public boolean shouldAskQuestion() {
        return actions.shouldAskQuestion();
    }

    @Override
    public boolean shouldIncrementPlace() {
        return actions.shouldIncrementPlace();
    }

    @Override
    public boolean shouldIncrementPurse() {
        return actions.shouldIncrementPurse();
    }
}
