package com.example.mydiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ListViewActivity extends AppCompatActivity {

    private String email;

    private String path;

    private LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        email = getIntent().getStringExtra("email");
        path = getFilesDir().getPath() + "/" + email;
        linearLayout = findViewById(R.id.linearLayoutId);

        File dir = new File(path);

        if(!dir.exists()) {
            return ;
        }

        File[] files = dir.listFiles();

        for(File file : files) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String string, note = "";
                while((string = bufferedReader.readLine()) != null) {
                    note += string;
                }
                bufferedReader.close();
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                textView.setTextColor(Color.rgb(0, 0, 0));
                textView.setMaxEms(10);
                textView.setText(note);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextSize(40);
                linearLayout.addView(textView);

            } catch (Exception e) {
                e.printStackTrace();
            }
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