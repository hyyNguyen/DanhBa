package com.example.asus.danhba;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.asus.danhba.adapter.ContactAdapter;
import com.example.asus.danhba.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Contact> arrayContact;
    private ContactAdapter adapter;
    private EditText edt_name, edt_phone;
    private RadioButton rbtn_male, rbtn_female;
    private Button btn_add;
    private ListView lv_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhba);
        initView();
        arrayContact = new ArrayList<>();
        adapter =  new ContactAdapter(this, R.layout.item_contact_listview, arrayContact);
        lv_contact.setAdapter(adapter);
        checkAndRequestPermissions();
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //position vi tri trong listview
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_phone = (EditText) findViewById(R.id.edt_phonenumber);
        rbtn_male = (RadioButton) findViewById(R.id.rbtn_male);
        rbtn_female = (RadioButton) findViewById(R.id.rbtn_felmale);
        btn_add = (Button) findViewById(R.id.btn_add_contact);
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       if(view.getId() ==  R.id.btn_add_contact){
           // trim la mat dau cach 2 dau
           String name = edt_name.getText().toString().trim();
           String phone = edt_phone.getText().toString().trim();
            boolean ismale = true;
           if(rbtn_male.isChecked()){
               ismale = true;
           }else {
               ismale = false;
           }
           if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)){
               Toast.makeText(this, "please input name or value", Toast.LENGTH_SHORT).show();
           }else {
               Contact contact = new Contact(ismale, name, phone);
               arrayContact.add(contact);


           }
//           adapter kiem tra
           adapter.notifyDataSetChanged();
       }
    }
    //xin quyen
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            //kiem tra quyen cho phep hay chua
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        //neu chua thi yeu cau cai quyen do
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
//cusstom dialog
    public void showDialogConfirm(final int position){
        //hien thi len mainactivity
        //setcontenview la set giao dien cho dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btn_call= (Button) dialog.findViewById(R.id.btn_call);
        Button btn_sendmessage= (Button) dialog.findViewById(R.id.btn_sendmessage);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentcall(position);
            }
        });
        btn_sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentmessage(position);
            }
        });
        dialog.show();

    }

    private void intentmessage(int position) {
        Intent intent = new Intent();
        //goi den man hinh goi dien
        intent.setAction(Intent.ACTION_VIEW);
        //goi den so duoc truyen vao
        intent.setData(Uri.parse("sms:"+ arrayContact.get(position).getmNumber()));
        //khoi dong intent
        startActivity(intent);

    }

    private void intentcall(int position) {
        Intent intent = new Intent();
        //goi den man hinh goi dien
        intent.setAction(Intent.ACTION_CALL);
        //goi den so duoc truyen vao
        intent.setData(Uri.parse("tel:"+ arrayContact.get(position).getmNumber()));
        //khoi dong intent
        startActivity(intent);

    }
}
