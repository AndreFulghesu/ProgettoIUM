package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class CommentFactory {

    private static CommentFactory singleton;


    private ArrayList<Comment> comments = new ArrayList<>();


    private CommentFactory(){
    }

    public static CommentFactory getInstance(){
        if(singleton == null){
            singleton = new CommentFactory();
        }
        return singleton;
    }

    public ArrayList<Comment> getComments () {

        //commenti per il libro 1
        Comment com1 = new Comment ("Questo libro e' molto bello", 5, 1,1,UserFactory.getInstance().getUserByName("Faber123"));
        Comment com2 = new Comment ("A me invece e' piaciuto poco", 2, 1,1,UserFactory.getInstance().getUserByName("Andre97"));
        Comment com3 = new Comment ("Veramente entusiasmante", 4, 2,1,UserFactory.getInstance().getUserByName("Faber123"));
        Comment com4 = new Comment ("Circa...",3, 2,1,UserFactory.getInstance().getUserByName("Andre97"));

        //commenti per il libro 2
        Comment com5 = new Comment ("Non male come scrittura", 4, 1,2,UserFactory.getInstance().getUserByName("Gio34"));
        Comment com6 = new Comment ("Poteva essere scritto meglio", 2, 1,2,UserFactory.getInstance().getUserByName("Faber123"));
        Comment com7 = new Comment ("Mmmmm non mi convince", 1, 2,2,UserFactory.getInstance().getUserByName("Gio34"));
        Comment com8 = new Comment ("Circa...",3, 2,2,UserFactory.getInstance().getUserByName("Andre97"));

        comments.add(com1);
        comments.add(com2);
        comments.add(com3);
        comments.add(com4);

        comments.add(com5);
        comments.add(com6);
        comments.add(com7);
        comments.add(com8);

        return this.comments;

    }


    public ArrayList<Comment> getCommentById (int idChapter, int idBook){

        ArrayList<Comment> commentsChapter = new ArrayList<>();
        List<Comment> comments = this.getComments();
        for (Comment c : comments) {
            if (c.getChapterId()==idChapter && c.getBookId()==idBook) {
                commentsChapter.add(c);
            }
        }
        return commentsChapter;
    }
    void addComment (Comment c) {
        ArrayList<Comment> newC;
        newC = getComments();
        newC.add(c);
        comments = newC;
    }

}


