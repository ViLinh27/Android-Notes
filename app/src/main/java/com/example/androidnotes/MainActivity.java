package com.example.androidnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.recyclerview.widget.*;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainActivity extends AppCompatActivity
 implements View.OnClickListener, View.OnLongClickListener{
   private static final String TAG = "MainActivity";

    public static final List<Notes> notesList = new ArrayList<>();//main content

    private RecyclerView recyclerView; // Layout's recyclerview

    public static NotesAdapter myAdapter; // Data to recyclerview adapter
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private TextView title;
    private TextView excerpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.item_listRecycler);
        myAdapter = new NotesAdapter(notesList,this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        title = findViewById(R.id.noteTitle);
        excerpt = findViewById(R.id.notePart);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater: takes layout and builds object and returns view
        getMenuInflater().inflate(R.menu.opt_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//menu item passed in as arg

        //never do ifchecks on titles becasue title can change
        if(item.getItemId()==R.id.addNote_item){

            //add new note
            Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
            startActivity(intent);
            //activityResultLauncher.launch(intent);
            //return true;
        }else if(item.getItemId() == R.id.about_item){
            //go to about screen
            //Toast.makeText(this, "about button chosen",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,AboutPage_activity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {// click selectd note to see the content in edit note
        int pos = recyclerView.getChildLayoutPosition(v);

        Notes n = notesList.get(pos);
        Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,n);

        activityResultLauncher.launch(intent);


    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
            int pos = recyclerView.getChildLayoutPosition(v);
            //Notes n = notesList.get(pos);

            //notesList.remove(pos);
            if(!notesList.isEmpty()){

               new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Delete?")
                       .setMessage("Are you sure you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               notesList.remove(pos);
                               myAdapter.notifyItemRemoved(pos);
                           }
                       })

                       .setNegativeButton("No",null)
                       .show();
            }

            return false;//set to true if you one thing then be done//false if you want other listenres to have access
    }

    @Override
    public void onBackPressed() {
            Toast.makeText(this, "The back button was pressed - Bye!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
    }

    public void handleResult(ActivityResult result){
        //int pos = recyclerView.getChildLayoutPosition(v);

        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Intent intent = result.getData();

            Notes nh = (Notes) intent.getSerializableExtra(EditNoteActivity.dataHolder);
            if(nh == null) {
                return;
            }

            String t = nh.getNoteTitle();
            Log.d(TAG,"length of t");

            String e = nh.getNotePart();
            Log.d(TAG,"length of e");

            if(t.length()>=80){
                String tsub = t.substring(0,79);
                title.setText(tsub+"...");
            }
            else{
                title.setText(t);
            }
            if(e.length()>=80){
                String esub = e.substring(0,79);
                excerpt.setText(esub+"...");
            }
            else{
                excerpt.setText(e);
            }

        }
    }

    /*
    @Override
    protected void onResume(){//called when app starts, but also when stopped anc when you come back

        //notesList.clear();
        notesList.addAll(loadFile());
        View v = recyclerView;
        int pos = recyclerView.getChildLayoutPosition(v);

        Notes n = notesList.get(pos);

        title.setText(n.getNoteTitle());
        excerpt.setText(n.getNotePart());

        super.onResume();
    }
    private ArrayList<Notes> loadFile(){//never specifiy file path
        Log.d(TAG, "loadFile: Loading JSON File");
        ArrayList<Notes> nList = new ArrayList<>();

        try{
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                Notes notes = new Notes(title,content);
                nList.add(notes);
            }

        }catch(FileNotFoundException e){
            Toast.makeText(this,getString(R.string.no_file),Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
        return nList;
    }

    @Override
    protected void onPause(){
        //View v = recyclerView;
        //int pos = recyclerView.getChildLayoutPosition(v);

        //Notes n = notesList.get(pos);
        if(!title.getText().toString().isEmpty() && !excerpt.toString().isEmpty()){
            Notes n = new Notes(title.getText().toString(),excerpt.getText().toString());
            notesList.add(n);
        }
        saveNotes();
        super.onPause();
    }
    private void saveNotes(){
        Log.d(TAG, "saveNotes: Saving JSON File");

        try{
            FileOutputStream fos = getApplicationContext()
                    .openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(notesList);
            fos.close();

            Log.d(TAG, "saveNotes: JSON:\n"+notesList.toString());

            Toast.makeText(this, getString(R.string.saved),Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.getStackTrace();
        }
    }

     */
}