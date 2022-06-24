package com.christopherhield.textview_edittext_button;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText userText;
    private TextView output1;
    private TextView output2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind var's to the screen widgets
        userText = findViewById(R.id.editText);
        output1 = findViewById(R.id.textView1);
        output2 = findViewById(R.id.textView2);

    }

    public void button1Clicked(View v) {   // Listener for button 1
        String text = userText.getText().toString();
        if (!text.trim().isEmpty())
            output1.setText(String.format("B1: %s", text));
    }

        public void button2Clicked(View v) {   // Listener for button 2
        String text = userText.getText().toString();
        if (!text.trim().isEmpty())
            output2.setText(String.format("B2: %s", text));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("OUTPUT1", output1.getText().toString());
        outState.putString("OUTPUT2", output2.getText().toString());

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        output1.setText(savedInstanceState.getString("OUTPUT1"));
        output2.setText(savedInstanceState.getString("OUTPUT2"));
    }
}
