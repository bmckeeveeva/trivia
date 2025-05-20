package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;

	private final List<Player> players;
	private final Printer printer;
	private final ActionChanger actionChanger;

    public Game() {
		this(new PrintWriter(System.out, true));
	}

	public Game(PrintWriter output) {
		this.players = new ArrayList<>();
		this.printer = new Printer(output, this::currentPlayer, this::currentCategory);
		this.actionChanger = new ActionChanger(this::currentPlayer);
		initializeQuestions();
	}

	private void initializeQuestions() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast("Science Question " + i);
			sportsQuestions.addLast("Sports Question " + i);
			rockQuestions.addLast("Rock Question " + i);
		}
	}

	public boolean add(String playerName) {
		players.add(new Player(playerName, ActionChanger.NORMAL));

	    printer.println(playerName + " was added");
	    printer.println("They are player number " + players.size());
		return true;
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
			askQuestion();
		}
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			printer.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			printer.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			printer.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			printer.println(rockQuestions.removeFirst());
	}
	
	
	private String currentCategory() {
		if (currentPlayer().getPlace() == 0) return "Pop";
		if (currentPlayer().getPlace() == 4) return "Pop";
		if (currentPlayer().getPlace() == 8) return "Pop";
		if (currentPlayer().getPlace() == 1) return "Science";
		if (currentPlayer().getPlace() == 5) return "Science";
		if (currentPlayer().getPlace() == 9) return "Science";
		if (currentPlayer().getPlace() == 2) return "Sports";
		if (currentPlayer().getPlace() == 6) return "Sports";
		if (currentPlayer().getPlace() == 10) return "Sports";
		return "Rock";
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

}
