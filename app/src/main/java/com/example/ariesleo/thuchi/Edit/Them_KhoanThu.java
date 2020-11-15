package com.example.ariesleo.thuchi.Edit;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Main.Main_KhoanThu;
import com.example.ariesleo.thuchi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class Them_KhoanThu extends AppCompatActivity {
    final String DATA_NAME = "QuanLy.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanThu> arKhoanThu = new ArrayList<>();
    Button btnHuy, btnLuu;
    ActionBar actionBar;
    String ten, loai, ngay, ghichu;
    int tien;
    int id;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat;
    EditText edTen, edTien, edLoai, edGhichu;
    TextView txtNgay;
    Button btnNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_khoanthu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            controls();
            actionBar = getSupportActionBar();
            actionBar.setTitle("");
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert();
                    startActivity(new Intent(Them_KhoanThu.this, Main_KhoanThu.class));
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Them_KhoanThu.this, Main_KhoanThu.class));
                }
            });
            btnNgay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog();
                }
            });
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            txtNgay.setText(dateFormat.format(Calendar.getInstance().getTime()));

        }
    }

    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfmonth);
                txtNgay.setText(dateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, event, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void insert() {
        KhoanThu khoanThu = new KhoanThu();
        ten=edTen.getText().toString();
        tien = Integer.parseInt(edTien.getText().toString());
        khoanThu.setLoai(edLoai.getText().toString());
        khoanThu.setNgay(txtNgay.getText().toString());
        khoanThu.setGhichu(edGhichu.getText().toString());
        ContentValues contentValues = new ContentValues();
        database=DataBase.initDatabase(this,DATA_NAME);
        Cursor cursor=database.rawQuery("select*from khoanthu where ten=?",new String[]{ten});
        if (cursor.getCount()==1)
        {
            Toast.makeText(this, "Bản ghi đã tồn tại", Toast.LENGTH_LONG).show();
        }
        else
            if (cursor.getCount()==0) {

            contentValues.put("ten", ten);
            contentValues.put("sotien", tien);
            contentValues.put("loai", khoanThu.getLoai());
            contentValues.put("ngay", khoanThu.getNgay());
            contentValues.put("ghichu", khoanThu.getGhichu());

            database = DataBase.initDatabase(Them_KhoanThu.this, DATA_NAME);
            database.insert("khoanthu", null, contentValues);
            database = DataBase.initDatabase(this, DATA_NAME);
            Cursor cursor1 = database.rawQuery("select*from khoanthu", null);
            cursor1.moveToFirst();
            for (int i = 0; i < cursor1.getCount(); i++) {
                cursor1.moveToPosition(i);
                id = cursor1.getInt(0);
                ten = cursor1.getString(1);
                tien = cursor1.getInt(2);
                loai = cursor1.getString(3);
                ngay = cursor1.getString(4);
                ghichu = cursor1.getString(5);

                arKhoanThu.add(new KhoanThu(id, ten, tien, loai, ngay, ghichu));

            }
        }
    }

    public void controls() {
        edTen = (EditText) findViewById(R.id.editTen);
        edTien = (EditText) findViewById(R.id.editSotien);
        edLoai = (EditText) findViewById(R.id.editLoai);
        edGhichu = (EditText) findViewById(R.id.editGC);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.bntHuy);
        txtNgay = (TextView) findViewById(R.id.txtNgay);
        btnNgay = (Button) findViewById(R.id.btnNgay);

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
