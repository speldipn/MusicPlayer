package org.androidtown.musicplayer.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.musicplayer.R;
import org.androidtown.musicplayer.domain.Music;
import org.androidtown.musicplayer.player.MusicPlayer;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {
  private List<Music> list;
  private MusicPlayer mp;
  private ImageButton prevBtnPlay = null;
  private ImageButton prevBtnStop = null;
  private boolean pushedPlay = false;

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    Music music = list.get(position);
    holder.tvTitle.setText(music.title);
    holder.tvArtist.setText(music.artist);
    long duration = music.duration / 1000;
    holder.tvDuration.setText((duration / 60) + "m:" + (duration % 60) + "s");
    holder.image.setImageURI(music.albumartUri);
    holder.music = music;
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setData(List list) {
    this.list = list;
  }

  public class Holder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView tvTitle;
    TextView tvArtist;
    TextView tvDuration;
    ImageButton btnPlay;
    ImageButton btnStop;
    Music music;

    public Holder(final View v) {
      super(v);
      image = v.findViewById(R.id.imgAlbumart);
      tvTitle = v.findViewById(R.id.textTitle);
      tvArtist = v.findViewById(R.id.textArtist);
      tvDuration = v.findViewById(R.id.textDuration);
      btnPlay = v.findViewById(R.id.btnPlay);
      btnPlay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(prevBtnStop != null && pushedPlay) {
            prevBtnStop.setVisibility(View.GONE);
            prevBtnPlay.setVisibility(View.VISIBLE);
          }
          btnPlay.setVisibility(View.GONE);
          btnStop.setVisibility(View.VISIBLE);
          prevBtnPlay = btnPlay;
          prevBtnStop = btnStop;
          play(v.getContext(), music.musicUri);
        }
      });
      btnStop = v.findViewById(R.id.btnStop);
      btnStop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          btnStop.setVisibility(View.GONE);
          btnPlay.setVisibility(View.VISIBLE);
          stop();
        }
      });
    }

    public void play(Context ctx, Uri musicUri) {
      mp.set(ctx, music.musicUri);
      mp.play();
      pushedPlay = true;
    }

    public void stop() {
      mp.stop();
      pushedPlay = false;
    }
  }
}
