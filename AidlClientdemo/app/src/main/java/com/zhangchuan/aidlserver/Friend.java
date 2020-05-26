package com.zhangchuan.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend  implements Parcelable {
    public String name;
    public String sex;
    public Friend(String name,String sex){
        this.name=name;
        this.sex =sex;
    }
    public  Friend(){
    }
    protected Friend(Parcel in){
      name=in.readString();
      sex=in.readString();
    }


    public static final Creator<Friend> CREATOR=new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
    }
}
