package ro.pub.cs.systems.eim.practicaltest01var05.service;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context;
    String text;
    String TAG = "tag";

    String typeString = "MESSAGE_STRING";
    final public static String ACTION_STRING = "ro.pub.cs.systems.eim.practicaltest01var05.service.startedservice.string";
    final public static String DATA = "ro.pub.cs.systems.eim.practicaltest01var05.service.startedservice.data";

    public ProcessingThread(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void run() {

        Log.d(TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());

        String[] arrSplit = text.split(",");
        for (int i=0; i < arrSplit.length; i++) {
            sleep();
            System.out.println(arrSplit[i]);
            sendMessage(0, arrSplit[i]);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage(int messageType, String wordText) {
        Intent intent = new Intent();

        switch(messageType) {
            case 0:
                intent.setAction(ACTION_STRING);

                intent.putExtra(DATA, "word = " + wordText);
                context.sendBroadcast(intent);
                break;
        }
    }

}