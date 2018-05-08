package org.birdback.histudents.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.birdback.histudents.R;

import java.util.List;

public class GoodsRightAdapter extends RecyclerView.Adapter<GoodsRightAdapter.ViewHolder>{
    private List<String> mRightData;
    private int selectedPosition = -1;

    public GoodsRightAdapter(List<String> leftData) {
        this.mRightData = leftData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_class, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvClassName.setText(mRightData.get(position));

        holder.itemView.setSelected(selectedPosition == position);
        if (selectedPosition == position) {
            holder.viewColor.setVisibility(View.VISIBLE);
            holder.llContent.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.viewColor.setVisibility(View.INVISIBLE);
            holder.llContent.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position; //选择的position赋值给参数，
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRightData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvClassName;
        private final View viewColor;
        private final LinearLayout llContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tv_class_name);
            viewColor = itemView.findViewById(R.id.view_color);
            llContent = itemView.findViewById(R.id.ll_content);
        }
    }
}
