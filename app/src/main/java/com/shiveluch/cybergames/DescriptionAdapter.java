package com.shiveluch.cybergames;




import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DescriptionAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Description> objects;

   // protected ListView mListView;
    public DescriptionAdapter(Context context, ArrayList<Description> stalkers, Activity activity) {
        super();
        ctx = context;
        objects = stalkers;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //mListView = activity.findViewById(R.id.descriptionlist);
    }

    // ???-?? ?????????
    @Override
    public int getCount() {
        return objects.size();
    }

    // ??????? ?? ???????
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.descdialog, parent, false);
        }

        Description p = getDescription(position);
        ((TextView) view.findViewById(R.id.maindesc)).setText(p.description);
        ((TextView) view.findViewById(R.id.link)).setText(p.link);


        return view;
    }

    Description getDescription(int position) {
        return ((Description) getItem(position));
    }

}