package com.example.alahamar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahamar.R;
import com.example.alahamar.apimodel.NotificationModelData;
import com.example.alahamar.apimodel.TotalNotificationModel;
import com.example.alahamar.sideMenu1.SideMenuActivity;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<NotificationModelData> notificationModelDataList;


    public NotificationAdapter(Context context, List<NotificationModelData> notificationModelDataList) {
        this.context = context;
        this.notificationModelDataList = notificationModelDataList;
    }



    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_notification,viewGroup, Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
   holder.title.setText(notificationModelDataList.get(position).getNotificationHeadding());
   holder.message.setText(notificationModelDataList.get(position).getNotificationMessage());
   holder.date1.setText(notificationModelDataList.get(position).getRecievedDate());

   holder.delete.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

       }
   });

     

    }

    

    @Override
    public int getItemCount() {
        return notificationModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton clearButton;

        TextView title,message,date1;
        ImageView delete;


        public ViewHolder(@NonNull View itemView) {
            
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date1 = itemView.findViewById(R.id.date1);
            delete = itemView.findViewById(R.id.delete);


            

        }
    }
}
