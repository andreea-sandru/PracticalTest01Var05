package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    Button verify, cancel;
    TextView textView;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        textView = findViewById(R.id.textView);
        verify = findViewById(R.id.verufyButton);
        cancel = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("text")) {
            String text = intent.getStringExtra("text");
            //int numberOfClicks = intent.getIntExtra("clicks", 0);
            textView.setText(String.valueOf(text));
        }

        verify.setOnClickListener(buttonClickListener);
        cancel.setOnClickListener(buttonClickListener);

    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.verufyButton:
                    setResult(RESULT_OK);
                    finish();
                    break;

                case R.id.cancelButton:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
            }
        }
    }
}