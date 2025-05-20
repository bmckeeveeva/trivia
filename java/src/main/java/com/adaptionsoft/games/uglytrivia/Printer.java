package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;
import java.util.function.Supplier;

class Printer {
    private final PrintWriter output;
    private final Supplier<Player> playerSupplier;
    private final Supplier<String> categorySupplier;

    Printer(PrintWriter output, Supplier<Player> playerSupplier, Supplier<String> categorySupplier) {
        this.output = output;
        this.playerSupplier = playerSupplier;
        this.categorySupplier = categorySupplier;
    }

    void println(String template) {
        Player player = playerSupplier.get();

        output.println(template
                .replace("{player}", player.getName())
                .replace("{location}", String.valueOf(player.getPlace()))
                .replace("{category}", categorySupplier.get())
                .replace("{coins}", String.valueOf(player.getPurse()))
        );
    }
}
