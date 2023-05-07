package com.example.section;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Relax extends AppCompatActivity {
    Button start,stop;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);
        start= findViewById(R.id.relax_start);
        stop= findViewById(R.id.relax_stop);

        start.setOnClickListener(view->{
            startService(new Intent(getBaseContext(),MyService.class));
        });

        stop.setOnClickListener(view->{
            stopService(new Intent(getBaseContext(), MyService.class));
        });
    }
}