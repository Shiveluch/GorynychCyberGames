package com.shiveluch.cybergames;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {

    MediaPlayer mp;
    Context context;

    public Audio(Context ct) {
        this.context = ct;
    }

    public void playClick() {
        mp = MediaPlayer.create(context, R.raw.button1);
        mp.start();
    }
}