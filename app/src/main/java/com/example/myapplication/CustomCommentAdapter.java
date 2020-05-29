package com.example.myapplication;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

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
            viewHolder.like = convertView.findViewById(R.id.favourite);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Comment c = getItem(position);
        final User u =UserFactory.getInstance().getUserByUsername(new UserSession(context).getUserSession());

        viewHolder.commento.setText(c.getText());

        if (UserFactory.getInstance().getUserByUsername(u.getUsername()).getLike(c)) {
            viewHolder.like.setLiked(true);
        }else{
            viewHolder.like.setLiked(false);

        }
        viewHolder.commentAuthor.setText(c.getUserAuthor().getNome() + " " + c.getUserAuthor().getCognome());

        viewHolder.like.setOnLikeListener(new OnLikeListener() {

            public void liked(LikeButton likeButton) {
                UserFactory.getInstance().getUserByUsername(u.getUsername()).addLike(c,true);
                UserFactory.getInstance().addUserModified(u);
                System.out.println("Utente loggato:   "+u.getUsername());
                //CommentFactory.getInstance().getComments().get(position).setLike(true);
                //c.setLike(true);
                viewHolder.like.setLiked(true);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                //CommentFactory.getInstance().getComments().get(position).setLike(false);
                //c.setLike(false);
                UserFactory.getInstance().getUserByUsername(u.getUsername()).addLike(c,false);
                System.out.println("Utente loggato:   "+u.getUsername());
                viewHolder.like.setLiked(false);

            }
        });

        return convertView;
    }

    private class ViewHolder{
        TextView commento, commentAuthor;
        com.like.LikeButton like;
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



