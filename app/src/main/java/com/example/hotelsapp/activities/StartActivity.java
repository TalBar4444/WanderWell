package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelsapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private MaterialButton login_BTN_login,login_BTN_register;
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();

        login_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterPage();
            }
        });
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });
    }

    private void findViews(){
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_register = findViewById(R.id.login_BTN_register);
    }

    private void openLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }

    private void openRegisterPage(){
        Intent intent = new Intent(this, RegisterActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(LoginActivity.KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }

    private void openSearchPage(){
        Intent intent = new Intent(this, SearchActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(SearchActivity.KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            openSearchPage();
        }
    }
}
