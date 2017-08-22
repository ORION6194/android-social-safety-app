package com.sadtech.socialsafety;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ISRO on 3/5/2017.
 */
public class ENCDisplayAdapter extends BaseAdapter{
    Activity activity;
    ArrayList<EmergencyContactModel> encArr;
    DeleteENCDelegate deleteENCDelegate;
    LayoutInflater li;
    public ENCDisplayAdapter(Activity activity, ArrayList<EmergencyContactModel> encArr, DeleteENCDelegate deleteENCDelegate) {
        this.activity = activity;
        this.encArr = encArr;
        this.deleteENCDelegate = deleteENCDelegate;
        this.li = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return encArr.size();
    }

    @Override
    public Object getItem(int position) {
        return encArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler h = null;
        if(convertView==null){
            convertView = li.inflate(R.layout.item_enc,null);
            h = new ViewHoler();
            h.txtCN = (TextView) convertView.findViewById(R.id.txt_cn);
            h.txtPN = (TextView) convertView.findViewById(R.id.txt_pn);
            h.imgD = (ImageView) convertView.findViewById(R.id.img_d);
            convertView.setTag(h);
        }
        else{
            h = (ViewHoler) convertView.getTag();
        }
        h.imgD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteENCDelegate.onDeleteClicked(position);
            }
        });
        h.txtCN.setText("Name: "+encArr.get(position).getContactName());
        h.txtPN.setText("Number: "+encArr.get(position).getPhoneNumber());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteENCDelegate.onENCClicked(encArr.get(position));
            }
        });
        return convertView;
    }
    private class ViewHoler{
        TextView txtCN;
        TextView txtPN;
        ImageView imgD;
    }
}
