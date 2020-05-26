// INumber.aidl
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
