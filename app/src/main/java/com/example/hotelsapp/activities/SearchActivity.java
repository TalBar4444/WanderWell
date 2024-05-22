package com.example.hotelsapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.R;
import com.example.hotelsapp.model.LocationData;
import com.example.hotelsapp.server.ServerConnect;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private TextInputLayout search_LAY_dest, search_LAY_checkIn, search_LAY_checkOut;
    private TextInputEditText search_EDT_dest, search_EDT_checkIn, search_EDT_checkOut;
    private MaterialButton search_BTN_findHotels, search_BTN_search, search_BTN_favorite,search_BTN_logout;
    private Calendar checkInCalendar, checkOutCalendar;
    private String checkIn, checkOut;
    private SimpleDateFormat dateFormat;
    private AutoCompleteTextView search_PICK_adults, search_PICK_rooms;
    private int adults, rooms;
    private ArrayAdapter<String> adapterAdults, adapterRooms;
    private String[] itemAdults = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] itemRooms = {"1", "2", "3", "4"};
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bundle = getIntent().getBundleExtra(SearchActivity.KEY_BUNDLE);

        findViews();
        onEditDestination();
        onDateEDT();
        onAmountOfPeopleInRoom();

        search_BTN_findHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmViews(v);
            }
        });

        search_BTN_favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMyFavoritePage();
            }
        });

        search_BTN_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        search_BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openMainActivity();
            }
        });

    }

    private void findViews() {

        search_LAY_dest = findViewById(R.id.search_LAY_dest);
        search_EDT_dest = findViewById(R.id.search_EDT_dest);
        search_LAY_checkIn = findViewById(R.id.search_LAY_checkIn);
        search_EDT_checkIn = findViewById(R.id.search_EDT_checkIn);
        search_LAY_checkOut = findViewById(R.id.search_LAY_checkOut);
        search_EDT_checkOut = findViewById(R.id.search_EDT_checkOut);

        search_PICK_adults = findViewById(R.id.search_PICK_adults);
        adapterAdults = new ArrayAdapter<String>(this, R.layout.list_item, itemAdults);
        search_PICK_adults.setAdapter(adapterAdults);

        search_PICK_rooms = findViewById(R.id.search_PICK_room);
        adapterRooms = new ArrayAdapter<String>(this, R.layout.list_item, itemRooms);
        search_PICK_rooms.setAdapter(adapterRooms);

        search_BTN_findHotels = findViewById(R.id.search_BTN_find);
        search_BTN_search = findViewById(R.id.search_BTN_search);
        search_BTN_favorite = findViewById(R.id.search_BTN_favorite);
        search_BTN_logout = findViewById(R.id.search_BTN_logout);
    }

    private void onEditDestination() {
        search_EDT_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_LAY_dest.setError(null);
            }
        });
    }
    private void onDateEDT(){
        search_EDT_checkIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // for lower the keyboard
                imm.hideSoftInputFromWindow(search_EDT_checkIn.getWindowToken(), 0);
                showDatePicker(true);
            }
        });

        search_EDT_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(false);
            }
        });

        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();
    }

    private void showDatePicker(final boolean isCheckIn) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                if (isCheckIn) {
                    checkInCalendar = selectedDate;
                    checkIn = formatDateForApi(checkInCalendar);
                    search_LAY_checkIn.setError(null);
                    search_EDT_checkIn.setText(formatDate(checkInCalendar));
                } else {
                    checkOutCalendar = selectedDate;

                    checkOut = formatDateForApi(checkOutCalendar);
                    search_LAY_checkOut.setError(null);
                    search_EDT_checkOut.setText(formatDate(checkOutCalendar));
                }
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, dateSetListener,
                checkInCalendar.get(Calendar.DAY_OF_MONTH),
                checkInCalendar.get(Calendar.MONTH),
                checkInCalendar.get(Calendar.YEAR));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        if(checkInCalendar != null && !isCheckIn)
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
        else
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private String formatDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d",day, month, year);
    }
    private String formatDateForApi(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d",year, month, day );
    }

    private void onAmountOfPeopleInRoom() {
        search_PICK_adults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                adults = Integer.parseInt(item);
                if (!validateAdultPicker())
                    return;
            }
        });

        search_PICK_rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                rooms = Integer.parseInt(item);
                if (!validateRoomsPicker())
                    return;
            }
        });
    }
    private boolean validateDestination() {
        if (search_EDT_dest.getText().toString().trim().isEmpty()) {
            search_LAY_dest.setError("Required*");
            return false;
        } else {
            search_LAY_dest.setError(null);
            search_EDT_dest.getText().toString();
            return true;
        }
    }
    private boolean validateCheckIn() {
        if (search_EDT_checkIn.getText().toString().trim().isEmpty()) {
            checkIn = DateFormat.getDateInstance().toString();
            return true;
        }
        return true;
    }
    private boolean validateCheckOut(){
        if(search_EDT_checkOut.getText().toString().trim().isEmpty()) {
            search_LAY_checkOut.setError("Required*");
            return false;
        }
        else {

            return true;
        }
    }
    private boolean validateAdultPicker(){
        if(search_PICK_adults.getText().toString().trim().isEmpty()) {
            adults = 2;
            return true;
        }
        return true;
    }
    private boolean validateRoomsPicker(){
        if(search_PICK_rooms.getText().toString().trim().isEmpty()) {
            rooms = 1;
            return true;
        }
        return true;
    }

    private void confirmViews(View v) {
        if(!validateDestination()|!validateCheckIn() | !validateCheckOut()|!validateAdultPicker() | !validateRoomsPicker())
            return;

        getGeoId(search_EDT_dest.getText().toString());

    }

    private void getGeoId(String destination) {
        ServerConnect.getInstance().invokeGetGeoLocation(destination, new Callback<Object>() {
            @Override
            public void onResponse(retrofit2.Call<Object> call, Response<Object> response) {
                Log.d("checkMe","Im in Search, in function onResponse start");
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        LocationData location = (LocationData) response.body();
                        openHotelsPage(location.getGeoId(),location.getSecondaryText());
                    }
                } else {
                    MySignal.getInstance().toast("unsuccessful response of geoId ");
                    openFailedLoadingPage();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Object> call, Throwable t) {
                openFailedLoadingPage();
                MySignal.getInstance().toast("Failed to get geoId");
            }
        });
    }

    private void openHotelsPage(String geoId,String location){
        Intent intent = new Intent(this, HotelsActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            myBundle.putString(HotelsActivity.KEY_LOCATION, location);
            myBundle.putString(HotelsActivity.KEY_GEOID, geoId);
            myBundle.putString(HotelsActivity.KEY_CHECK_IN, checkIn);
            myBundle.putString(HotelsActivity.KEY_CHECK_OUT, checkOut);
            myBundle.putInt(HotelsActivity.KEY_ADULTS, adults);
            myBundle.putInt(HotelsActivity.KEY_ROOMS, rooms);
            intent.putExtra(HotelsActivity.KEY_BUNDLE, myBundle);
        }
        else {
            bundle.putString(HotelsActivity.KEY_GEOID, geoId);
            bundle.putString(HotelsActivity.KEY_LOCATION, location);
            bundle.putString(HotelsActivity.KEY_CHECK_IN, checkIn);
            bundle.putString(HotelsActivity.KEY_CHECK_OUT, checkOut);
            bundle.putInt(HotelsActivity.KEY_ADULTS, adults);
            bundle.putInt(HotelsActivity.KEY_ROOMS, rooms);
            intent.putExtra(HotelsActivity.KEY_BUNDLE, bundle);
        }
        startActivity(intent);
    }

    private void  openFailedLoadingPage() {
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

    private void openMyFavoritePage() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);

    }

    private void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();


    }


}
