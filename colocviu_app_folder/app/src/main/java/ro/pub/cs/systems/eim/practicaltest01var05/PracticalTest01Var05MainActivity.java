package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01var05.service.PracticalTest01Var05Service;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, otherActivity;
    TextView textView;
    int Req_code = 2;
    int clicks = 0;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private final static String CLICKS_KEY = "clicks";
    private final static int CLICKS_MAX = 4;

    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    final public static String ACTION_STRING = "ro.pub.cs.systems.eim.practicaltest01var05.service.startedservice.string";
    final public static String DATA = "ro.pub.cs.systems.eim.practicaltest01var05.service.startedservice.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        textView = findViewById(R.id.editText);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        otherActivity = findViewById(R.id.nextActivityButton);

        button1.setOnClickListener(buttonClickListener);
        button2.setOnClickListener(buttonClickListener);
        button3.setOnClickListener(buttonClickListener);
        button4.setOnClickListener(buttonClickListener);
        button5.setOnClickListener(buttonClickListener);

        otherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PracticalTest01Var05SecondaryActivity.class);

                String text = textView.getText().toString();
                intent.putExtra("text", text);
                intent.putExtra("cliks", clicks);

                clicks = 0;
                textView.setText("");

                startActivityForResult(intent, Req_code);
            }
        });

        if (savedInstanceState == null) {
            Log.d("TAGGG", "onCreate() method was invoked without a previous state");
        } else {
            Log.d("TAGGG", "onCreate() method was invoked with a previous state");
            if (savedInstanceState.containsKey(CLICKS_KEY)) {
                Toast.makeText(this, "Clicks =  " + savedInstanceState.getString(CLICKS_KEY), Toast.LENGTH_LONG).show();
            }
        }

        intentFilter.addAction(ACTION_STRING);
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            clicks ++;

            if(clicks >= CLICKS_MAX) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra("text", textView.getText().toString());
                getApplicationContext().startService(intent);
                Log.d("TAG", "Sent service request!!!!");
            }
            String text = ((Button)v).getText().toString();

            String oldText = textView.getText().toString();

            if(oldText.equals("")) {
                textView.setText(text);
            }
            else {
                textView.setText(oldText + " ," + text);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(CLICKS_KEY, clicks + " ");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(CLICKS_KEY)) {
            Toast.makeText(this, "Clicks =  " + savedInstanceState.getString(CLICKS_KEY), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Req_code) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", intent.getStringExtra(DATA));
        }
    }
}