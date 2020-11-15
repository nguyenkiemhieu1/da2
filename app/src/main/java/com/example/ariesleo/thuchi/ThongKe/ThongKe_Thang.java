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

public class ThongKe_Thang extends AppCompatActivity {
    ImageButton img1, img2;
    TextView tvhienthi, tvtienchi, tvtienthu, tvhieu;
    Integer month, year;
    final String DATABASE_NAME = "QuanLy.sqlite";
    SQLiteDatabase db;
    Integer tienchi = 0;
    Integer tienthu = 0;
    Integer hieu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke_thang);
        Anhxa();
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Thống kê tháng");
        }
        tvhienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog();
            }
        });
        String chuoi = tvhienthi.getText().toString();
        final String[] mang = chuoi.split("/");
        month = Integer.parseInt(mang[0]);
        year = Integer.parseInt(mang[1]);
        db = DataBase.initDatabase(ThongKe_Thang.this, DATABASE_NAME);
        String time = month + "/" + year;
        Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + time + "'", null);
        if (cursor.moveToFirst()) {
            do {
                tienchi = tienchi + cursor.getInt(2);
            } while (cursor.moveToNext());
        }
        tvtienchi.setText("" + tienchi);

        Cursor c = db.rawQuery("Select *From khoanthu Where ngay like '%" + time + "'", null);
        if (c.moveToFirst()) {
            do {
                tienthu = tienthu + c.getInt(2);
            } while (c.moveToNext());
        }
        tvtienthu.setText("" + tienthu);

        hieu = tienthu - tienchi;
        tvhieu.setText("" + hieu);
        hieu = 0;
        tienthu = 0;
        tienchi = 0;
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month < 12) {
                    month = month + 1;
                    tvhienthi.setText(month + "/" + year);
                } else {
                    month = 1;
                    year = year + 1;
                }
                tvhienthi.setText(month + "/" + year);
                String time = month + "/" + year;
                db = DataBase.initDatabase(ThongKe_Thang.this, DATABASE_NAME);
                Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + time + "'", null);
                if (cursor.moveToFirst()) {
                    do {
                        tienchi = tienchi + cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
                tvtienchi.setText("" + tienchi);

                Cursor c = db.rawQuery("Select *From khoanthu Where ngay like '%" + time + "'", null);
                if (c.moveToFirst()) {
                    do {
                        tienthu = tienthu + c.getInt(2);
                    } while (c.moveToNext());
                }
                tvtienthu.setText("" + tienthu);
                hieu = tienthu - tienchi;
                tvhieu.setText("" + hieu);
                tienthu = 0;
                tienchi = 0;
                hieu = 0;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month > 1) {
                    month = month - 1;
                    tvhienthi.setText(month + "/" + year);
                } else {
                    month = 12;
                    year = year - 1;
                }
                tvhienthi.setText(month + "/" + year);
                String time = month + "/" + year;
                db = DataBase.initDatabase(ThongKe_Thang.this, DATABASE_NAME);
                Cursor cursor = db.rawQuery("Select *From khoanchi Where ngay like '%" + time + "'", null);
                if (cursor.moveToFirst()) {
                    do {
                        tienchi = tienchi + cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
                tvtienchi.setText("" + tienchi);

                Cursor c = db.rawQuery("Select *From khoanthu Where ngay like '%" + time + "'", null);
                if (c.moveToFirst()) {
                    do {
                        tienthu = tienthu + c.getInt(2);
                    } while (c.moveToNext());
                }
                tvtienthu.setText("" + tienthu);

                hieu = tienthu - tienchi;
                tvhieu.setText("" + hieu);
                hieu = 0;
                tienchi = 0;
                tienthu = 0;
            }
        });


    }

    void Anhxa() {
        img1 = (ImageButton) findViewById(R.id.imgAdd);
        img2 = (ImageButton) findViewById(R.id.imgsub);
        tvhienthi = (TextView) findViewById(R.id.TimeThongke);
        tvtienchi = (TextView) findViewById(R.id.tvtienchi);
        tvtienthu = (TextView) findViewById(R.id.tvtienthu);
        tvhieu = (TextView) findViewById(R.id.tvhieu);

    }

    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month = i1 + 1;
                year = i;
                tvhienthi.setText(month + "/" + year);
            }
        };
        DatePickerDialog dl = new DatePickerDialog(this, event, 2017, 5, 12);
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
