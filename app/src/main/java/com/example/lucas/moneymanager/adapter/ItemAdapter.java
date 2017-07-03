package com.example.lucas.moneymanager.adapter;

/**
 * Created by Lucas on 6/30/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucas.moneymanager.R;
import com.example.lucas.moneymanager.classes.Item;

import java.util.List;


public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private List<Item> mList;

    public ItemAdapter(List<Item> mList, Context mContext) {
        super(mContext, 0, mList);
        this.mContext = mContext;
        this.mList = mList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // if convertview is null we inflate our new view, else we recylce an old view
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_purchase_log, null);
        }

        //item position
        Item item = getItem(position);

        TextView itemDescription = (TextView) convertView.findViewById(R.id.textView_linearlayout_logItem);
        TextView itemAmount = (TextView) convertView.findViewById(R.id.textView_linearlayout_logAmount);
        itemDescription.setText(item.getItemDesc());
        itemAmount.setText(item.getDollarAmount());


        return convertView;
    }

//    /**
//     * Add a goal to our adapter
//     * @param item goal to be added
//     */
//    public void addItem(String item) {
//        mList.add(item);
//        notifyDataSetChanged();
//    }
}
