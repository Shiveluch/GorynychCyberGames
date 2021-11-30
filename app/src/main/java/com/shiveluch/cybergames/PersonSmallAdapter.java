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

public class PersonSmallAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<PersonSmall> objects;

    protected ListView mListView;
    public PersonSmallAdapter(Context context, ArrayList<PersonSmall> personsmall, Activity activity) {
        super();
        ctx = context;
        objects = personsmall;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.personsmallinfo);
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
            view = lInflater.inflate(R.layout.perconsmallinfoadapter, parent, false);
        }

        PersonSmall p = getPersonSmall(position);
        ((TextView) view.findViewById(R.id.a_contperson)).setText(p.contperson);
        ((TextView) view.findViewById(R.id.a_persondata)).setText(p.persondata);


        return view;
    }

    PersonSmall getPersonSmall(int position) {
        return ((PersonSmall) getItem(position));
    }

}