package com.example.ariesleo.thuchi.Edit;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ariesleo.thuchi.Adapter.Custom_Khoanthu;
import com.example.ariesleo.thuchi.Adapter.Custom_Spiner_Thu;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Main.Main_KhoanChi;
import com.example.ariesleo.thuchi.Main.Main_KhoanThu;
import com.example.ariesleo.thuchi.R;

import java.util.ArrayList;



public class Sua_KhoanThu extends AppCompatActivity {
    final String DATA_NAME = "QuanLy.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanThu> arKhoanThu = new ArrayList<>();
    ArrayList<KhoanThu> khoanThu = new ArrayList<>();
    ListView lvKhoanThu;
    int tien;
    int id;
    int sotien;
    String tien1=String.valueOf(sotien);
    int vitri;
    Toolbar toolbar;
    Spinner spinner;
    Custom_Spiner_Thu adapterThu;
    EditText edTen1, edTien1, edLoai1, edNgay1, edGhichu1;
    String ten, loai, ngay, ghichu;
    Button btnLuu1, btnHuy1, btnChon;
    Custom_Khoanthu adapter;
    Button btnKhoanChi, btnThang, btnNam;
    FloatingActionButton floatingActionButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sua_khoanthu);
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Sửa Khoản Thu");
        }
        edTen1 = (EditText) findViewById(R.id.editTen1);
        edTien1 = (EditText) findViewById(R.id.editSotien1);
        edLoai1 = (EditText) findViewById(R.id.editLoai1);
        edNgay1 = (EditText) findViewById(R.id.editNgay1);
        edGhichu1 = (EditText) findViewById(R.id.editGCh1);
        btnLuu1 = (Button) findViewById(R.id.btnLuu1);
        btnHuy1 = (Button) findViewById(R.id.bntHuy1);
        btnChon = (Button) findViewById(R.id.btbThu);
        spinner = (Spinner) findViewById(R.id.spinnerSuaThu);
        adapterThu=new Custom_Spiner_Thu(khoanThu,this);
        spinner.setAdapter(adapterThu);
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select*from khoanthu", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            tien = cursor.getInt(2);
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            khoanThu.add(new KhoanThu(id, ten, tien, loai, ngay, ghichu));

        }
        adapterThu.notifyDataSetChanged();
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog();
            }
        });
        btnLuu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                read();

                startActivity(new Intent(getApplicationContext(), Main_KhoanThu.class));
            }
        });
        btnHuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main_KhoanThu.class));
            }
        });
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        vitri = bundle.getInt("id");
        ten = bundle.getString("ten");
        tien1 = bundle.getString("sotien");
        loai = bundle.getString("loai");
        ngay = bundle.getString("ngay");
        ghichu = bundle.getString("ghichu");

        edTen1.setText(ten);
        edLoai1.setText(loai);
        edNgay1.setText(ngay);
        edGhichu1.setText(ghichu);
    }

    public void upload() {
        int sotien;
        KhoanThu khoanThu = new KhoanThu();
        khoanThu.setTen(edTen1.getText().toString());
        sotien = Integer.parseInt(edTien1.getText() + "");
        khoanThu.setLoai(edLoai1.getText().toString());
        khoanThu.setNgay(edNgay1.getText().toString());
        khoanThu.setGhichu(edGhichu1.getText().toString());

        ContentValues values = new ContentValues();

        values.put("ten", khoanThu.getTen());
        values.put("sotien", sotien);
        values.put("loai", khoanThu.getLoai());
        values.put("ngay", khoanThu.getNgay());
        values.put("ghichu", khoanThu.getGhichu());
        database = DataBase.initDatabase(Sua_KhoanThu.this, DATA_NAME);
        database.update("khoanthu", values, "id=?", new String[]{vitri + ""});

    }
    public void read() {
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select*from khoanthu", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            tien = cursor.getInt(2);
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            arKhoanThu.add(new KhoanThu(id, ten, tien, loai, ngay, ghichu));

        }
    }


    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edNgay1.setText(i2 + "/" + (i1+1) + "/" + i);
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
