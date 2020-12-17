package com.launcher.samsung.galaxy.galaxys8launcher.activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.models.HomeWatcher;
import com.launcher.samsung.galaxy.galaxys8launcher.models.OnHomePressedListener;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ads;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.SharedPreferencesManager;

import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/5/2017.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout btKichThuoc, btAnim, btRate;
    private Dialog dialog;
    private RelativeLayout ads;
    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        initViews();

        Ads.loadBannerAds(this, ads);
    }

    private void initViews() {
        btKichThuoc = (LinearLayout) findViewById(R.id.bt_size);
        btAnim = (LinearLayout) findViewById(R.id.bt_effect);
        btRate = (LinearLayout) findViewById(R.id.bt_rate);
        ads = (RelativeLayout) findViewById(R.id.layout_ads);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                mHomeWatcher.stopWatch();
                Ads.loadFullScreenAds(SettingActivity.this);
                finish();
            }
            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();

        btKichThuoc.setOnClickListener(this);
        btAnim.setOnClickListener(this);
        btRate.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        try {
            if (mHomeWatcher!=null){
                mHomeWatcher.stopWatch();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void showDialogSize(){
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_size, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final NumberPicker npHang = (NumberPicker) v.findViewById(R.id.np_hang);
        final NumberPicker npCot = (NumberPicker) v.findViewById(R.id.np_cot);

        npCot.setWrapSelectorWheel(false);
        npHang.setWrapSelectorWheel(false);

        TextView btOk = (TextView) v.findViewById(R.id.bt_ok);
        TextView btCancel = (TextView) v.findViewById(R.id.bt_cancel);
        TextView btDefault = (TextView) v.findViewById(R.id.bt_default);

        npCot.setMinValue(3);
        npCot.setMaxValue(5);
        npHang.setMinValue(3);
        npHang.setMaxValue(5);

        npCot.setValue(SharedPreferencesManager.getCot(this));
        npHang.setValue(SharedPreferencesManager.getHang(this));

        btDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.setCot(SettingActivity.this, 4);
                SharedPreferencesManager.setHang(SettingActivity.this, 5);
                Intent intent = new Intent("change_size");
                sendBroadcast(intent);
                Ads.loadFullScreenAds(SettingActivity.this);
                dialog.dismiss();
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.setCot(SettingActivity.this, npCot.getValue());
                SharedPreferencesManager.setHang(SettingActivity.this, npHang.getValue());
                Intent intent = new Intent("change_size");
                sendBroadcast(intent);
                Ads.loadFullScreenAds(SettingActivity.this);
                dialog.dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.loadFullScreenAds(SettingActivity.this);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.setContentView(v);
        dialog.show();
    }

    private void showDialogEffect(){
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_effect, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView lvEffect = (ListView) v.findViewById(R.id.lv_effect);

        ArrayList<String> arr = new ArrayList<>();

        arr.add(getResources().getString(R.string.Accordion));
        arr.add(getResources().getString(R.string.BackgroundToForeground));
        arr.add(getResources().getString(R.string.CubeIn));
        arr.add(getResources().getString(R.string.CubeOut));
        arr.add(getResources().getString(R.string.Default));
        arr.add(getResources().getString(R.string.DepthPage));
        arr.add(getResources().getString(R.string.DrawFromBack));
        arr.add(getResources().getString(R.string.FlipHorizontal));
        arr.add(getResources().getString(R.string.FlipVertical));
        arr.add(getResources().getString(R.string.ForegroundToBackground));
        arr.add(getResources().getString(R.string.RotateDown));
        arr.add(getResources().getString(R.string.RotateUp));
        arr.add(getResources().getString(R.string.Stack));
        arr.add(getResources().getString(R.string.Tablet));
        arr.add(getResources().getString(R.string.ZoomIn));
        arr.add(getResources().getString(R.string.ZoomOutSlide));
        arr.add(getResources().getString(R.string.ZoomOut));


        lvEffect.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTypeface(null, Typeface.NORMAL);
                text.setTextColor(Color.BLACK);
                return view;
            }
        });

        lvEffect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("change_anim");
                SharedPreferencesManager.setAnim(SettingActivity.this, i);
                sendBroadcast(intent);
                Ads.loadFullScreenAds(SettingActivity.this);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.setContentView(v);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_size:
                showDialogSize();
                break;
            case R.id.bt_effect:
                showDialogEffect();
                break;
            case R.id.bt_rate:
                Uri uri = Uri.parse("market://details?id=" + SettingActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + SettingActivity.this.getPackageName())));
                }
                Ads.loadFullScreenAds(SettingActivity.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_rate, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView btnOK = (TextView) v.findViewById(R.id.tv_ok);
        TextView btnNotNow = (TextView) v.findViewById(R.id.tv_not_now);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + SettingActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + SettingActivity.this.getPackageName())));
                }
                finish();
            }

        });

        btnNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.setContentView(v);
        dialog.show();
    }
}
