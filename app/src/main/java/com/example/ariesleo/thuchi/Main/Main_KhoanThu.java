package com.example.ariesleo.thuchi.Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ariesleo.thuchi.Adapter.Custom_Khoanthu;
import com.example.ariesleo.thuchi.Adapter.Custom_Spiner_Thu;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.Data.DataBase;
import com.example.ariesleo.thuchi.Edit.Sua_KhoanChi;
import com.example.ariesleo.thuchi.Edit.Sua_KhoanThu;
import com.example.ariesleo.thuchi.Edit.Them_KhoanThu;
import com.example.ariesleo.thuchi.R;
import com.example.ariesleo.thuchi.ThongKe.ThongKe_Nam;
import com.example.ariesleo.thuchi.ThongKe.ThongKe_Thang;

import java.util.ArrayList;

public class Main_KhoanThu extends AppCompatActivity {

    final String DATA_NAME = "KhoanChi.sqlite";
    SQLiteDatabase database;
    ArrayList<KhoanThu> arKhoanThu = new ArrayList<>();
    ArrayList<KhoanThu> khoanThu = new ArrayList<>();
    ListView lvKhoanThu;
    int tien;
    int id;
    Toolbar toolbar;
    Spinner spinner;
    ActionBar actionBar;
    Custom_Spiner_Thu adapterThu;
    EditText edTen1, edTien1, edLoai1, edNgay1, edGhichu1;
    String ten, loai, ngay, ghichu;
    Button btnLuu1, btnHuy1, btnChon;
    Custom_Khoanthu adapter;
    Button btnKhoanChi,btnThang,btnNam;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("");
        btnKhoanChi= (Button) findViewById(R.id.btnKhoanChi);
        btnKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_KhoanThu.this, Main_KhoanChi.class);
                startActivity(intent);
            }
        });
        btnThang= (Button) findViewById(R.id.btnThang);
        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_KhoanThu.this, ThongKe_Thang.class);
                startActivity(intent);
            }
        });
        btnNam= (Button) findViewById(R.id.btnNam);
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_KhoanThu.this, ThongKe_Nam.class);
                startActivity(intent);
            }
        });


        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Them_KhoanThu.class));
            }
        });
        read();

    }

    public void read() {
        database = DataBase.initDatabase(this, DATA_NAME);
        Cursor cursor = database.rawQuery("select * from khoanthu ", null);
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

    public void event() {
        lvKhoanThu = (ListView) findViewById(R.id.lvkhoanThu);
        adapter = new Custom_Khoanthu(arKhoanThu, this);
        lvKhoanThu.setAdapter(adapter);
        registerForContextMenu(lvKhoanThu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Chức năng");
        menu.setHeaderIcon(R.drawable.home);
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id1 = item.getItemId();
        if (id1 == R.id.menuSua) {
            Intent intent=new Intent(getApplicationContext(), Sua_KhoanThu.class);
            intent.putExtra("id",arKhoanThu.get(info.position).getId());
            intent.putExtra("ten",arKhoanThu.get(info.position).getTen());
            intent.putExtra("tien",arKhoanThu.get(info.position).getSotien());
            intent.putExtra("loai",arKhoanThu.get(info.position).getLoai());
            intent.putExtra("ngay",arKhoanThu.get(info.position).getNgay());
            intent.putExtra("ghichu",arKhoanThu.get(info.position).getGhichu());
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
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(getApplicationContext(), Main_KhoanThu.class);
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

    public void delete(int tenKhoanThu) {
        database = DataBase.initDatabase(this, DATA_NAME);
        database.delete("khoanthu", "id=?", new String[]{tenKhoanThu + ""});
        Cursor cursor = database.rawQuery("select*from khoanthu", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            Integer.parseInt(String.valueOf(tien = cursor.getInt(2)));
            loai = cursor.getString(3);
            ngay = cursor.getString(4);
            ghichu = cursor.getString(5);

            arKhoanThu.add(new KhoanThu(id, ten, tien, loai, ngay, ghichu));

        }
        adapter.notifyDataSetChanged();
    }

    public void upload(int id) {
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
        database = DataBase.initDatabase(Main_KhoanThu.this, DATA_NAME);
        database.update("khoanthu", values, "id=?", new String[]{id + ""});

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
