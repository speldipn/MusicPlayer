package org.androidtown.musicplayer.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import org.androidtown.musicplayer.R;
import org.androidtown.musicplayer.adapter.PlayerAdapter;
import org.androidtown.musicplayer.domain.Music;
import org.androidtown.musicplayer.domain.MusicLoader;

import java.util.List;

public class Player extends AppCompatActivity {

  public static final String MUSIC_KEY = "MUSIC";
  public static final int REQ_MUSIC = 5555;

  ViewPager viewPager;
  PlayerAdapter playerAdapter;
  MusicLoader musicLoader;
  MusicPlayer mp;
  List list;
  ImageButton btnPlay, btnStop, btnForward, btnReward, btnPresious, btnNext;
  SeekBar seekBar;
  Thread threadDuration;

  private boolean isPlaying = false;
  int position = (-1);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);

    init();
  }

  private void init() {
    // 플레이어 생성
    mp = new MusicPlayer();

    // 뷰페이저 설정
    viewPager = findViewById(R.id.viewPager);
    musicLoader = new MusicLoader(this);
    list = musicLoader.getList();
    playerAdapter = new PlayerAdapter(list);
    viewPager.setAdapter(playerAdapter);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });


    // 페이저 인덱스 지정
    Intent intent = getIntent();
    position = intent.getIntExtra(MUSIC_KEY, (-1));
    viewPager.setCurrentItem(position);

    // 버튼 설정
    btnPlay = findViewById(R.id.btnPlay);
    btnStop = findViewById(R.id.btnStop);
    btnForward = findViewById(R.id.btnForward);
    btnReward = findViewById(R.id.btnReward);
    btnPresious = findViewById(R.id.btnPresious);
    btnNext = findViewById(R.id.btnNext);

    // 자동 재생
    btnPlay.callOnClick();

    // seekBar
    seekBar = findViewById(R.id.seekBar);

    // TODO: PLAY 로 옮겨야함
    threadDuration = new Thread(new Runnable() {
      @Override
      public void run() {
        Music music = (Music)list.get(position);
        int totalDuration = (int)music.duration;
        seekBar.setMax(totalDuration);
        do {
          int duration = mp.duration();
          if(duration == (-1)) {
            break;
          }
          seekBar.setProgress(duration);
        }while(isPlaying == true && mp.duration() <= totalDuration);
      }
    });
    threadDuration.start();
  }

  public void play(Context ctx, Uri musicUri) {
    mp.set(ctx, musicUri);
    mp.play();
    isPlaying = true;
  }

  public void stop() {
    mp.stop();
    isPlaying = false;
  }

  public void release() {
    if (mp != null) {
      mp.release();
    }
  }

  public void command(View v) {
    Music music = (Music) list.get(position);
    switch (v.getId()) {
      case R.id.btnPlay:
        btnPlay.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        play(v.getContext(), music.musicUri);
        break;
      case R.id.btnStop:
        btnStop.setVisibility(View.GONE);
        btnPlay.setVisibility(View.VISIBLE);
        stop();
        break;
      case R.id.btnForward:
        break;
      case R.id.btnReward:
        break;
      case R.id.btnPresious:
        break;
      case R.id.btnNext:
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    isPlaying = false;
  }
}
