package com.example.hotelsapp.server;

import android.util.Log;


import com.example.hotelsapp.model.Hotel;
import com.example.hotelsapp.model.HotelData;
import com.example.hotelsapp.model.LocationData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerConnect {
    private static ServerConnect serverConnect = new ServerConnect();
    private final int STATUS_OK = 200;
    private ApiServer apiServer = RetrofitService.getInstance().getRetrofit().create(ApiServer.class);
    public static ServerConnect getInstance() {
        return serverConnect;
    }

    public void invokeGetGeoLocation(String query, Callback<Object> callback) {
        apiServer.getGeoId(query).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    String geoId = null;
                    String title = null;
                    String secondaryText = null;

                    int index =-1;
                    boolean perfectMatch;
                    try {
                        List<LocationData> geoIdList = parseGeoList(response.body());
                        String tempTitle = "";
                        for (int i = 0; i < geoIdList.size(); i++) {
                            title = filterTitle(geoIdList.get(i).getTitle());
                            if (title.toLowerCase().matches(query.toLowerCase())) { // perfect Match
                                perfectMatch = true;
                                index = i;
                                break;
                            } else if (title.toLowerCase().contains(query.toLowerCase())) { //contained inside
                                if (title.toLowerCase().length() > tempTitle.length()) {// checked the most suitable
                                    tempTitle = title;
                                    perfectMatch = false;
                                    index = i;
                                }
                            }
                        }
                        LocationData location = new LocationData();
                        if(index != -1) {
                            geoId = filterGeoId(geoIdList.get(index).getGeoId());
                            title = filterTitle(geoIdList.get(index).getTitle());
                            secondaryText = geoIdList.get(index).getSecondaryText();
                            location.setTitle(title);
                            location.setGeoId(geoId);
                            location.setSecondaryText(secondaryText);
                            callback.onResponse(call, Response.success(location));}
                        else
                            callback.onFailure(call, new Throwable("Failed to get geoId"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    callback.onFailure(call, new Throwable("Failed to get geoId"));
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void invokeGetHotelsByCheapestPrice(String geoId, String checkIn, String checkOut, String sort, int adults, int rooms, Callback<Object> callback) {
        apiServer.getHotelList(geoId,checkIn,checkOut,sort,adults,rooms).enqueue(new Callback<Object>(){
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    List<HotelData> hotelDataList = parseHotelDataList(response.body());

                    // Create a list of Hotel objects from the HotelData list
                    List<Hotel> hotels = new ArrayList<>();
                    for (int i = 1; i < hotelDataList.size(); i++) {

                        HotelData hotelData = hotelDataList.get(i);
                        Hotel hotel = new Hotel();

                        hotel.setId(hotelData.getId());
                        hotel.setTitle(reFormatTitle(hotelData.getTitle()));
                        if (!hotelData.getCardPhotos().isEmpty()) {
                            hotel.setImage(hotelData.getCardPhotos().get(0).getSizes().getUrlTemplate());
                        } else {
                            // Handle the case when cardPhotos is empty
                            hotel.setImage(""); // Set a default image URL or handle it based on your requirements
                        }
                        hotel.setNumOfAdults(adults);  // update this depending on your JSON structure

                        if (hotelData.getPriceForDisplay() != null) {
                            if (hotelData.getPriceForDisplay().length() > 1) {
                                String priceForDisplay = hotelData.getPriceForDisplay().substring(1).replace(",","");
                                hotel.setPrice(Integer.parseInt(priceForDisplay));

                            } else {
                                hotel.setPrice(0);
                            }
                        } else {
                            hotel.setPrice(0);
                        }

                        String review = hotelData.getBubbleRating().getCount();
                        int reviewNum = 0; // Default value if the rating is null
                        if (review != null) {
                            review = review.trim().replace(",","");;
                            try {
                                reviewNum = Integer.parseInt(review);
                            } catch (NumberFormatException e) {
                                Log.e("myLog", "Failed to parse review: " + review);
                                e.printStackTrace();
                            }
                        }
                        hotel.setNumOfReviews(reviewNum);

                        String ratingString = hotelData.getBubbleRating().getRating();
                        double rating = 0.0; // Default value if the rating is null
                        if (ratingString != null) {
                            ratingString = ratingString.trim();
                            try {
                                rating = Double.parseDouble(ratingString);
                            } catch (NumberFormatException e) {
                                Log.e("myLog", "Failed to parse rating: " + ratingString);
                                e.printStackTrace();
                            }
                        }
                        hotel.setRating(rating);
                        hotels.add(hotel);
                    }


                    // Invoke the callback's onResponse method, passing the list of hotels
                    callback.onResponse(call, Response.success(hotels));
                } else {
                    callback.onFailure(call, new Throwable("Failed to get hotels"));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public List<LocationData> parseGeoList(Object responseObj) {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseObj);

        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        Type listType = new TypeToken<List<LocationData>>() {}.getType();
        return gson.fromJson(dataArray, listType);
    }

    public JsonObject parseDataList(Object responseObj) {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseObj);

        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");

        return dataObject;
    }

    public List<HotelData> parseHotelDataList(Object responseObj) {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseObj);

        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        JsonArray dataArray = dataObject.getAsJsonArray("data");

        Type listType = new TypeToken<List<HotelData>>() {}.getType();
        return gson.fromJson(dataArray, listType);
    }

    private String filterTitle(String location) { //location is the string with the <b>##<b>
        if(location!=null) {
            return location.replace("<b>", "").replace("</b>", "");
        }
        return null;
    }

    private String filterGeoId(String geoId){
        if(geoId != null){
            int firstSemicolonIndex = geoId.indexOf(';');
            int secondSemicolonIndex = geoId.indexOf(';', firstSemicolonIndex + 1);

            if (firstSemicolonIndex != -1 && secondSemicolonIndex != -1) {
                return geoId.substring(firstSemicolonIndex + 1, secondSemicolonIndex);
            }
            if(firstSemicolonIndex != -1 && secondSemicolonIndex == -1 ){
                return geoId.substring(firstSemicolonIndex + 1);
            }
        }
        return null;
    }

    private String reFormatTitle(String title) {
        int dotIndex = title.indexOf('.');
        String newTitle;

        if (dotIndex != -1) {
            newTitle = title.substring(dotIndex + 2);
        } else {
            newTitle = title;
        }
        return newTitle;
    }

}
