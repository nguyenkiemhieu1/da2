package com.example.ariesleo.thuchi.Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


import com.example.ariesleo.thuchi.Adapter.Custom_KhoanChi;
import com.example.ariesleo.thuchi.Class.KhoanChi;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Edit.Sua_KhoanChi;
import com.example.ariesleo.thuchi.Edit.Them_KhoanChi;

import com.example.ariesleo.thuchi.R;
import com.example.ariesleo.thuchi.ThongKe.ThongKe_Nam;
import com.example.ariesleo.thuchi.ThongKe.ThongKe_Thang;

import java.util.ArrayList;

/**
 * Created by Aries Leo on 5/12/2017.
 */

public class Main_KhoanChi extends AppCompatActivity {
    final String DATA_NAME = "KhoanChi.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanChi> arKhoanThu = new ArrayList<>();
    ListView lvKhoanThu;
    int tien;
    int id;
    Custom_KhoanChi custom_khoanChi;
    String ten, loai, ngay, ghichu;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoanchi);
        ActionBar actionBar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Khoản Chi");
        }
        event();
        read();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Them_KhoanChi.class));
            }
        });
    }


    public void read() {
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select*from khoanchi", null);
        arKhoanThu.clear();
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
        custom_khoanChi.notifyDataSetChanged();
    }
    // Đọc dữ liệu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ThongKeNam) {
            Intent intent = new Intent(getApplicationContext(), ThongKe_Thang.class);
            startActivity(intent);
        }
        if (id == R.id.ThongKeThang) {
            Intent intent = new Intent(getApplicationContext(), ThongKe_Nam.class);
            startActivity(intent);
        }
        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), Main_KhoanThu.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void event() {
        lvKhoanThu = (ListView) findViewById(R.id.lvKhoanChi);
        custom_khoanChi = new Custom_KhoanChi(this, arKhoanThu);
        lvKhoanThu.setAdapter(custom_khoanChi);
        registerForContextMenu(lvKhoanThu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Chức năng");
        menu.setHeaderIcon(R.drawable.home);
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //Bắt sự kiện cho intem trên context menu
    //info vị trí khi mình nhấn giữ chọn
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id1 = item.getItemId();
        if (id1 == R.id.menuSua) {

            Intent intent = new Intent(getBaseContext(), Sua_KhoanChi.class);
            intent.putExtra("id", arKhoanThu.get(info.position).getId());
            intent.putExtra("ten", arKhoanThu.get(info.position).getTen());
            intent.putExtra("tien", arKhoanThu.get(info.position).getTien());
            intent.putExtra("loai", arKhoanThu.get(info.position).getLoai());
            intent.putExtra("ngay", arKhoanThu.get(info.position).getNgay());
            intent.putExtra("ghichu", arKhoanThu.get(info.position).getGhichi());
            startActivity(intent);
        }

        if (id1 == R.id.menuXoa) {
            final int viti = arKhoanThu.get(info.position).getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc là muốn xóa không");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete(viti);
                    read();
                    custom_khoanChi.notifyDataSetChanged();
                    Intent intent = new Intent(getApplicationContext(), Main_KhoanChi.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }
        return super.onContextItemSelected(item);
    }


    public void delete(int tenKhoanChi) {
        database = DataBase.initDatabase(this, DATA_NAME);
        database.delete("khoanchi", "id=?", new String[]{tenKhoanChi + ""});
        Cursor cursor = database.rawQuery("select*from khoanchi", null);
        arKhoanThu.clear();
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
        custom_khoanChi.notifyDataSetChanged();
    }

}
