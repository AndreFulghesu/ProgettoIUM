package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

class BookFactory {

    private static BookFactory singleton;

    private ArrayList<Book> books = new ArrayList<>();

    private BookFactory(){}

    public static BookFactory getInstance(){
        if(singleton == null){
            singleton = new BookFactory();
        }
        return singleton;
    }

    public ArrayList<Book> getBooks(){
        Book b1 = new Book("Harry Potter", "Trama generica di Harry Potter", Genres.FANTASY, 1, UserFactory.getInstance().getUserByUsername("Faber123"));
        Book b2 = new Book("Agatha Christie", "Trama generica di Agatha Christie", Genres.THRILLER, 2, UserFactory.getInstance().getUserByUsername("Andre97"));
        Book b3 = new Book("Fulghenzio e i 4 Gigi mannari", "Trama riguardo Fulgenzio e i 4 Gigi mannari", Genres.HORROR, 3, UserFactory.getInstance().getUserByUsername("Gio34"));
        Book b4 = new Book("Fabrizio during his sintesi clorofiliana time", "Beh, effettivamente c'è poco da dire, si capisce da solo",Genres.STORICO,4,UserFactory.getInstance().getUserByUsername("Andre97"));


        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);

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


}
