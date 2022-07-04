package com.example.userbtbs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnLogin,btnSignUp;

    private EditText edtEmail,edtPassword;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialize();
        Buttons();
    }

    private void Initialize(){
        mAuth = FirebaseAuth.getInstance();

        //Button
        btnLogin = findViewById(R.id.btnLgLogin);
        btnSignUp = findViewById(R.id.btnLgSignUp);

        //EditText
        edtEmail = findViewById(R.id.edtLgEmail);
        edtPassword = findViewById(R.id.edtLgPassword);
        progressDialog = new ProgressDialog(this);
    }

    private void Buttons(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(in);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edtEmail.getText().toString();
                String Password = edtPassword.getText().toString();
                
                if(!Email.isEmpty() && !Password.isEmpty()){
                    progressDialog.setTitle("Processing");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Login(Email,Password);
                }else {
                    Toast.makeText(LoginActivity.this,"Please fill each box",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void Login(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        progressDialog.dismiss();
                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(in);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Please verify your email",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
