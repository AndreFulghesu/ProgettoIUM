package com.example.myapplication;

public class Chapter {
    private int bookId;
    private int chaptNum;
    private String text;

    public Chapter(int bookId, int chaptNum, String text){
        this.setBookId(bookId);
        this.setChaptNum(chaptNum);
        this.setText(text);
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
}
