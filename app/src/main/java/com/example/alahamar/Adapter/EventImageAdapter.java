package com.example.alahamar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahamar.R;
import com.example.alahamar.apimodel.EventGallery;
import com.example.alahamar.retrofit.Api_Client;

import java.util.List;

public class EventImageAdapter extends RecyclerView.Adapter<EventImageAdapter.ViewHolder> {

    Context context;
    List<String> eventGalleryList;

    public EventImageAdapter(Context context, List<String> eventGalleryList) {
        this.context = context;
        this.eventGalleryList = eventGalleryList;
    }

    @Override
    public EventImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallary_recycler,viewGroup,  Boolean.parseBoolean("false"));

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(EventImageAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(Api_Client.BASE_URL_IMAGE3+ eventGalleryList.get(position)).into(holder.imgGallery);

    }


    @Override
    public int getItemCount() {
        return eventGalleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgGallery;

        public ViewHolder(View itemView) {
            super(itemView);

            imgGallery = itemView.findViewById(R.id.imgGallery);





        }
    }









        }

