package com.sadtech.socialsafety;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsScreenActivity extends AppCompatActivity {
    Activity activity;
    Button btnAPN;
    Button btnI;
    Button btnR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        activity = this;
        initUI();
        initListener();
    }

    private void initListener() {
        btnAPN.setOnClickListener(new APNButtonOnClickListener());
        btnI.setOnClickListener(new IButtonOnClickListener());
        btnR.setOnClickListener(new RButtonOnClickListener());
    }

    private void initUI() {
        btnAPN = (Button) findViewById(R.id.btn_apn);
        btnR = (Button) findViewById(R.id.btn_r);
        btnI = (Button) findViewById(R.id.btn_i);
    }

    private class APNButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentAPN = new Intent(activity,AddPhoneNumberActivity.class);
            startActivity(intentAPN);
        }
    }

    private class IButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentI = new Intent(activity,InstructionsActivity.class);
            startActivity(intentI);
        }
    }

    private class RButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentRegister = new Intent(activity,RegistrationActivity.class);
            startActivity(intentRegister);
        }
    }
}
