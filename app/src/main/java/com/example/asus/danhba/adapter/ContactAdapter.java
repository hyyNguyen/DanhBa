package com.example.asus.danhba.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.danhba.R;
import com.example.asus.danhba.model.Contact;

import java.util.List;

/**
 * Created by ASUS on 10/30/17.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
//contexxt la tuong trung cho man hinh hien tai hien hanh
    private Context context;
    private int resource;
    private List<Contact> arrContact;


    public ContactAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contact> objects) {
        //context , resource la file listview tao, ob la danh sahc so dt trong danh ba
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        // lan dau tien khoi tao
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_listview, parent, false);
            viewHolder.imgAvataContact = (ImageView) convertView.findViewById(R.id.imv_avata_contact);
            viewHolder.tvNameContact = (TextView) convertView.findViewById(R.id.tv_name_contact);
            viewHolder.tvPhoneContact = (TextView) convertView.findViewById(R.id.lv_phone_contact);


            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrContact.get(position);
        //check nam hay nu
        viewHolder.tvNameContact.setText(contact.getmName());
        viewHolder.tvPhoneContact.setText(contact.getmNumber());

        if(contact.isMale()){
            viewHolder.imgAvataContact.setBackgroundResource(R.drawable.icon_male);
        }else {
            viewHolder.imgAvataContact.setBackgroundResource(R.drawable.icon_female);
        }


        return convertView;
    }

    public class ViewHolder{
        ImageView imgAvataContact;
        TextView tvNameContact, tvPhoneContact;

    }
}
