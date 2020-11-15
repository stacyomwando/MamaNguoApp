package com.example.mamanguo.chooseServices.helperClasses;

import android.content.Context;

import com.example.mamanguo.R;

import java.util.ArrayList;

public class ServicesLists {

    public static ArrayList<DummyParentDataItem> topsList(Context mContext) {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.shirt), childDataItems));

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.blouse), childDataItems));

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("45"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.t_shirt), childDataItems));

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.sweater), childDataItems));

        return arrDummyData;
    }

    public static ArrayList<DummyParentDataItem> bottomsList(Context mContext) {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.trousers), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.shorts), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.skirt), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("100"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.jeans), childDataItems));

        return arrDummyData;
    }

    public static ArrayList<DummyParentDataItem> fullBodyList(Context mContext) {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.dresses), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("55"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.jumpsuit), childDataItems));

        return arrDummyData;
    }

    public static ArrayList<DummyParentDataItem> shoesList(Context mContext) {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_pair)));
        childDataItems.add(new DummyChildDataItem("30"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.leather_shoes), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_pair)));
        childDataItems.add(new DummyChildDataItem("90"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.sport_shoes), childDataItems));
        /////////
        return arrDummyData;
    }

    public static ArrayList<DummyParentDataItem> accessoriesList(Context mContext) {
        ArrayList<DummyParentDataItem> arrDummyData = new ArrayList<>();
        ArrayList<DummyChildDataItem> childDataItems;

        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("20"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.scarves), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_pair)));
        childDataItems.add(new DummyChildDataItem("15"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.socks), childDataItems));
        /////////
        childDataItems = new ArrayList<>();
        childDataItems.add(new DummyChildDataItem(mContext.getString(R.string.price_per_item)));
        childDataItems.add(new DummyChildDataItem("15"));
        arrDummyData.add(new DummyParentDataItem(mContext.getString(R.string.ties), childDataItems));
        /////////
        return arrDummyData;
    }
}
