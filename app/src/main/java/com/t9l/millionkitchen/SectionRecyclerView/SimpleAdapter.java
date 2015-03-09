package com.t9l.millionkitchen.SectionRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.t9l.millionkitchen.R;
import com.t9l.millionkitchen.dao.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<FoodItem> mData;

    public void add(FoodItem item,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView remainingQuantity;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_name);
            remainingQuantity=(TextView)view.findViewById(R.id.item_quantity);
        }
    }

    public SimpleAdapter(Context context, ArrayList<FoodItem> data) {
        mContext = context;
        if (data != null)
            mData = data;
        else mData = new ArrayList<FoodItem>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.section_child, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        if(position<mData.size()) {
            holder.title.setText(mData.get(position).getItemName());
            holder.remainingQuantity.setText("Quantity Left : "+mData.get(position).getRemainingQuantity());
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Position =" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}