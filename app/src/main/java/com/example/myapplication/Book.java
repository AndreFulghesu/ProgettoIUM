package com.example.myapplication;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String plot;
    private Genres genre;
    private int id;
    private User author;
    private List<Chapter> chapters;

    public Book(String title, String plot, Genres genre, int id, User author){
        setTitle(title);
        setPlot(plot);
        setGenre(genre);
        setId(id);
        setAuthor(author);
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
    public List<Chapter> getChapters() {
        return chapters;
    }
    public Chapter getChapter(int chapId) {
        List<Chapter> chapters = getChapters();
        for (Chapter c: chapters){
            if (c.getChaptNum() == chapId) {
                return c;
            }
        }
        return null;
    }
}
