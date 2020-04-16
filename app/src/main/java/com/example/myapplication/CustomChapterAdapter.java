package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Chapter;

import java.util.ArrayList;

public class CustomChapterAdapter extends ArrayAdapter<String> {

    public CustomChapterAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
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
        String cNumber = getItem(position);
        viewHolder.rateNumber.setText("4.5");
        viewHolder.cNumber.setText(cNumber);
        viewHolder.star.setImageResource(R.drawable.star);
        return convertView;
    }
    private static class ViewHolder{
        TextView cNumber, rateNumber;
        ImageView star;
    }
}
