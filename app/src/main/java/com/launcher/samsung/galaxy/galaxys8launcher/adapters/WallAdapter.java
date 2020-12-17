package com.launcher.samsung.galaxy.galaxys8launcher.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.launcher.samsung.galaxy.galaxys8launcher.R;

import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/6/2017.
 */

public class WallAdapter extends RecyclerView.Adapter<WallAdapter.RecyclerViewHolderWall>  {
    private ArrayList<WallPaper> data = new ArrayList<>();
    private OnActionClick onActionClick;
    private View mview;

    public WallAdapter(ArrayList<WallPaper> data, OnActionClick onActionClick) {
        this.data = data;
        this.onActionClick = onActionClick;
    }

    @Override
    public RecyclerViewHolderWall onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_wal, parent, false);

        return new RecyclerViewHolderWall(view);
    }

    public interface OnActionClick {
        void onClicked(WallPaper wallPaper);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolderWall holder, final int position) {
        holder.ivWal.setImageResource(data.get(position).getSource());

        holder.ivWal.setOnClickListener(new View.OnClickListener() {
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


    public class RecyclerViewHolderWall extends RecyclerView.ViewHolder {
        ImageView ivWal;

        public RecyclerViewHolderWall(View itemView) {
            super(itemView);
            ivWal = (ImageView) itemView.findViewById(R.id.iv_wal);
        }
    }

    public static class WallPaper{
        int source;
        int source2;
        boolean isWall;

        public WallPaper(int source, int source2, boolean isWall) {
            this.source = source;
            this.source2 = source2;
            this.isWall = isWall;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getSource2() {
            return source2;
        }

        public void setSource2(int source2) {
            this.source2 = source2;
        }

        public boolean isWall() {
            return isWall;
        }

        public void setWall(boolean wall) {
            isWall = wall;
        }
    }
}
