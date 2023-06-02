package com.example.hotelsapp.activities;

import android.location.Location;
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


    private RecyclerView main_LST_hotels;
    private AppCompatImageView main_IMG_background;
    private ArrayList<Hotel> hotels;
    //private Adapter_Hotel adapter_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        bundle = getIntent().getBundleExtra(MainActivity.KEY_BUNDLE);
        findViews();
        hotels = DataManager.mockHotels();

        initList();
    }

    private void findViews(){
        main_LST_hotels = findViewById(R.id.main_LST_hotels);
        main_IMG_background = findViewById(R.id.main_IMG_background);

        Imager.me().imageByDrawableCrop(main_IMG_background, R.drawable.img_pattern_big);
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

        main_LST_hotels.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        main_LST_hotels.setHasFixedSize(true);
        main_LST_hotels.setAdapter(adapter_hotel);

    }

    private void likeClicked(int position) {
        Hotel hotel = hotels.get(position);
        hotel.setLiked(!hotel.isLiked());
        main_LST_hotels.getAdapter().notifyItemChanged(position);

        Snackbar
                .make(findViewById(android.R.id.content),(hotel.isLiked() ? "liked " : "unliked ") + hotel.getTitle(), Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hotel.setLiked(!hotel.isLiked());
                        main_LST_hotels.getAdapter().notifyItemChanged(position);
                    }
                })
                .show();
    }

}
