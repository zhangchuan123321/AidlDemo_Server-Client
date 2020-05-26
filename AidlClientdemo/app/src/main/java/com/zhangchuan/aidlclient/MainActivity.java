package com.zhangchuan.aidlclient;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangchuan.aidlserver.Friend;
import com.zhangchuan.aidlserver.INumber;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private EditText mNum1,mNum2;
private TextView displayInfotext;
protected INumber iNumber;
private String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNum1=(EditText)findViewById(R.id.num1);
        mNum2=(EditText)findViewById(R.id.num2);
        displayInfotext=(TextView)findViewById(R.id.total);
        InitConnnect();
    }
    public void MultResult(View v){
    if(mNum1.length()>0&&mNum2.length()>0&&iNumber!=null){
        displayInfotext.setText("");
        int a=Integer.parseInt(mNum1.getText().toString());
        int b=Integer.parseInt(mNum2.getText().toString());

        try {
            displayInfotext.setText(""+iNumber.multNumber(a,b));
            Log.d(TAG, "num1*num2:"+a+"*"+b+"="+iNumber.multNumber(a,b));

        } catch (RemoteException e) {
            e.printStackTrace();
            Log.d(TAG, "Connection cannot be establish");
        }

    }
    }
    public void getfriend(View v){

        if(iNumber!=null){
            displayInfotext.setText("");
            try {
                List<Friend> friends=iNumber.getFriendList();
                if(friends.size()>0){
                    for(int i=0;i<friends.size();i++)
                    displayInfotext.append("\n"+"服务端获取的朋友:"+friends.get(i).name+"是我"+friends.get(i).sex);
                Log.d(TAG,"获取数据："+displayInfotext.getText().toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    protected void InitConnnect(){
        if(iNumber==null){
            Intent intent=new Intent();
            intent.setAction("service.jisuan");
            intent.setPackage("com.zhangchuan.aidlserver");
            bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);

        }
    }
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"链接成功");
            iNumber=INumber.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"链接失败");
            iNumber=null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(iNumber==null){
            InitConnnect();
        }}
}
