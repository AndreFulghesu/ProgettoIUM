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
        Book book = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bookitem, null);
            viewHolder= new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.booktitle);
            viewHolder.bookAuthor = convertView.findViewById(R.id.bookauthor);
            viewHolder.bookGenre = convertView.findViewById(R.id.bookgenreimg);
            viewHolder.eyeImage = convertView.findViewById(R.id.eye_views);
            viewHolder.views = convertView.findViewById(R.id.viewsTextView);
            viewHolder.star = convertView.findViewById(R.id.starimg);
            viewHolder.averageValutation = convertView.findViewById(R.id.averageValutation);
            setStarColor(book.getAverage(),viewHolder.star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookTitle.setText(book.getTitle());
        viewHolder.bookAuthor.setText(book.getAuthor().getUsername());
        System.out.println("Sono nell adapter dentro "+book.getViews());
        viewHolder.views.setText(book.getViews());

        if (findImg(book)!=-1) {
            viewHolder.bookGenre.setImageResource(findImg(book));
        }
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        viewHolder.eyeImage.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        viewHolder.averageValutation.setText("" +roundDown5(book.getAverage()));



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
            case HORROR:
                return R.drawable.ghost;
        }
        return -1;
    }
    private class ViewHolder{
        TextView bookTitle, bookAuthor, averageValutation, views;
        ImageView bookGenre, eyeImage, star;
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
                        if (valutation > 0f && valutation < 2) {
                            star.setColorFilter(getContext().getResources().getColor(R.color.red));
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

