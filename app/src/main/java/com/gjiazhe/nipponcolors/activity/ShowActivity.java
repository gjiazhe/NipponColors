package com.gjiazhe.nipponcolors.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjiazhe.nipponcolors.ColorAdapter;
import com.gjiazhe.nipponcolors.R;
import com.gjiazhe.nipponcolors.model.NipponColor;
import com.gjiazhe.ggprogressbar.GGArcProgressBar;
import com.gjiazhe.nipponcolors.widget.GGScrollNumberView;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView colorRecyclerView;
    private List<NipponColor> colorList = new ArrayList<>();
    private RelativeLayout root;
    private GGArcProgressBar c_ring;
    private GGArcProgressBar m_ring;
    private GGArcProgressBar y_ring;
    private GGArcProgressBar k_ring;
    private GGScrollNumberView r_number;
    private GGScrollNumberView g_number;
    private GGScrollNumberView b_number;
    private TextView tv_name_ja;
    private AutofitTextView tv_name_en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        //init Data
        colorList = NipponColor.getColorList(this);

        initView();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        c_ring.setProgress(2);
        m_ring.setProgress(43);
        y_ring.setProgress(3);
        k_ring.setProgress(0);
        r_number.setCurrentNumberOfInt(220);
        g_number.setCurrentNumberOfInt(159);
        b_number.setCurrentNumberOfInt(180);
        tv_name_ja.setText("撫子");
        tv_name_en.setText("NADESHIKO");
    }


    private void initView() {
        root = (RelativeLayout) findViewById(R.id.root);
        colorRecyclerView = (RecyclerView) findViewById(R.id.color_list);
        colorRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        c_ring = (GGArcProgressBar) findViewById(R.id.c_ring);
        m_ring = (GGArcProgressBar) findViewById(R.id.m_ring);
        y_ring = (GGArcProgressBar) findViewById(R.id.y_ring);
        k_ring = (GGArcProgressBar) findViewById(R.id.k_ring);

        r_number = (GGScrollNumberView) findViewById(R.id.r_number);
        g_number = (GGScrollNumberView) findViewById(R.id.g_number);
        b_number = (GGScrollNumberView) findViewById(R.id.b_number);

        tv_name_ja = (TextView) findViewById(R.id.color_name_ja);
        tv_name_en = (AutofitTextView) findViewById(R.id.color_name_en);
    }

    private void initAdapter() {
        ColorAdapter adapter = new ColorAdapter(this, colorList);
        adapter.setOnItemClickListener(new ColorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NipponColor nipponColor = colorList.get(position);
                int rgb = Color.parseColor('#' + nipponColor.getRgb());
                root.setBackgroundColor(rgb);
                c_ring.setProgress(nipponColor.getCmyk_c());
                m_ring.setProgress(nipponColor.getCmyk_m());
                y_ring.setProgress(nipponColor.getCmyk_y());
                k_ring.setProgress(nipponColor.getCmyk_k());
                r_number.setNumberSetOfInt(Color.red(rgb)).startAnim();
                g_number.setNumberSetOfInt(Color.green(rgb)).startAnim();
                b_number.setNumberSetOfInt(Color.blue(rgb)).startAnim();
                tv_name_ja.setText(nipponColor.getNameJA());
                tv_name_en.setText(nipponColor.getNameEN());
            }
        });

        colorRecyclerView.setAdapter(adapter);
    }
}