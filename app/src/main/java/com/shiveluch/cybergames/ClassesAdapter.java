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

public class ClassesAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Classes> objects;

    protected ListView mListView;
    public ClassesAdapter(Context context, ArrayList<Classes> classes, Activity activity) {
        super();
        ctx = context;
        objects = classes;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.classlist);
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
            view = lInflater.inflate(R.layout.classesadapter, parent, false);
        }

        Classes p = getClasses(position);
        ((TextView) view.findViewById(R.id.a_class)).setText(p.classname);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);


        return view;
    }

    Classes getClasses(int position) {
        return ((Classes) getItem(position));
    }

}