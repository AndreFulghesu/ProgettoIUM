package com.example.myapplication;

public class Comment {

    private User author_comment;
    private String text;
    private int chapterId;
    private int bookId;
    private int vote;


    public Comment(String text, int vote, int chapterId, int bookId, User author_comment) {
        setText(text);
        setChapterId(chapterId);
        setBookId(bookId);
        setUserAuthor(author_comment);
        setVote(vote);
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText (){
        return this.text;
    }

    public void setChapterId (int chapterId){
        this.chapterId = chapterId;
    }

    public int getChapterId (){
        return this.chapterId;
    }

    public void setBookId (int bookId){
        this.bookId = bookId;
    }

    public int getBookId (){
        return this.bookId;
    }

    public void setUserAuthor (User author_comment){
        this.author_comment = author_comment;
    }

    public User getUserAuthor (){
        return this.author_comment;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
