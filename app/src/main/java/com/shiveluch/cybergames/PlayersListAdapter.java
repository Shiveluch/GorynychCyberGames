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

public class PlayersListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<PlayersList> objects;

    protected ListView mListView;
    public PlayersListAdapter(Context context, ArrayList<PlayersList> chars, Activity activity) {
        super();
        ctx = context;
        objects = chars;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.playerslist);
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

        PlayersList p = getPlayersList(position);
        ((TextView) view.findViewById(R.id.a_person)).setText(p.charName);
        ((TextView) view.findViewById(R.id.a_roleclass)).setText(p.charRole+"\n"+p.charClass);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.charID);


        return view;
    }

    PlayersList getPlayersList(int position) {
        return ((PlayersList) getItem(position));
    }

}