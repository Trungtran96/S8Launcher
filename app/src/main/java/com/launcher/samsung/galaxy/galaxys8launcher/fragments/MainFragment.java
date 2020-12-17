package com.launcher.samsung.galaxy.galaxys8launcher.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.activities.MainActivity;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.MyAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.models.S8TextView;
import com.launcher.samsung.galaxy.galaxys8launcher.models.SimpleItemTouchHelperCallback;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ultils;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class MainFragment extends Fragment {
    private ArrayList<App> arr = new ArrayList<>();
    private Context context;
    private int p;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private MyAdapter myAdapter;
    private RecyclerView rvApp;
    private S8TextView tvHour, tvMinute;
    private TextView tvDate;
    private RelativeLayout bgTime;
    private float sizeTime, sizeDate;
    private LinearLayout bgBg;
    private RelativeLayout bgFrag;
    private RecyclerView rvAppMain;
    private ArrayList<App> arrApp = new ArrayList<>();
    private MyAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private float rawX;
    private float rawY;
    private boolean isMoving;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight;
    private static final int REQUEST_ENABLE = 2;
    private int lastAssistiveTouchViewX;
    private int lastAssistiveTouchViewY;
    private Dialog dialogMain, dialogDevice, dialogFavour;
    private View mView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams  mParams;
    private AlertDialog.Builder mBulider;
    private AlertDialog mAlertDialog;
    private FrameLayout touch;
    private LayoutInflater mInflater;
    public static boolean isMove = false;

    public static final String TAG = "MainFragment";


    public MainFragment() {
    }

    public static MainFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        context = getContext();
        p = getArguments().getInt("position");
        arr.clear();
        arrApp.clear();

        Drawable drawable = context.getResources().getDrawable(R.drawable.trans);

        if (p==0){
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));

        } else {
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));
            arrApp.add(new App(drawable, "", ""));

        }


        initViews(v);

        if (p==0){
            bgTime.setVisibility(View.VISIBLE);
            setTime();
        } else {
            bgTime.setVisibility(View.GONE);
        }

