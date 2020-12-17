package com.launcher.samsung.galaxy.galaxys8launcher.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.launcher.samsung.galaxy.galaxys8launcher.R;

import java.util.ArrayList;

/**
 * Created by Trung Tran Thanh on 10/5/2017.
 */

public class EditAdapter  extends RecyclerView.Adapter<EditAdapter.RecyclerViewHolder>{
    private ArrayList<Bitmap> data = new ArrayList<>();
    private OnActionClick onActionClick;
    private Context context;
    private boolean isLong = false;

    public static final String TAG = "EditAdapter";

    public EditAdapter(Context context, ArrayList<Bitmap> data, OnActionClick onActionClick) {
        this.context = context;
        this.data = data;
        this.onActionClick = onActionClick;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.edit_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    public interface OnActionClick{
        void onClicked(int p);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        final Bitmap item = data.get(data.size()-1-position);

        holder.ivEdit.setImageBitmap(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onActionClick.onClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEdit;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ivEdit = (itemView.findViewById(R.id.iv_edit));
        }
    }
}
