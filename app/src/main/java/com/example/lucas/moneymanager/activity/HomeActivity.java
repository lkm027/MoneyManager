package com.example.lucas.moneymanager.activity;

import android.content.DialogInterface;
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

    public DbHelper db;
    private float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_homeScreen_toolbar);
//        myToolbar.setBackgroundColor(Color.BLUE);
//        myToolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(myToolbar);

        db = new DbHelper(this);
        amount = db.getAmount();

        setTextviewAmount();
    }

    /**
     * Create an AlertDialog that allows the user to enter the amount of money they have spent
     */
    public void changeAmount(View buttonView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_money, null);
        dialog.setMessage(R.string.dialog_addMoney)
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        String userAmount = editText.getText().toString();
                        if (!userAmount.isEmpty()) {
                            changeAmount(Float.valueOf(userAmount));
                        }
                    }
                })
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        String userAmount = editText.getText().toString();
                        if (!userAmount.isEmpty()) {
                            changeAmount(Float.valueOf(userAmount) * -1.0f);
                        }
                    }
                });
        dialog.create().show();
    }

    /**
     * Create an AlertDialog that allows the user to remove the amount of money from the spent amount
     */
    public void removeSpentMoney() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_money, null);
        dialog.setMessage(R.string.dialog_removemoney)
                .setView(view)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        String userAmount = editText.getText().toString();
                        if (!userAmount.isEmpty()) {
                            changeAmount(Float.valueOf(userAmount) * -1.0f);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        dialog.create().show();
    }
//
//    /**
//     * Inflates our menu items to our toolbar
//     * @param menu the menu resource we are using
//     * @return //
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.homescreen_toolbar, menu);
//        return true;
//    }
//
//    /**
//     * Allows event handling for user clicks on our toolbar
//     * @param item what is clicked
//     * @return //
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.toolbar_plus:
////                addSpentMoney();
//                return true;
//            case R.id.toolbar_minus:
//                removeSpentMoney();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void changeAmount(float value) {
        amount += value;
        setTextviewAmount();
        db.deleteAmount();
        db.addAmount(amount);
    }

    public void setTextviewAmount() {
        TextView textView = (TextView) findViewById(R.id.textview_homeScreen_moneySpent);
        textView.setText(String.format("%.02f", amount));
    }
}
