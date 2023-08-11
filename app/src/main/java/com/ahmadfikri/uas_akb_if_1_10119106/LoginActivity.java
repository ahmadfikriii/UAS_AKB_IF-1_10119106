package com.ahmadfikri.uas_akb_if_1_10119106;
/*
NIM     : 10119106
Nama    : Ahmad Fikri Maulana
Kelas   : IF-1/S1/VI
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtRegister;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        imgBack = findViewById(R.id.imgBack);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan tunggu!");
        progressDialog.setCancelable(false);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(v -> {
            if (edtEmail.getText().length()>0 && edtPassword.getText().length()>0){
                login(edtEmail.getText().toString(), edtPassword.getText().toString());
            }else {
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data!",Toast.LENGTH_SHORT).show();
            }
        });

        edtPassword.setOnClickListener(v -> {
            password = edtPassword.getText().toString();
            if (password.equals("")){
                Toast.makeText(LoginActivity.this,"Enter Password!", Toast.LENGTH_SHORT);
            }else {
                Toast.makeText(LoginActivity.this,password, Toast.LENGTH_SHORT);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void login(String email, String password){
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    if (task.getResult().getUser()!=null){
                        reload();
                    }else {
                        Toast.makeText(getApplicationContext(),"Login Berhasil!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Login Gagal!",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            reload();
        }
    }
}