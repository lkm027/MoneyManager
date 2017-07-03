package com.example.lucas.moneymanager.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lucas.moneymanager.R;
import com.example.lucas.moneymanager.adapter.ItemAdapter;
import com.example.lucas.moneymanager.classes.Item;
import com.example.lucas.moneymanager.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class PurchaseLog extends AppCompatActivity {

    private DbHelper db;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_log);

        ListView listView = (ListView) findViewById(R.id.listView_purchase_items);
        List<Item> list = new ArrayList<Item>();


        db = new DbHelper(this);

        if (db.getAllGoals().size() != 0) {
            list = db.getAllGoals();
        }
        mAdapter = new ItemAdapter(list, this);
        listView.setAdapter(mAdapter);

    }

    /**
     * Tells our activity that the user has pressed the back key
     * @param view
     */
    public void returnToParentActivity(View view) {
        onBackPressed();
    }

    /**
     * Is notified when the activity notices the user press the abck key
     * Overrides the default slide animation when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
