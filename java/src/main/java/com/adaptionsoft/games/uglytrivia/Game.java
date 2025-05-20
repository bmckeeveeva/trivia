package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Game {
    int currentPlayer = 0;

	private final List<Player> players;
	private final ActionChanger actionChanger;
	private final QuestionBank questionBank;
	private final Printer printer;

    public Game(QuestionBank questionBank) {
		this(new PrintWriter(System.out, true), questionBank);
	}

	public Game(PrintWriter output, QuestionBank questionBank) {
		this.players = new ArrayList<>();
		this.actionChanger = new ActionChanger(this::currentPlayer);
		this.questionBank = questionBank;
		this.printer = new Printer(output, this::currentPlayer, this::currentCategory);
	}

	public void add(String playerName) {
		players.add(new Player(playerName, ActionChanger.NORMAL));
	    printer.println(playerName + " was added");
	    printer.println("They are player number " + players.size());
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		printer.println("{player} is the current player");
		printer.println("They have rolled a " + roll);

		Player player = currentPlayer();

		player.roll(actionChanger, printer, roll);

		if (player.shouldIncrementPlace()) {
			player.incrementPlace(roll);
		}

		if (player.shouldAskQuestion()) {
			printer.println("{player}'s new location is {location}");
			printer.println("The category is {category}");
			printer.println(questionBank.getQuestion(player.getPlace()));
		}
	}

	public boolean wasCorrectlyAnswered() {
		Player player = currentPlayer();

		player.wasCorrectlyAnswered(actionChanger, printer);

		if (player.shouldIncrementPurse()) {
			player.incrementPurse();
			printer.println("Answer was correct!!!!");
			printer.println("{player} now has {coins} Gold Coins.");
		}

		nextPlayer();
		return !player.playerWon();
	}

	public boolean wrongAnswer() {
		Player player = currentPlayer();

		printer.println("Question was incorrectly answered");
		printer.println("{player} was sent to the penalty box");

		player.wrongAnswer(actionChanger, printer);

		nextPlayer();
		return !player.playerWon();
	}

	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	private Player currentPlayer() {
		return players.get(currentPlayer);
	}

	private String currentCategory() {
		return questionBank.getCategory(currentPlayer().getPlace());
	}
}
