package com.lucas.moneymanager.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lucas.moneymanager.R;
import com.lucas.moneymanager.database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeActivity extends AppCompatActivity {

    private DbHelper db;
    private float amount;
    private float budget;
    private boolean budgetisSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DbHelper(this);
        amount = db.getAmount();


        //shared Preferences
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        budgetisSet = sharedPref.getBoolean(getString(R.string.budget), false);

        if (!budgetisSet) {
            budgetisSet = true;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.budget), true);
            editor.commit();
            setBudget();
        }

        setTextviewAmount();
    }

    /**
     * Create an AlertDialog that allows the user to enter the amount of money they have spent
     */
    public void addAmount(View buttonView) {
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
                });
        dialog.setNegativeButton("Cancel", null);
        dialog.create().show();
    }

    /**
     * Create an AlertDialog that allows the user to enter the amount of money they have spent
     */
    public void removeAmount(View buttonView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialong_changemoney, null);
        dialog.setView(view)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView textView = (TextView) view.findViewById(R.id.dialog_addRemove_money);
                        textView.setText(getString(R.string.textview_itemRemoval));
                        EditText editTextAmount = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        EditText editTextItem = (EditText) view.findViewById(R.id.edittext_dialog_item);
                        String userItem = editTextItem.getText().toString();
                        String userAmount = editTextAmount.getText().toString();
                        if (!userAmount.isEmpty()) {
                            UpdateAmount(Float.valueOf(userAmount) * -1.0f, userItem);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        dialog.create().show();
    }

    /**
     * Updates the user budget preferences
     */
    private void setBudget() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_change_budget, null);
        dialog.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editTextAmount = (EditText) view.findViewById(R.id.edittext_dialog_budgetAMount);
                        String budgetAmount = editTextAmount.getText().toString();
                        if (budgetAmount.isEmpty()) {
                            budgetAmount = "0";
                        }
                        UpdateAmount(Float.valueOf(budgetAmount), getResources().getString(R.string.set_budget));
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
        String date = getDate();
        amount += value;
        setTextviewAmount();
        db.deleteAmount();
        db.updateAmount(amount);
        db.updateItems(value, item, date);
    }

    /**
     * Sets the amount spent that is displayed on the screen
     */
    public void setTextviewAmount() {
        TextView textView = (TextView) findViewById(R.id.textview_homeScreen_moneySpent);
        textView.setText(String.format("%.02f", amount));
//        textView.setText(String.format("%.02f / %d", amount, goal));
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

    /**
     * Opens the settings page
     * @param buttonView
     */
    public void openSettings(View buttonView) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        amount = db.getAmount();
        setTextviewAmount();
    }

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd");
        return format1.format(cal.getTime());
    }
}
