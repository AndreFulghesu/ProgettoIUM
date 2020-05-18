package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class BookFactory {

    private static BookFactory singleton;

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Book> booksModified = new ArrayList<>();

    private BookFactory(){}

    public static BookFactory getInstance(){
        if(singleton == null){
            singleton = new BookFactory();
        }
        return singleton;
    }

    public ArrayList<Book> getBooks(){
        Book b1 = new Book("Harry Potter", "Trama generica di Harry Potter", Genres.FANTASY, 1, UserFactory.getInstance().getUserByUsername("Faber123"),0);
        Book b2 = new Book("Agatha Christie", "Trama generica di Agatha Christie", Genres.THRILLER, 2, UserFactory.getInstance().getUserByUsername("Andre97"),0);
        Book b3 = new Book("Fulghenzio e i 4 Gigi mannari", "Trama riguardo Fulgenzio e i 4 Gigi mannari", Genres.HORROR, 3, UserFactory.getInstance().getUserByUsername("Gio34"),0);
        Book b4 = new Book("Fabrizio during his sintesi clorofiliana time", "Beh, effettivamente c'è poco da dire, si capisce da solo",Genres.STORICO,4,UserFactory.getInstance().getUserByUsername("Andre97"),0);


        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);

        for (int i =0; i<booksModified.size();i++){

            if (books.get(i).getTitle().equals(booksModified.get(i).getTitle())) {

                books.remove(i);
                books.add(booksModified.get(i));
            }


        }

        return this.books;
    }
    public Book getBookById (int id) {
        ArrayList<Book> books = this.getBooks();
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public ArrayList<Book> getBookByUser (User user){

        ArrayList<Book> getBooksUser = new ArrayList<>();
        ArrayList<Book> totalLibrery = this.getBooks();
        for(Book b : totalLibrery){
            if (b.getAuthor().getUsername().equals(user.getUsername())){
                getBooksUser.add(b);
            }
        }

        return getBooksUser;

    }

    public float getValutationTotalBookUser (User user) {

        float average =0;
        int contatore = 0;
        ArrayList<Book> getBooksUser = this.getBookByUser(user);

        for (Book b : getBooksUser) {
            average += b.getAverage();
            contatore++;
        }

        return average/contatore;

    }
    public ArrayList<Book> getBooksByGenre(Genres genre) {
        ArrayList<Book> books = this.getBooks();
        ArrayList<Book> booksPerGenre = new ArrayList<>();
        for (Book b : books) {
            if (b.getGenre().equals(genre)) {
                booksPerGenre.add(b);
            }
        }
        return booksPerGenre;
    }

    public ArrayList<Book> sortBooksByEvaluation(ArrayList<Book> books) {
        Collections.sort(books);
        System.out.println("Libri ordinati:");
        for(Book b : books){
            System.out.println("Nome : " +b.getTitle() +" " + "Media: "+ b.getAverage());
        }
        return books;
    }


    public void addViewsBook (Book book) {


        // aggiungo il libro alla lista di quelli modificati
        booksModified.add(book);

        //se il libro passato come argomento trova riscrontro nella lista allora effettua modifica
        for (int i =0 ; i<booksModified.size();i++){
            if (book.getTitle().equals(booksModified.get(i).getTitle())){
                booksModified.get(i).incrementViews();
            }
        }

    }

}
