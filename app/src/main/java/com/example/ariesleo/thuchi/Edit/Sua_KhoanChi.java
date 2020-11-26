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
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ariesleo.thuchi.Adapter.Custom_KhoanChi;
import com.example.ariesleo.thuchi.Adapter.Custom_Spiner_Chi;
import com.example.ariesleo.thuchi.Class.KhoanChi;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Main.Main_KhoanChi;
import com.example.ariesleo.thuchi.Main.Main_KhoanThu;
import com.example.ariesleo.thuchi.R;

import java.util.ArrayList;

/**
 * Created by Aries Leo on 5/13/2017.
 */

public class Sua_KhoanChi extends AppCompatActivity {
    final String DATA_NAME = "KhoanChi.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanChi> arKhoanThu = new ArrayList<>();
    ArrayList<KhoanChi> arKhoanThu1 = new ArrayList<>();

    int tien;
    int id;
    int sotien;
    String tien1=String.valueOf(sotien);
    Spinner spinner;
    Custom_Spiner_Chi custom_spiner_chi;
    EditText edTen1, edTien1, edLoai1, edNgay1, edGhichu1;
    String ten, loai, ngay, ghichu;
    Button btnLuu1, btnHuy1, btnChon;
    Custom_KhoanChi adapter;
    int vitri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sua_khoanchi);
        edTen1 = (EditText) findViewById(R.id.editTen1);
        edTien1 = (EditText) findViewById(R.id.editSotien1);
        edLoai1 = (EditText) findViewById(R.id.editLoai1);
        edNgay1 = (EditText) findViewById(R.id.editNgay1);
        edGhichu1 = (EditText) findViewById(R.id.editGChu);
        btnLuu1 = (Button) findViewById(R.id.btnLuu1);
        btnHuy1 = (Button) findViewById(R.id.bntHuy1);
        btnChon = (Button) findViewById(R.id.btnChi);
        spinner = (Spinner) findViewById(R.id.spinnerSuaThu);
        Intent in = getIntent();
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Sửa Khoản Chi");
        }
        // Lấy dữ liệu của Inten
        Bundle bundle = in.getExtras();
        custom_spiner_chi = new Custom_Spiner_Chi(arKhoanThu1, this);
        spinner.setAdapter(custom_spiner_chi);
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select*from khoanchi", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            tien = cursor.getInt(2);
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            arKhoanThu1.add(new KhoanChi(id, ten, tien, loai, ngay, ghichu));

        }
        custom_spiner_chi.notifyDataSetChanged();
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
                startActivity(new Intent(getApplicationContext(), Main_KhoanChi.class));
            }
        });
        btnHuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main_KhoanChi.class));
            }
        });
    }

    public void read() {
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select*from khoanchi", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            tien = cursor.getInt(2);
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            arKhoanThu.add(new KhoanChi(id, ten, tien, loai, ngay, ghichu));

        }
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
        database = DataBase.initDatabase(Sua_KhoanChi.this, DATA_NAME);
        database.update("Khoanchi", values, "id=?", new String[]{vitri + ""});
        Cursor cursor = database.rawQuery("select*from khoanchi", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            Integer.parseInt(String.valueOf(tien = cursor.getInt(2)));
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            arKhoanThu.add(new KhoanChi(id, ten, tien, loai, ngay, ghichu));

        }

    }

    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edNgay1.setText(i2 + "/" + i1 + "/" + i);
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
            startActivity(new Intent(getApplicationContext(), Main_KhoanChi.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
