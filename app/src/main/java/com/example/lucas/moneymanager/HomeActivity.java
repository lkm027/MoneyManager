package com.example.lucas.moneymanager;

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

import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    public static float amountSpent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_homeScreen_toolbar);
        myToolbar.setBackgroundColor(Color.BLUE);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        TextView moneyView = (TextView) findViewById(R.id.textview_homeScreen_moneySpent);
        moneyView.setText(String.format("%.02f", amountSpent));
    }

    public void AddSpentMoney() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_money, null);
        dialog.setMessage(R.string.dialog_addMoney)
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_addmoney);
                        String userAmount = editText.getText().toString();
                        if (userAmount != "") {
                            amountSpent += Float.valueOf(userAmount);
                            Log.d("TAG", userAmount);
                        }
                        Log.d("TAG", amountSpent + "");
                        TextView moneyView = (TextView) findViewById(R.id.textview_homeScreen_moneySpent);
                        moneyView.setText(String.format("%.02f", amountSpent));
                    }
                })
                .setNegativeButton("Cancel", null);
        dialog.create().show();
    }

    /**
     * Inflates our menu items to our toolbar
     * @param menu the menu resource we are using
     * @return //
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homescreen_toolbar, menu);
        return true;
    }

    /**
     * Allows event handling for user clicks on our toolbar
     * @param item what is clicked
     * @return //
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_plus:
                AddSpentMoney();
                return true;
            case R.id.toolbar_minus:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
