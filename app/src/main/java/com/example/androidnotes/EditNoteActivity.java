package com.example.androidnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class EditNoteActivity  extends AppCompatActivity {
    //private final String TAG = "MainActivity";
    private EditText titleText;
    private EditText contentText;
    private MenuItem save;
    public static final String dataHolder = "DATA HOLDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);

        titleText = findViewById(R.id.titleEdit_display);
        contentText = findViewById(R.id.contentEdit_display);
        contentText.setMovementMethod(new ScrollingMovementMethod());
        save = findViewById(R.id.saveNote_item);
        Intent intent = getIntent();

        if(getIntent().hasExtra(Intent.EXTRA_TEXT)){
            Notes text = (Notes)intent.getSerializableExtra(Intent.EXTRA_TEXT);
            titleText.setText(text.getNoteTitle());
            contentText.setText(text.getNotePart());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater: takes layout and builds object and returns view
        getMenuInflater().inflate(R.menu.savenote_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveNote_item){//works on new note, doesn't seem to like saving on existing notes too much
            if(titleText.getText().toString() != ""){
                String title = titleText.getText().toString();
                String excerpt = contentText.getText().toString();

                //notesList.add(0, new Notes(title,excerpt));
                //myAdapter.notifyItemInserted(0);

                Notes dTarget = new Notes(title, excerpt);
                Intent sender = new Intent();

                MainActivity.notesList.add(0,dTarget);
                MainActivity.myAdapter.notifyItemInserted(0);

                sender.putExtra(dataHolder,dTarget);
                setResult(RESULT_OK,sender);//send intent full of stuff back to main
                finish();
            }
            else{
                Toast.makeText(this, "Your note needs a title!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);

    }
    public void returnData(MenuItem item){

       if(titleText.getText().toString() != ""){
           String title = titleText.getText().toString();
           String excerpt = contentText.getText().toString();

           //notesList.add(0, new Notes(title,excerpt));
           //myAdapter.notifyItemInserted(0);
           
           Notes dTarget = new Notes(title, excerpt);
           Intent sender = new Intent();

           MainActivity.notesList.add(0,dTarget);
           MainActivity.myAdapter.notifyItemInserted(0);

           sender.putExtra(dataHolder,dTarget);
           setResult(RESULT_OK,sender);//send intent full of stuff back to main
           finish();
       }
       else{
           Toast.makeText(this, "Your note needs a title!", Toast.LENGTH_SHORT).show();
       }
    }
    @Override
    public void onBackPressed(){//pressing No works fine on existing notes, saving over existing notes doesn't seem to work
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save");
        builder.setMessage("Do you want to save this data?");
        builder.setPositiveButton("Yes",(dialog,id)->returnData(null));
        builder.setNegativeButton("No",(dialog,id)->finish());
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
