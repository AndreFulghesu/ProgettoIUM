package com.example.myapplication;

public class Chapter {
    private int bookId;
    private int chaptNum;
    private String text;
    private float valutation;


    public Chapter(int bookId, int chaptNum, String text, float valutation){
        this.setBookId(bookId);
        this.setChaptNum(chaptNum);
        this.setText(text);
        this.setValutation(valutation);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getChaptNum() {
        return chaptNum;
    }

    public void setChaptNum(int chaptNum) {
        this.chaptNum = chaptNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValutation (float valutation){ this.valutation = valutation; }

    public float getValutation (){ return this.valutation;}
}
