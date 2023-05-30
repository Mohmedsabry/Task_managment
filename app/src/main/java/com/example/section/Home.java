
package com.example.section;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.section.Database.TaskDB;
import com.example.section.Database.User;
import com.example.section.Database.UserDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class Home extends AppCompatActivity {
    RecyclerView rv;
    FloatingActionButton add;
    TaskDB db;
    ArrayList<Task>arrayList;
    Adpter adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv = findViewById(R.id.home_rv);
        add = findViewById(R.id.home_btn_add);

        db=new TaskDB(this);
        arrayList= new ArrayList<>();

        arrayList = db.getTasks();
        adpter = new Adpter(arrayList, new Adpter.Listener() {
            @Override
            public void Onclick(int id,Boolean state) {
                    Task task = new Task();
                    task = db.get_task(id);
                    Intent intent = new Intent(getBaseContext(),Show_task.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("state",state);
                    if (state){
                        doNotification(task.getTitle());
                    }
                    startActivityForResult(intent,6);
            }
        },this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getBaseContext(),AddTask.class),2);
            }
        });

        rv.setAdapter(adpter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        arrayList = db.getTasks();
        adpter.setArrayList(arrayList);
    }
    @SuppressLint("MissingPermission")
    public  void doNotification(String name){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel ch = new NotificationChannel("Notify","configure change", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(ch);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(),"Notify");
        builder.setSmallIcon(R.drawable.baseline_notifications_24).setContentTitle("Expired").setContentText("this task "+name+" is Expired").setPriority(NotificationCompat.PRIORITY_HIGH)
        ;
        builder.setTimeoutAfter(5000);
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(1,builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu){
            startActivity(new Intent(getBaseContext(), Relax.class));
        }
        return true;
    }

}