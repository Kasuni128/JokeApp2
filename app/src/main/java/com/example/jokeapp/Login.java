package com.example.jokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        e1=findViewById(R.id.editTextEmail);
        e2=findViewById(R.id.editTextPassword);
        b1=findViewById(R.id.buttonLogin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean chkemailpass = db.emailpassword(email,password);
                if(chkemailpass==true)
                {
                    Intent intent = new Intent(Login.this, AddNewJokes.class);
                    startActivity(intent);
                     Toast.makeText(getApplicationContext(),"sucessfully Login",Toast.LENGTH_SHORT).show();}
                else
                    Toast.makeText(getApplicationContext(),"wrong email or password",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
