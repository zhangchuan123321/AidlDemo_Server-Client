AIDL通信须知：

1、AIDL（Android Interface Definition Language）使客户端和服务端通过它定义的编程接口来达成共识，以便进行进程间通信（IPC）

2、如果从本地进程发起调用，则调用将在调用线程中运行。如果想要本地调用依然在另一个进程中运行，可以在清单文件的Service中声明android:process=":remote”

3、流程： 创建.aidl文件、 服务端实现接口并向客户端公布 、客户端获取接口实例、服务端调用客户端、 Parcelable

4、Parcelable的作用：实现序列化并且都可以用于Intent间传递数据

5、Parcelable与Serializable的比较：

Parcelable直接在内存中读写，而Serializable读写到硬盘上。

因此，Parcelable速度也更快。而Serializable使用了反射，速度更慢。

但是因为Parcelable只在内存中读写，所以，它并不能通过网络传递，只能跨进程使用。而Serializable都可以。

6、AIDL文件的包名需与服务端保持一致

接下来讲方法：

一、User类并实现Parcelable接口：

package com.zhangchuan.aidlserver;

import android.os.Parcel;

import android.os.Parcelable;

public class Friend  implements Parcelable {

    private String name;
    private String sex;
    
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

二、AIDL文件创建：
package com.zhangchuan.aidlserver;
import com.zhangchuan.aidlserver.Friend;

// Declare any non-default types here with import statements

interface INumber {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int multNumber(int num1,int num2);
     List<Friend> getFriendList();
}
三、AIDLService创建并在manifest文件中（注意：必须添加intent-filter，意义在于使其他进程可以找到此服务

package com.zhangchuan.aidlserver;


public class AidlService extends Service {
    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iBinder;
    }
    private INumber.Stub iBinder=new INumber.Stub() {
        @Override
        public int multNumber(int num1, int num2) throws RemoteException {
            return num1*num2;
        }

        @Override
        public List<Friend> getFriendList() throws RemoteException {
            return MainActivity.getFriends();
        }
    };
}
<service
    android:name=".AidlService"
    android:enabled="true"
    android:exported="true"
    android:process=":remote">
    <intent-filter>
        <action android:name="service.jisuan"/>
    </intent-filter>

</service>
在AIDLService中声明INumber.Stub类型变量（Binder类型），并重写INumber 的方法.
四、新建客户端工程，将服务端中AIDL文件夹移植到客户端（注意：包名一致）
五、声明INumber接口类型变量、连接与服务端的AIDL服务，连接成功成功后，将INumber实例化对象赋值。

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
六、INumber实例化对象调用服务端方法：

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

运行结果
：
D/MainActivity: 链接成功

D/MainActivity: num1*num2:22*14=308

D/MainActivity: 获取数据：

    服务端获取的朋友:小红是我女朋友
    服务端获取的朋友:小明是我男朋友
    服务端获取的朋友:小刚是我中性朋友






