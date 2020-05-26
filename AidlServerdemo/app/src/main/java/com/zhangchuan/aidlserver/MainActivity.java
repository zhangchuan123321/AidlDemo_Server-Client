package com.zhangchuan.aidlserver;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Friend> friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static List<Friend> getFriends(){
        friend=new ArrayList<>();
        friend.add(new Friend("小红","女朋友"));
        friend.add(new Friend("小明","男朋友"));
        friend.add(new Friend("小刚","中性朋友"));
        return  friend;
    }
}
