package com.example.hotelsapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.R;
import com.example.hotelsapp.model.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private Bundle bundle;
    private MaterialButton register_BTN_register;
    private AppCompatEditText register_EDT_name,register_EDT_email,register_EDT_username,register_EDT_password;
    private AppCompatTextView register_LBL_newLogin;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        register_LBL_newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        register_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = register_EDT_name.getText().toString();
                String txt_email = register_EDT_email.getText().toString();
                String txt_username = register_EDT_username.getText().toString();
                String txt_password = register_EDT_password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)
                        ||TextUtils.isEmpty(txt_name)){
                    MySignal.getInstance().toast("empty credentials!");
                    register_EDT_name.setError("Required*");
                    register_EDT_email.setError("Required*");
                    register_EDT_username.setError("Required*");
                    register_EDT_password.setError("Required*");
                } else if(txt_password.length() < 6){
                    MySignal.getInstance().toast("Password too short!");
                    register_EDT_password.setError("Password too short!");
                } else{
                    registerUser(txt_name,txt_email,txt_username,txt_password);
                }
            }
        });
    }

    private void findViews(){
        register_EDT_name = findViewById(R.id.register_EDT_name);
        register_EDT_email = findViewById(R.id.register_EDT_email);
        register_EDT_username = findViewById(R.id.register_EDT_userName);
        register_EDT_password = findViewById(R.id.register_EDT_password);
        register_BTN_register = findViewById(R.id.register_BTN_register);
        register_LBL_newLogin = findViewById(R.id.register_LBL_newLogin);
    }

    private void registerUser(String name,String email,String username, String password) {
        pd.setMessage("Please wait");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d("pttt", "User creation successful");
                HashMap<String, Object> map = new HashMap<>();

                HashMap<String, Object> mapHotels = new HashMap<>();
                mapHotels.put("hotelId",null);

                String userId = mAuth.getCurrentUser().getUid();
                map.put("userId", userId);
                map.put("name", name);
                map.put("email", email);
                map.put("username", username);
                map.put("Hotels",mapHotels);

                Log.d("pttt", "Attempting to write user data to database");

                mRootRef.child("Users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("pttt", "Database write complete");
                        pd.dismiss();
                        if(task.isSuccessful()){
                            MySignal.getInstance().toast("Registration complete!");
                            openSearchPage();
                        } else {
                            MySignal.getInstance().toast("Database write failed: " + task.getException().getMessage());
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Log.e("pttt", "User creation failed: " + e.getMessage());
                showSnackbar("Registration failed");
                MySignal.getInstance().toast("Registration failed! " + e.getMessage());
            }
        });
    }


    private void openLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .show();
    }

    private void openSearchPage(){
        Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            intent.putExtra(KEY_BUNDLE, myBundle);
        }
        startActivity(intent);
        finish();
    }
}
