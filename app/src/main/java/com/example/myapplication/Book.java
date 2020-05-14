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
    private int views;
    private ArrayList<Chapter> chapters;

    public Book(String title, String plot, Genres genre, int id, User author){
        setTitle(title);
        setPlot(plot);
        setGenre(genre);
        setId(id);
        setAuthor(author);
        setViews(0);
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

    public float getAverage (){
        float average=0;
        int contatore=0;
        float total = 0;
        for(Chapter c : chapters){

            total += c.getValutation();
            contatore++;
        }

        average = total/contatore;
        return average;

    }

    /*
    public static Comparator<Book> evaluationComparator = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            return o1.compareTo(o2);
        }
    };

     */



    @Override
    public int compareTo(Book o) {


        if (this.getAverage() > o.getAverage()){
            return -1;
        }else{
            if (this.getAverage() == o.getAverage()){
                return 0;
            }
        }

        return 1;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
    public void incrementViews () {
        this.views++;
    }
}
