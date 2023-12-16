package com.example.dictionary.core;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dictionary {
    public void initialize() throws SQLException {
    }

    public void close() {
    }

    public abstract ArrayList<Word> getAllWords();

    public abstract ArrayList<String> getWords();

    public abstract String search(final String word);

    public abstract boolean insert(final String word, final String meaning);

    public abstract boolean delete(final String word);

    public abstract boolean update(final String word, final String meaning);

}
