package com.example.hotelsapp.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServer {
    @GET("searchLocation?")
        Call<Object> getGeoId(@Query("query") String location);
    @GET("searchHotels?")
    Call<Object> getHotelList(@Query("geoId") String geoId, @Query("checkIn") String checkIn, @Query("checkOut") String checkOut,
                              @Query("sort") String sort, @Query("adults") int adults, @Query("rooms") int rooms);
}
