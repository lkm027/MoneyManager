package com.lucas.moneymanager.adapter;

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

import com.lucas.moneymanager.R;
import com.lucas.moneymanager.classes.Item;

import org.w3c.dom.Text;

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

        //grab our textviews from our convertview and populate them with our data
        TextView itemDate = (TextView) convertView.findViewById(R.id.textView_linearlayout_logDate);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.textView_linearlayout_logItem);
        TextView itemAmount = (TextView) convertView.findViewById(R.id.textView_linearlayout_logAmount);
        itemDescription.setText(item.getItemDesc());
        itemAmount.setText(item.getDollarAmount());
        itemDate.setText(item.getDate());


        return convertView;
    }

    public void removeAll() {
        mList.removeAll(mList);
        notifyDataSetChanged();
    }

    public void addToList(Item item) {
        mList.add(item);
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
