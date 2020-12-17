package com.launcher.samsung.galaxy.galaxys8launcher.activities;

import android.Manifest;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.DefaultApp;
import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.EditAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.MyAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.SearchAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.fragments.AppFragment;
import com.launcher.samsung.galaxy.galaxys8launcher.fragments.MainFragment;
import com.launcher.samsung.galaxy.galaxys8launcher.models.HomeWatcher;
import com.launcher.samsung.galaxy.galaxys8launcher.models.OnHomePressedListener;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ads;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.SharedPreferencesManager;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ultils;
import com.znanotech.ZAndroidSDK;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static ArrayList<App> arr = new ArrayList<>();
    public static ArrayList<App> arr2 = new ArrayList<>();
    private RelativeLayout bgApp;
    private RecyclerView rvApp;
    private ViewPager viewPagerMain, viewPagerApp;
    private ViewPagerAdapter adapterMain, adapterApp;
    private MyAdapter myAdapter;
    private ArrayList<DefaultApp> arrSnack = new ArrayList<>();
    private ImageView iv0, iv1, iv2, iv3;
    private TextView tv0, tv1, tv2, tv3;
    private ImageView btWall, btWidget, btSetting;
    private LinearLayout bgSnack;
    private HomeWatcher mHomeWatcher;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private GestureDetector gestureDetector;
    public static RelativeLayout bgUp, bgDown;
    private RecyclerView rvMain;
    public static ArrayList<Bitmap> arrBit = new ArrayList<>();
    private static ArrayList<Bitmap> arrBit2 = new ArrayList<>();
    private EditAdapter editAdapter;
    private ArrayList<App> arrSearch = new ArrayList<>();
    private EditText etSearch;
    private SearchAdapter searchAdapter;
    private RecyclerView rvResult;
    public static int count = 0;
    public static boolean isRv = false;
    public static ArrayList<Integer> arrScreen = new ArrayList<>();
    private ArrayList<Integer> arrScreen2 = new ArrayList<>();
    private RelativeLayout bg;
    private static final String[] PERMISSION = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Ultils.checkPermission(PERMISSION, MainActivity.this) != PackageManager.PERMISSION_GRANTED) {
                MainActivity.this.requestPermissions(PERMISSION, 1);
            }
        }

        arr.clear();
        arr2.clear();
        arr.addAll(Ultils.getAppInfor(this));
        arr2.addAll(Ultils.getAllApp(this));
        arrSnack.addAll(Ultils.getArrSnack(this));
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        initViews();

        ZAndroidSDK.init(this);
        Ads.loadFullScreenAds(this);
    }

    private void initViews() {
        viewPagerApp = (ViewPager) findViewById(R.id.viewpager_app);
        setupViewpagerApp();
        viewPagerMain = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewpagerMain();

        viewPagerMain.setOffscreenPageLimit(adapterMain.getCount());
        viewPagerApp.setOffscreenPageLimit(adapterApp.getCount());

        viewPagerMain.setPageTransformer(true, Ultils.getAnimation(SharedPreferencesManager.getAnim(MainActivity.this)));
        viewPagerApp.setPageTransformer(true, Ultils.getAnimation(SharedPreferencesManager.getAnim(MainActivity.this)));

        count = adapterMain.getCount();

        iv0 = (ImageView) findViewById(R.id.iv_1);
        iv1 = (ImageView) findViewById(R.id.iv_2);
        iv2 = (ImageView) findViewById(R.id.iv_3);
        iv3 = (ImageView) findViewById(R.id.iv_4);

        tv0 = (TextView) findViewById(R.id.tv_1);
        tv1 = (TextView) findViewById(R.id.tv_2);
        tv2 = (TextView) findViewById(R.id.tv_3);
        tv3 = (TextView) findViewById(R.id.tv_4);

        btWall = (ImageView) findViewById(R.id.bt_wallpaper);
        btWidget = (ImageView) findViewById(R.id.bt_widget);
        btSetting = (ImageView) findViewById(R.id.bt_setting);
        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        etSearch = (EditText) findViewById(R.id.et_search);
        rvResult = (RecyclerView) findViewById(R.id.rv_result);
        bg = (RelativeLayout) findViewById(R.id.bg);

        tv0.setText(arrSnack.get(0).getName());
        tv1.setText(arrSnack.get(1).getName());
        tv2.setText(arrSnack.get(2).getName());
        tv3.setText(arrSnack.get(3).getName());

        bgUp = (RelativeLayout) findViewById(R.id.bg_up);
        bgDown = (RelativeLayout) findViewById(R.id.bg_down);
        bgSnack = (LinearLayout) findViewById(R.id.bg_snack);

        iv0.setImageResource(arrSnack.get(0).getIcon());
        iv1.setImageResource(arrSnack.get(1).getIcon());
        iv2.setImageResource(arrSnack.get(2).getIcon());
        iv3.setImageResource(arrSnack.get(3).getIcon());

        bgApp = (RelativeLayout) findViewById(R.id.bg_app);

        iv0.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        btWall.setOnClickListener(this);
        btWidget.setOnClickListener(this);
        btSetting.setOnClickListener(this);

        bgApp.setVisibility(View.GONE);
        bgSnack.setVisibility(View.VISIBLE);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        bg.setBackground(wallpaperDrawable);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")){
                    if (trimSpace(charSequence.toString()).equals("")){
                        rvResult.setVisibility(View.GONE);
                        isRv = false;
                    } else {
                        arrSearch.clear();
                        for (int k=0; k<arr.size(); k++){
                            if (stripAccents(trimSpace(arr.get(k).getName()).toLowerCase()).contains(stripAccents(trimSpace(charSequence.toString().toLowerCase())))){
                                arrSearch.add(arr.get(k));
                            }
                        }

                        searchAdapter = new SearchAdapter(arrSearch, new SearchAdapter.OnActionClick() {
                            @Override
                            public void onClicked(App app) {
                                Ultils.goToAction(MainActivity.this, app);
                                isRv = false;
                                etSearch.setText("");
                            }
                        });

                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(LinearLayout.VERTICAL);
                        rvResult.setLayoutManager(layoutManager);

                        rvResult.setAdapter(searchAdapter);

                        rvResult.setVisibility(View.VISIBLE);

                        isRv = true;
                    }
                } else {
                    isRv = false;
                    rvResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                viewPagerMain.setCurrentItem(0);
                if (!(bgApp.getVisibility()==View.GONE)){
                    animation = (TranslateAnimation) AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_down);
                    bgApp.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            bgApp.clearAnimation();
                            etSearch.setText("");
                            rvResult.setVisibility(View.GONE);
                            bgApp.setVisibility(View.GONE);
                            isRv = false;
                            viewPagerApp.setCurrentItem(0);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "android.intent.action.TIME_SET":
                        Log.e(TAG, "time");
                        break;
                    case "done":
                        bgUp.setVisibility(View.VISIBLE);
                        bgDown.setVisibility(View.VISIBLE);
                        bgSnack.setVisibility(View.INVISIBLE);
                        viewPagerMain.setVisibility(View.GONE);

                        arrScreen2.clear();
                        arrBit2.clear();
                        if (count<=4){
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add);
                            arrBit2.add(bitmap);
                        }
                        for (int i=arrBit.size()-1; i>=0; i--){
                            if (!arrScreen2.contains(arrScreen.get(i))){
                                arrBit2.add(arrBit.get(i));
                                arrScreen2.add(arrScreen.get(i));
                            }
                        }

                        rvMain.setVisibility(View.VISIBLE);
                        editAdapter = new EditAdapter(MainActivity.this, arrBit2, new EditAdapter.OnActionClick() {
                            @Override
                            public void onClicked(int p) {
                                rvMain.setVisibility(View.GONE);
                                bgSnack.setVisibility(View.VISIBLE);
                                viewPagerMain.setVisibility(View.VISIBLE);
                                bgUp.setVisibility(View.GONE);
                                bgDown.setVisibility(View.GONE);
                                viewPagerMain.setCurrentItem(p);
                                if (p==count && count<=5){
                                    adapterMain.addFragment(MainFragment.newInstance(adapterMain.getCount()), "3");
                                    adapterMain.notifyDataSetChanged();
                                    count++;
                                    viewPagerMain.setCurrentItem(count-1);
                                } else {
                                    viewPagerMain.setCurrentItem(p);
                                }
                            }
                        });

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        rvMain.setLayoutManager(layoutManager);
                        rvMain.setAdapter(editAdapter);
                        break;
                    case "change_anim":
                        viewPagerMain.setPageTransformer(true, Ultils.getAnimation(SharedPreferencesManager.getAnim(MainActivity.this)));
                        viewPagerApp.setPageTransformer(true, Ultils.getAnimation(SharedPreferencesManager.getAnim(MainActivity.this)));
                        break;
                    case "move_item":
                        if (!(bgApp.getVisibility()==View.GONE)){
                            animation = (TranslateAnimation) AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_down);
                            bgApp.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    bgApp.clearAnimation();
                                    bgApp.setVisibility(View.GONE);
                                    rvResult.setVisibility(View.GONE);
                                    viewPagerApp.setCurrentItem(0);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                        break;
                    case "change_size":
                        setupViewpagerApp();
                        sendBroadcast(new Intent("change_size_2"));
                        adapterApp.notifyDataSetChanged();
                        break;
                    case "change_wall":
                        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                        bg.setBackground(wallpaperDrawable);
                        break;
                    case "new_app":
