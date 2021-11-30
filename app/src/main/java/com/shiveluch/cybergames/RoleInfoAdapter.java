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

public class RoleInfoAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<RoleInfo> objects;

    protected ListView mListView;
    public RoleInfoAdapter(Context context, ArrayList<RoleInfo> RoleInfo, Activity activity) {
        super();
        ctx = context;
        objects = RoleInfo;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.roledesc);
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
            view = lInflater.inflate(R.layout.roleinfoadapter, parent, false);
        }

        RoleInfo p = getRoleInfo(position);
        ((TextView) view.findViewById(R.id.a_fieldname)).setText(p.fieldname+":");
        ((TextView) view.findViewById(R.id.a_fielddat)).setText(p.fielddata);
        ((TextView) view.findViewById(R.id.a_field)).setText(p.field);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);

        return view;
    }

    RoleInfo getRoleInfo(int position) {
        return ((RoleInfo) getItem(position));
    }

}
