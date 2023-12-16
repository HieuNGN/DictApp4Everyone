package com.example.dictionary.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    private static final ArrayList<String> searchedWords = new ArrayList<>();
    private static final Node root = new Node();

    public static ArrayList<String> getSearchedWords() {
        return searchedWords;
    }

    /**
     * Insert a word into the trie\
     *
     * @param word the word to be inserted
     */
    public static void insert(String word) {
        // current node
        Node current = root;
        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            // if the character is not in the trie, add it
            if (current.children.get(ch) == null) {
                current.children.put(ch, new Node());
            }
            // move to the next node
            current = current.children.get(ch);
        }
        // mark the current node as the end of the word
        current.isEndOfWord = true;
    }

    /**
     * Search for every word that begins with prefix and add to list
     */
    private static void dfs(Node current, String prefix) {
        // if the current node is the end of the word, add it to the list
        if (current.isEndOfWord) {
            searchedWords.add(prefix);
        }
        // for each character in the trie, recursively call dfs
        for (char ch : current.children.keySet()) {
            if (current.children.get(ch) != null) {
                dfs(current.children.get(ch), prefix + ch);
            }

        }
    }

    /**
     * Search for a word in the trie
     *
     * @param prefix the prefix to be searched
     * @return a list of words that start with the prefix
     */
    public static ArrayList<String> search(String prefix) {
        // if the prefix is null, return an empty list
        if (prefix.isEmpty()) {
            return new ArrayList<>();
        }
        searchedWords.clear();
        Node current = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char ch = prefix.charAt(i);
            // if the character is not in the trie, return an empty list
            if (current.children.get(ch) == null) {
                return getSearchedWords();
            }
            current = current.children.get(ch);
        }

        // Add every word with beginning with `prefix` to the list
        dfs(current, prefix);
        return getSearchedWords();
    }

    /**
     * Delete a word from the trie
     *
     * @param word the word to be deleted
     */
    public static void delete(String word) {
        Node current = root;

        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            // if the character is not in the trie, return
            if (current.children.get(ch) == null) {
                System.out.println("Word not found");
                return;
            }
        }
        if (!current.isEndOfWord) {
            System.out.println("Word not found");
        }
        current.isEndOfWord = false;
    }

    /**
     * A node in the trie
     */
    public static class Node {
        Map<Character, Node> children = new TreeMap<>();
        boolean isEndOfWord;

        public Node() {
            isEndOfWord = false;
        }
    }
}
