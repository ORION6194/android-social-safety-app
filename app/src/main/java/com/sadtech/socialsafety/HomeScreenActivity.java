package com.sadtech.socialsafety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity {
    Activity activity;
    ImageView imgPhone;
    ImageView imgRecord;
    ImageView imgSettings;
    ImageView imgStop;
    String AudioSavePathInDevice = null;
    MediaRecorder myAudioRecorder;
    SimpleDateFormat sdf;
    boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        activity = this;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        isRecording = false;
        initUI();
        initListener();
    }

    private void initListener() {
        imgRecord.setOnClickListener(new RecorderImageOnClickListener());
        imgSettings.setOnClickListener(new SettingsImageOnClickListener());
        imgPhone.setOnClickListener(new PhoneImageOnClickListener());
        imgStop.setOnClickListener(new StopImageOnClickListener());
    }

    private void initUI() {
        imgPhone = (ImageView) findViewById(R.id.img_phone);
        imgRecord = (ImageView) findViewById(R.id.img_record);
        imgSettings = (ImageView) findViewById(R.id.img_settings);
        imgStop = (ImageView) findViewById(R.id.img_stop);
    }

    private class RecorderImageOnClickListener implements View.OnClickListener {

        /** Method to check whether external media available and writable. This is adapted from
         http://developer.android.com/guide/topics/data/data-storage.html#filesExternal */

        private boolean checkExternalMedia(){
            boolean mExternalStorageAvailable = false;
            boolean mExternalStorageWriteable = false;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // Can read and write the media
                mExternalStorageAvailable = mExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // Can only read the media
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
            } else {
                // Can't read or write
                mExternalStorageAvailable = mExternalStorageWriteable = false;
            }
            if(mExternalStorageAvailable&&mExternalStorageWriteable){
                return true;
            }
            else{
                return false;
            }
        }

        private void prepareMediaRecorder(){
            try {
                if(checkExternalMedia()) {
                    Date date = new Date();
                    String dtStr = sdf.format(date);
                    myAudioRecorder = new MediaRecorder();

                    File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Social Safety App");
                    dir.mkdirs();

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Social Safety App/AudioRecording_" + dtStr + ".mp3";

                    File f = new File(dir,"AudioRecording_" + dtStr + ".mp3");
                    boolean isCreated = f.createNewFile();
                    if (isCreated) {
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(AudioSavePathInDevice);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Failed to create the Audio File. Please make sure that file system exists.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        activity.finish();
                                    }
                                });
                        builder.create().
                                show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("File Storage not Writeable.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    activity.finish();
                                }
                            });
                    builder.create().
                            show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            try {
                if(!isRecording) {
                    prepareMediaRecorder();
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    isRecording = true;
                }
                else{
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    isRecording = false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class SettingsImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentSettings = new Intent(activity,SettingsScreenActivity.class);
            startActivity(intentSettings);
        }
    }

    private class PhoneImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentPhone = new Intent(activity,AddPhoneNumberActivity.class);
            startActivity(intentPhone);
        }
    }

    private class StopImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            stopService(new Intent(activity,BgService.class));
            startService(new Intent(activity,BgService.class));
        }
    }
}
