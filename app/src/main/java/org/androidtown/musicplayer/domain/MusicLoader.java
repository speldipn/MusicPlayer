package org.androidtown.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MusicLoader {
  List list = new ArrayList<>();

  public MusicLoader(Context ctx) {
    ContentResolver resolver = ctx.getContentResolver();
    Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String projections[] = {
      MediaStore.Audio.Media._ID,
      MediaStore.Audio.Media.TITLE,
      MediaStore.Audio.Media.ARTIST,
      MediaStore.Audio.Media.ALBUM_ID,
      MediaStore.Audio.Media.DURATION
    };
    Cursor cursor = resolver.query(musicUri, projections, null, null, null);
    while (cursor.moveToNext()) {
      Music music = new Music(
        cursor.getString(cursor.getColumnIndex(projections[0])),
        cursor.getString(cursor.getColumnIndex(projections[1])),
        cursor.getString(cursor.getColumnIndex(projections[2])),
        cursor.getString(cursor.getColumnIndex(projections[3])),
        cursor.getLong(cursor.getColumnIndex(projections[4]))
      );
      music.update();
      list.add(music);
    }
    if(cursor != null) {
      cursor.close();
    }
  }

  public List getList() {
    if (list.size() == 0) {
      return null;
    }
    return list;
  }
}
