package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WritingNoteActivity extends AppCompatActivity {

    private TextView dateTextView;
    private EditText editText;

    private String path = null;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_note);

        dateTextView = findViewById(R.id.dateTextViewId);
        editText = findViewById(R.id.noteEditTextId);

        path = getFilesDir().getPath();
        email = getIntent().getStringExtra("email");

        String str = getIntent().getStringExtra("noteTitle");
        String str2 = getIntent().getStringExtra("date");
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setSubtitle(str2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_save) {
            try {
                saveText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(WritingNoteActivity.this,ListViewActivity.class));
        }
        switch(item.getItemId()) {
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void saveText() throws IOException {
        String newPath = path + "/" + email;
        File dir = new File(newPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        int pos = dir.listFiles().length;
        File file = new File(dir, toString(pos) + ".txt");
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(editText.getText().toString());
        bufferedWriter.flush();
        bufferedWriter.close();
        System.out.println("text = " + editText.getText());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        System.out.println("readed = " + bufferedReader.readLine());
        bufferedReader.close();
    }

    private String toString(int n) {
        if(n == 0) {
            return "0";
        }
        String ret = null;
        while(n != 0) {
            ret = (char) (n % 10 + '0') + ret;
            n /= 10;
        }
        return ret;
    }
}
