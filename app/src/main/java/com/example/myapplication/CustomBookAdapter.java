package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBookAdapter extends ArrayAdapter<Book> {

    Context context;
    ArrayList<Book> books;

    public CustomBookAdapter(Context context, int textViewResourceId, ArrayList<Book> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
        this.books = objects;
    }
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bookitem, null);
            viewHolder= new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.booktitle);
            viewHolder.bookAuthor = convertView.findViewById(R.id.bookauthor);
            viewHolder.bookGenre = convertView.findViewById(R.id.bookgenreimg);
            viewHolder.goToBook = convertView.findViewById(R.id.gotobook);
            viewHolder.star = convertView.findViewById(R.id.starimg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Book book = getItem(position);
        viewHolder.bookTitle.setText(book.getTitle());
        viewHolder.bookAuthor.setText(book.getAuthor().getUsername());
        if (findImg(book)!=-1) {
            viewHolder.bookGenre.setImageResource(findImg(book));
        }
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        viewHolder.goToBook.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        return convertView;
    }
    public int findImg(Book book) {
        switch(book.getGenre()) {
            case FANTASY:
                return R.drawable.dragon;
            case STORICO:
                return R.drawable.museum;
            case THRILLER:
                return R.drawable.knife;
            case POLIZIESCO:
                return R.drawable.policeman;
            case FANTASCIENZA:
                return R.drawable.robot;
        }
        return -1;
    }
    private class ViewHolder{
        TextView bookTitle, bookAuthor;
        ImageView bookGenre, goToBook, star;
    }
}

