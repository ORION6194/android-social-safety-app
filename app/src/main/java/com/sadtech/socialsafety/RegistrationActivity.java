package com.sadtech.socialsafety;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class RegistrationActivity extends AppCompatActivity {
    Activity activity;
    EditText edtCN;
    EditText edtPN;
    Button btnS;
    Gson gson;
    EmergencyContactModel enc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        activity = this;
        gson = new Gson();
        initUI();
        initListener();
        initData();
    }

    private void initData() {
        enc = gson.fromJson(SharedPreference.getObject(activity,"OWN"),EmergencyContactModel.class);
        if(enc!=null && enc.getContactName()!=null && enc.getPhoneNumber()!=null){
            edtCN.setText(enc.getContactName());
            edtPN.setText(enc.getPhoneNumber());
        }
    }

    private void initListener() {
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    enc = new EmergencyContactModel();
                    enc.setContactName(edtCN.getText().toString());
                    enc.setPhoneNumber(edtPN.getText().toString());
                    SharedPreference.putObject(activity,"OWN",gson.toJson(enc));
                }
            }
        });
    }

    private boolean validate() {
        String cn = edtCN.getText().toString().trim();
        String pn = edtPN.getText().toString().trim();
        if(isNull(cn)){
            return false;
        }
        if(isNull(pn)){
            return false;
        }
        return true;
    }

    private void initUI() {
        edtCN = (EditText) findViewById(R.id.edt_cn);
        edtPN = (EditText) findViewById(R.id.edt_pn);
        btnS = (Button) findViewById(R.id.btn_s);
    }
    public static boolean isNull(String val){
        if(val==null||val.equals(null)||val.trim().equals("")||val.trim().equals("null")|| val.trim()==""||val.trim()=="null")
            return true;
        return false;
    }

}
