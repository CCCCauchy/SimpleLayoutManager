package com.example.administrator.simplelayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WangXing on 2017/10/9.
 */

public class SimpleAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_CENTER = 0;
    private final int VIEW_TYPE_OUTSIDE = 1;
    private int centerX;
    private int centerY;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        centerX = parent.getWidth() / 2;
        centerY = parent.getHeight() / 2;
        SimpleViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_CENTER:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_center, parent, false));
                break;
            case VIEW_TYPE_OUTSIDE:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false));
                break;
            default:
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
        simpleViewHolder.textView.setText("测试测试" + position);
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleViewHolder.textView.setBackgroundResource(R.mipmap.green_cicle);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_CENTER;
        else return VIEW_TYPE_OUTSIDE;
    }

    @Override
    public int getItemCount() {
        return 17;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
