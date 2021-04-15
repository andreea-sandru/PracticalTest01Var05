package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, otherActivity;
    TextView textView;
    int Req_code = 2;
    int clicks = 0;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private final static String CLICKS_KEY = "clicks";

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
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            clicks ++;
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
}