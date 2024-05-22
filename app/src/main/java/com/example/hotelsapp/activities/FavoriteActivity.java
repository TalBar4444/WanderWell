package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.R;
import com.example.hotelsapp.activities.fragments.Adapter_Hotel;
import com.example.hotelsapp.model.Hotel;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavoriteActivity extends AppCompatActivity {

    private Bundle bundle;
    private View rootView;

    private MaterialToolbar hotels_topAppBar_back;
    private RecyclerView hotel_LST_hotels;
    private MaterialRadioButton hotels_BTN_popularity,hotels_BTN_price,hotels_BTN_center;
    private AppCompatImageView hotel_IMG_background;
    private AppCompatTextView hotels_LBL_sortByTitle;
    private RadioGroup hotels_BTN_allRadio;
    private List<Hotel> hotelList;
    private DatabaseReference mRootRef;
    private FirebaseUser fUser;
    String userId;
    private String hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        findViews();
        setViewGone();
        hotels_topAppBar_back.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                backToSearchPage();
            }
        });
        mRootRef = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = fUser.getUid();
        hotelList = new ArrayList<>();
        getFavoriteHotels();
    }

    private void getFavoriteHotels() {
        DatabaseReference hotelsRef = mRootRef.child("Users").child(userId).child("Hotels");
        hotelsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String hotelId = childSnapshot.getKey();
                    Hotel hotel = childSnapshot.getValue(Hotel.class);

                    hotelList.add(hotel);

                }
                if(hotelList.size()==0)
                    openFailedLoadingPage();
                initList((ArrayList<Hotel>) hotelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void setViewGone() {
        hotels_LBL_sortByTitle.setVisibility(View.GONE);
        hotels_BTN_allRadio.setVisibility(View.GONE);
        hotels_BTN_price.setVisibility(View.GONE);
        hotels_BTN_popularity.setVisibility(View.GONE);
        hotels_BTN_center.setVisibility(View.GONE);
    }

    private void findViews(){
        hotel_LST_hotels = findViewById(R.id.hotel_LST_hotels);
        rootView = findViewById(android.R.id.content);
        hotel_IMG_background = findViewById(R.id.hotel_IMG_background);
        hotels_topAppBar_back = findViewById(R.id.hotels_topAppBar_back);
        hotels_BTN_center = findViewById(R.id.hotels_BTN_center);
        hotels_BTN_price= findViewById(R.id.hotels_BTN_price);
        hotels_BTN_popularity= findViewById(R.id.hotels_BTN_popularity);
        hotels_LBL_sortByTitle= findViewById(R.id.hotels_LBL_sortByTitle);
        hotels_BTN_allRadio= findViewById(R.id.hotels_BTN_allRadio);

        Imager.me().imageByDrawableCrop(hotel_IMG_background, R.drawable.background);
    }

    private void initList(ArrayList<Hotel> hotels) {
        Adapter_Hotel adapter_hotel = new Adapter_Hotel(hotels, this.getClass().getSimpleName());
        adapter_hotel.setOnHotelClickListener(new Adapter_Hotel.OnHotelClickListener() {
            @Override
            public void onClick(View view, Hotel hotel, int position) {
                MySignal.getInstance().toast(hotel.getTitle() + " clicked");
            }

            @Override
            public void onLikeClicked(View view, Hotel hotel, int position) {
                likeClicked(position);
            }
        });

        hotel_LST_hotels.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        hotel_LST_hotels.setHasFixedSize(true);
        hotel_LST_hotels.setAdapter(adapter_hotel);

    }

    private void likeClicked(int position) {
        Hotel hotel = hotelList.get(position);
        hotel.setLiked(!hotel.isLiked());
        hotel_LST_hotels.getAdapter().notifyItemChanged(position);


        if(!hotel.isLiked()) {
            mRootRef.child("Users").child(userId).child("Hotels").child(hotel.getId()).removeValue();

        }else
            mRootRef.child("Users").child(userId).child("Hotels").child(hotel.getId()).setValue(hotel);

        Snackbar.make(findViewById(android.R.id.content),(hotel.isLiked() ? "liked " : "unliked ") + hotel.getTitle(), Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hotel.setLiked(!hotel.isLiked());
                        hotel_LST_hotels.getAdapter().notifyItemChanged(position);
                    }
                })
                .show();
    }

    private void backToSearchPage(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    private void openFailedLoadingPage() {
        Intent intent = new Intent(this, FailedLoadActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            myBundle.putString(FailedLoadActivity.KEY_CLASS, this.getClass().getSimpleName());
            intent.putExtra(FailedLoadActivity.KEY_BUNDLE, myBundle);
        }
        else{
            bundle.putString(FailedLoadActivity.KEY_CLASS, this.getClass().getSimpleName());
            intent.putExtra(FailedLoadActivity.KEY_BUNDLE, bundle);
        }
        startActivity(intent);
    }

}
