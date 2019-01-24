package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewNoteActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText;
    private DatePicker datePicker;
    private Button startWritingButton;
    private TextView dateTextView;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        textInputEditText = findViewById(R.id.editTextTitleId);
        datePicker = findViewById(R.id.datePickerId);
        startWritingButton = findViewById(R.id.startWritingId);
        dateTextView = findViewById(R.id.dateTextViewId);
        email = getIntent().getStringExtra("email");

        datePicker.setEnabled(true);
        dateTextView.setText(currentDate());

        startWritingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInputEditText.getText().toString().length() == 0){
                    Toast.makeText(AddNewNoteActivity.this,"Give a title of your note please",Toast.LENGTH_LONG).show();
                }
                else{

                    Intent intent = new Intent(AddNewNoteActivity.this, WritingNoteActivity.class);
                    intent.putExtra("noteTitle", textInputEditText.getText().toString());
                    intent.putExtra("date", currentDate());
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });

    }
    String currentDate()
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(datePicker.getDayOfMonth()+"/");
        stringBuilder.append(datePicker.getMonth()+1+"/");
        stringBuilder.append(datePicker.getYear());
        return  stringBuilder.toString();
    }
}
