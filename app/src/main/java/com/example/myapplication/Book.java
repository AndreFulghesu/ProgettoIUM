package com.example.myapplication;

import java.util.ArrayList;

public class Book implements Comparable<Book>{

    /** Definizione attributi della classe */
    private String title;
    private String plot;
    private Genres genre;
    private int id;
    private User author;
    private int  views = 0;
    private ArrayList<Chapter> chapters;

    /**Definizione costruttore*/
    public Book(String title, String plot, Genres genre, int id, User author, int views){
        setTitle(title);
        setPlot(plot);
        setGenre(genre);
        setId(id);
        setAuthor(author);
        setViews(views);
        chapters = ChapterFactory.getInstance().getChaptersByBookId(id);
    }

    /** Definizione metodi getter e setter dei vari attributi*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getPlot() {
        return plot;
    }

    private void setPlot(String plot) {
        this.plot = plot;
    }

    Genres getGenre() {
        return genre;
    }

    private void setGenre(Genres genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    User getAuthor() {
        return this.author;
    }

    private void setAuthor(User author) {
        this.author = author;
    }

    private ArrayList<Chapter> getChapters() {
        return chapters;
    }

    Chapter getChapter(int chapId) {
        ArrayList<Chapter> chapters = getChapters();
        for (Chapter c: chapters){
            if (c.getChaptNum() == chapId) {
                return c;
            }
        }
        return null;
    }

    int getViews() {
        return this.views;
    }

    private void setViews(int views) {
        this.views = views;
    }

    void incrementViews() {
        this.views++;
    }

    float getTotalValutation(){
        float totalValutation=0;
        int counter=0;
        for (Chapter c : this.chapters){
            totalValutation = totalValutation + c.getValutation();
            counter++;
        }

        return totalValutation/counter;
    }

    @Override
    public int compareTo(Book o) {

        if (this.getTotalValutation() > o.getTotalValutation()){
            return -1;
        }else{
            if (this.getTotalValutation() == o.getTotalValutation()){
                return 0;
            }
        }
        return 1;
    }

}
