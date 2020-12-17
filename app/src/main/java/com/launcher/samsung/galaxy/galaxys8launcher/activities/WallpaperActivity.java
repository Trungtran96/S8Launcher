package com.launcher.samsung.galaxy.galaxys8launcher.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.WallAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.models.HomeWatcher;
import com.launcher.samsung.galaxy.galaxys8launcher.models.OnHomePressedListener;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ads;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/5/2017.
 */

public class WallpaperActivity extends AppCompatActivity {
    private RecyclerView rvWal;
    private ArrayList<WallAdapter.WallPaper> arr = new ArrayList<>();
    private WallAdapter adapter;
    private RelativeLayout ads;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        Window window = WallpaperActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        initViews();

        Ads.loadBannerAds(this, ads);
    }

    private void initViews() {
        rvWal = (RecyclerView) findViewById(R.id.rv_wal);
        ads = (RelativeLayout) findViewById(R.id.layout_ads);

        final HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                mHomeWatcher.stopWatch();
                Ads.loadFullScreenAds(WallpaperActivity.this);
                finish();
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

        arr.add(new WallAdapter.WallPaper(R.drawable.wall_1, R.drawable.wall_1_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_2, R.drawable.wall_2_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_3, R.drawable.wall_3_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_4, R.drawable.wall_4_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_5, R.drawable.wall_5_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_6, R.drawable.wall_6_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_7, R.drawable.wall_7_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_8, R.drawable.wall_8_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_9, R.drawable.wall_9_2, false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_10, R.drawable.wall_10_2,  false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_11, R.drawable.wall_11_2,  false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_12, R.drawable.wall_12_2,  false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_13, R.drawable.wall_13_2,  false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_14, R.drawable.wall_14_2,  false));
        arr.add(new WallAdapter.WallPaper(R.drawable.wall_15, R.drawable.wall_15_2,  false));
        adapter = new WallAdapter(arr, new WallAdapter.OnActionClick() {
            @Override
            public void onClicked(WallAdapter.WallPaper wallPaper) {
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setResource(wallPaper.getSource2());
                    Intent intent = new Intent("change_wall");
                    sendBroadcast(intent);
                    Ads.loadFullScreenAds(WallpaperActivity.this);
                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvWal.setLayoutManager(layoutManager);

        rvWal.setAdapter(adapter);
    }
}
