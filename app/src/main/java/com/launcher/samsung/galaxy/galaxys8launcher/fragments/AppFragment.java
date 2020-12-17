package com.launcher.samsung.galaxy.galaxys8launcher.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.activities.MainActivity;
import com.launcher.samsung.galaxy.galaxys8launcher.adapters.MyAdapter;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.SharedPreferencesManager;
import com.launcher.samsung.galaxy.galaxys8launcher.ultils.Ultils;

import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class AppFragment extends Fragment {
    private ArrayList<App> arr = new ArrayList<>();
    private Context context;
    private int p;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private RecyclerView rvApp;
    private MyAdapter myAdapter;
    private ItemTouchHelper itemTouchHelper;

    public static final String TAG = "MainFragment";


    public AppFragment() {
    }

    public static AppFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);

        AppFragment fragment = new AppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        p = getArguments().getInt("position");
        arr.clear();

        if (p < MainActivity.p2-1){
            for (int i = p*SharedPreferencesManager.getCot(context)*SharedPreferencesManager.getHang(context); i<p* SharedPreferencesManager.getCot(context)*SharedPreferencesManager.getHang(context)+SharedPreferencesManager.getCot(context)*SharedPreferencesManager.getHang(context); i++){
                arr.add(MainActivity.arr.get(i));
            }
        } else {
            for (int i=p*SharedPreferencesManager.getCot(context)*SharedPreferencesManager.getHang(context); i<MainActivity.arr.size(); i++){
                arr.add(MainActivity.arr.get(i));
            }
        }

        initViews(getView());
    }

    private void initViews(View view){
        rvApp = (RecyclerView) view.findViewById(R.id.rv_app);

        myAdapter = new MyAdapter(context, arr, new MyAdapter.OnActionClick() {
            @Override
            public void onClicked(App app) {
                Ultils.goToAction(context, app);
            }
        }, new MyAdapter.OnActionLongClick() {
            @Override
            public void onLongClicked(App app) {
                Intent intent = new Intent("move_item");
                intent.putExtra("what_name", app.getName());
                intent.putExtra("what_package", app.getPackageName());
                context.sendBroadcast(intent);
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(context, SharedPreferencesManager.getCot(context));
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvApp.setLayoutManager(layoutManager);

        rvApp.setAdapter(myAdapter);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "change_size_2":
                        GridLayoutManager layoutManager = new GridLayoutManager(context, SharedPreferencesManager.getCot(context));
                        myAdapter.notifyDataSetChanged();
                        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
                        rvApp.setLayoutManager(layoutManager);
                        break;
                }
            }
        };

        filter = new IntentFilter();
        filter.addAction("change_size_2");
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
