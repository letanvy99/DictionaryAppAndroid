package com.example.projectgroup.model;

import java.io.Serializable;

public class AnhViet implements Serializable {
    private int id;
    private String word;
    private String content;

    public AnhViet() {
    }

    public AnhViet(int id, String word, String content) {
        this.id = id;
        this.word = word;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
