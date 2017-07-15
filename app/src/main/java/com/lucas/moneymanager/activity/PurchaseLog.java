package com.lucas.moneymanager.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lucas.moneymanager.R;
import com.lucas.moneymanager.adapter.ItemAdapter;
import com.lucas.moneymanager.classes.Item;
import com.lucas.moneymanager.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class PurchaseLog extends AppCompatActivity {

    private DbHelper db;
    private ItemAdapter mAdapter;
    private float amount;

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
//        Collections.reverse(list);
        mAdapter = new ItemAdapter(list, this);
        listView.setAdapter(mAdapter);


        amount = db.getAmount();
        setMoneyAmount();
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

    /**
     * Reset our purchase Log and give ourselves a new budget
     * @param v view
     */
    public void resetLog(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_change_budget, null);
        dialog.setView(view)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editTextAmount = (EditText) view.findViewById(R.id.edittext_dialog_budgetAMount);
                        String budgetAmount = editTextAmount.getText().toString();

                        if (budgetAmount.isEmpty()) {
                            budgetAmount = "0";
                        }

                        amount = Float.valueOf(budgetAmount);

                        db.deleteAll();
                        mAdapter.removeAll();
                        db.updateAmount(amount);
                        db.updateItems(amount, getResources().getString(R.string.set_budget));
                        mAdapter.add(new Item(getResources().getString(R.string.set_budget), amount));

                        setMoneyAmount();
                    }
                })
                .setNegativeButton("Cancel", null);
        dialog.create().show();
    }

    private void setMoneyAmount() {
        TextView moneyAmount = (TextView) findViewById(R.id.textview_purchaselog_availableMoney);
        moneyAmount.setText(String.format("%.02f", amount));
    }

}
