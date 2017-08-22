package com.sadtech.socialsafety;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddPhoneNumberActivity extends AppCompatActivity {
    Activity activity;
    EditText edtCN;
    EditText edtPN;
    Button btnS;
    ListView lvENC;
    ArrayList<EmergencyContactModel> encArr;
    private ENCDisplayAdapter encDisplayAdapter;
    Gson gson;
    private EmergencyContactModel enc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        activity = this;
        gson = new Gson();
        initUI();
        initData();
        initListener();
    }

    private void initData() {
        encArr = new ArrayList<>();
        encArr = gson.fromJson(SharedPreference.getObject(activity, "ENCARR"), new TypeToken<List<EmergencyContactModel>>() {
        }.getType());
        encDisplayAdapter = new ENCDisplayAdapter(activity, encArr, new DeleteENCDelegate() {
            @Override
            public void onDeleteClicked(int position) {
                encArr.remove(position);
                encDisplayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onENCClicked(EmergencyContactModel enc) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enc.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Please provide the permissions requested. Call will not be placed.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    builder.create().
                            show();
                }
                startActivity(intent);
            }
        });
        lvENC.setAdapter(encDisplayAdapter);
    }

    private void initListener() {
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    enc = new EmergencyContactModel();
                    enc.setContactName(edtCN.getText().toString());
                    enc.setPhoneNumber(edtPN.getText().toString());
                    if(!encArr.contains(enc)){
                        encArr.add(enc);
                        SharedPreference.putObject(activity,"ENCARR",gson.toJson(encArr));
                        encDisplayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initUI() {
        edtCN = (EditText) findViewById(R.id.edt_cn);
        edtPN = (EditText) findViewById(R.id.edt_pn);
        btnS = (Button) findViewById(R.id.btn_s);
        lvENC = (ListView) findViewById(R.id.lv_ec);
    }

    private boolean validate() {
        String cn = edtCN.getText().toString().trim();
        String pn = edtPN.getText().toString().trim();
        if(isNull(cn)){
            Toast.makeText(activity,"Please provide a valid Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isNull(pn)||pn.length()!=10){
            Toast.makeText(activity,"Please provide a valid Phone Number",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isNull(String val){
        if(val==null||val.equals(null)||val.trim().equals("")||val.trim().equals("null")|| val.trim()==""||val.trim()=="null")
            return true;
        return false;
    }
}
