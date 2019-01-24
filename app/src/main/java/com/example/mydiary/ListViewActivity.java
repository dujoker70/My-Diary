package com.example.mydiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ListViewActivity extends AppCompatActivity  {

    private String email;

    private String path;

    private ListView listView;

    private File[] files;

    private String[] titles, subtitles;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        email = getIntent().getStringExtra("email");
        path = getFilesDir().getPath() + "/" + email;
        listView = findViewById(R.id.listViewId);



        File dir = new File(path);

        if(!dir.exists()) {
            return ;
        }

        files = dir.listFiles();

        try {
            makeTitlesSubtitles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(this, titles, subtitles);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListViewActivity.this, NoteViewActivity.class);
                String now = files[i].getAbsolutePath();
                intent.putExtra("road", now);
                System.out.println("path on here = " + files[i].getAbsolutePath());
                startActivity(intent);
            }
        });
    }

    private void makeTitlesSubtitles() throws FileNotFoundException {
        titles = new String[files.length];
        subtitles = new String[files.length];

        for(int i = 0; i < files.length; i++) {
            Scanner scanner = new Scanner(files[i]);
            scanner.nextLine();
            titles[i] = scanner.nextLine();
            subtitles[i] = scanner.nextLine();
            scanner.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.addNewNoteId)
        {
            Intent intent = new Intent(ListViewActivity.this,AddNewNoteActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null).show();
    }
}