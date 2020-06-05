package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapterSearch extends ArrayAdapter<Book> {

    /**Gestione del layout della listview della searchbar attraverso l'apposito adapter*/
    Context context;
    ArrayList<Book> books;
    String text;

    public BookAdapterSearch(Context context, int textViewResourceId, ArrayList<Book> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
        this.books = objects;
    }

    /**Metodo che associa gli elementi del layout adapter agli elementi della lista*/
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        Book book = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_searched, null);
            viewHolder= new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.nome_libro);
            viewHolder.bookAuthor = convertView.findViewById(R.id.nome_autore);
            viewHolder.star = convertView.findViewById(R.id.star);
            viewHolder.valutationSearch = convertView.findViewById(R.id.valutationSearch);
            setStarColor(book.getTotalValutation(),viewHolder.star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookTitle.setText(book.getTitle());
        viewHolder.bookAuthor.setText(book.getAuthor().getUsername());

        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        if (!Float.isNaN(book.getTotalValutation())) {
            viewHolder.valutationSearch.setText("" + roundDown5(book.getTotalValutation()));
        }else{
            viewHolder.valutationSearch.setText("0.0");

        }

        return convertView;
    }

    /**Dichiarazione elementi layout dell'adapter per la listView */
    private static class ViewHolder{
        TextView bookTitle, bookAuthor, valutationSearch;
        ImageView star;
    }

    public void setStarColor (float valutation, ImageView star){
        if(valutation==5){
            star.setColorFilter(getContext().getResources().getColor(R.color.blue));

        }else {
            if (valutation > 4.2f) {
                star.setColorFilter(getContext().getResources().getColor(R.color.green));
            } else {
                if (valutation >= 3.2f && valutation <= 4.2f) {
                    star.setColorFilter(getContext().getResources().getColor(R.color.yellow));
                } else {
                    if (valutation > 2f && valutation < 3.2f) {
                        star.setColorFilter(getContext().getResources().getColor(R.color.orange));
                    } else {
                        if (valutation > 0f && valutation <= 2) {
                            star.setColorFilter(getContext().getResources().getColor(R.color.red));
                        }else {
                            star.setColorFilter(getContext().getResources().getColor(R.color.grey));
                        }
                    }
                }
            }
        }

    }

    public static double roundDown5(float d) {
        return Math.floor(d * 1e2) / 1e2;
    }
}