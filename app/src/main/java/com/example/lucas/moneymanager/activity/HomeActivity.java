package com.example.lucas.moneymanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lucas.moneymanager.R;
import com.example.lucas.moneymanager.database.DbHelper;

import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    private DbHelper db;
    private float amount;
    private int goal = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DbHelper(this);
        amount = db.getAmount();

        setTextviewAmount();
    }

    /**
     * Create an AlertDialog that allows the user to enter the amount of money they have spent
     */
    public void changeAmount(View buttonView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialong_changemoney, null);
        dialog.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editTextAmount = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        EditText editTextItem = (EditText) view.findViewById(R.id.edittext_dialog_item);
                        String userItem = editTextItem.getText().toString();
                        String userAmount = editTextAmount.getText().toString();
                        if (!userAmount.isEmpty()) {
                            UpdateAmount(Float.valueOf(userAmount), userItem);

                        }
                    }
                })
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editTextAmount = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        EditText editTextItem = (EditText) view.findViewById(R.id.edittext_dialog_item);
                        String userItem = editTextItem.getText().toString();
                        String userAmount = editTextAmount.getText().toString();
                        if (!userAmount.isEmpty()) {
                            UpdateAmount(Float.valueOf(userAmount) * -1.0f, userItem);
                        }
                    }
                });
        dialog.create().show();
    }

    /**
     * OnClick method that starts the Purchase Log activity
     * @param buttonView
     */
    public void openLog(View buttonView) {
        Intent intent = new Intent(this, PurchaseLog.class);
        startActivity(intent);
    }

    /**
     * Changes the current amount displayed to the user
     * @param value
     */
    public void UpdateAmount(float value, String item) {
        if (item.equals("")) {
            item = getResources().getString(R.string.no_description);
        }
        amount += value;
        setTextviewAmount();
        db.deleteAmount();
        db.updateAmount(amount);
        db.updateItems(value, item);
    }

    /**
     * Sets the amount spent that is displayed on the screen
     */
    public void setTextviewAmount() {
        TextView textView = (TextView) findViewById(R.id.textview_homeScreen_moneySpent);
        textView.setText(String.format("%.02f / %d", amount, goal));
    }

    /**
     * Deletes all of our stored information in out db
     * @param buttonView
     */
    public void resetAmounts(View buttonView) {
        db.deleteAll();
        amount = 0;
        setTextviewAmount();
    }
}
