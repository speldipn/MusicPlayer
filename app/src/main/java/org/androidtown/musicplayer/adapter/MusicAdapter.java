package org.androidtown.musicplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.musicplayer.MainActivity;
import org.androidtown.musicplayer.R;
import org.androidtown.musicplayer.domain.Music;
import org.androidtown.musicplayer.player.MusicPlayer;
import org.androidtown.musicplayer.player.Player;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {
  private List<Music> list;
  private MusicPlayer mp;
  private MainActivity mainActivity;

  public MusicAdapter(Context ctx) {
    this.mainActivity = (MainActivity)ctx;
  }

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
    holder.position = position;
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
    Music music;
    ConstraintLayout layout;
    int position;

    public Holder(final View v) {
      super(v);
      image = v.findViewById(R.id.imgAlbumart);
      tvTitle = v.findViewById(R.id.textTitle);
      tvArtist = v.findViewById(R.id.textArtist);
      tvDuration = v.findViewById(R.id.textDuration);
      layout = v.findViewById(R.id.layoutItem);
      layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(v.getContext(), Player.class);
          intent.putExtra(Player.MUSIC_KEY, position);
          mainActivity.startActivityForResult(intent, Player.REQ_MUSIC);
        }
      });
    }
  }
}
