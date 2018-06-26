package com.example.zzn.p4;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper= new DatabaseHelper(this);
        mCostBeanList = new ArrayList<CostBean>();
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initCostData();
        costList.setAdapter(new CostListAdapter(this, mCostBeanList));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mDatabaseHelper.deleteAllData();
//                Snackbar.make(view, "delete all data!!!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDiaglog = inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = viewDiaglog.findViewById(R.id.edt_cost_title);
                final EditText money = viewDiaglog.findViewById(R.id.edt_cost_money);
                final DatePicker date = viewDiaglog.findViewById(R.id.dp_cost_date);

                builder.setView(viewDiaglog);
                builder.setTitle("new cost");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CostBean costBean = new CostBean();
                        costBean.costTitle=title.getText().toString();
                        costBean.costMoney  =money.getText().toString();
                        costBean.costDate = date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth();
                        mDatabaseHelper.insertCost(costBean);
                    }
                });
                builder.setNegativeButton("cancel",null);
                builder.create().show();
            }
        });
    }

    private void initCostData() {
        for (int i=0;i<7;i++) {
            CostBean costBean = new CostBean();
            costBean.costTitle =i+ "zzn";
            costBean.costDate = "11-11-"+(i+2018);
            costBean.costMoney = i+"100";
           // mCostBeanList.add(costBean);
            mDatabaseHelper.insertCost(costBean);
        }
        Cursor cursor=mDatabaseHelper.getAllCostData();
        while(cursor.moveToNext()){
            CostBean costBean = new CostBean();
            costBean.costTitle=cursor.getString(cursor.getColumnIndex("costTitle"));
            costBean.costDate=cursor.getString(cursor.getColumnIndex("costDate"));
            costBean.costMoney=cursor.getString(cursor.getColumnIndex("costMoney"));
            mCostBeanList.add(costBean);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
