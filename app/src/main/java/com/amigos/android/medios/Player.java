package com.amigos.android.medios;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    static MediaPlayer mp;
    ArrayList<File> mySongs;
    int position;
    Uri u;
    String songTitle;

    SeekBar sb;
    ImageButton btPlay, btNxt, btPv;
    ImageView sImage;
    TextView timer1, timer2;
    MediaMetadataRetriever mmr;
    Bitmap bm;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    static int REP_DELAY = 50;
    private boolean onLongClickSoNoClick = false;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */

    Thread updateSeekBar = new Thread() {
        @Override
        public void run() {
            int totalDuration = mp.getDuration();
            int currentPostion = 0;
            while (currentPostion < totalDuration)
                try {
                    sleep(500);
                    currentPostion = mp.getCurrentPosition();
                    sb.setProgress(currentPostion);
                    timer1.setText(time(mp.getCurrentPosition()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mp.stop();
            mp.release();
            position = (position + 1) % mySongs.size();
            u = Uri.parse(mySongs.get(position).toString());
            mp = MediaPlayer.create(getApplicationContext(), u);
            mp.start();
            sb.setMax(mp.getDuration());
            onSongChange();
            mp.setOnCompletionListener(mCompletionListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btPlay = (ImageButton) findViewById(R.id.btPlay);
        btNxt = (ImageButton) findViewById(R.id.btNxt);
        btPv = (ImageButton) findViewById(R.id.btPv);
        sImage = (ImageView) findViewById(R.id.songImage);
        btPlay.setOnClickListener(this);
        btNxt.setOnClickListener(this);
        btPv.setOnClickListener(this);

        btNxt.setOnLongClickListener(this);
        btPv.setOnLongClickListener(this);
        btNxt.setOnTouchListener(this);
        btPv.setOnTouchListener(this);

        sb = (SeekBar) findViewById(R.id.seekBar);

        timer1 = (TextView) findViewById(R.id.timer1);
        timer2 = (TextView) findViewById(R.id.timer2);

        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos", 0);


        u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();

        onSongChange();

        mp.setOnCompletionListener(mCompletionListener);

        sb.setMax(mp.getDuration());
        updateSeekBar.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
    }


    public void onSongChange() {
        //To change the song title
        songTitle = mySongs.get(position).getName().replace(".mp3", "").replace(".m4a","").replace(".wav","");

        //txt.setText(songTitle);
        setTitle(songTitle);
        sb.setProgress(0);

        //To change the total duration
        timer2.setText(time(mp.getDuration()));

        //To change the album art
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mySongs.get(position).getPath());
        byte[] artBytes = mmr.getEmbeddedPicture();
        if (artBytes != null) {
            bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
            sImage.setImageBitmap(bm);
        } else
            sImage.setImageResource(R.drawable.defaultalbumart);
    }

    /**
     * This method to calculate song duration and current position in munite format
     * @param millis takes timeunit in milli second
     * @return ":" seperaterd strings i.e min:sec
     */
    public static String time( int millis ) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return String.format("%d:%02d",minutes, seconds);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btPlay:
                if (mp.isPlaying()) {
                    btPlay.setImageResource(R.drawable.ic_media_play);
                    mp.pause();
                } else {
                    btPlay.setImageResource(R.drawable.ic_media_pause);
                    mp.start();
                }
                break;
            case R.id.btNxt:
                if (onLongClickSoNoClick)
                    break;
                mp.stop();
                mp.reset();
                mp.release();
                position = (position + 1) % mySongs.size();
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                sb.setMax(mp.getDuration());
                onSongChange();
                btPlay.setImageResource(R.drawable.ic_media_pause);
                break;
            case R.id.btPv:
                if (onLongClickSoNoClick)
                    break;
                mp.stop();
                mp.reset();
                mp.release();
                position = (position - 1 < 0) ? mySongs.size() - 1 : position - 1;
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                sb.setMax(mp.getDuration());
                onSongChange();
                btPlay.setImageResource(R.drawable.ic_media_pause);
                break;
        }
        onLongClickSoNoClick = false;
        if (updateSeekBar.getState() == Thread.State.NEW) {
            updateSeekBar.start();
        }
        mp.setOnCompletionListener(mCompletionListener);
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        switch (id) {
            case R.id.btNxt:
                mAutoIncrement = true;
                break;
            case R.id.btPv:
                mAutoDecrement = true;
                break;
        }
        onLongClickSoNoClick = true;
        repeatUpdateHandler.post(new RptUpdater());
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) && mAutoIncrement) {
            mAutoIncrement = false;
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
        if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) && mAutoDecrement) {
            mAutoDecrement = false;
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
        return false;
    }

    private class RptUpdater implements Runnable {
        public void run() {
            if (mAutoIncrement) {
                mp.seekTo(mp.getCurrentPosition() + 750);
                repeatUpdateHandler.postDelayed(new RptUpdater(), REP_DELAY);
            } else if (mAutoDecrement) {
                mp.seekTo(mp.getCurrentPosition() - 750);
                repeatUpdateHandler.postDelayed(new RptUpdater(), REP_DELAY);
            }
        }
    }
}
