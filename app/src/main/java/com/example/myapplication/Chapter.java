package com.example.myapplication;

import java.util.ArrayList;

public class Chapter {
    private int bookId;
    private int chaptNum;
    private String text;
    private float valutation = 0;
    private ArrayList<Comment> commenti;


    public Chapter(int bookId, int chaptNum, String text){

        this.commenti = CommentFactory.getInstance().getCommentById(chaptNum,bookId);
        this.setBookId(bookId);
        this.setChaptNum(chaptNum);
        this.setText(text);
        this.setValutation();
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

    public void setValutation (){
        float valutation = 0;
        int contatore = 0;

        for (Comment c : this.commenti){
            valutation = valutation + c.getVote();
            System.out.println("Voto commento: " + c.getVote());
            contatore++;
        }
        valutation =valutation / this.commenti.size();
        this.valutation = valutation;
    }

    public float getValutation (){
        return this.valutation;}

    public void addComment (Comment nuovo) {

        this.commenti.add(nuovo);

    }

    public ArrayList<Comment> getComment (){

        return this.commenti;

    }
}
