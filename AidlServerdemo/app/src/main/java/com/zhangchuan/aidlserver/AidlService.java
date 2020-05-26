package com.zhangchuan.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

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