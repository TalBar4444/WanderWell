package com.example.hotelsapp.activities.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelsapp.R;
import com.example.hotelsapp.activities.Imager;
import com.example.hotelsapp.model.Hotel;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_Hotel extends RecyclerView.Adapter<Adapter_Hotel.HotelViewHolder> {

    private final String urlNoImage = "https://storage.googleapis.com/proudcity/mebanenc/uploads/2021/03/placeholder-image.png";
    private List<Hotel> hotels;
    private String className;

    private OnHotelClickListener onHotelClickListener;

    public Adapter_Hotel(List<Hotel> hotels, String className) {
        this.hotels = hotels;
        this.className= className;
    }

    public void updateList(List<Hotel> hotels) {
        this.hotels = hotels;
        notifyDataSetChanged();
    }

    public void setOnHotelClickListener(OnHotelClickListener onHotelClickListener) {
        this.onHotelClickListener = onHotelClickListener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hotel_square, viewGroup, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = getItem(position);

        holder.hotel_LBL_title.setText(hotel.getTitle());
        holder.hotel_LBL_adults.setText("For " + hotel.getNumOfAdults() + " adults !");
        holder.hotel_LBL_price.setText(addThousandsSeparator(hotel.getPrice())+"$");
        holder.hotel_LBL_reviews.setText("" + addThousandsSeparator(hotel.getNumOfReviews()) + " reviews");

        holder.hotel_RTG_rating.setRating((float) hotel.getRating());

        String imageUrl = hotel.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Imager.me().imageCrop(holder.hotel_IMG_main, imageUrl, className);
        }
        else
            Imager.me().imageCrop(holder.hotel_IMG_main, urlNoImage, className);


        if (hotel.isLiked()) {
            holder.hotel_IMG_like.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.hotel_IMG_like.setImageResource(R.drawable.ic_heart_linear);
        }
    }

    @Override
    public int getItemCount() {
        return hotels == null ? 0 : hotels.size();
    }

    private Hotel getItem(int position) {
        return hotels.get(position);
    }

    public interface OnHotelClickListener {
        void onClick(View view, Hotel hotel, int position);

        void onLikeClicked(View view, Hotel hotel, int position);
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView hotel_IMG_main;
        private AppCompatImageView hotel_IMG_like;
        private MaterialTextView hotel_LBL_title;
        private MaterialTextView hotel_LBL_adults;
        private MaterialTextView hotel_LBL_price;
        private MaterialTextView hotel_LBL_reviews;
        private AppCompatRatingBar hotel_RTG_rating;

        HotelViewHolder(View itemView) {
            super(itemView);
            hotel_IMG_main = itemView.findViewById(R.id.hotel_IMG_main);
            hotel_IMG_like = itemView.findViewById(R.id.hotel_IMG_like);
            hotel_LBL_title = itemView.findViewById(R.id.hotel_LBL_title);
            hotel_LBL_adults = itemView.findViewById(R.id.hotel_LBL_adults);
            hotel_LBL_price = itemView.findViewById(R.id.hotel_LBL_price);
            hotel_LBL_reviews = itemView.findViewById(R.id.hotel_LBL_reviews);
            hotel_RTG_rating = itemView.findViewById(R.id.hotel_RTG_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHotelClickListener.onClick(v, getItem(getAdapterPosition()), getAdapterPosition());
                }
            });

            hotel_IMG_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHotelClickListener.onLikeClicked(v, getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
    private String addThousandsSeparator(int number) { // and a comma (',') for numbers bigger than 999
        DecimalFormat dForm = new DecimalFormat("#,###");
        return dForm.format(number);
    }
}

