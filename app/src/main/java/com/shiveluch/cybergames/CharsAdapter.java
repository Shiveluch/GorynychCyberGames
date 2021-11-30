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

public class CharsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Chars> objects;

    protected ListView mListView;
    public CharsAdapter(Context context, ArrayList<Chars> chars, Activity activity) {
        super();
        ctx = context;
        objects = chars;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.charslist);
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
            view = lInflater.inflate(R.layout.charlistadapter, parent, false);
        }

        Chars p = getChars(position);
        ((TextView) view.findViewById(R.id.a_person)).setText(p.charName);
        ((TextView) view.findViewById(R.id.a_roleclass)).setText(p.charRole+"\n"+p.charClass);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.charID);
        if (!p.charTime.equals("null") && p.charTime.length()!=0 )
        ((TextView) view.findViewById(R.id.timing)).setText("Последний вход: "+ p.charTime);
        else
            ((TextView) view.findViewById(R.id.timing)).setText("Последний вход: неизвестно");



        return view;
    }

    Chars getChars(int position) {
        return ((Chars) getItem(position));
    }

}