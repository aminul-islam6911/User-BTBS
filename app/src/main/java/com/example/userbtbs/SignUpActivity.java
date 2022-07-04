package com.example.userbtbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUp, btnCancel, btnLogIn;

    private EditText edtName, edtEmail, edtPassowrd, edtPhone, edtConPass;
    private String Name, Email, Password, Phone, emaillg, passwordlg;
    private FirebaseAuth mauth;
    private FirebaseUser currentuser;
    private DatabaseReference database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Initialize();
        buttons();
    }

    private void Initialize() {
        progressDialog = new ProgressDialog(this);

        //Firebase
        mauth = FirebaseAuth.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        //Buttons
        btnSignUp = findViewById(R.id.btnSuSignUp);
        btnCancel = findViewById(R.id.btnSuCancel);
        btnLogIn = findViewById(R.id.btnSuLogin);

        //EditText
        edtEmail = findViewById(R.id.edtSuEmail);
        edtPassowrd = findViewById(R.id.edtSuPassword);
        edtPhone = findViewById(R.id.edtSuPhone);
        edtName = findViewById(R.id.edtSuName);
        edtConPass = findViewById(R.id.edtSuConfirmPassword);
    }

    private void buttons() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                edtEmail.setText("");
                edtPhone.setText("");
                edtPassowrd.setText("");
                edtConPass.setText("");
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edtEmail.getText().toString();
                Password = edtPassowrd.getText().toString();
                Phone = edtPhone.getText().toString();
                Name = edtName.getText().toString();
                String STConfirm_Password = edtConPass.getText().toString();
                if (STConfirm_Password.equals(Password)) {
                    if (!Phone.isEmpty() && !Password.isEmpty() && !Email.isEmpty()) {
                        progressDialog.setTitle("Registering");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        SignUp(Email, Password);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Please fill each box", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Password do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SignUp(String email, String password) {
        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                Register(Name, Email, Password);
                                Login();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Wrong email address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Register(String Name, String Email, String Password) {
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String Current_Uid = currentuser.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(Current_Uid);
        HashMap<String, String> user = new HashMap<>();
        user.put("Name", Name);
        user.put("Email", Email);
        user.put("Password", Password);
        database.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Filed to register info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Login() {
        Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(in);
    }
}