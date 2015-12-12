package com.gjiazhe.nipponcolors.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gjiazhe.nipponcolors.R;
import com.gjiazhe.nipponcolors.model.NipponColor;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        NipponColor.initColorData(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, ShowActivity.class));
                finish();
            }
        }, 3000);

    }
}
