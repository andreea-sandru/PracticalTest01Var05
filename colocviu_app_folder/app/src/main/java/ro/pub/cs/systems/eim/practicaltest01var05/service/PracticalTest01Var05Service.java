package ro.pub.cs.systems.eim.practicaltest01var05.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var05Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("tag", "onStartCommand() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());

        String text = intent.getStringExtra("text");

        processingThread = new ProcessingThread(this, text);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stop();
        super.onDestroy();
    }

}