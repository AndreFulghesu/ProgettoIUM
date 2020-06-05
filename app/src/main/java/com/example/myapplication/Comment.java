package com.example.myapplication;

public class Comment {

    /** Definizione attributi della classe */
    private User author_comment;
    private String text;
    private int chapterId;
    private int bookId;
    private int vote;
    private boolean like;

    /**Definizione costruttore*/
    Comment(String text, int vote, int chapterId, int bookId, User author_comment, boolean like) {
        setText(text);
        setChapterId(chapterId);
        setBookId(bookId);
        setUserAuthor(author_comment);
        setVote(vote);
        setLike(like);
    }

    /** Definizione metodi getter e setter dei vari attributi*/
    private void setLike(boolean b) { this.like = b;}

    public boolean getLike () { return this.like; }

    public void setText(String text){
        this.text = text;
    }

    public String getText (){
        return this.text;
    }

    private void setChapterId(int chapterId){
        this.chapterId = chapterId;
    }

    int getChapterId(){
        return this.chapterId;
    }

    public void setBookId (int bookId){
        this.bookId = bookId;
    }

    public int getBookId (){
        return this.bookId;
    }

    private void setUserAuthor(User author_comment){
        this.author_comment = author_comment;
    }

    User getUserAuthor(){
        return this.author_comment;
    }

    int getVote() {
        return vote;
    }

    private void setVote(int vote) { this.vote = vote;}

    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (o == null) return false;
        if(!(o instanceof Comment)) return false;

        Comment other = (Comment)o;
        return this.getText().equals(other.getText()) && this.getUserAuthor().getUsername().equals(other.getUserAuthor().getUsername()) && this.getBookId() == other.getBookId();
    }
}
