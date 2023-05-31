package com.example.alahamar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahamar.R;
import com.example.alahamar.apimodel.Datum;
import com.example.alahamar.retrofit.Api_Client;

import java.util.List;

public class AboutTeamsAdapter extends RecyclerView.Adapter<AboutTeamsAdapter.ViewHolder> {
    Context context;
    List<Datum>datumList;

    public AboutTeamsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @Override
    public AboutTeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.about_teams_recycler,viewGroup, Boolean.parseBoolean("false"));

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(AboutTeamsAdapter.ViewHolder holder, int position) {
        holder.EmployeeName.setText(datumList.get(position).getName());
        holder.designation.setText(datumList.get(position).getDesignation());
        Glide.with(context).load(Api_Client.BASE_URL_IMAGE3 +datumList.get(position).getImage()).into(holder.blankImg);



    }

    @Override
    public int getItemCount() {
        return datumList.size();
}

public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView blankImg;
        TextView EmployeeName,designation;
    public ViewHolder(View itemView) {
        super(itemView);

        blankImg = itemView.findViewById(R.id.blankImg);
        EmployeeName = itemView.findViewById(R.id.EmployeeName);
        designation = itemView.findViewById(R.id.designation);
    }
}

}
