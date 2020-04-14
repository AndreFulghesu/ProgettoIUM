package com.example.myapplication;
import java.util.List;

public class Book {
    private String title;
    private String plot;
    private Genres genre;
    private int id;
    private User author;
    private int imgId;
    private List<Chapter> chapters;

    public Book(String title, String plot, Genres genre, int id, User author){
        setTitle(title);
        setPlot(plot);
        setGenre(genre);
        setId(id);
        setAuthor(author);
        setImgId(genre);
        chapters = ChapterFactory.getInstance().getChaptersByBookId(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(Genres genre) {
        switch(genre) {
            case FANTASY:
                this.imgId= R.drawable.dragon;
            case STORICO:
                this.imgId= R.drawable.museum;
            case THRILLER:
                this.imgId= R.drawable.knife;
            case POLIZIESCO:
                this.imgId= R.drawable.policeman;
            case FANTASCIENZA:
                this.imgId= R.drawable.chatbot;
        }
    }

}