//        init();
//        calculateForMyPhone();
//        createView();

        return v;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public Bitmap loadBitmapFromView(final View v) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();

        Bitmap b = Bitmap.createBitmap( display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private void initViews(View view){
        tvHour = (S8TextView) view.findViewById(R.id.tv_hour);
        tvMinute = (S8TextView) view.findViewById(R.id.tv_minute);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        bgTime = (RelativeLayout) view.findViewById(R.id.bg_time);
        bgBg = (LinearLayout) view.findViewById(R.id.bg_bg);
        bgFrag = (RelativeLayout) view.findViewById(R.id.bg_frag);

        sizeTime = tvHour.getTextSize();
        sizeDate = tvDate.getTextSize();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "swipe":
                        Bitmap bitmap = loadBitmapFromView(bgFrag);
                        MainActivity.arrBit.add(bitmap);
                        MainActivity.arrScreen.add(p);

                        if (p==MainActivity.count-1){
                            context.sendBroadcast(new Intent("done"));
                        }

                        break;
                    case "un_swipe":
                        break;
                    case "move_item":
                        String name = intent.getExtras().getString("what_name");
                        String packageName = intent.getExtras().getString("what_package");
                        App app = null;

                        for (int i=0; i<MainActivity.arr.size(); i++){
                            if (MainActivity.arr.get(i).getName().equals(name) && MainActivity.arr.get(i).getPackageName().equals(packageName)){
                                app = MainActivity.arr.get(i);
                                break;
                            }
                        }

                        if (p==MainActivity.current){
                            boolean isHave = false;
                            for (int i=0; i<arrApp.size(); i++){
                                if (arrApp.get(i).getName().equals(name)){
                                    isHave = true;
                                    break;
                                }
                            }
                            if (!isHave){
                                for (int i=0; i<arrApp.size(); i++){
                                    if (arrApp.get(i).getName().equals("")){
                                        arrApp.get(i).setName(app.getName());
                                        arrApp.get(i).setPackageName(app.getPackageName());
                                        arrApp.get(i).setIcon(app.getIcon());
                                        myAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        };
        filter = new IntentFilter();
        filter.addAction("swipe");
        filter.addAction("un_swipe");
        filter.addAction("move_item");
        context.registerReceiver(receiver, filter);

        if (p!=0){
            rvAppMain = (RecyclerView) view.findViewById(R.id.rv_app_main);
            myAdapter = new MyAdapter(context, arrApp, new MyAdapter.OnActionClick() {
                @Override
                public void onClicked(App app) {
                    if (!app.getName().equals("")) {
                        Ultils.goToAction(context, app);
                    }
                }
            }, new MyAdapter.OnActionLongClick() {
                @Override
                public void onLongClicked(App app) {
                    if (!app.getName().equals("")){
                        isMove = true;
                    } else {
                        isMove = false;
                    }
                }
            });

            GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            rvAppMain.setLayoutManager(layoutManager);

            rvAppMain.setAdapter(myAdapter);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(rvAppMain);
        } else {
            rvAppMain = (RecyclerView) view.findViewById(R.id.rv_app_main_2);
            myAdapter = new MyAdapter(context, arrApp, new MyAdapter.OnActionClick() {
                @Override
                public void onClicked(App app) {
                    if (!app.getName().equals("")) {
                        Ultils.goToAction(context, app);
                    }
                }
            }, new MyAdapter.OnActionLongClick() {
                @Override
                public void onLongClicked(App app) {
                    if (!app.getName().equals("")){
                        isMove = true;
                    } else {
                        isMove = false;
                    }
                }
            });

            GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            rvAppMain.setLayoutManager(layoutManager);

            rvAppMain.setAdapter(myAdapter);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(rvAppMain);
        }


    }


    @Override
    public void onResume() {
        if (MainActivity.bgUp.getVisibility()==View.VISIBLE){
            bgBg.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }

    private Handler handler;
    private void setTime(){

        final String[] days = new String[] { ""
                , context.getResources().getString(R.string.sun), context.getResources().getString(R.string.mon), context.getResources().getString(R.string.tue)
                , context.getResources().getString(R.string.wed), context.getResources().getString(R.string.thu), context.getResources().getString(R.string.fri)
                , context.getResources().getString(R.string.sat) };
        final String[] months = new String[] { "" , context.getResources().getString(R.string.jan), context.getResources().getString(R.string.feb), context.getResources().getString(R.string.mar)
                , context.getResources().getString(R.string.apr), context.getResources().getString(R.string.may), context.getResources().getString(R.string.jun)
                , context.getResources().getString(R.string.jul), context.getResources().getString(R.string.aug), context.getResources().getString(R.string.sep)
                , context.getResources().getString(R.string.oct), context.getResources().getString(R.string.nov), context.getResources().getString(R.string.dec) };
        handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour<10){
                    tvHour.setText("0"+hour);
                } else {
                    tvHour.setText(hour+"");
                }
                int minute = calendar.get(Calendar.MINUTE);
                if (minute<10){
                    tvMinute.setText("0"+minute);
                } else {
                    tvMinute.setText(minute+"");
                }

                tvDate.setText(days[calendar.get(Calendar.DAY_OF_WEEK)]+ ", "+ months[calendar.get(Calendar.MONTH)]);

                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateSize() {
        ViewGroup.LayoutParams params = touch.getLayoutParams();
        params.height = (int) Ultils.convertDpToPixel(50, context);
        params.width = (int) Ultils.convertDpToPixel(50, context);
        touch.setLayoutParams(params);

        mParams.x = mScreenWidth;
        mWindowManager.updateViewLayout(mView, mParams);
    }

    private void init(){
        mParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(R.layout.touch_view, null);

        touch = (FrameLayout) mView.findViewById(R.id.bt_touch);
        touch.setBackgroundResource(R.drawable.camera);
    }

    private void calculateForMyPhone(){
        DisplayMetrics displayMetrics = Ultils.getScreenSize(context);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mStatusBarHeight = Ultils.getStatusBarHeight(context);
    }

    private float currentX, currentY;

    public void createView(){
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mParams.x = mScreenWidth;
        mParams.y = 520;
        mParams.gravity = Gravity.TOP|Gravity.LEFT;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowManager.addView(mView, mParams);

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rawX = event.getRawX();
                rawY = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touch.setAlpha(1);
                        currentX = event.getX();
                        currentY = event.getY();
                        isMoving = false;
                        break;
                    case MotionEvent.ACTION_UP:
//                        touch.setAlpha((float) SharedPreferencesManager.getAlpha(context)/100);
                        setAssitiveTouchViewAlign();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touch.setAlpha(1);
                        isMoving = true;
                        if (Math.abs(currentX - event.getX())>40 || Math.abs(currentY - event.getY())>40){
                            mParams.x = (int) (rawX - mView.getMeasuredWidth() / 2);
                            mParams.y = (int) (rawY - mView.getMeasuredHeight() / 2 - mStatusBarHeight);
                            mWindowManager.updateViewLayout(mView, mParams);
                        } else {

                        }
                }
                if (isMoving){
                    return true;
                }

                return false;
            }
        });
    }

    private ValueAnimator myAssitiveTouchAnimator(final int fromx, final int tox, int fromy, final int toy, final boolean flag){
        PropertyValuesHolder p1 = PropertyValuesHolder.ofInt("X", fromx, tox);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofInt("Y", fromy, toy);
        ValueAnimator v1 = ValueAnimator.ofPropertyValuesHolder(p1, p2);
        v1.setDuration(100L);
        v1.setInterpolator(new DecelerateInterpolator());
        v1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer x = (Integer) animation.getAnimatedValue("X");
                Integer y = (Integer) animation.getAnimatedValue("Y");
                mParams.x = x;
                mParams.y = y;
                mWindowManager.updateViewLayout(mView, mParams);
            }
        });
        v1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (flag)
                    mView.setAlpha(0.85f);
            }
        });
        return v1;
    }

    private void setAssitiveTouchViewAlign(){
        int mViewWidth = mView.getMeasuredWidth();
        int mViewHeight = mView.getMeasuredHeight();
        int top = mParams.y + mViewWidth/2;
        int left = mParams.x + mViewHeight/2;
        int right = mScreenWidth - mParams.x - mViewWidth/2;
        int bottom = mScreenHeight - mParams.y - mViewHeight/2;
        int lor = Math.min(left, right);
        int tob = Math.min(top, bottom);
        int min = Math.min(lor, tob);
        lastAssistiveTouchViewX = mParams.x;
        lastAssistiveTouchViewY = mParams.y;
        if(min == top) mParams.y = 0;
        if(min == left) mParams.x = 0;
        if(min == right) mParams.x = mScreenWidth - mViewWidth;
        if(min == bottom) mParams.y = mScreenHeight - mViewHeight;
        myAssitiveTouchAnimator(lastAssistiveTouchViewX, mParams.x, lastAssistiveTouchViewY, mParams.y, false).start();
    }
}
