package com.sadtech.socialsafety;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

/**
 * Created by ISRO on 1/28/2017.
 */

public class SplashScreenActivity extends Activity{

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1001;
    private static final long SPLASH_SCREEN_TIMEOUT = 1000;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.INTERNET},
                            PERMISSION_WRITE_EXTERNAL_STORAGE);
                }
                else{
                    goAhead();
                }

            }
        }, SPLASH_SCREEN_TIMEOUT);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                if(grantResults.length>0){
                    boolean allGranted = true;
                    for (int result:grantResults
                            ) {
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            allGranted = false;
                            break;
                        }
                    }
                    if(!allGranted){
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Please provide the permissions requested. Application will shutdown now.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        activity.finish();
                                    }
                                });
                        builder.create().
                                show();
                    }
                    else{
                        goAhead();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Failed to recognize the permissions. Please restart the app and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    activity.finish();
                                }
                            });
                    builder.create().
                            show();
                }
        }
    }


    private void goAhead() {
        if(SharedPreference.getBoolean(activity,"isInstall")){
            SharedPreference.putObject(activity,"OWN",new Gson().toJson(new EmergencyContactModel()));
            SharedPreference.putObject(activity,"ENCARR",new Gson().toJson(new ArrayList<EmergencyContactModel>()));
            SharedPreference.putBoolean(activity,"isInstall",false);
        }
        Intent homeScreenActivity = new Intent(activity, HomeScreenActivity.class);
        startActivity(homeScreenActivity);
        startService(new Intent(activity,BgService.class));
        finish();
    }

}

