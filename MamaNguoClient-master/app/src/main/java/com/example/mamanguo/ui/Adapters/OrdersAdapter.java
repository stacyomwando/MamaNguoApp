package com.example.mamanguo.ui.Adapters;

//Extend the RecyclerView.Adapter class//

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;
import com.example.mamanguo.Retrofit.Models.MamaNguo;


import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.CustomViewHolder> implements View.OnClickListener{

    private List<MamaNguo> dataList;
    private OnItemClickListener mOnItemClickListener;

    public OrdersAdapter(List<MamaNguo> dataList, OnItemClickListener onItemClickListener){
        this.dataList = dataList;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {

    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Get a reference to the Views in our layout//
        public final View myView;
        TextView textUser;
        TextView mamanguoId;
        TextView rateTextView;
        TextView requestStatus;
        RatingBar ratingBar;
        OnItemClickListener onItemClickListener;

        CustomViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            myView = itemView;
            this.onItemClickListener = onItemClickListener;
            textUser = myView.findViewById(R.id.user);
            mamanguoId = myView.findViewById(R.id.mamanguo_id);
            rateTextView = myView.findViewById(R.id.rating_text);
            requestStatus = myView.findViewById(R.id.request_status);
            myView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onCardClick(getAdapterPosition());
        }
    }

    private View view;
    @Override
//Construct a RecyclerView.ViewHolder//
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_choose_mamanguo_items, parent, false);
        return new CustomViewHolder(view, mOnItemClickListener);
    }

    //Set the data
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textUser.setText(dataList.get(position).getFullName());
        holder.mamanguoId.setText(String.format(Locale.getDefault(), "%d",dataList.get(position).getMamanguoId()));
        holder.requestStatus.setText(String.format("Order status: %s", dataList.get(position).getStatus()));
    }


    //Calculate the item count for the RecylerView//
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onCardClick(int position);
    }

}
