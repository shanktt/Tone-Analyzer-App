package com.example.ludwigvonpoopen.toneanalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String text;
    String toneStr;

    EditText textInput;

    Button submit;

    ToneAnalyst tone = new ToneAnalyst();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = (EditText) findViewById(R.id.textAnalysis);

        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                text = textInput.getText().toString();
                new Thread(new Runnable () {
                    public void run() {
                        getToneStr();

                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                showToast();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    public void getToneStr()
    {
        tone.setText(text);
        toneStr = tone.sendTone();
    }

    public void showToast() {
        Toast.makeText(MainActivity.this, toneStr, Toast.LENGTH_LONG).show();
    }

}
