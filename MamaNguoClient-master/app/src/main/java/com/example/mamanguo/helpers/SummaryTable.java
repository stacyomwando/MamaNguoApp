package com.example.mamanguo.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mamanguo.R;

import java.util.Locale;

public class SummaryTable {
    Context context;

    public SummaryTable(Context context) {
        this.context = context;
    }

    public TableRow itemRow(String item, int unitPrice, String subtotal) {
        TableRow tableRow = new TableRow(context);
        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(layoutParams);


        return tableRow;
    }

    public TableRow grandTotalRow(int billTotal) {
        TableRow tableRow = new TableRow(context);

        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(layoutParams);

        // Add a TextView in the first column.
        TextView textView = new TextView(context);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 3;
        textView.setLayoutParams(params);
        textView.setText(context.getString(R.string.grand_total_text));
        textView.setTypeface(null, Typeface.BOLD);
        tableRow.addView(textView, 0);

        // Add a TextView in the first column.
        TextView billTotalText = new TextView(context);
        billTotalText.setText(String.format(Locale.getDefault(), "%d", billTotal));
        billTotalText.setTypeface(null, Typeface.BOLD);
        tableRow.addView(billTotalText, 1);

        return tableRow;
    }

    public TableRow createRow(String item, String unitPrice, String quantity, String subtotal) {
        TableRow tableRow = new TableRow(context);

        // Set new table row layout parameters.
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);

        // Add four columns with TextViews
        TextView itemText = new TextView(context);
        itemText.setText(item);
        tableRow.addView(itemText, 0);

        TextView unitPriceText = new TextView(context);
        unitPriceText.setText(unitPrice);
        tableRow.addView(unitPriceText, 1);

        TextView quantityText = new TextView(context);
        quantityText.setText(quantity);
        tableRow.addView(quantityText, 2);

        TextView subtotalText = new TextView(context);
        subtotalText.setText(subtotal);
        tableRow.addView(subtotalText, 3);

        return tableRow;
    }

}
