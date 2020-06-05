package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

/** Classe di simulazione di un database per il salvataggio dei commenti dei vari capitoli*/
public class CommentFactory {

    private static CommentFactory singleton;

    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Comment> newComments = new ArrayList<>();
    private ArrayList<Comment> toDelete = new ArrayList<>();

    /**commenti per il libro 1*/
    Comment com1 = new Comment ("Questo libro e' molto bello", 5, 1,1,UserFactory.getInstance().getUserByUsername("Faber123"),false);
    Comment com2 = new Comment ("A me invece e' piaciuto poco", 3, 1,1,UserFactory.getInstance().getUserByUsername("Andre97"),false);
    Comment com3 = new Comment ("Veramente entusiasmante", 4, 2,1,UserFactory.getInstance().getUserByUsername("Faber123"),false);
    Comment com4 = new Comment ("Circa...",5, 2,1,UserFactory.getInstance().getUserByUsername("Andre97"),false);

    /**commenti per il libro 2*/
    Comment com5 = new Comment ("Non male come scrittura", 4, 1,2,UserFactory.getInstance().getUserByUsername("Gio34"),false);
    Comment com6 = new Comment ("Poteva essere scritto meglio", 2, 1,2,UserFactory.getInstance().getUserByUsername("Faber123"),false);
    Comment com7 = new Comment ("Mmmmm non mi convince", 1, 2,2,UserFactory.getInstance().getUserByUsername("Gio34"),false);
    Comment com8 = new Comment ("Circa...",3, 2,2,UserFactory.getInstance().getUserByUsername("Andre97"),false);

    /**Commenti per il libro 3*/
    Comment com9 = new Comment ("Mmmmm non mi convince", 1, 1,3,UserFactory.getInstance().getUserByUsername("Gio34"),false);
    Comment com10 = new Comment ("Circa...",3, 2,3,UserFactory.getInstance().getUserByUsername("Andre97"),false);

    /**Commenti per il libro 4*/
    Comment com11 = new Comment ("Mmmmm non mi convince", 1, 1,4,UserFactory.getInstance().getUserByUsername("Gio34"),false);
    Comment com12 = new Comment ("Circa...",3, 2,4,UserFactory.getInstance().getUserByUsername("Andre97"),false);


    private CommentFactory(){
    }

    /** Generazione del token per l'accesso alla Factory */
    public static CommentFactory getInstance(){
        if(singleton == null){
            singleton = new CommentFactory();
        }
        return singleton;
    }

    /** Metodi getter per fornire, in caso di richiesta da parte dell'utente,
     *  tutti o una parte degli oggetti salvati nella Factory
     */
    public ArrayList<Comment> getComments () {

        comments.clear();

        comments.add(com1);
        comments.add(com2);
        comments.add(com3);
        comments.add(com4);
        comments.add(com5);
        comments.add(com6);
        comments.add(com7);
        comments.add(com8);
        comments.add(com9);
        comments.add(com10);
        comments.add(com11);
        comments.add(com12);

        if (!newComments.isEmpty()) {
            for (int i = 0; i < newComments.size(); i++) {
                comments.add(newComments.get(i));
            }
        }
        deleteComments();

        return this.comments;
    }

    public ArrayList<Comment> getCommentById (int idChapter, int idBook){

        ArrayList<Comment> commentsChapter = new ArrayList<>();
        ArrayList<Comment> comments = this.getComments();
        deleteComments();
        for (Comment c : comments) {
            if (c.getChapterId()==idChapter && c.getBookId()==idBook) {
                commentsChapter.add(c);
            }
        }
        return commentsChapter;
    }
    void addComment (Comment c) {

        this.newComments.add(c);
    }

    /** Gestione eliminazione commeneto*/
    public void toDelete (Comment c){
        this.toDelete.add(c);
    }

    public void deleteComments (){
        if (!this.toDelete.isEmpty()) {
            for (int i = 0; i < this.toDelete.size(); i++) {

                for (int j=0;j<this.comments.size();j++) {
                    if (this.toDelete.get(i).getText().equals(this.comments.get(j).getText()) && this.toDelete.get(i).getUserAuthor().getUsername().equals(this.comments.get(j).getUserAuthor().getUsername())) {
                        this.comments.remove(j);
                    }
                }

            }
        }
    }
}






