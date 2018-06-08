package org.androidtown.musicplayer;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidtown.musicplayer.adapter.MusicAdapter;
import org.androidtown.musicplayer.domain.MusicLoader;

public class MainActivity extends BaseActivity {

  RecyclerView recyclerView;

  // 필요한 권한
  private static String permissions[] = {
    Manifest.permission.READ_EXTERNAL_STORAGE
  };

  public MainActivity() {
    super(permissions);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void init() {
    setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.recyclerView);
    MusicLoader loader = new MusicLoader(this);
    MusicAdapter adapter = new MusicAdapter();
    adapter.setData(loader.getList());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}
