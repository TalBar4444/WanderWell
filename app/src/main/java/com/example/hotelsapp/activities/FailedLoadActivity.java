package com.example.hotelsapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.hotelsapp.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FailedLoadActivity extends AppCompatActivity {

    public static final String KEY_CLASS = "KEY_CLASS";
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private AppCompatTextView failed_LBL_noMatch;
    private MaterialToolbar failed_topAppBar_back;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_load);
        findViews();
        bundle = getIntent().getBundleExtra(FailedLoadActivity.KEY_BUNDLE);
        String className = bundle.getString(KEY_CLASS);
        if(className!=null) {
            switch (className) {
                case "HotelsActivity":
                    failed_LBL_noMatch.setText(R.string.hotels_not_found);
                    break;
                case "FavoriteActivity":
                    failed_LBL_noMatch.setText(R.string.favorite_not_found);
                    break;
                case "SearchActivity":
                    failed_LBL_noMatch.setText(R.string.search_hotels_available);
                    break;
                default:
                    failed_LBL_noMatch.setText(R.string.default_not_found);
                    break;
            }
        }


        failed_topAppBar_back.setNavigationOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToSearchPage();
            }
        });
    }

    private void findViews(){
        failed_topAppBar_back = findViewById(R.id.failed_topAppBar_back);
        failed_LBL_noMatch =  findViewById(R.id.failed_LBL_noMatch);
    }

    private void backToSearchPage(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}
