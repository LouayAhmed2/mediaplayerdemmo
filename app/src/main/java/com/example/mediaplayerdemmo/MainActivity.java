package com.example.mediaplayerdemmo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView starttime, duration, songname;
    SeekBar time, volume;
    Button play;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starttime = findViewById(R.id.starttime);
        duration = findViewById(R.id.duration);
        time = findViewById(R.id.seekbartime);
        volume = findViewById(R.id.seekbarvol);
        play = findViewById(R.id.btnplay);
        songname = findViewById(R.id.songname);
        mediaPlayer = MediaPlayer.create(this, R.raw.amrdiab);
        songname.setText("Amr Diab");
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f, 0.5f);
        volume.setProgress(50);
        String duration2 = millsec(mediaPlayer.getDuration());
        duration.setText(duration2);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float vol = progress / 100f;
                mediaPlayer.setVolume(vol, vol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        time.setMax(mediaPlayer.getDuration());
        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnplay) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        play.setBackgroundResource(R.drawable.ic_play);
                    } else {
                        mediaPlayer.start();
                        play.setBackgroundResource(R.drawable.ic_pause);

                    }

                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        try {
                            final double current = mediaPlayer.getCurrentPosition();
                            final String elapsedtim = millsec((int) current);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    starttime.setText(elapsedtim);
                                    time.setProgress((int) current);
                                }
                            });

                            Thread.sleep(1000);

                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
        }).start();

    }


    public String millsec(int time) {
        String elapsedtime = "";
        int minutes = time / 1000 / 60;
        int sec = time / 1000 % 60;
        elapsedtime = minutes + ":";
        if (sec < 10) {
            elapsedtime += "0";
        }
        elapsedtime += sec;
        return elapsedtime;

    }
}