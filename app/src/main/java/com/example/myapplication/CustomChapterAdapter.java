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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chapteritem, null);
            viewHolder= new ViewHolder();
            viewHolder.cNumber = convertView.findViewById(R.id.stringchapter);
            viewHolder.rateNumber = convertView.findViewById(R.id.ratingNumber);
            viewHolder.star = convertView.findViewById(R.id.star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Chapter chap = getItem(position);
        viewHolder.rateNumber.setText("4.5");
        assert chap != null;
        String str = "Capitolo" + chap.getChaptNum();
        viewHolder.cNumber.setText(str);
        viewHolder.star.setImageResource(R.drawable.ic_star_black_36dp);
        return convertView;
    }
    private class ViewHolder{
        TextView cNumber, rateNumber;
        ImageView star;
    }
}
