package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private MaterialButton login_BTN_login;
    private AppCompatEditText login_EDT_email,login_EDT_password;
    private AppCompatTextView login_LBL_newRegister;
    private FirebaseAuth mAuth;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bundle = getIntent().getBundleExtra(LoginActivity.KEY_BUNDLE);
        findViews();

        mAuth = FirebaseAuth.getInstance();

        login_LBL_newRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPage();
            }
        });

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = login_EDT_email.getText().toString();
                String txt_password = login_EDT_password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    MySignal.getInstance().toast("Empty credentials!");
                    login_EDT_email.setError("Required*");
                    login_EDT_password.setError("Required*");
                } else{
                    loginUser(txt_email,txt_password);
                }
            }
        });
    }

    private void findViews(){
        login_EDT_email = findViewById(R.id.login_EDT_email);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_LBL_newRegister = findViewById(R.id.login_LBL_newRegister);
    }

    private void openRegistrationPage() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    MySignal.getInstance().toast("Login successful!");
                    openSearchPage();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showSnackbar("Login failed");
                MySignal.getInstance().toast(e.getMessage());
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .show();
    }

    private void openSearchPage(){
        Intent intent = new Intent(LoginActivity.this, SearchActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
