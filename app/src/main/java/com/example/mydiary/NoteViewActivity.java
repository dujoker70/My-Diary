package com.example.mydiary;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
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
            temp += scanner.next() + "\n";
        }
        scanner.close();
        textView.setText(temp);
        textView.setTextSize(20);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
}
