package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    StringWriter stringWriter = new StringWriter();
    PrintWriter output = new PrintWriter(stringWriter);
    Game game = new Game(output);

    @Test
    void addPlayer() {
        game.add("Bob");
        game.add("Bill");
        assertEquals(2, game.howManyPlayers());
    }
}