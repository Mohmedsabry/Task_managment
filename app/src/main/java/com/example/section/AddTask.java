package com.example.section;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.section.Database.TaskDB;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    EditText Title,Desc;
    TextView Date;
    Button add;
    TaskDB db;
    Calendar calendar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Title = findViewById(R.id.Add_ed_title);
        Desc = findViewById(R.id.Add_ed_desc);
        Date = findViewById(R.id.Add_ed_date);
        add = findViewById(R.id.Add_btn_insert);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year,month,day);
                        Date.setText(day+"/"+(month+1)+" click to change");
                    }
                },Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
                dialog.show();
            }
        });

        db = new TaskDB(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Title.getText().toString();
                String desc = Desc.getText().toString();
                if (title.equals("")||desc.equals("")||calendar==null){
                    Toast.makeText(getBaseContext(),"don't let anything empty",Toast.LENGTH_LONG).show();
                }else{
                    Task task = new Task(title,calendar.getTime().toString(),desc);
                    System.out.println(db.insert(task));
                    finish();
                }
            }
        });
    }
}