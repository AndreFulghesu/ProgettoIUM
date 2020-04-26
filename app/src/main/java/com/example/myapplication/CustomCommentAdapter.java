package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.commentitem, null);
            viewHolder= new ViewHolder();
            viewHolder.commento = convertView.findViewById(R.id.commento);
            viewHolder.commentAuthor = convertView.findViewById(R.id.autore_commento);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.commento.setText(commenti.get(position).getText());
        viewHolder.commentAuthor.setText(commenti.get(position).getUserAuthor().getNome() + " " + commenti.get(position).getUserAuthor().getCognome());

        return convertView;
    }

    private class ViewHolder{
        TextView commento, commentAuthor;
    }
}



