package com.launcher.samsung.galaxy.galaxys8launcher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.models.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<App> data = new ArrayList<>();
    private OnActionClick onActionClick;
    private OnActionLongClick onActionLongClick;
    private Context context;
    private OnStartDragListener onStartDragListener;
    private boolean isLong = false;

    public static final String TAG = "MyAdapter";


    public MyAdapter(Context context, ArrayList<App> data, OnActionClick onActionClick, OnActionLongClick onActionLongClick) {
        this.context = context;
        this.data = data;
        this.onActionClick = onActionClick;
        this.onActionLongClick = onActionLongClick;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);

    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public interface OnActionClick{
        void onClicked(App app);
    }

    public interface OnActionLongClick{
        void onLongClicked(App app);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.ivApp.setImageDrawable(data.get(position).getIcon());

        final App item = data.get(position);

        holder.tvName.setText(item.getName());
        holder.ivApp.setImageDrawable(item.getIcon());

        holder.bgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onActionClick.onClicked(item);
            }
        });

        holder.bgItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onActionLongClick.onLongClicked(item);
                return true;
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
        RelativeLayout bgItem;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivApp = (ImageView) itemView.findViewById(R.id.iv_app);
            bgItem = (RelativeLayout) itemView.findViewById(R.id.bg_item);
        }
    }
}
