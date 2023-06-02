package com.example.hotelsapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.hotelsapp.MySignal;
import com.example.hotelsapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private TextInputLayout search_LAY_dest, search_LAY_checkIn, search_LAY_checkOut;
    private TextInputEditText search_EDT_dest, search_EDT_checkIn, search_EDT_checkOut;
    private MaterialButton search_BTN_findHotels, search_BTN_search, search_BTN_favorite;
    private Calendar checkInCalendar, checkOutCalendar;
    private String checkIn, checkOut;
    private SimpleDateFormat dateFormat;
    private AutoCompleteTextView search_PICK_adults, search_PICK_kids;
    private String adults, kids;
    private ArrayAdapter<String> adapterAdults, adapterKids;
    private String[] itemAdults = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] itemKids = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getBundleExtra(LoginActivity.KEY_BUNDLE);
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
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getCurrentLocation();
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

        search_PICK_kids = findViewById(R.id.search_PICK_kids);
        adapterKids = new ArrayAdapter<String>(this, R.layout.list_item, itemKids);
        search_PICK_kids.setAdapter(adapterKids);

        search_BTN_findHotels = findViewById(R.id.search_BTN_find);
        search_BTN_search = findViewById(R.id.search_BTN_search);
        search_BTN_favorite = findViewById(R.id.search_BTN_favorite);
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
            //         datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                Log.d("selectedDate", formatDate(selectedDate));

                if (isCheckIn) {
                    checkInCalendar = selectedDate;
                    checkIn = formatDate(checkInCalendar);
                    search_LAY_checkIn.setError(null);
                    Log.d("checkIn",checkIn);
                    search_EDT_checkIn.setText(formatDate(checkInCalendar));
                    //MySignal.getInstance().toast("Check-In Date: " + formatDate(checkInCalendar));
                } else {
                    checkOutCalendar = selectedDate;
                    //MySignal.getInstance().toast("Check-Out Date: " + formatDate(checkOutCalendar));
                    checkOut = formatDate(checkOutCalendar);
                    search_LAY_checkOut.setError(null);
                    Log.d("checkOut",checkOut);
                    search_EDT_checkOut.setText(formatDate(checkOutCalendar));
                }
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, dateSetListener,
                checkInCalendar.get(Calendar.DAY_OF_MONTH),
                checkInCalendar.get(Calendar.MONTH),
                checkInCalendar.get(Calendar.YEAR));
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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

        return String.format("%d/%02d/%02d",day, month, year);
    }

    private void onAmountOfPeopleInRoom() {
        search_PICK_adults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
                adults = item;
                if (!validateAdultPicker())
                    return;
            }
        });

        search_PICK_kids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
                kids = item;
                if (!validateKidsPicker())
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
            adults = "2";
            return true;
        }
        return true;
    }
    private boolean validateKidsPicker(){
        if(search_PICK_kids.getText().toString().trim().isEmpty()) {
            kids = "0";
            return true;
        }
        return true;
    }

    private void confirmViews(View v) {
        if(!validateDestination()|!validateCheckIn() | !validateCheckOut()|!validateAdultPicker() | !validateKidsPicker())
            return;
        openHotelsPage();
    }



    private void openHotelsPage(){
//        Log.d("TAG_ALL", "dest: " + dest + ", adults: " + adults + ", kids: " + kids);
//        Log.d("TAG_DATE","check in: " + checkIn + ", checkOut: " + checkOut);
        String data= "Destination:" + search_EDT_dest + ",checkIn:" + checkIn + ",checkOut:" +
                checkOut + ",adults:"+ adults+",kids:"+kids;
        Log.d("myData" , data);
        Intent intent = new Intent(this, HotelsActivity.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
//            myBundle.putString(HotelsActivity.KEY_BUNDLE, dest);
//            myBundle.putString(HotelsActivity.KEY_BUNDLE, checkIn);
//            myBundle.putString(HotelsActivity.KEY_BUNDLE, checkOut);
//            myBundle.putString(HotelsActivity.KEY_BUNDLE, adults);
//            myBundle.putString(HotelsActivity.KEY_BUNDLE, kids);
            myBundle.putString(HotelsActivity.KEY_BUNDLE,data);
            intent.putExtra(KEY_BUNDLE, myBundle);
        }
        //updateLocationInBundle(myBundle);
        startActivity(intent);
        finish();
    }

//    private void updateLocationInBundle(Bundle bundle){
//        try{
//            bundle.putDouble(ActivityScoreTable.KEY_LAT, currentLocation.getLatitude());
//            bundle.putDouble(ActivityScoreTable.KEY_LNG, currentLocation.getLongitude());
//        }catch (Exception e){
//            bundle.putDouble(ActivityScoreTable.KEY_LAT, 0);
//            bundle.putDouble(ActivityScoreTable.KEY_LNG, 0);
//        }
//    }

}
