package com.example.deannhom;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deannhom.model.AudioModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    private View background;
    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon;
    ArrayList<AudioModel> songsList;
    MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;

    boolean isNegativeTotal = true;
    long totalDuration;
    long currentDuration = 0;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        getMusicFromIntent();
        setResourcesToMusic();
    }

    private void initView() {
        background = findViewById(R.id.background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            background.setBackground(this.getDrawable(R.drawable.drawabke_linear_bg_playermedia_api31));
        } else {
            /**
             * If the current version is under S version, it does not allow to set angle is under 45.
             * In others word, it must be multiple of 45.
             */
            background.setBackground(this.getDrawable(R.drawable.drawabke_linear_bg_playermedia_api26));
        }
        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon_big);

        titleTv.setSelected(true);

        pausePlay.setOnClickListener(v -> pausePlay());
        nextBtn.setOnClickListener(v -> playNextSong());
        previousBtn.setOnClickListener(v -> playPreviousSong());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setupMediaPlayer();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler = new Handler(Looper.getMainLooper());
                if (mediaPlayer != null)
                    onPlayingMedia();
                handler.postDelayed(this, 1);
            }
        };
        MusicPlayerActivity.this.runOnUiThread(runnable);


        totalTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNegativeTotal = !isNegativeTotal;
                bindTotalDuration();
            }
        });
    }

    private void setupMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build());
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    private void onPlayingMedia() {
        if (mediaPlayer.isPlaying()) {
            currentDuration = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) currentDuration);
            currentTimeTv.setText(convertToMMSS(currentDuration));
            bindTotalDuration();
            if (mediaPlayer.isPlaying()) {
                pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                musicIcon.setRotation(x++);
            } else {
                pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                musicIcon.setRotation(0);
            }
        }
    }

    private void getMusicFromIntent() {
        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
    }

    private void setResourcesToMusic() {
        AudioModel song = songsList.get(MyMediaPlayer.currentIndex);

        titleTv.setText(song.getTitle());
        totalTimeTv.setText(convertToMMSS(song.getDuration()));
        String path = "android.resource://" + getPackageName() + "/raw/" + song.getPath();

        playMusic(path);
    }

    private void playMusic(String path) {
        Uri uri = Uri.parse(path);
        mediaPlayer = new MediaPlayer();
        totalDuration = getDuration(uri);
        try {

            mediaPlayer.setDataSource(MusicPlayerActivity.this, uri);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.seekTo((int) currentDuration);
                }
            });
            mediaPlayer.prepareAsync();
            bindTotalDuration();
            seekBar.setProgress((int) currentDuration);
            seekBar.setMax((int) totalDuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playNextSong() {
        if (MyMediaPlayer.currentIndex == songsList.size() - 1)
            return;
        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesToMusic();
    }

    private void playPreviousSong() {
        if (MyMediaPlayer.currentIndex == 0)
            return;
        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesToMusic();
    }

    private void pausePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pausePlay.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_circle_outline_24));
        } else {
            mediaPlayer.start();
            pausePlay.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_circle_outline_24));
        }
    }

    public static String convertToMMSS(long mili) {
        long duration = mili / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void bindTotalDuration() {
        if (isNegativeTotal && totalDuration != 0) {
            totalTimeTv.setText("-" + convertToMMSS(totalDuration - currentDuration));
        } else {
            totalTimeTv.setText(convertToMMSS(totalDuration));
        }
    }

    public long getDuration(Uri uri) {
        MediaPlayer mp = MediaPlayer.create(this, uri);
        if (mp != null) {
            return mp.getDuration();
        }
        return 0;
    }

    public void stopMusic() {
        mediaPlayer.release();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        mediaPlayer = null;
    }

    @Override
    protected void onPause() {
        stopMusic();
        super.onPause();
    }
}