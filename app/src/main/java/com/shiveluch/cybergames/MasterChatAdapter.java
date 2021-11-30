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

public class MasterChatAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<MasterChat> objects;

    protected ListView mListView;
    public MasterChatAdapter(Context context, ArrayList<MasterChat> MasterChat, Activity activity) {
        super();
        ctx = context;
        objects = MasterChat;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView = activity.findViewById(R.id.masterchat);
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
            view = lInflater.inflate(R.layout.masterchatadapter, parent, false);
        }

        MasterChat p = getMasterChat(position);
        ((TextView) view.findViewById(R.id.p_timerec)).setText(p.receiverlist);
        ((TextView) view.findViewById(R.id.a_message)).setText(p.message);
        ((TextView) view.findViewById(R.id.a_id)).setText(p.id);



        return view;
    }

    MasterChat getMasterChat(int position) {
        return ((MasterChat) getItem(position));
    }

}