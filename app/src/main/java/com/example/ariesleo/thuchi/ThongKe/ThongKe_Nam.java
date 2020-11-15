package com.example.ariesleo.thuchi.ThongKe;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Main.Main_KhoanChi;
import com.example.ariesleo.thuchi.Main.Main_KhoanThu;
import com.example.ariesleo.thuchi.R;

/**
 * Created by Aries Leo on 5/12/2017.
 */

public class ThongKe_Nam extends AppCompatActivity {
    ImageButton img1, img2;
    TextView tvhienthi, tvtienchi, tvtienthu, tvhieu;
    Integer year;
    final String DATABASE_NAME = "QuanLy.sqlite";
    SQLiteDatabase db;

    int tienchi = 0;
    int tienthu = 0;
    int hieu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke_nam);
        Anhxa();
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Thống kê năm");
        }
        tvhienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog();
            }
        });
        year = Integer.parseInt(tvhienthi.getText().toString());
        db = DataBase.initDatabase(this, DATABASE_NAME);
        Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + year + "'", null);
        if (cursor.moveToFirst()) {
            do {
                tienchi = tienchi + cursor.getInt(2);
            } while (cursor.moveToNext());
        }
        Cursor cursor1 = db.rawQuery("Select *From khoanthu Where ngay like '%" + year + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                tienthu = tienthu + cursor1.getInt(2);
            } while (cursor1.moveToNext());
        }
        tvtienthu.setText("" + tienthu);
        tvtienchi.setText("" + tienchi);
        hieu = tienthu - tienchi;
        tvhieu.setText("" + hieu);
        tienchi = 0;
        tienthu = 0;
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year++;
                tvhienthi.setText(""+year);
                db = DataBase.initDatabase(ThongKe_Nam.this, DATABASE_NAME);
                Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + year + "'", null);
                if (cursor.moveToFirst()) {
                    do {
                        tienchi = tienchi + cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
                Cursor cursor1 = db.rawQuery("Select *From khoanthu Where ngay like '%" + year + "'", null);
                if (cursor1.moveToFirst()) {
                    do {
                        tienthu = tienthu + cursor1.getInt(2);
                    } while (cursor1.moveToNext());
                }
                tvtienthu.setText("" + tienthu);
                tvtienchi.setText("" + tienchi);
                hieu = tienthu - tienchi;
                tvhieu.setText("" + hieu);
                tienchi = 0;
                tienthu = 0;
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year--;
                tvhienthi.setText(""+year);
                db = DataBase.initDatabase(ThongKe_Nam.this, DATABASE_NAME);
                Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + year + "'", null);
                if (cursor.moveToFirst()) {
                    do {
                        tienchi = tienchi + cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
                Cursor cursor1 = db.rawQuery("Select *From khoanthu Where ngay like '%" + year + "'", null);
                if (cursor1.moveToFirst()) {
                    do {
                        tienthu = tienthu + cursor1.getInt(2);
                    } while (cursor1.moveToNext());
                }
                tvtienthu.setText("" + tienthu);
                tvtienchi.setText("" + tienchi);
                hieu = tienthu - tienchi;
                tvhieu.setText("" + hieu);
                tienchi = 0;
                tienthu = 0;
            }
        });

    }

    void Anhxa() {
        img1 = (ImageButton) findViewById(R.id.imageButton2);
        img2 = (ImageButton) findViewById(R.id.imageButton);
        tvhienthi = (TextView) findViewById(R.id.textView2);
        tvtienchi = (TextView) findViewById(R.id.namhttienchi);
        tvtienthu = (TextView) findViewById(R.id.namhttienthu);
        tvhieu = (TextView) findViewById(R.id.nohienthi);

    }

    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                tvhienthi.setText("" + year);
            }
        };
        DatePickerDialog dl = new DatePickerDialog(this, event, 2017, 4, 12);
        dl.setTitle("Chọn ngày!");
        dl.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), Main_KhoanThu.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
