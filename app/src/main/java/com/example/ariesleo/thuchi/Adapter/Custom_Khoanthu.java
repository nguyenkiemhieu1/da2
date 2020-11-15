package com.example.ariesleo.thuchi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.R;

import java.util.ArrayList;

/**
 * Created by Admin on 5/10/2017.
 */

public class Custom_Khoanthu extends BaseAdapter {
    ArrayList<KhoanThu> arrKhoanThu;
    Context context;
    TextView ten,tien,loai,ngay,ghichu;

    public Custom_Khoanthu(ArrayList<KhoanThu> arrKhoanThu, Context context) {
        this.arrKhoanThu = arrKhoanThu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrKhoanThu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_khoanthu,null);
        ten= (TextView) view.findViewById(R.id.txtTen);
        tien= (TextView) view.findViewById(R.id.txtTien);
        loai= (TextView) view.findViewById(R.id.txtLoai);
        ngay= (TextView) view.findViewById(R.id.txtNgay);
        ghichu= (TextView) view.findViewById(R.id.txtGhichu);

        KhoanThu khoanThu=arrKhoanThu.get(position);
        ten.setText(khoanThu.getTen());
        tien. setText(String.valueOf(khoanThu.getSotien()));
        loai.setText(khoanThu.getLoai());
        ngay.setText(khoanThu.getNgay());
        ghichu.setText(khoanThu.getGhichu());
        return view;
    }
}
