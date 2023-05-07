package com.example.section;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.section.Database.User;
import com.example.section.Database.UserDB;

public class Register extends AppCompatActivity {
    EditText UserName,PassWord;
    Button reg;
    UserDB userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserName = findViewById(R.id.Reg_ed_username);
        PassWord = findViewById(R.id.Reg_ed_password);
        reg = findViewById(R.id.Reg_btn_register);

        userDB = new UserDB(this);

        reg.setOnClickListener(view->{
            String username= UserName.getText().toString();
            String password= PassWord.getText().toString();
            if (!(username.equals("")&&password.equals(""))){
                User user = new User(username,password);
                Log.d("insert", userDB.insert(user)+"");
                finish();
            }else{
                Toast.makeText(Register.this, "Don't let username or password empty! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}