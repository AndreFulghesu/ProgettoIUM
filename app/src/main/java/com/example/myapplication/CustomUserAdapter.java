package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomUserAdapter extends ArrayAdapter<User> {

    Context context;
    ArrayList<User> users;

    public CustomUserAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.context= context;
        this.users = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final User user = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_username, null);
            viewHolder= new ViewHolder();
            viewHolder.profileImage = convertView.findViewById(R.id.image_user_report);
            viewHolder.usernameString = convertView.findViewById(R.id.user_name_report);
            viewHolder.removeItem = convertView.findViewById(R.id.remove_user_report);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        assert user != null;
        viewHolder.usernameString.setText(user.getUsername());
        viewHolder.profileImage.setImageResource(R.drawable.person);
        viewHolder.removeItem.setImageResource(R.drawable.ic_close_black_24dp);

        return convertView;
    }



    private class ViewHolder {
        ImageView profileImage, removeItem;
        TextView usernameString;
    }
}