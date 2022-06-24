package com.example.christopher.nobundlesave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText numText;
    private TextView history;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            Toast.makeText(this, "savedInstanceState is NULL", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "savedInstanceState is NOT NULL", Toast.LENGTH_LONG).show();

        // Bind a variable to the screen widgets
        numText = findViewById(R.id.phoneNum);
        history = findViewById(R.id.history);
    }

    public void buttonClicked(View v) {

        String newText = numText.getText().toString();
        String historyText = history.getText().toString();
        history.setText(String.format("%s\n%s", newText, historyText));
        numText.setText("");

        value += Integer.parseInt(newText);
        Toast.makeText(this, "Current value: " + value, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("HISTORY", history.getText().toString());
        outState.putInt("VALUE", value);

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        history.setText(savedInstanceState.getString("HISTORY"));
        value = savedInstanceState.getInt("VALUE");
    }
}
