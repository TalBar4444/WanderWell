package com.example.hotelsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelsapp.logic.Hotel;
import com.example.hotelsapp.R;
import com.example.hotelsapp.activities.fragments.Adapter_Hotel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HotelsActivity extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_DEST = "KEY_DEST";
    public static final String KEY_CHECK_IN = "KEY_CHECK_IN";
    public static final String KEY_CHECK_OUT = "KEY_CHECK_OUT";
    public static final String KEY_ADULTS = "KEY_ADULTS";
    public static final String KEY_KIDS = "KEY_KIDS";

    private Bundle bundle;

//    private Location currentLocation;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int REQUEST_CODE = 101;

    private MaterialToolbar hotels_topAppBar_back;

    private MaterialRadioButton hotels_BTN_popularity,hotels_BTN_price,hotels_BTN_Rank;
    private RecyclerView hotel_LST_hotels;
    private AppCompatImageView main_IMG_background;
    private ArrayList<Hotel> hotels;
    //private Adapter_Hotel adapter_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        bundle = getIntent().getBundleExtra(SearchActivity.KEY_BUNDLE);
        findViews();
        hotels = DataManager.mockHotels();

        initList();
    }

    private void findViews(){
        hotel_LST_hotels = findViewById(R.id.hotel_LST_hotels);
        main_IMG_background = findViewById(R.id.main_IMG_background);
        hotels_topAppBar_back = findViewById(R.id.hotels_topAppBar_back);
        hotels_BTN_Rank= findViewById(R.id.hotels_BTN_Rank);
        hotels_BTN_price= findViewById(R.id.hotels_BTN_price);
        hotels_BTN_popularity= findViewById(R.id.hotels_BTN_popularity);


        Imager.me().imageByDrawableCrop(main_IMG_background, R.drawable.background);
    }

    private void initList() {
        Adapter_Hotel adapter_hotel = new Adapter_Hotel(hotels);
        adapter_hotel.setOnHotelClickListener(new Adapter_Hotel.OnHotelClickListener() {
            @Override
            public void onClick(View view, Hotel hotel, int position) {
                Toast.makeText(HotelsActivity.this, hotel.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLikeClicked(View view, Hotel hotel, int position) {
                likeClicked(position);
            }
        });

        // one item in a row
        //main_LST_games.setLayoutManager(new LinearLayoutManager(this));

        // Grid view
//        main_LST_games.setLayoutManager(new GridLayoutManager(this, 2));

        hotel_LST_hotels.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        hotel_LST_hotels.setHasFixedSize(true);
        hotel_LST_hotels.setAdapter(adapter_hotel);

    }

    private void onBTNBack(){
        hotels_topAppBar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToSearchPage();
            }
        });
    }

    private void likeClicked(int position) {
        Hotel hotel = hotels.get(position);
        hotel.setLiked(!hotel.isLiked());
        hotel_LST_hotels.getAdapter().notifyItemChanged(position);

        Snackbar
                .make(findViewById(android.R.id.content),(hotel.isLiked() ? "liked " : "unliked ") + hotel.getTitle(), Snackbar.LENGTH_LONG)
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
//        if(bundle == null) {
//            Bundle myBundle = new Bundle();
//            intent.putExtra(KEY_BUNDLE, myBundle);
//        }
        startActivity(intent);
        finish();
    }


}
