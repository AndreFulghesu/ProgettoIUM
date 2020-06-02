package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomCommentAdapter extends ArrayAdapter<Comment> {
    Context context;
    ArrayList<Comment> commenti = new ArrayList<Comment>();


    public CustomCommentAdapter(Context context, int textViewResourceId, ArrayList<Comment> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
        this.commenti = objects;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         final ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.commentitem, null);


            viewHolder= new ViewHolder();
            viewHolder.commento = convertView.findViewById(R.id.commento);
            viewHolder.commentAuthor = convertView.findViewById(R.id.autore_commento);
            viewHolder.delete_button = convertView.findViewById(R.id.button_delete);
            viewHolder.like = convertView.findViewById(R.id.favourite);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Comment c = getItem(position);
        final User u =UserFactory.getInstance().getUserByUsername(new UserSession(context).getUserSession());

        System.out.println("\nUtente loggato:   "+u.getUsername());
        System.out.println("\nCommento relativo:   "+c.getUserAuthor().getUsername());


        viewHolder.commento.setText(c.getText());



        if (u.getUsername().equals(c.getUserAuthor().getUsername())){
            viewHolder.delete_button.setVisibility(View.VISIBLE);
        }else{
            viewHolder.delete_button.setVisibility(View.INVISIBLE);
        }

        if (UserFactory.getInstance().getUserByUsername(u.getUsername()).getLike(c)==null || !UserFactory.getInstance().getUserByUsername(u.getUsername()).getLike(c)) {
            viewHolder.like.setLiked(false);
        }else{
            viewHolder.like.setLiked(true);

        }






        for (Comment a : u.getMapLikes().keySet()){
            System.out.println("\nchiavi commenti nella tabella: "+a.getUserAuthor());
            System.out.println("\nvalore commenti nella tabella: "+u.getMapLikes().get(a));
        }


        viewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cancella commento")
                        .setMessage("Sei sicuro di voler cancellare il commento?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Comment  toDelete=getItem(position);
                                remove(getItem(position));
                                CommentFactory.getInstance().toDelete(toDelete);
                                ChapterFactory.getInstance().getChapterByChapNum(c.getChapterId(),c.getBookId()).deleteComment(c);

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        viewHolder.commentAuthor.setText(c.getUserAuthor().getNome() + " " + c.getUserAuthor().getCognome());

        viewHolder.like.setOnLikeListener(new OnLikeListener() {

            public void liked(LikeButton likeButton) {
                UserFactory.getInstance().getUserByUsername(u.getUsername()).addLikeComments(c,true);

                viewHolder.like.setLiked(true);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                UserFactory.getInstance().getUserByUsername(u.getUsername()).addLikeComments(c,false);
                viewHolder.like.setLiked(false);

            }
        });

        return convertView;
    }

    private class ViewHolder{
        TextView commento, commentAuthor;
        com.like.LikeButton like;
        ImageView delete_button;
    }

    /*
    public void setLikeColor (Button b,Comment c){

        if (c.getLike()==false){
            b.setBackgroundResource(R.drawable.ic_favorite_shadows_36dp);
        }else{
            b.setBackgroundResource(R.drawable.ic_favorite_red_36dp);
        }
    }

     */
}



