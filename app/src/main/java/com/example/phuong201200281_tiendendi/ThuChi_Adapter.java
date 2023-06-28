package com.example.phuong201200281_tiendendi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThuChi_Adapter extends BaseAdapter {
    private ArrayList<ThuChi> data;
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<ThuChi> databackup;

    public ThuChi_Adapter(){}
    public ThuChi_Adapter(ArrayList<ThuChi> data, Activity context) {
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        if(v == null) {
            v = inflater.inflate(R.layout.itemlistview, null);
            TextView phuchi = v.findViewById(R.id.txtthuchi);
            TextView ngay = v.findViewById(R.id.txtngay);
            TextView tenkhoan = v.findViewById(R.id.txttenkhoan);
            TextView tien = v.findViewById(R.id.txttien);
            if(data.get(position).isPhuchi()){
                phuchi.setBackgroundColor(Color.parseColor("#C0C0C0"));
                ngay.setBackgroundColor(Color.parseColor("#C0C0C0"));
                phuchi.setText("Tiền đến từ "+ data.get(position).getTennguoi());
            }else{
                phuchi.setText("Chi");
                phuchi.setBackgroundColor(Color.parseColor("#404040"));
                ngay.setBackgroundColor(Color.parseColor("#404040"));
                tenkhoan.setBackgroundColor(Color.parseColor("#A0A0A0"));
                tien.setBackgroundColor(Color.parseColor("#A0A0A0"));
                phuchi.setTextColor(Color.WHITE);
                ngay.setTextColor(Color.WHITE);
                tenkhoan.setTextColor(Color.WHITE);
                tien.setTextColor(Color.WHITE);

            }
            ngay.setText(data.get(position).getNgaythang());
            tenkhoan.setText("Tiền đi tới  " + data.get(position).getTennguoi());
            tien.setText(String.valueOf(data.get(position).getSotien()));
        }
        return v;
    }
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(databackup==null)
                    databackup = new ArrayList<>(data);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else{
                    //Tìm theo tên
//                    ArrayList<ThuChi> newdata = new ArrayList<>();
//                    for(ThuChi u:databackup)
//                        if(u.getTenkhoan().toLowerCase().contains(
//                                charSequence.toString().toLowerCase()))
//                            newdata.add(u);
//                    fr.count=newdata.size();
//                    fr.values=newdata;
                    //Tìm theo id lớn hơn 1 số nhập vào
                    ArrayList<ThuChi> newdata = new ArrayList<>();
                    for(ThuChi u:databackup)
                        if(u.getSotien() >
                                Integer.parseInt(charSequence.toString()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<ThuChi>();
                ArrayList<ThuChi> tmp =(ArrayList<ThuChi>)filterResults.values;
                for(ThuChi u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}