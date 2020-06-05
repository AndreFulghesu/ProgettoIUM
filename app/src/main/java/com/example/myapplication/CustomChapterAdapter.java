package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**Gestione del layout della listview nella lista dei capitoli di un libro attraverso l'apposito adapter*/

public class CustomChapterAdapter extends ArrayAdapter<Chapter> {

    public CustomChapterAdapter(Context context, int resource, ArrayList<Chapter> objects) {
        super(context, resource, objects);
    }

    /**Metodo che associa gli elementi del layout adapter agli elementi della lista*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Chapter chap = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.chapteritem, null);
            viewHolder= new ViewHolder();
            viewHolder.cNumber = convertView.findViewById(R.id.stringchapter);
            viewHolder.rateNumber = convertView.findViewById(R.id.ratingNumber);
            viewHolder.star = convertView.findViewById(R.id.star);
            assert chap != null;
            setStarColor(chap.getValutation(),viewHolder.star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert chap != null;
        String str = "Capitolo " + chap.getChaptNum();
        viewHolder.cNumber.setText(str);
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        if (!Float.isNaN(chap.getValutation())) {
            viewHolder.rateNumber.setText("" + roundDown5(chap.getValutation()));
        }else{
            viewHolder.rateNumber.setText("0.0");

        }
        return convertView;
    }

    /**Dichiarazione elementi layout dell'adapter per la listView */
    private static class ViewHolder{
        TextView cNumber, rateNumber;
        ImageView star;
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
                        }else {
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
