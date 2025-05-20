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

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public void incrementPlace(int increment) {
        this.place = (place + increment) % 12;
    }

    void incrementPurse() {
        purse++;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public boolean playerWon() {
        return purse == 6;
    }

    @Override
    public void roll(Changer changer, Printer printer, int roll) {
        actions.roll(changer, printer, roll);
    }

    @Override
    public void wasCorrectlyAnswered(Changer changer, Printer printer) {
        actions.wasCorrectlyAnswered(changer, printer);
    }

    @Override
    public void wrongAnswer(Changer changer, Printer printer) {
        actions.wrongAnswer(changer, printer);
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
