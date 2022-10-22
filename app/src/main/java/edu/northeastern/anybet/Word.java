package edu.northeastern.anybet;

import java.util.List;

public class Word {
    String word;
    String phonetic;
    String partOfSpeech;
    List<Definition> definitions;

    public Word(String word, String phonetic, String partOfSpeech, List<Definition> definitions) {
        this.word = word;
        this.phonetic = phonetic;
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }
}
