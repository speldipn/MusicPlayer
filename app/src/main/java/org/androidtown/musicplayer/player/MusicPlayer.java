package org.androidtown.musicplayer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MusicPlayer {
  private static MediaPlayer mediaPlayer;

  private enum STATUS {PLAY, STOP}

  private static STATUS status = STATUS.STOP;

  public static void set(Context ctx, Uri uri) {
    if (status == STATUS.PLAY) {
      mediaPlayer.stop();
      mediaPlayer.release();
    }
    mediaPlayer = MediaPlayer.create(ctx, uri);
    status = STATUS.STOP;
  }

  public static void play() {
    mediaPlayer.start();
    status = STATUS.PLAY;
  }

  public static void stop() {
    mediaPlayer.stop();
    status = STATUS.STOP;
  }
}
