package com.example.mydiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class NoteViewActivity extends AppCompatActivity {

    private TextView textView;
    private String path;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);

        textView = findViewById(R.id.noteViewId);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("road");
        file = new File(path);
        try {
            setNote();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setNote() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String temp = "";
        scanner.nextLine();
        String title = scanner.nextLine();
        String subtitle = scanner.nextLine();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        while(scanner.hasNext()) {
            temp += scanner.nextLine() + "\n";
        }
        scanner.close();
        textView.setText(temp);
        textView.setTextColor(Color.rgb(0, 0, 0));
        textView.setTextSize(20);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                onEdit();
                return true;
            case R.id.action_delete:
                onDelete();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onEdit() {
        
    }

    private void onDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete note?")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteNote();
                            }
                        })
                .setNegativeButton("No", null)
                .create()
                .show();
    }


    private void deleteNote() {
        file.delete();
        startActivity(new Intent(NoteViewActivity.this, ListViewActivity.class));
    }
}
