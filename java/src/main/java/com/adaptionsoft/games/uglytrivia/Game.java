package com.adaptionsoft.games.uglytrivia;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<String> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	PrintWriter output;

    public Game(){
		this(new PrintWriter(System.out, true));
	}

	public Game(PrintWriter output){
		this.output = output;
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
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    output.println(playerName + " was added");
	    output.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		output.println(players.get(currentPlayer) + " is the current player");
		output.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				output.println(players.get(currentPlayer) + " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				output.println(players.get(currentPlayer)
						+ "'s new location is " 
						+ places[currentPlayer]);
				output.println("The category is " + currentCategory());
				askQuestion();
			} else {
				output.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			output.println(players.get(currentPlayer)
					+ "'s new location is " 
					+ places[currentPlayer]);
			output.println("The category is " + currentCategory());
			askQuestion();
		}
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			output.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			output.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			output.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			output.println(rockQuestions.removeFirst());
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
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				output.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				output.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
		} else {
		
			output.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			output.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer(){
		output.println("Question was incorrectly answered");
		output.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
