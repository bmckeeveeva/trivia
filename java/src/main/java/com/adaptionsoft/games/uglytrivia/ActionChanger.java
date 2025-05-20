package com.adaptionsoft.games.uglytrivia;

import java.util.function.Supplier;

class ActionChanger {
    static final Actions NORMAL = new NormalActions();
    static final Actions IN_PENALTY_BOX = new InPenaltyBoxActions();
    static final Actions GETTING_OUT_OF_PENALTY_BOX = new GettingOutOfPenaltyBoxActions();

    private final Supplier<Player> playerSupplier;

    ActionChanger(Supplier<Player> playerSupplier) {
        this.playerSupplier = playerSupplier;
    }

    void normal() {
        playerSupplier.get().setActions(NORMAL);
    }

    void inPenaltyBox() {
        playerSupplier.get().setActions(IN_PENALTY_BOX);
    }

    void gettingOutOfPenaltyBox() {
        playerSupplier.get().setActions(GETTING_OUT_OF_PENALTY_BOX);
    }
}
