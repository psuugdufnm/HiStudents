package org.birdback.histudents.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.entity.OrderListEntity;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

class GoodListAdapter extends RecyclerView.Adapter<GoodListAdapter.ViewHolder> {
    private List<OrderListEntity.GrabListBean.GoodsListBean> mData;

    public GoodListAdapter(List<OrderListEntity.GrabListBean.GoodsListBean> goods_list) {
        mData = goods_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_list, parent, false);
        return new GoodListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderListEntity.GrabListBean.GoodsListBean goodsListBean = mData.get(position);
        holder.tvGoodName.setText(goodsListBean.getName());
        holder.tvGoodNum.setText("x".concat(String.valueOf(goodsListBean.getNum())));
        holder.tvGoodPrice.setText(String.valueOf(goodsListBean.getPrice()));

        String showDesc = goodsListBean.getShowDesc();
        if (!VerifyUtil.isEmpty(showDesc)){
            holder.tvGoodDesc.setVisibility(View.VISIBLE);
            holder.tvGoodDesc.setText(showDesc);
        }else {
            holder.tvGoodDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGoodName;
        private final TextView tvGoodNum;
        private final TextView tvGoodPrice;
        private final TextView tvGoodDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            tvGoodName = itemView.findViewById(R.id.tv_good_name);
            tvGoodNum = itemView.findViewById(R.id.tv_good_num);
            tvGoodPrice = itemView.findViewById(R.id.tv_good_price);
            tvGoodDesc = itemView.findViewById(R.id.tv_good_desc);
        }
    }

}
