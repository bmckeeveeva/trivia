package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class QuestionBank {
    private final Map<String, Integer> categories;
    private final Map<Integer, String> categoriesIndex;
    private final Map<Integer, Deque<String>> questionsIndex;

    public QuestionBank() {
        this.categories = new HashMap<>();
        this.categoriesIndex = new HashMap<>();
        this.questionsIndex = new HashMap<>();
    }

    public void addQuestion(String category, String question) {
        if (!categories.containsKey(category)) {
            int categoryIndex = categories.size();
            categories.put(category, categoryIndex);
            categoriesIndex.put(categoryIndex, category);
            questionsIndex.put(categoryIndex, new ArrayDeque<>());
        }

        questionsIndex.get(categories.get(category)).add(question);
    }

    String getCategory(int place) {
        return categoriesIndex.get(place % categories.size());
    }

    String getQuestion(int place) {
        return questionsIndex.get(place % categories.size()).pop();
    }

    public static QuestionBank sample() {
        QuestionBank questionBank = new QuestionBank();

        for (int i = 0; i < 50; i++) {
            questionBank.addQuestion("Pop", "Pop Question " + i);
            questionBank.addQuestion("Science", "Science Question " + i);
            questionBank.addQuestion("Sports", "Sports Question " + i);
            questionBank.addQuestion("Rock", "Rock Question " + i);
        }

        return questionBank;
    }
}
