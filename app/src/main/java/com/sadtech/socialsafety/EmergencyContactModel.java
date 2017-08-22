package com.sadtech.socialsafety;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ISRO on 3/4/2017.
 */

public class EmergencyContactModel implements Parcelable{
    String contactName;
    String phoneNumber;

    public EmergencyContactModel() {
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected EmergencyContactModel(Parcel in) {
        contactName = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EmergencyContactModel> CREATOR = new Creator<EmergencyContactModel>() {
        @Override
        public EmergencyContactModel createFromParcel(Parcel in) {
            return new EmergencyContactModel(in);
        }

        @Override
        public EmergencyContactModel[] newArray(int size) {
            return new EmergencyContactModel[size];
        }
    };
}
