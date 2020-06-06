package com.example.myapplication;

import java.util.ArrayList;

/** Classe di simulazione di un database per il salvataggio dei libri*/
class BookFactory {

    private static BookFactory singleton;

    private ArrayList<Book> books = new ArrayList<>();

    private ArrayList<Book> booksModified = new ArrayList<>();

    private Book b1 = new Book("Harry Potter", "Harry Potter scopre nel giorno del suo 11 compleanno di essere " +
            "il figlio orfano di due potenti maghi e di possedere anch'egli straordinari poteri magici. Nella Scuola di Magia" +
            " e Stregoneria di Hogwarts, Harry impara a volare praticando il Quidditch e gioca un'emozionante partita a scacchi" +
            " viventi prima di affrontare un mago malvagio, determinato a distruggerlo.", Genres.FANTASY, 1, UserFactory.getInstance().getUserByUsername("Faber123"),55);
    private Book b2 = new Book("Agatha Christie", "Trama generica di Agatha Christie", Genres.THRILLER, 2, UserFactory.getInstance().getUserByUsername("Andre97"),0);
    private Book b3 = new Book("La storia della Verità", "La verità è un concetto assai complicato per il nostro tempo. Attraverso un'attenta analisi l'autore si preoccupa di esporre tutte le sue sfaccettature.", Genres.FANTASCIENZA, 3, UserFactory.getInstance().getUserByUsername("Gio34"),4);
    private Book b4 = new Book("Sintesi clorofiliana time", "Beh, effettivamente c'è poco da dire, si capisce da solo, solo un'entusiasmante scalata verso il processo di sintesi clorofilliala della cellula vegetale.",Genres.NATURE,4,UserFactory.getInstance().getUserByUsername("Andre97"),0);
    private Book b5 = new Book("La spada della Congrega","La complessa narrazione del ciclo de La Spada della" +
            " Congrega vede la guida dei boschi Richard Cypher scoprire di essere in realtà un Cercatore: " +
            "l’ultimo mago guerriero della sua epoca, che ha il compito di mantenere la pace ed evitare " +
            "che forze oscure e umani assetati di potere prendano il sopravvento.",Genres.FANTASY,5, UserFactory.getInstance().getUserByUsername("GiaNan66"),10);

    private Book b6 = new Book("Alessandro Magno: Storia di un Impero", "Pella, 356 a.C. Nasce Alessandro, figlio di Filippo II di Macedonia, uno dei più grandi condottieri della storia dell'umanità.", Genres.STORICO, 6, UserFactory.getInstance().getUserByUsername("Faber123"), 0);
    private BookFactory(){}

    /** Generazione del token per l'accesso alla Factory */
    public static BookFactory getInstance(){
        if(singleton == null){
            singleton = new BookFactory();
        }
        return singleton;
    }

    /** Metodi getter per fornire, in caso di richiesta da parte dell'utente,
     *  tutti o una parte degli oggetti salvati nella Factory
     */

    ArrayList<Book> getBooks(){

        books.clear();

        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);
        books.add(b5);
        books.add(b6);

        for (int i =0; i<booksModified.size()-1;i++){
            if (books.get(i).getId() == booksModified.get(i).getId()) {
                books.remove(i);
                books.add(booksModified.get(i));
            }
        }

        return this.books;
    }

    Book getBookById(int id) {
        ArrayList<Book> books = this.getBooks();
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    ArrayList<Book> getBookByUser(User user){

        ArrayList<Book> getBooksUser = new ArrayList<>();
        ArrayList<Book> totalLibrery = this.getBooks();
        for(Book b : totalLibrery){
            if (b.getAuthor().getUsername().equals(user.getUsername())){
                getBooksUser.add(b);
            }
        }
        return getBooksUser;
    }

    float getValutationTotalBookUser(User user) {

        float average =0;
        int contatore = 0;
        ArrayList<Book> getBooksUser = this.getBookByUser(user);

        for (Book b : getBooksUser) {
            average += b.getTotalValutation();
            contatore++;
        }
        return average/contatore;
    }

    ArrayList<Book> getBooksByGenre(Genres genre) {
        ArrayList<Book> books = this.getBooks();
        ArrayList<Book> booksPerGenre = new ArrayList<>();
        for (Book b : books) {
            if (b.getGenre().equals(genre)) {
                booksPerGenre.add(b);
            }
        }
        return booksPerGenre;
    }

    void addViewsBook(Book book) {

        if(booksModified != null) {
            for (int i = 0; i < booksModified.size(); i++) {
                if (booksModified.get(i).getId() == book.getId()) {
                    booksModified.get(i).incrementViews();
                    break;
                }
            }
        }
        assert booksModified != null;
        if (!contiene(book,booksModified)){
            booksModified.add(book);
            booksModified.get(booksModified.size()-1).incrementViews();
        }
    }

    private boolean contiene(Book book, ArrayList<Book> elenco){

        for (int i =0; i<elenco.size();i++){
            if (book.getId() == elenco.get(i).getId()){
                return true;
            }
        }
        return false;
    }
}
