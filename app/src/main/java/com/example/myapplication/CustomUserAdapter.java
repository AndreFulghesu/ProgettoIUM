package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomUserAdapter extends ArrayAdapter<User> {

    CustomUserAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        /**Gestione del layout della listview per la ricerca nella activity Report attraverso l'apposito adapter*/
    }

    /**Metodo che associa gli elementi del layout adapter agli elementi della lista*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final User user = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
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
        switch (user.getSex()){
            case MALE:
                viewHolder.profileImage.setImageResource(R.drawable.bananaicon);
                break;
            case FEMALE:
                viewHolder.profileImage.setImageResource(R.drawable.peachicon);
                break;
            case UNDEFINED:
                viewHolder.profileImage.setImageResource(R.drawable.blackholeicon);
                break;
            default:
                viewHolder.profileImage.setImageResource(R.drawable.ic_person_black_24dp);
        }
        viewHolder.removeItem.setImageResource(R.drawable.ic_close_black_24dp);

        return convertView;
    }

    /**Dichiarazione elementi layout dell'adapter per la listView */
    private static class ViewHolder {
        ImageView profileImage, removeItem;
        TextView usernameString;
    }
}