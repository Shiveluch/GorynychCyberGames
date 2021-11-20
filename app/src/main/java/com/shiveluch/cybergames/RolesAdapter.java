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

public class RolesAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Roles> objects;

    protected ListView mListView;
    public RolesAdapter(Context context, ArrayList<Roles> stalkers, Activity activity) {
        super();
        ctx = context;
        objects = stalkers;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.roleslist);
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
            view = lInflater.inflate(R.layout.rolesadapter, parent, false);
        }

        Roles p = getRoles(position);
        ((TextView) view.findViewById(R.id.a_role)).setText(p.rolename);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);


        return view;
    }

    Roles getRoles(int position) {
        return ((Roles) getItem(position));
    }

}