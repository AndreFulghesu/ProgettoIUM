package com.example.myapplication;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Book implements Comparable<Book>{
    private String title;
    private String plot;
    private Genres genre;
    private int id;
    private User author;
    private int  views = 0;
    private ArrayList<Chapter> chapters;

    public Book(String title, String plot, Genres genre, int id, User author, int views){
        setTitle(title);
        setPlot(plot);
        setGenre(genre);
        setId(id);
        setAuthor(author);
        setViews(views);
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

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public Chapter getChapter(int chapId) {
        ArrayList<Chapter> chapters = getChapters();
        for (Chapter c: chapters){
            if (c.getChaptNum() == chapId) {
                return c;
            }
        }
        return null;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void incrementViews () {
        this.views++;
    }

    public float getTotalValutation (){
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
