package com.example.section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.section.Database.User;
import com.example.section.Database.UserDB;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText UserName,PassWord;
    Button Login,Register;
    UserDB userDB;
    ArrayList<User>arrayList;
  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        UserName = findViewById(R.id.Login_ed_username);
        PassWord = findViewById(R.id.Login_ed_password);
        Login = findViewById(R.id.Login_btn_login);
        Register = findViewById(R.id.Login_btn_reg);

        userDB = new UserDB(this);
        arrayList = new ArrayList<>();



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = UserName.getText().toString();
                String password = PassWord.getText().toString();
                if (!(username.equals("")&&password.equals(""))){
                    arrayList = userDB.getUsers();
                    for (int i =0 ;i <arrayList.size();i++){
                        if (arrayList.get(i).getUsername().equals(username)&&arrayList.get(i).getPassword().equals(password)){
                            Log.d("login", "it is login true");
                            startActivity(new Intent(getBaseContext(), Home.class));
                        }
                    }
                }else{
                    Toast.makeText(Login.this, "Don't let username or password empty! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Register.setOnClickListener(view->{
            startActivity(new Intent(getBaseContext(),Register.class));
        });
    }
}