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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariesleo.thuchi.Adapter.Custom_Spiner_Chi;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Main.Main_KhoanThu;
import com.example.ariesleo.thuchi.Main.Main_KhoanChi;
import com.example.ariesleo.thuchi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Aries Leo on 5/12/2017.
 */

public class Them_KhoanChi extends AppCompatActivity {
    final String DATA_NAME = "QuanLy.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanThu> arKhoanThu = new ArrayList<>();
    Button btnHuy, btnLuu;
    ActionBar actionBar;
    String ten, loai, ngay, ghichu;
    TextView txNgay;
    Button button;
    int tien;
    int id;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat;
    EditText edTen, edTien, edLoai, edGhichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_khoanchi);

        controls();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar = getSupportActionBar();
            actionBar.setTitle("");
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert();
                    startActivity(new Intent(Them_KhoanChi.this, Main_KhoanChi.class));
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Them_KhoanChi.this, Main_KhoanChi.class));
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog();
                }
            });
//Lấy ngày giờ tự động
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            txNgay.setText(dateFormat.format(Calendar.getInstance().getTime()));
        }
    }

    void DatePickerDialog() {
        DatePickerDialog.OnDateSetListener event = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfmonth);
                txNgay.setText(dateFormat.format(calendar.getTime()));
            }
        };
        //Lắng nghe sự kiện khi ng dùng thay đôit datapicker
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, event, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    //Kiểm tra bản gi tồn tại hay chưa
    //Cursor.getCount() đọc tất cả các dữ liệu trong sql

    public void insert() {
        KhoanThu khoanThu = new KhoanThu();
        ten = edTen.getText().toString();
        tien = Integer.parseInt(edTien.getText().toString());
        khoanThu.setLoai(edLoai.getText().toString());
        khoanThu.setNgay(txNgay.getText().toString());
        khoanThu.setGhichu(edGhichu.getText().toString());
        ContentValues contentValues = new ContentValues();
        database = DataBase.initDatabase(this, DATA_NAME);

        Cursor cursor = database.rawQuery("select*from khoanchi where ten=?", new String[]{ten});
        if (cursor.getCount() == 1) {
            Toast.makeText(this, "Bản ghi đã tồn tại", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() == 0) {
         //contenvalues.put() 1 là tên các dữ liệu,2 là biến mình cần truyền vào
            contentValues.put("ten", ten);
            contentValues.put("sotien", tien);
            contentValues.put("loai", khoanThu.getLoai());
            contentValues.put("ngay", khoanThu.getNgay());
            contentValues.put("ghichu", khoanThu.getGhichu());

            database = DataBase.initDatabase(Them_KhoanChi.this, DATA_NAME);
            database.insert("khoanchi", null, contentValues);

            database = DataBase.initDatabase(this, DATA_NAME);
            Cursor cursor1 = database.rawQuery("select*from khoanchi", null);
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
        txNgay = (TextView) findViewById(R.id.txtNgay);
        edGhichu = (EditText) findViewById(R.id.editGC);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.bntHuy);
        button = (Button) findViewById(R.id.btNgay);

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
