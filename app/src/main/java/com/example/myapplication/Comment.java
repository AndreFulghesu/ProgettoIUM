package com.example.myapplication;

public class Comment {

    private User author_comment;
    private String text;
    private int chapterId;
    private int bookId;


    public Comment(String text, int chapterId, int bookId, User author_comment) {
        setText(text);
        setCahpterId(chapterId);
        setBookId(bookId);
        setUserAuthor(author_comment);
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText (){
        return this.text;
    }

    public void setCahpterId (int chapterId){
        this.chapterId = chapterId;
    }

    public int getCahpterId (){
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


}
