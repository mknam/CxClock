package com.example.cxclock;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private ClockView mClkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClkView = new ClockView(this);
        setContentView(mClkView);

        //# Hide Action Bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //getActionBar().hide();

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.hide();
            }
        }

        //# Hide Status Bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        //# Keep Screen On
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mClkView.isStop()) {
            mClkView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mClkView.stop();
    }
}
