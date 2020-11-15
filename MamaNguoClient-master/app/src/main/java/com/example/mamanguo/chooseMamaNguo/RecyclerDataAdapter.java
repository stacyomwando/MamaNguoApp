package com.example.mamanguo.chooseMamaNguo;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mamanguo.ui.Activities.MapViewActivity;
import com.example.mamanguo.R;

import java.util.ArrayList;


class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    private ArrayList<DummyParentDataItem> dummyParentDataItems;
    public static int[] bill_total;
    private OnItemClickListener mListener;

    RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems) {
        this.dummyParentDataItems = dummyParentDataItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dummy_activity_choose_mamanguo_items, parent, false);
        final Context context = itemView.getContext();

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        float rating = dummyParentDataItem.getRating();
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        holder.ratingBar.setRating(rating);
        holder.textView_rating.setText("Rating: "+Float.toString(rating));

        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        for (int index = 0; index < noOfChildTextViews; index++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
            currentTextView.setVisibility(View.VISIBLE);
        }

        int noOfChild = dummyParentDataItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                currentTextView.setVisibility(View.GONE);
            }
        }

        for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(textViewIndex);
            currentTextView.setText(dummyParentDataItem.getChildDataItems().get(textViewIndex).getChildName());
                /*currentTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
        }
    }

    @Override
    public int getItemCount() {
        return dummyParentDataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private TextView textView_parentName;
        private LinearLayout linearLayout_childItems;
        private RatingBar ratingBar;
        private TextView textView_rating;
        private String TAG = this.getClass().getSimpleName();

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textView_rating = itemView.findViewById(R.id.textView_rating);
            bill_total = new int[dummyParentDataItems.size()];

            int intMaxNoOfChild = 0;
            for (int index = 0; index < dummyParentDataItems.size(); index++) {
                int intMaxSizeTemp = dummyParentDataItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }

            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                TextView textView = new TextView(context);
                textView.setId(indexView);
                textView.setPadding(0, 30, 0, 30);
                textView.setGravity(Gravity.CENTER);
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout_childItems.addView(textView, layoutParams);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                    Intent intent = new Intent(context, MapViewActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}