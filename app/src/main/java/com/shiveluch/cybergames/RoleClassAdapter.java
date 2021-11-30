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

public class RoleClassAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Roleclass> objects;

    protected ListView mListView;
    public RoleClassAdapter(Context context, ArrayList<Roleclass> roleclass, Activity activity) {
        super();
        ctx = context;
        objects = roleclass;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.roleclasslist);
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
            view = lInflater.inflate(R.layout.roleclassadapter, parent, false);
        }

        Roleclass p = getRoleclass(position);
        ((TextView) view.findViewById(R.id.a_role)).setText(p.name);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);


        return view;
    }

    Roleclass getRoleclass(int position) {
        return ((Roleclass ) getItem(position));
    }

}