package org.birdback.histudents.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.birdback.histudents.R;
import org.birdback.histudents.base.BaseApplication;
import org.birdback.histudents.entity.MyMenuEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> implements View.OnClickListener {
    private List<MyMenuEntity.MenuBean> mData;


    public GridAdapter(List<MyMenuEntity.MenuBean> data) {
        this.mData = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview, parent, false);
        return new GridAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyMenuEntity.MenuBean menuBean = mData.get(position);
        holder.mName.setText(menuBean.getName());
        Glide.with(BaseApplication.getApplication()).load(menuBean.getIcon()).into(holder.mIcon);

        holder.itemView.setTag(String.valueOf(position));
        holder.itemView.setOnClickListener(this);
    }

    OnRecyclerViewListener mOnClickListener;
    public void setOnItemclickListener(OnRecyclerViewListener listener){
        this.mOnClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null){
            mOnClickListener.onItemClick(view,Integer.parseInt(view.getTag().toString()));
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mIcon;
        private final TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_name);
            mIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
