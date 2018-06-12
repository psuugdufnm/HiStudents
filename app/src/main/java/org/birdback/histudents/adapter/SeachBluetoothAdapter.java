package org.birdback.histudents.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.birdback.histudents.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/22.
 */

public class SeachBluetoothAdapter extends RecyclerView.Adapter<SeachBluetoothAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<BluetoothDevice> mData;
    public SeachBluetoothAdapter(ArrayList<BluetoothDevice> deviceList) {
        this.mData = deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blue_tooth, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BluetoothDevice device = mData.get(position);
        holder.tvBlueName.setText(device.getName());
        holder.tvBlueState.setText(device.getBondState() == BluetoothDevice.BOND_BONDED ? "[已绑定]":"[未绑定]");
        holder.tvBlueAddress.setText(device.getAddress());

        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public OnRecyclerViewListener mOnRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.mOnRecyclerViewListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewListener != null) {
            mOnRecyclerViewListener.onItemClick(v,Integer.parseInt(String.valueOf(v.getTag())));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvBlueName,tvBlueState,tvBlueAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBlueName = itemView.findViewById(R.id.tv_bluetooth_name);
            tvBlueState = itemView.findViewById(R.id.tv_bluetooth_state);
            tvBlueAddress = itemView.findViewById(R.id.tv_bluetooth_address);
        }
    }
}
