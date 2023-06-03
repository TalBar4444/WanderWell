package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelsapp.R;
import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {
    private MaterialButton login_BTN_login,login_BTN_signUp;
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getBundleExtra(SearchActivity.KEY_BUNDLE);
        findViews();
        onLoginAction();
    }

    private void findViews(){
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_signUp = findViewById(R.id.login_BTN_signUp);
    }

    private void onLoginAction() {
        login_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchPage();
            }
        });
    }

    private void openSearchPage(){
        Intent intent = new Intent(this, SearchActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }

    private void openSignUpPage(){
        Intent intent = new Intent(this, SignUpActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }
}
