package com.example.section;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.section.Database.TaskDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Show_task extends AppCompatActivity {
    EditText title,desc;
    TextView change,update,delete,date;
    Intent intent;
    TaskDB db;
    DateFormat dateFormat;
    Calendar calendar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        title = findViewById(R.id.Show_ed_title);
        date = findViewById(R.id.Show_ed_date);
        desc = findViewById(R.id.Show_ed_desc);
        change = findViewById(R.id.Show_tv_change);
        update = findViewById(R.id.Show_tv_update);
        delete = findViewById(R.id.Show_tv_Delete);

        intent=getIntent();

        int id = intent.getIntExtra("Id",-1);
        Boolean expired = intent.getBooleanExtra("state",false);

        db = new TaskDB(this);
        dateFormat = new SimpleDateFormat("M/d");
        Task task = db.get_task(id);

        if (expired){
            update.setVisibility(View.GONE);
        }

        title.setText(task.getTitle());
        desc.setText(task.getDesc());
        date.setText(dateFormat.format(new Date(task.getDate())).toString());

        title.setEnabled(false);
        date.setEnabled(false);
        desc.setEnabled(false);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                new DatePickerDialog(Show_task.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year,month,day);
                        date.setText(day+"/"+(month+1)+" click to change");
                    }
                }, Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.Delete(id);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setEnabled(true);
                date.setEnabled(true);
                desc.setEnabled(true);
                delete.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
                change.setVisibility(View.VISIBLE);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equals("")||desc.equals("")){
                    Toast.makeText(getBaseContext(),"don't let anything empty",Toast.LENGTH_LONG).show();
                }else{
                    task.setTitle(title.getText().toString());
                    task.setDesc(desc.getText().toString());
                }
                if (calendar!=null)task.setDate(calendar.getTime().toString());
                task.setId(id);
                System.out.println(db.Modify(task));
                finish();
            }
        });
    }
}