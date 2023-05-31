package com.example.alahamar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahamar.AlahamarActivity.EventDetailsActivity;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.EventData;
import com.example.alahamar.apimodel.EventGallery;
import com.example.alahamar.retrofit.Api_Client;

import java.util.ArrayList;
import java.util.List;

public class  EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    Context context;
 List<EventData>eventDataList;
 List<EventGallery> eventGalleryList = new ArrayList<>();


String id;
String date;
String description;
String gallery;


    public EventAdapter(Context context, List<EventData> eventDataList) {
        this.context = context;
        this.eventDataList = eventDataList;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup  viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_recycler_adapter,viewGroup, Boolean.parseBoolean("false"));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
      //  holder.image.setImageResource(eventModelList.get(position).getImage())

      Glide.with(context).load(Api_Client.BASE_URL_IMAGE +eventDataList.get(position).getImage()).into(holder.image);
        holder.date.setText(eventDataList.get(position).getDate());
        holder.headding.setText(eventDataList.get(position).getHeadding());
        holder.description.setText(eventDataList.get(position).getDescription());

        id = String.valueOf(eventDataList.get(position).getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = String.valueOf(eventDataList.get(position).getId());

                eventGalleryList =  eventDataList.get(position).getGallery();

                StaticKey.eventGalleryListStatic.clear();

                for(int i=0; i<eventGalleryList.size();i++){
                    StaticKey.eventGalleryListStatic.add(eventGalleryList.get(i).getImage());

                }
                    Intent intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra("date",eventDataList.get(position).getDate());
                    intent.putExtra("heading",eventDataList.get(position).getHeadding());
                    intent.putExtra("description",eventDataList.get(position).getDescription());
                    context.startActivity(intent);


            }
        });

    }





    @Override
    public int getItemCount() {
        return eventDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView date,headding,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date);
            headding = itemView.findViewById(R.id.headding);
            description = itemView.findViewById(R.id.description);

        }
    }
}
