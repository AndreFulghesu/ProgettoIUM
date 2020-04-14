package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CustomBookAdapter extends ArrayAdapter<Book> {
    public CustomBookAdapter(Context context, int textViewResourceId, List<Book> objects) {
        super(context, textViewResourceId, objects);
    }
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bookitem, null);
        TextView bookTitle = view.findViewById(R.id.booktitle);
        TextView bookAuthor = view.findViewById(R.id.bookauthor);
        ImageView bookGenre = view.findViewById(R.id.bookgenreimg);
        ImageView goToBook = view.findViewById(R.id.gotobook);
        Book book = getItem(position);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor().getUsername());
        if (findImg(book)!=-1) {
            bookGenre.setImageResource(findImg(book));
        }
        goToBook.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        return view;
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
}
