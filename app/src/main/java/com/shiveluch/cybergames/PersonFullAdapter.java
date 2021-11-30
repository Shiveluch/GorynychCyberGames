package com.shiveluch.cybergames;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonFullAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<PersonFull> objects;

    protected ListView mListView;
    public PersonFullAdapter(Context context, ArrayList<PersonFull> personFull, Activity activity) {
        super();
        ctx = context;
        objects = personFull;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.personfullinfo);
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
            view = lInflater.inflate(R.layout.personfullinfoadapter, parent, false);
        }

        PersonFull p = getPersonFull(position);
        ((TextView) view.findViewById(R.id.a_percname)).setText(p.percname);
        ((TextView) view.findViewById(R.id.a_percdata)).setText(p.percdata);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.percstatus);
        if (p.percstatus.equals("1"))
        {
            ((RelativeLayout) view.findViewById(R.id.pfullRL)).setVisibility(View.VISIBLE);
        }
        else  ((RelativeLayout) view.findViewById(R.id.pfullRL)).setVisibility(View.INVISIBLE);




        return view;
    }

    PersonFull getPersonFull(int position) {
        return ((PersonFull) getItem(position));
    }

}