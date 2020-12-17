package com.launcher.samsung.galaxy.galaxys8launcher.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.R;

import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/6/2017.
 */

public class SearchAdapter  extends  RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder> {
    private ArrayList<App> data = new ArrayList<>();
    private OnActionClick onActionClick;
    private View mview;

    public SearchAdapter(ArrayList<App> data, OnActionClick onActionClick) {
        this.data = data;
        this.onActionClick = onActionClick;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);

        return new RecyclerViewHolder(view);
    }

    public interface OnActionClick {
        void onClicked(App app);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.ivApp.setImageDrawable(data.get(position).getIcon());

        holder.btApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onActionClick.onClicked(data.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivApp;
        LinearLayout btApp;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivApp = (ImageView) itemView.findViewById(R.id.iv_app);
            btApp = (LinearLayout) itemView.findViewById(R.id.bt_app);

        }
    }
}