package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.ArrayList;

public class ContinuaLetturaAdapter extends ArrayAdapter<Pair<Book, Chapter>> {

    Context context;
    ArrayList<Pair<Book, Chapter>> books;
    String text;

    public ContinuaLetturaAdapter(Context context, int textViewResourceId, ArrayList<Pair<Book, Chapter>> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
        this.books = objects;
    }

    public ContinuaLetturaAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        Pair<Book, Chapter> pair = getItem(position);
        assert pair != null;
        Book book = pair.first;
        Chapter chap = pair.second;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.continua_lettura_item, null);
            viewHolder= new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.book_name);
            viewHolder.chapterNum = convertView.findViewById(R.id.chapter_number);
            viewHolder.star = convertView.findViewById(R.id.star);
            viewHolder.valutation = convertView.findViewById(R.id.valutation_continue);
            setStarColor(book.getAverage(),viewHolder.star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookTitle.setText(book.getTitle());
        String chapNumber = "Riprendi la lettura dal capitolo " + chap.getChaptNum();
        viewHolder.chapterNum.setText(chapNumber);
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        viewHolder.valutation.setText("" +roundDown5(book.getAverage()));

        return convertView;
    }

    private class ViewHolder{
        TextView bookTitle, chapterNum, valutation;
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