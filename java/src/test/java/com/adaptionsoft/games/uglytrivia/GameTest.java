package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    StringWriter output = new StringWriter();
    Game game = new Game(new PrintWriter(output));

    StringWriter capturedOutput = new StringWriter();

    @Test
    void addPlayer() {
        game.add("Bob");
        game.add("Bill");
        assertEquals(2, game.howManyPlayers());
        assertEquals(
            "Bob was added\n" +
            "They are player number 1\n" +
            "Bill was added\n" +
            "They are player number 2\n",
            output.toString());
    }

    @Test
    void roll() {
        game.add("Bob");
        captureOutput(() -> game.roll(3));

        assertEquals(
            "Bob is the current player\n" +
            "They have rolled a 3\n" +
            "Bob's new location is 3\n" +
            "The category is Rock\n" +
            "Rock Question 0\n",
            capturedOutput.toString());
    }

    @Test
    void rightAnswer() {
        game.add("Bob");
        game.roll(3);

        boolean notAWinner = captureOutput(() -> game.wasCorrectlyAnswered());

        assertEquals(
    "Answer was correct!!!!\n" +
            "Bob now has 1 Gold Coins.\n",
            capturedOutput.toString());

        assertTrue(notAWinner);
    }

    @Test
    void wrongAnswer() {
        game.add("Bob");
        game.roll(3);

        boolean notAWinner = captureOutput(() -> game.wrongAnswer());

        assertEquals(
    "Question was incorrectly answered\n" +
            "Bob was sent to the penalty box\n",
            capturedOutput.toString());

        assertTrue(notAWinner);
    }

    @Test
    void staysInPenaltyBox() {
        game.add("Bob");
        game.roll(3);
        game.wrongAnswer();

        captureOutput(() -> game.roll(2)); // odd gets out, even stays in

        assertEquals(
    "Bob is the current player\n" +
            "They have rolled a 2\n" +
            "Bob is not getting out of the penalty box\n",
            capturedOutput.toString());
    }

    @Test
    void getsOutOfPenaltyBox() {
        game.add("Bob");
        game.roll(3);
        game.wrongAnswer();

        captureOutput(() -> game.roll(1)); // odd gets out, even stays in

        assertEquals(
    "Bob is the current player\n" +
            "They have rolled a 1\n" +
            "Bob is getting out of the penalty box\n" +
            "Bob's new location is 4\n" +
            "The category is Pop\n" +
            "Pop Question 0\n",
            capturedOutput.toString());
    }

    @Test
    void staysInPenaltyBox_answersCorrectly() {
        game.add("Bob");
        game.roll(3);
        game.wrongAnswer();
        game.roll(2); // odd gets out, even stays in
        boolean notAWinner = captureOutput(() -> game.wasCorrectlyAnswered());

        assertEquals("", capturedOutput.toString());
        assertTrue(notAWinner);
    }

    @Test
    void getsOutOfPenaltyBox_answersCorrectly() {
        game.add("Bob");
        game.roll(3);
        game.wrongAnswer();
        game.roll(1); // odd gets out, even stays in

        boolean notAWinner = captureOutput(() -> game.wasCorrectlyAnswered());

        assertEquals(
    "Answer was correct!!!!\n" +
            "Bob now has 1 Gold Coins.\n",
            capturedOutput.toString());

        assertTrue(notAWinner);
    }

    @Test
    void winner() {
        game.add("Bob");

        for (int i = 0; i < 5; i++) {
            game.roll(1);
            game.wasCorrectlyAnswered();
        }

        game.roll(1);

        boolean notAWinner = captureOutput(() -> game.wasCorrectlyAnswered());

        assertEquals(
    "Answer was correct!!!!\n" +
            "Bob now has 6 Gold Coins.\n",
            capturedOutput.toString());

        assertFalse(notAWinner); // WINNER!
    }

    @Test
    void categories() {
        game.add("Bob");

        for (int i = 0; i < 30; i++) {
            captureOutput(() -> game.roll(1));
            game.wrongAnswer();
        }

        String categories = getTheCategoriesFrom(capturedOutput);

        assertEquals(
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports\n" +
            "Rock\n" +
            "Pop\n" +
            "Science\n" +
            "Sports",
            categories);
    }

    @Test
    void playerRotation_correctAnswer() {
        game.add("Bob");
        game.add("Bill");

        // player 1
        captureOutput(() -> game.roll(1));
        game.wasCorrectlyAnswered();

        // player 2
        captureOutput(() -> game.roll(1));
        game.wasCorrectlyAnswered();

        // player 1
        captureOutput(() -> game.roll(1));
        game.wasCorrectlyAnswered();

        // player 2
        captureOutput(() -> game.roll(1));
        game.wasCorrectlyAnswered();

        String players = getPlayersFrom(capturedOutput);

        assertEquals(
            "Bob\n" +
            "Bill\n" +
            "Bob\n" +
            "Bill",
            players);
    }

    @Test
    void playerRotation_correctAnswerAfterPenaltyBoxRelease() {
        game.add("Bob");
        game.add("Bill");

        // player 1
        captureOutput(() -> game.roll(1));
        game.wrongAnswer();

        // player 2
        captureOutput(() -> game.roll(1));
        game.wrongAnswer();

        // player 1
        captureOutput(() -> game.roll(1)); // odd gets out, even stays in
        game.wasCorrectlyAnswered();

        // player 2
        captureOutput(() -> game.roll(1)); // odd gets out, even stays in
        game.wasCorrectlyAnswered();

        String players = getPlayersFrom(capturedOutput);

        assertEquals(
    "Bob\n" +
            "Bill\n" +
            "Bob\n" +
            "Bill",
            players);
    }

    @Test
    void playerRotation_correctAnswerStayInPenaltyBox() {
        game.add("Bob");
        game.add("Bill");

        // player 1
        captureOutput(() -> game.roll(1));
        game.wrongAnswer();

        // player 2
        captureOutput(() -> game.roll(1));
        game.wrongAnswer();

        // player 1
        captureOutput(() -> game.roll(2)); // odd gets out, even stays in
        game.wasCorrectlyAnswered();

        // player 2
        captureOutput(() -> game.roll(2)); // odd gets out, even stays in
        game.wasCorrectlyAnswered();

        String players = getPlayersFrom(capturedOutput);

        assertEquals(
    "Bob\n" +
            "Bill\n" +
            "Bob\n" +
            "Bill",
            players);
    }

    @Test
    void walkBoard() {
        game.add("Bob");

        for (int i = 0; i < 4; i++) {
            captureOutput(() -> game.roll(5));
            game.wasCorrectlyAnswered();
        }

        String locations = getLocationsFrom(capturedOutput);

        assertEquals("5\n10\n3\n8", locations);
    }

    private String getTheCategoriesFrom(StringWriter input) {
        return input.toString().lines()
                .filter(line -> line.startsWith("The category is "))
                .map(line -> line.substring(16))
                .collect(Collectors.joining("\n"));
    }

    private String getPlayersFrom(StringWriter input) {
        return input.toString().lines()
                .filter(line -> line.endsWith(" is the current player"))
                .map(line -> line.substring(0, line.length() - 22))
                .collect(Collectors.joining("\n"));
    }

    private String getLocationsFrom(StringWriter input) {
        return input.toString().lines()
                .filter(line -> line.contains("new location is"))
                .map(line -> line.substring(line.length() - 2).trim())
                .collect(Collectors.joining("\n"));
    }

    void captureOutput(Runnable runnable) {
        int start = output.getBuffer().length();
        runnable.run();
        capturedOutput.append(output.toString().substring(start));
    }

    <V> V captureOutput(Callable<V> callable) {
        int start = output.getBuffer().length();
        V value;

        try {
            value = callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        capturedOutput.append(output.toString().substring(start));

        return value;
    }
}