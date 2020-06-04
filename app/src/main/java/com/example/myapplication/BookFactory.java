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

    Book b1 = new Book("Harry Potter", "Trama generica di Harry Potter", Genres.FANTASY, 1, UserFactory.getInstance().getUserByUsername("Faber123"),0);
    Book b2 = new Book("Agatha Christie", "Trama generica di Agatha Christie", Genres.THRILLER, 2, UserFactory.getInstance().getUserByUsername("Andre97"),0);
    Book b3 = new Book("La storia della Verità", "La verità è un concetto assai complicato per il nostro tempo. Attraverso un'attenta analisi l'autore si preoccupa di esporre tutte le sue sfaccettature.", Genres.FANTASCIENZA, 3, UserFactory.getInstance().getUserByUsername("Gio34"),4);
    Book b4 = new Book("Sintesi clorofiliana time", "Beh, effettivamente c'è poco da dire, si capisce da solo, solo un'entusiasmante scalata verso il processo di sintesi clorofilliala della cellula vegetale.",Genres.NATURE,4,UserFactory.getInstance().getUserByUsername("Andre97"),0);
    Book b5 = new Book("La spada della Congrega","La complessa narrazione del ciclo de La Spada della" +
            " Congrega vede la guida dei boschi Richard Cypher scoprire di essere in realtà un Cercatore: " +
            "l’ultimo mago guerriero della sua epoca, che ha il compito di mantenere la pace ed evitare " +
            "che forze oscure e umani assetati di potere prendano il sopravvento.",Genres.FANTASY,5, UserFactory.getInstance().getUserByUsername("GiaNan66"),10);

    private BookFactory(){}

    public static BookFactory getInstance(){
        if(singleton == null){
            singleton = new BookFactory();
        }
        return singleton;
    }

    public ArrayList<Book> getBooks(){

        books.clear();

        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);
        books.add(b5);

        for (int i =0; i<booksModified.size()-1;i++){
            if (books.get(i).getId() == booksModified.get(i).getId()) {
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
            average += b.getTotalValutation();
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
        return books;
    }


    public void addViewsBook (Book book) {


        if(booksModified != null) {
            for (int i = 0; i < booksModified.size(); i++) {
                if (booksModified.get(i).getId() == book.getId()) {
                    booksModified.get(i).incrementViews();
                    break;
                }
            }
        }

        if (!contiene(book,booksModified)){
            booksModified.add(book);
            booksModified.get(booksModified.size()-1).incrementViews();
        }


        for(Book b : booksModified){
            System.out.println("Nome: "+b.getTitle()+ " Numero views libro : "+b.getViews());
        }


    }

    public boolean contiene (Book book, ArrayList<Book> elenco){

        for (int i =0; i<elenco.size();i++){
            if (book.getId() == elenco.get(i).getId()){
                return true;
            }
        }

        return false;

    }

}
