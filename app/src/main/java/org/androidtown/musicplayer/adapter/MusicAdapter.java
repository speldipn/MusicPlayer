package org.androidtown.musicplayer.adapter;

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
  List<Music> list;
  MusicPlayer musicPlayer;

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

    public Holder(final View itemView) {
      super(itemView);
      image = itemView.findViewById(R.id.imgAlbumart);
      tvTitle = itemView.findViewById(R.id.textTitle);
      tvArtist = itemView.findViewById(R.id.textArtist);
      tvDuration = itemView.findViewById(R.id.textDuration);
      btnPlay = itemView.findViewById(R.id.btnPlay);
      btnPlay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          for(int i = 0; i < list.size(); ++i) {
            Music m = list.get(i);
            if(m.id != music.id) {

            }
          }
          btnPlay.setVisibility(View.GONE);
          btnStop.setVisibility(View.VISIBLE);
          musicPlayer.set(itemView.getContext(), music.musicUri);
          musicPlayer.play();
        }
      });
      btnStop = itemView.findViewById(R.id.btnStop);
      btnStop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          btnStop.setVisibility(View.GONE);
          btnPlay.setVisibility(View.VISIBLE);
          musicPlayer.stop();
        }
      });
    }
  }
}
