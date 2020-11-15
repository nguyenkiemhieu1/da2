package com.example.ariesleo.thuchi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ariesleo.thuchi.Class.KhoanChi;
import com.example.ariesleo.thuchi.Class.KhoanThu;
import com.example.ariesleo.thuchi.R;

import java.util.ArrayList;

/**
 * Created by Aries Leo on 5/12/2017.
 */

public class Custom_Spiner_Thu extends BaseAdapter {
    ArrayList<KhoanThu> arChi;
    Context context;

    public Custom_Spiner_Thu(ArrayList<KhoanThu> arChi, Context context) {
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
        View view=inflater.inflate(R.layout.custom_spiner,null);

        TextView txtsp= (TextView) view.findViewById(R.id.txtSp);

        KhoanThu khoanThu=arChi.get(position);

        txtsp.setText(khoanThu.getLoai());
        return view;
    }
}
