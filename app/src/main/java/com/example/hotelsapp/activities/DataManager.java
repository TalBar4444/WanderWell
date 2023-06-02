package com.example.hotelsapp.activities;

import com.example.hotelsapp.logic.Hotel;

import java.util.ArrayList;

public class DataManager {

    public static ArrayList<Hotel> mockHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();

        hotels.add(new Hotel()
                .setTitle("Tall")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/24/38/24381382.jpeg")
                .setNumOfAdults(2)
                .setPrice(175)
                .setRating(4.8)
                .setNumOfReviews(1126)
        );

        hotels.add(new Hotel()
                .setTitle("Warwick New York")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/88/45/8845_v11.jpeg")
                .setNumOfAdults(2)
                .setPrice(199)
                .setRating(4.1)
                .setNumOfReviews(2754)
        );

        hotels.add(new Hotel()
                .setTitle("Park Hyatt Bangkok")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/66/23/6623740_v2.jpeg")
                .setNumOfAdults(3)
                .setPrice(377)
                .setRating(4.6)
                .setNumOfReviews(540)
        );

        hotels.add(new Hotel()
                .setTitle("Centara Grand at Central Plaza Ladprao Bangkok")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/13/87/13877_v7.jpeg")
                .setNumOfAdults(3)
                .setPrice(106)
                .setRating(4)
                .setNumOfReviews(1868)
        );

        hotels.add(new Hotel()
                .setTitle("Novotel Belleville Paris 20")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/25/47/25479948.jpeg")
                .setNumOfAdults(2)
                .setPrice(191)
                .setRating(4.3)
                .setNumOfReviews(43)
        );

        hotels.add(new Hotel()
                .setTitle("Hotel 31 - Paris Tour Eiffel")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/26/55/26556296.jpeg")
                .setNumOfAdults(2)
                .setPrice(194)
                .setRating(4.3)
                .setNumOfReviews(59)
        );

        hotels.add(new Hotel()
                .setTitle("Tribe London Canary Wharf")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/30/44/30441926.jpeg")
                .setNumOfAdults(2)
                .setPrice(166)
                .setRating(4.2)
                .setNumOfReviews(12)
        );

        hotels.add(new Hotel()
                .setTitle("The Tower Hotel")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/11/44/11442502.jpeg")
                .setNumOfAdults(2)
                .setPrice(155)
                .setRating(4.2)
                .setNumOfReviews(242)
        );

        hotels.add(new Hotel()
                .setTitle("Grand Oasis Cancún")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/34/41/34410_v7.jpeg")
                .setNumOfAdults(4)
                .setPrice(439)
                .setRating(3.5)
                .setNumOfReviews(17196)
        );

        hotels.add(new Hotel()
                .setTitle("The Westin Lagunamar Ocean Resort Villas & Spa - Cancun")
                .setImage("https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/10/19/10197122_v5.jpeg")
                .setNumOfAdults(4)
                .setPrice(280)
                .setRating(4.8)
                .setNumOfReviews(4287)
        );


        return hotels;
    }
}
