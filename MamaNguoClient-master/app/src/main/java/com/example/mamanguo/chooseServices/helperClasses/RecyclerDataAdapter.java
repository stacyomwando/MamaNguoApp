package com.example.mamanguo.chooseServices.helperClasses;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamanguo.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    //Custom interface to keep track of the changes
    private ServicesSelectedListener mServicesSelectedListener;
    private Context mContext;
    //Data structures to hold the activity data
    private ArrayList<DummyParentDataItem> dummyParentDataItems;

    public RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems, ServicesSelectedListener servicesSelectedListener) {
        this.dummyParentDataItems = dummyParentDataItems;
        this.mServicesSelectedListener = servicesSelectedListener;
    }

    //e.g. Shirts: 2
    private void setServiceQuantity(String item, int quantity) {
        //To eliminate possibility of having duplicate key entries
        if (Order.serviceCount.containsKey(item)) Order.serviceCount.remove(item);
        Order.serviceCount.put(item, quantity);
    }

    private Map<String, Integer> getServiceCount() {
        return Order.serviceCount;
    }

    //Shirts: sh. 255
    private void setServiceSubtotal(String item, int subtotal) {
        //To eliminate possibility of having duplicate key entries
        if (Order.serviceTotal.containsKey(item)) Order.serviceTotal.remove(item);
        Order.serviceTotal.put(item, subtotal);
    }

    private void setUnitPrice(String item, int unitPrice) {
        //To eliminate possibility of having duplicate key entries
        if (Order.serviceUnitPrice.containsKey(item)) Order.serviceUnitPrice.remove(item);
        Order.serviceUnitPrice.put(item, unitPrice);
    }

    private Map<String, Integer> getServiceTotal() {
        return Order.serviceTotal;
    }

    private void removeItem(String item) {
        if (Order.serviceCount.containsKey(item)) Order.serviceCount.remove(item);
        if (Order.serviceTotal.containsKey(item)) Order.serviceTotal.remove(item);
        if (Order.serviceUnitPrice.containsKey(item)) Order.unitPrice.remove(item);
    }

    private Map<String, Integer> getUnitPrice() {
        return Order.serviceUnitPrice;
    }

    private void updateBillTotal() {
        int bill_total = 0;
        //Returns set view
        Set<Map.Entry<String, Integer>> set = Order.serviceTotal.entrySet();
        for (Map.Entry<String, Integer> me : set) {
            bill_total += me.getValue();
        }
        Order.setBillTotal(bill_total);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView, mServicesSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        String text = dummyParentDataItems.get(position).getChildDataItems().get(1).getChildName();
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        holder.field_value.setText(text);
        //holder.field_index.setText(Integer.toString(position));
        holder.subtotal_value.setText(mContext.getString(R.string.default_subtotal));

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
        }
    }

    @Override
    public int getItemCount() {
        return dummyParentDataItems.size();
    }

    public interface ServicesSelectedListener {
        void onServicesUpdated(Map<String, Integer> serviceUnitPrice,
                               Map<String, Integer> serviceCount,
                               Map<String, Integer> serviceTotal);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private TextView field_value;
        private TextView field_index;
        private TextView subtotal_value;
        private EditText editText_parent;
        private LinearLayout linearLayout_childItems;
        private String TAG = this.getClass().getSimpleName();

        //My custom interface to get that keeps track of the values entered
        ServicesSelectedListener servicesSelectedListener;

        public MyViewHolder(View itemView, final ServicesSelectedListener servicesSelectedListener) {
            super(itemView);
            this.servicesSelectedListener = servicesSelectedListener;
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            field_value = itemView.findViewById(R.id.field_value);
            field_index = itemView.findViewById(R.id.field_index);
            subtotal_value = itemView.findViewById(R.id.subtotal_value);
            editText_parent = itemView.findViewById(R.id.editText_parent);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);

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
                textView.setOnClickListener(this);
                linearLayout_childItems.addView(textView, layoutParams);
            }


            textView_parentName.setOnClickListener(this);
            editText_parent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence editText_value, int start, int before, int count) {
                    //String index = field_index.getText().toString();
                    int length = editText_value.toString().length();
                    int unitPrice = Integer.parseInt(field_value.getText().toString());
                    int noItems = length == 0 ? 0 : Integer.parseInt(editText_value.toString());
                    int subtotal = unitPrice * noItems;

                    String item = textView_parentName.getText().toString();
                    subtotal_value.setText(String.format(context.getString(R.string.new_subtotal), subtotal));
                    //Add the order items
                    setUnitPrice(item, unitPrice);
                    setServiceQuantity(item, noItems);
                    setServiceSubtotal(item, subtotal);
                    updateBillTotal();

                    //Remove item from order if field is empty
                    if (noItems == 0) removeItem(item);
                    //Update the info
                    servicesSelectedListener.onServicesUpdated(getUnitPrice(), getServiceCount(), getServiceTotal());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_parentName) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}