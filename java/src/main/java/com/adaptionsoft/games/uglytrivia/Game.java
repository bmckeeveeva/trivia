package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<String> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses  = new int[6];
    
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;

	private final Printer printer;
	private final Actions[] playerActions = new Actions[6];
	private final Changer changer;


    public Game(){
		this(new PrintWriter(System.out, true));
	}

	public Game(PrintWriter output){
		this.printer = new Printer(output);
		this.changer = new Changer();
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
	    players.add(playerName);
	    places[howManyPlayers() - 1] = 0;
	    purses[howManyPlayers() - 1] = 0;

		playerActions[howManyPlayers() - 1] = Changer.NORMAL;
	    
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

		playerActions().roll(changer, printer, roll);

		if (playerActions().shouldIncrementPlace()) {
			places[currentPlayer] = (places[currentPlayer] + roll) % 12;
		}

		if (playerActions().shouldAskQuestion()) {
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
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		playerActions().wasCorrectlyAnswered(changer, printer);

		if (playerActions().shouldIncrementPurse()) {
			purses[currentPlayer]++;
			printer.println("Answer was correct!!!!");
			printer.println("{player} now has {coins} Gold Coins.");
		}

		nextPlayer();
		return didPlayerWin();
	}

	public boolean wrongAnswer() {
		printer.println("Question was incorrectly answered");
		printer.println("{player} was sent to the penalty box");

		playerActions().wrongAnswer(changer, printer);

		nextPlayer();
		return didPlayerWin();
	}

	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	private Actions playerActions() {
		return playerActions[currentPlayer];
	}

	private void changeTo(Actions actions) {
		playerActions[currentPlayer] = actions;
	}

	class Changer implements Actions.Changer {
		private static final Actions NORMAL = new NormalActions();
		private static final Actions IN_PENALTY_BOX = new InPenaltyBoxActions();
		private static final Actions GETTING_OUT_OF_PENALTY_BOX = new GettingOutOfPenaltyBoxActions();

		@Override
		public void normal() {
			changeTo(NORMAL);
		}

		@Override
		public void inPenaltyBox() {
			changeTo(IN_PENALTY_BOX);
		}

		@Override
		public void gettingOutOfPenaltyBox() {
			changeTo(GETTING_OUT_OF_PENALTY_BOX);
		}
	}

	class Printer implements Actions.Printer {
		private final PrintWriter output;

		Printer(PrintWriter output) {
			this.output = output;
		}

		@Override
		public void println(String template) {
			output.println(template
					.replace("{player}", players.get(currentPlayer))
					.replace("{location}", String.valueOf(places[currentPlayer]))
					.replace("{category}", currentCategory())
					.replace("{coins}", String.valueOf(purses[currentPlayer]))
			);
		}
	}
}
