package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBookAdapter extends ArrayAdapter<Book> {

    /**Gestione del layout della listview nel Catalogo Libri attraverso l'apposito adapter*/
    private Context context;
    private final int classValue = 2;

    CustomBookAdapter(Context context, int textViewResourceId, ArrayList<Book> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
    }

    /**Metodo che associa gli elementi del layout adapter agli elementi della lista*/
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        final Book book = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.bookitem, null);
            viewHolder= new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.booktitle);
            viewHolder.bookAuthor = convertView.findViewById(R.id.bookauthor);
            viewHolder.bookGenre = convertView.findViewById(R.id.bookgenreimg);
            viewHolder.eyeImage = convertView.findViewById(R.id.eye_views);
            viewHolder.views = convertView.findViewById(R.id.viewsTextView);
            viewHolder.star = convertView.findViewById(R.id.starimg);
            viewHolder.averageValutation = convertView.findViewById(R.id.averageValutation);
            assert book != null;
            setStarColor(book.getTotalValutation(),viewHolder.star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert book != null;
        viewHolder.bookTitle.setText(book.getTitle());
        SpannableString content = new SpannableString(book.getAuthor().getUsername());
        content.setSpan(new UnderlineSpan(), 0, book.getAuthor().getUsername().length(), 0);
        viewHolder.bookAuthor.setText(content);
        viewHolder.bookAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserSession userSession = new UserSession(context);
                userSession.setCallingActivity(classValue);
                userSession.setUserAuthor(book.getAuthor().getUsername());
                context.startActivity(new Intent(context, ProfiloAutore.class));
            }
        });

        viewHolder.views.setText("" + book.getViews());

        if (findImg(book)!=-1) {
            viewHolder.bookGenre.setImageResource(findImg(book));
        }
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        viewHolder.eyeImage.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        if (!Float.isNaN(book.getTotalValutation())) {
            viewHolder.averageValutation.setText("" + roundDown5(book.getTotalValutation()));
        }else{
            viewHolder.averageValutation.setText("0.0");
        }
        return convertView;
    }

    private int findImg(Book book) {
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
            case NATURE:
                return R.drawable.foglia_libro;
        }
        return -1;
    }

    /**Dichiarazione elementi layout dell'adapter per la listView */
    private static class ViewHolder{
        TextView bookTitle, bookAuthor, averageValutation, views;
        ImageView bookGenre, eyeImage, star;
    }

    private void setStarColor(float valutation, ImageView star){
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
                        }else{
                            star.setColorFilter(getContext().getResources().getColor(R.color.grey));

                        }
                    }
                }
            }
        }
    }

    private static double roundDown5(float d) {
        return Math.floor(d * 1e2) / 1e2;
    }

}

