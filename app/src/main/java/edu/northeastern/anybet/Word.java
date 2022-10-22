package edu.northeastern.anybet;

import java.util.List;

public class Word {
    String word;
    String phonetic;
    List<String> definitions;

    public Word(String word, String phonetic, List<String> definitions) {
        this.word = word;
        this.phonetic = phonetic;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public List<String> getDefinitions() {
        return definitions;
    }
}
