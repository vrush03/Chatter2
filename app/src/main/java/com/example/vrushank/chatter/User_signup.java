package com.example.vrushank.chatter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_signup extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    TextView textViewSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        buttonSignup= (Button) findViewById(R.id.buttonSignup);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewSignup = (TextView)findViewById(R.id.textViewSignup);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            //registerUser();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            //registerUser();
        }
        System.out.println("Here");
        progressDialog.setMessage("Registering,Please wait..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(User_signup.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(User_signup.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            //display some message here
                            Toast.makeText(User_signup.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSignup)
        {
            //System.out.println("Here");
            registerUser();
        }
        if(v == textViewSignup){
            //loginOpen
        }
    }
}
