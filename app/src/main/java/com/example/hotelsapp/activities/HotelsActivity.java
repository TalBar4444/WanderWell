package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.model.Hotel;
import com.example.hotelsapp.R;
import com.example.hotelsapp.activities.fragments.Adapter_Hotel;
import com.example.hotelsapp.server.ServerConnect;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;
import retrofit2.Response;

public class HotelsActivity extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_GEOID = "KEY_GEOID";
    public static final String KEY_DEST = "KEY_DEST";
    public static final String KEY_CHECK_IN = "KEY_CHECK_IN";
    public static final String KEY_CHECK_OUT = "KEY_CHECK_OUT";
    public static final String KEY_ADULTS = "KEY_ADULTS";
    public static final String KEY_ROOMS = "KEY_ROOMS";
    public static final String KEY_LOCATION = "KEY_LOCATION";

    private Bundle bundle;


    private ProgressBar hotels_loadingProgressBar;
    private AppCompatTextView hotel_LBL_message;
    private MaterialToolbar hotels_topAppBar_back;
    private MaterialRadioButton hotels_BTN_popularity,hotels_BTN_price,hotels_BTN_center;
    private RecyclerView hotel_LST_hotels;
    private AppCompatImageView hotel_IMG_background;
    private ArrayList<Hotel> hotels;
    private String destination,geoId,checkIn, checkOut,location;
    private int adults, rooms;
    private FirebaseUser fUser;
    String userId;
    private DatabaseReference mRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        findViews();
        bundle = getIntent().getBundleExtra(SearchActivity.KEY_BUNDLE);
        if(bundle !=null) {
            location = bundle.getString(KEY_LOCATION);
            hotel_LBL_message.setText(String.format("%s%s", getString(R.string.hotels_message), location));
            hotels_loadingProgressBar.setVisibility(View.VISIBLE);
            getKey();
        }
        mRootRef = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = fUser.getUid();

        hotels_BTN_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchHotels(geoId ,checkIn, checkOut,"PRICE_LOW_TO_HIGH", adults, rooms);
            }
        });
        hotels_BTN_popularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchHotels(geoId ,checkIn, checkOut,"POPULARITY", adults, rooms);
            }
        });
        hotels_BTN_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchHotels(geoId,checkIn, checkOut,"DISTANCE_FROM_CITY_CENTER", adults, rooms);
            }
        });
        hotels_topAppBar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToSearchPage();
            }
        });
    }

    private void getKey() {
        if(bundle.getString(KEY_DEST) != null)
            destination = bundle.getString(KEY_DEST);
        else {
            geoId = bundle.getString(KEY_GEOID);
            checkIn = bundle.getString(KEY_CHECK_IN);
            checkOut = bundle.getString(KEY_CHECK_OUT);
            adults = bundle.getInt(KEY_ADULTS);
            rooms = bundle.getInt(KEY_ROOMS);
            Log.d("checkMe", "Im in Hotel in function getKey, in if, geoId= " + geoId + " checkIn= " + checkIn
                    + " checkOut=" + checkOut + " adults =" + adults + " rooms=" + rooms);
            fetchHotels(geoId, checkIn, checkOut, "BEST_VALUE", adults, rooms);
        }

    }
    private void findViews(){
        hotels_loadingProgressBar = findViewById(R.id.hotels_loadingProgressBar);
        hotel_LST_hotels = findViewById(R.id.hotel_LST_hotels);
        hotel_IMG_background = findViewById(R.id.hotel_IMG_background);
        hotels_topAppBar_back = findViewById(R.id.hotels_topAppBar_back);
        hotel_LBL_message = findViewById(R.id.hotel_LBL_message);
        hotels_BTN_center = findViewById(R.id.hotels_BTN_center);
        hotels_BTN_price= findViewById(R.id.hotels_BTN_price);
        hotels_BTN_popularity= findViewById(R.id.hotels_BTN_popularity);

        Imager.me().imageByDrawableCrop(hotel_IMG_background, R.drawable.new_background);
    }

    private void fetchHotels(String geoId, String checkIn, String checkOut, String sort, int adults, int rooms) {
        ServerConnect.getInstance().invokeGetHotelsByCheapestPrice(geoId,checkIn, checkOut,sort, adults, rooms, new Callback<Object>() {
            @Override
            public void onResponse(retrofit2.Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    hotels = (ArrayList<Hotel>) response.body();
                    if(hotels.size()==0)
                        openFailedLoadingPage();

                    initList(hotels);

                } else {
                    openFailedLoadingPage();
                    MySignal.getInstance().toast("Failed to fetch hotels after success call");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<Object> call, Throwable t) {
                openFailedLoadingPage();
                MySignal.getInstance().toast("Failed to fetch hotels");
            }
        });
    }

    private void initList(ArrayList<Hotel> hotels) {
        hotels_loadingProgressBar.setVisibility(View.GONE);

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
        Hotel hotel = hotels.get(position);
        hotel.setLiked(!hotel.isLiked());
        hotel_LST_hotels.getAdapter().notifyItemChanged(position);

        if(hotel.isLiked()) {
            putHotel(hotel);
            mRootRef.child("Users").child(userId).child("Hotels").child(hotel.getId()).child("liked").setValue(true);
        }
        else
            mRootRef.child("Users").child(userId).child("Hotels").child(hotel.getId()).removeValue();
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

    private void putHotel(Hotel hotel) {

        HashMap<String, Object> hotelsMap = new HashMap<>();
        hotelsMap.put("id",hotel.getId());
        hotelsMap.put("title",hotel.getTitle());
        hotelsMap.put("image",hotel.getImage());
        hotelsMap.put("rating",hotel.getRating());
        hotelsMap.put("numOfAdults",hotel.getNumOfAdults());
        hotelsMap.put("price",hotel.getPrice());
        hotelsMap.put("numOfReviews",hotel.getNumOfReviews());
        hotelsMap.put("liked",hotel.isLiked());

        mRootRef.child("Users").child(userId).child("Hotels").child(hotel.getId()).setValue(hotelsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MySignal.getInstance().toast("Hotel added!! ");
                } else {
                    MySignal.getInstance().toast(task.getException().getMessage());
                }
            }
        });

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
