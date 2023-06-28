package com.example.phuong201200281_tiendendi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
 private ArrayList<ThuChi> ContacList;
    private EditText etSreach;
     TextView sodu;
    private ThuChi_Adapter ListAdapter;
    private ListView lstContact;
    FloatingActionButton btthem;
  //  private ConnectionReceiver receiver;
    private IntentFilter intentFilter;
    int selectedid = -1;
    private Phuong_Sql_ThuChi db;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.idsua:
                Intent intent = new Intent(MainActivity.this,
                        Add.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", ContacList.get(selectedid).getId());
                //bundle.putString("Name", ContacList.get(selectedid).getName());
                //bundle.putString("Phone", ContacList.get(selectedid).getPhone());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.idxoa:
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Phuong wants to delete?");
                dlgAlert.setTitle("Confirm");
                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
//                                ContacList.remove(selectedid);
                        db.deleteContact(ContacList.get(selectedid).getId());
                        resetData();
                    }
                });
                dlgAlert.setNegativeButton("Cancel",null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        if(requestCode==100 && resultCode==200 )
        {
            //đặt vào listData
           // db.addContact(new Contact_Doan(name, phone));
            //ContacList.add(new Contact(id,"img1",name, phone, "p1","s1"));

        }
        else if(requestCode==200 && resultCode==201)
          //  db.UpdateContact(id,new Contact_Doan(id,name, phone));
        //ContacList.set(selectedid,new Contact(id,"img1",name, phone, "p1","s1"));
        //cập nhật adapter
        ListAdapter.notifyDataSetChanged();
        resetData();
    }
    private void resetData(){
        db = new Phuong_Sql_ThuChi(MainActivity.this, "ContacttDB2",null,1);
        ContacList  = db.GetAllContact();
        ListAdapter = new ThuChi_Adapter(ContacList, MainActivity.this);
        lstContact.setAdapter(ListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Phuong_Sql_ThuChi(this, "ContacttDB2",null,1);
//
//        db.addContact(new ThuChi("Nguyễn Như Anh ","29-11-2023","Sinh nhật vui vẻ",13021230,true));
//        db.addContact(new ThuChi("Đào Bình Minh ","20-01-2023","Đóng học",3000000,false));
//        db.addContact(new ThuChi("Tô Bích Liên ","20-01-2023","Ăn trưa nay",2000000,false));
//        db.addContact(new ThuChi("Trần Bình An ","29-11-2023","Lương Thưởng",4321232,true));


        ContacList = db.GetAllContact();
        lstContact = findViewById(R.id.lstView);
        ListAdapter = new ThuChi_Adapter(ContacList, this);
        etSreach = findViewById(R.id.etSearch);
        btthem = findViewById(R.id.btnAdd);
        sodu = findViewById(R.id.tvSoDu);
        sodu.setBackgroundColor(Color.parseColor("#C0C0C0"));
        int tongthu = 0, tongchi = 0;
        for (ThuChi a: ContacList
             ) {
            if(a.isPhuchi()){
                tongthu += a.getSotien();
            }else {
                tongchi += a.getSotien();
            }
        }
        int tongtien = tongthu - tongchi;
        sodu.setText("Số dư: " + tongtien);
//        Sắp Xếp tăng giảm theo id
        //Tăng
//        Collections.sort(ContacList, new Comparator<Contact_Doan>() {
//            @Override
//            public int compare(Contact_Doan o1, Contact_Doan o2) {
//                return o2.getId() - o1.getId();
//            }
//        });
       // Giảm
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

       Collections.sort(ContacList, new Comparator<ThuChi>() {
        @Override
       public int compare(ThuChi o1, ThuChi o2) {
          try {
            Date date1 = dateFormat.parse(o1.getNgaythang());
            Date date2 = dateFormat.parse(o2.getNgaythang());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
});


        lstContact.setAdapter(ListAdapter);
        btthem.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this,
//                    a.phone, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Add.class);
            startActivityForResult(intent,100);

        });
        registerForContextMenu(lstContact);
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                selectedid = position;

                return false;
            }
        });
        etSreach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ListAdapter.getFilter().filter(s.toString());
                ListAdapter.notifyDataSetChanged();
                lstContact.setAdapter(ListAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}