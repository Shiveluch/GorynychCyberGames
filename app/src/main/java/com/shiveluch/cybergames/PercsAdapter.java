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

public class PercsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Percs> objects;

    protected ListView mListView;
    public PercsAdapter(Context context, ArrayList<Percs> percs, Activity activity) {
        super();
        ctx = context;
        objects = percs;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.percslist);
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
            view = lInflater.inflate(R.layout.percsadapter, parent, false);
        }

        Percs p = getPercs(position);
        ((TextView) view.findViewById(R.id.a_perc)).setText(p.percname);
        ((TextView) view.findViewById(R.id.a_descript)).setText(p.percdesc);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);
        ((TextView) view.findViewById(R.id.p_lon)).setText(p.s_id);


        return view;
    }

   Percs getPercs(int position) {
        return ((Percs) getItem(position));
    }

}
