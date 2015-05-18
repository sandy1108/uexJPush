package org.zywx.wbpalmstar.widgetone.uexJPush;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;

import javax.crypto.KeyAgreement;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

    public static CallBack callBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("title",regId);
                callBack.onReceiveRegistration(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            callbackMessage(bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            callbackNotification(bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            callbackNotificationOpen(bundle);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("connect",connected?0:1);
                callBack.onReceiveConnectionChange(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void callbackNotificationOpen(Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        JSONObject jsonObject=new JSONObject();
        put(jsonObject,"title",title);
        put(jsonObject,"content",content);
        put(jsonObject,"extras",extras);
        put(jsonObject,"notificationId",notificationId);
        put(jsonObject,"msgId",msgId);
        callBack.onReceiveNotificationOpen(jsonObject.toString());
    }

    private void callbackNotification(Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
        String fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);
        String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        JSONObject jsonObject=new JSONObject();
        put(jsonObject,"title",title);
        put(jsonObject,"content",content);
        put(jsonObject,"extras",extras);
        put(jsonObject,"notificationId",notificationId);
        put(jsonObject,"type",type);
        put(jsonObject,"fileHtml",fileHtml);
        put(jsonObject,"fileStr",fileStr);
        put(jsonObject,"msgId",msgId);
        callBack.onReceiveNotification(jsonObject.toString());
    }

    //send msg to MainActivity
    private void callbackMessage(Bundle bundle) {
        JSONObject jsonObject=new JSONObject();
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        String file = bundle.getString(JPushInterface.EXTRA_RICHPUSH_FILE_PATH);
        String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        put(jsonObject, "title", title);
        put(jsonObject, "message",message);
        put(jsonObject,"extras",extras);
        put(jsonObject,"type",type);
        put(jsonObject,"file",file);
        put(jsonObject,"msgId",msgId);
        callBack.onReceiveMessage(jsonObject.toString());
    }

    private void put(JSONObject jsonObject,String key,Object value){
        if (value!=null){
            try {
                jsonObject.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCallBack(CallBack temp) {
        callBack = temp;
    }
}