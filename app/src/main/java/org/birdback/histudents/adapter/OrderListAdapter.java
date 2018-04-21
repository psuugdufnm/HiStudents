package org.birdback.histudents.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.base.BaseApplication;
import org.birdback.histudents.entity.OrderListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> implements View.OnClickListener {
    private List<OrderListEntity.GrabListBean> mData;
    public OrderListAdapter(List<OrderListEntity.GrabListBean> data){
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderListEntity.GrabListBean entity = mData.get(position);


        holder.tvNo.setText("#".concat(entity.getOrder_num()));
        holder.tvName.setText(entity.getAddr_name().concat("(" +entity.getAddr_sex() + ")"));
        holder.tvAddress.setText(entity.getAddress());
        holder.tvPayPrice.setText(entity.getPay_price());
        holder.tvRealPrice.setText("￥".concat(entity.getReal_price()));
        holder.tvRebate.setText(String.valueOf(entity.getRebate()));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getApplication()));
        holder.recyclerView.setAdapter(new GoodListAdapter(entity.getGoods_list()));

        int sum = 0;
        List<OrderListEntity.GrabListBean.GoodsListBean> goods_list = entity.getGoods_list();
        for (int i = 0; i < goods_list.size(); i++) {
            sum += goods_list.get(i).getNum();
        }
        holder.tvNum.setText(String.valueOf(sum + "件商品"));
        holder.tvRemake.setText(entity.getRemark());

        holder.tvPhone.setTag(String.valueOf(position));
        holder.btnJiedan.setTag(String.valueOf(position));
        holder.tvPhone.setOnClickListener(this);
        holder.btnJiedan.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewListener != null) {
            mOnRecyclerViewListener.onItemClick(v,Integer.parseInt(String.valueOf(v.getTag())));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public OnRecyclerViewListener mOnRecyclerViewListener;


    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.mOnRecyclerViewListener = l;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNo;//单序号
        private final TextView tvName;//姓名
        private final TextView tvPhone;//手机号
        private final TextView tvAddress;//配送地址
        private final TextView tvNum; //商品件数
        private final TextView tvPayPrice; //顾客支付金额
        private final TextView tvRealPrice; //预计收益
        private final TextView tvRebate; //优惠金额
        private final TextView tvRemake; //备注
        private final Button btnJiedan; //接单
        private final RecyclerView recyclerView;//商品列表


        public ViewHolder(View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvPayPrice = itemView.findViewById(R.id.tv_pay_price);
            tvRealPrice = itemView.findViewById(R.id.tv_real_price);
            tvRebate = itemView.findViewById(R.id.tv_rebate);
            tvRemake = itemView.findViewById(R.id.tv_remake);
            btnJiedan = itemView.findViewById(R.id.btn_jiedan);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            //禁用滑动事件
            recyclerView.setNestedScrollingEnabled(false);
        }


    }
}
