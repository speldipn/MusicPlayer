package org.androidtown.musicplayer.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.musicplayer.R;
import org.androidtown.musicplayer.domain.Music;

import java.util.List;

/**
 * Author : Joonyoung Oh
 * Since  : 2018-06-14
 */
public class PlayerAdapter extends PagerAdapter {
    List<Music> list;

    public PlayerAdapter(List<Music> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    // View inflation fucntion
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item, container, false);

        Music music = list.get(position);

        ImageView imgAlbum = view.findViewById(R.id.imgAlbum);
        imgAlbum.setImageURI(music.albumartUri);

        TextView textTitle = view.findViewById(R.id.textTitle);
        textTitle.setText(music.title);

        TextView textArtist = view.findViewById(R.id.textArtist);
        textArtist.setText(music.artist);

        // 컨테이너에 뷰를 추가
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 컨테이너에 추가되었던 뷰를 삭제
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // TODO : 이건 무엇??
        return view == object;
    }
}
