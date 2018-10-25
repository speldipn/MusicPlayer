package org.androidtown.musicplayer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;

    public static void set(Context ctx, Uri uri) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("speldipn", "set >> release 호출");
        }
        mediaPlayer = MediaPlayer.create(ctx, uri);
        // for prepare, do sleep
        sleep(200);
    }

    public static void play() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                Log.d("speldipn", "play 호출");
            }
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                Log.d("speldipn", "stop 호출");
            }
        }
    }

    public static void sleep(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public int duration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return (-1);
    }

    public void seekTo(int msec) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(msec);
        }
    }

    public int getMax() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return -1;
    }
}
