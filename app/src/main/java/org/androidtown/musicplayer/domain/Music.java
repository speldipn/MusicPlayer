package org.androidtown.musicplayer.domain;

import android.content.ContentUris;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageButton;

public class Music {
  public String id;
  public String title;
  public String artist;
  public String albumid;
  public long duration;
  public Uri musicUri;
  public Uri albumartUri;

  public Music(String id, String title, String artist, String albumid, long duration) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.albumid = albumid;
    this.duration = duration;
    this.musicUri = null;
    this.albumartUri = null;
  }

  public void update() {
    Uri content = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    this.musicUri = content.withAppendedPath(content, this.id);

    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    albumartUri = ContentUris.withAppendedId(sArtworkUri, Integer.parseInt(this.albumid));
  }
}
