package org.androidtown.musicplayer;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    String[] permission = null;
    private final int REQ_PERM = 99;

    public BaseActivity(String[] permission) {
        this.permission = permission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }
    }

    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        boolean ok = true;
        for (int i = 0; i < permission.length; ++i) {
            if (checkSelfPermission(permission[i]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission, REQ_PERM);
                ok = false;
            }
        }
        if (ok) {
            init();
        }
    }

    public abstract void init();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_PERM) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한요청을 승인하셔야 사용가능합니다", Toast.LENGTH_LONG)
                        .show();

                    finish();
                }
            }
            // 권한을 얻었다면..
            init();
        }
    }
}