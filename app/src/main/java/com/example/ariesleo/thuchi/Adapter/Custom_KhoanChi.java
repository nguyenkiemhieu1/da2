package com.example.ariesleo.thuchi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariesleo.thuchi.Class.KhoanChi;
import com.example.ariesleo.thuchi.R;

import java.util.ArrayList;

/**
 * Created by Aries Leo on 5/10/2017.
 */

public class Custom_KhoanChi extends BaseAdapter {
    ArrayList<KhoanChi> arChi;
    Context context;
    TextView ten,tien,loai,ngay,ghichu;

    public Custom_KhoanChi(Context context,ArrayList<KhoanChi> arChi) {
        this.arChi = arChi;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arChi.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_khoanchi,null);
        ten= (TextView) view.findViewById(R.id.txtTen1);
        tien= (TextView) view.findViewById(R.id.txtTien1);
        loai= (TextView) view.findViewById(R.id.txtLoai1);
        ngay= (TextView) view.findViewById(R.id.txtNgay1);
        ghichu= (TextView) view.findViewById(R.id.txtGhichu1);

        KhoanChi khoanChi=arChi.get(position);
        ten.setText(khoanChi.getTen());
        tien. setText(String.valueOf(khoanChi.getTien()));
        loai.setText(khoanChi.getLoai());
        ngay.setText(khoanChi.getNgay());
        ghichu.setText(khoanChi.getGhichi());
        return view;
    }
}
