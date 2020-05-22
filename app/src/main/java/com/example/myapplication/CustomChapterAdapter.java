package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomChapterAdapter extends ArrayAdapter<Chapter> {

    Context context;
    ArrayList<Chapter> chaps;

    public CustomChapterAdapter(Context context, int resource, ArrayList<Chapter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.chaps = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Chapter chap = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chapteritem, null);
            viewHolder= new ViewHolder();
            viewHolder.cNumber = convertView.findViewById(R.id.stringchapter);
            viewHolder.rateNumber = convertView.findViewById(R.id.ratingNumber);
            viewHolder.star = convertView.findViewById(R.id.star);
            setStarColor(chap.getValutation(),viewHolder.star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rateNumber.setText(""+  roundDown5(chap.getValutation()));
        assert chap != null;
        String str = "Capitolo " + chap.getChaptNum();
        viewHolder.cNumber.setText(str);
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        return convertView;
    }
    private class ViewHolder{
        TextView cNumber, rateNumber;
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