//                        Log.e(TAG, "vv");
                        arr.clear();
                        arr.addAll(Ultils.getAppInfor(MainActivity.this));
                        setupViewpagerApp();
                        adapterApp.notifyDataSetChanged();
                        break;
                }
            }
        };
        filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_SET");
        filter.addAction("done");
        filter.addAction("change_anim");
        filter.addAction("move_item");
        filter.addAction("change_size");
        filter.addAction("change_wall");
        filter.addAction("new_app");
        filter.addAction(Intent.ACTION_INSTALL_PACKAGE);
        registerReceiver(receiver, filter);
    }

    public String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.replace("đ", "d");
        s = s.replace("Đ", "D");
        return s;
    }

    public String trimSpace(String str)
    {
        str = str.replaceAll("\\s+", " ");
        str = str.replaceAll("(^\\s+|\\s+$)", "");
        return str;
    }

    @Override
    public void onClick(View view) {
        App app;
        switch (view.getId()){
            case R.id.iv_1:
                app = new App(null, getResources().getString(R.string.phone), "");
                Ultils.goToAction(MainActivity.this, app);
                break;
            case R.id.iv_2:
                app = new App(null, getResources().getString(R.string.mess), "");
                Ultils.goToAction(MainActivity.this, app);
                break;
            case R.id.iv_3:
                app = new App(null, getResources().getString(R.string.internet), "");
                Ultils.goToAction(MainActivity.this, app);
                break;
            case R.id.iv_4:
                app = new App(null, getResources().getString(R.string.camera), "");
                Ultils.goToAction(MainActivity.this, app);
                break;
            case R.id.bt_wallpaper:
                startActivity(new Intent(MainActivity.this, WallpaperActivity.class));
                break;
            case R.id.bt_widget:
                Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
                pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 12);
                startActivityForResult(pickIntent, 13);
                break;
            case R.id.bt_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        arrBit.clear();
        if (bgUp.getVisibility()==View.VISIBLE){
            bgUp.setVisibility(View.GONE);
            bgDown.setVisibility(View.GONE);
            bgSnack.setVisibility(View.VISIBLE);
            rvMain.setVisibility(View.GONE);
            viewPagerMain.setVisibility(View.VISIBLE);
            sendBroadcast(new Intent("un_swipe"));

        }
        rvResult.setVisibility(View.GONE);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        arrBit.clear();
        try {
            if (mHomeWatcher!=null){
                mHomeWatcher.stopWatch();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (bgUp.getVisibility()==View.VISIBLE){
            bgUp.setVisibility(View.GONE);
            bgDown.setVisibility(View.GONE);
            bgSnack.setVisibility(View.VISIBLE);
            sendBroadcast(new Intent("un_swipe"));
            viewPagerMain.setVisibility(View.VISIBLE);
            rvMain.setVisibility(View.GONE);
        }
        if (isRv){
            rvResult.setVisibility(View.GONE);
            etSearch.setText("");
        } else {
            if (!(bgApp.getVisibility()==View.GONE)){
                animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_down);
                bgApp.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        bgApp.clearAnimation();
                        bgApp.setVisibility(View.GONE);
                        rvResult.setVisibility(View.GONE);
                        viewPagerApp.setCurrentItem(0);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
    }

    private int xDown, yDown;
    private TranslateAnimation animation;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector.onTouchEvent(ev)) {
            gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {

                    return false;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    return false;
                }
            });
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = (int) ev.getX();
                yDown = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (!isRv){
                    if (Math.abs(xDown - ev.getX())<250 && !MainFragment.isMove){
                        if (ev.getY()-yDown>250){
                            if (bgApp.getVisibility() == View.GONE){
                                bgApp.setVisibility(View.VISIBLE);
                                animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_up);
                                bgApp.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        bgApp.clearAnimation();
                                        bgApp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                            } else {
                                animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_down);
                                bgApp.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        bgApp.clearAnimation();
                                        rvResult.setVisibility(View.GONE);
                                        bgApp.setVisibility(View.GONE);
                                        viewPagerApp.setCurrentItem(0);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                            }
                        } else if (yDown - ev.getY()>250){
                            if (bgApp.getVisibility() == View.GONE){
                                bgApp.setVisibility(View.VISIBLE);
                                animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_up_2);
                                bgApp.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        bgApp.clearAnimation();
                                        bgApp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            } else {
                                animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_down_2);
                                bgApp.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        bgApp.clearAnimation();
                                        rvResult.setVisibility(View.GONE);
                                        bgApp.setVisibility(View.GONE);
                                        viewPagerApp.setCurrentItem(0);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                            }
                        }
                    }
                }

                if (MainFragment.isMove){
                    MainFragment.isMove = false;
                }
                break;
        }
        ;
        return super.dispatchTouchEvent(ev);
    }

    public static int current = 0;
    private void setupViewpagerMain(){
        adapterMain = new ViewPagerAdapter(getSupportFragmentManager());
        adapterMain.addFragment(MainFragment.newInstance(0), "0");
        adapterMain.addFragment(MainFragment.newInstance(1), "1");
        adapterMain.addFragment(MainFragment.newInstance(2), "2");

        viewPagerMain.setAdapter(adapterMain);

        viewPagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static int p2;
    private void setupViewpagerApp(){
        adapterApp = new ViewPagerAdapter(getSupportFragmentManager());

        int countApp = arr.size();
        int countPage = countApp/(SharedPreferencesManager.getCot(this)*SharedPreferencesManager.getHang(this));
        if (countApp%(SharedPreferencesManager.getCot(this)*SharedPreferencesManager.getHang(this))>0){
            countPage++;
        }

        p2 = countPage;

        for (int i=0; i<countPage; i++){
            adapterApp.addFragment(AppFragment.newInstance(i), (i)+"");
        }


        viewPagerApp.setAdapter(adapterApp);

        viewPagerApp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }



        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onPause() {
        arrBit.clear();
        super.onPause();
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (bgApp.getVisibility()==View.GONE && !MainFragment.isMove){
                arrBit.clear();
                arrScreen.clear();
                sendBroadcast(new Intent("swipe"));
            }

            super.onLongPress(e);
        }
    }
}
