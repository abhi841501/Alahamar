package com.example.alahamar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alahamar.R;
import com.example.alahamar.apimodel.DataItem;

import java.util.List;

public class FormsListAdapter extends RecyclerView.Adapter<FormsListAdapter.ViewHolder> {
    Context context;
    List<DataItem> dataItemList;
    String name;

    public FormsListAdapter(Context context, List<DataItem> dataItemList) {
        this.context = context;
        this.dataItemList = dataItemList;
    }

    @Override
    public FormsListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_adapter_formlist,viewGroup, Boolean.parseBoolean("false"));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormsListAdapter.ViewHolder holder, int position) {
        holder.nameForm.setText(dataItemList.get(position).getName());
        name = String.valueOf(dataItemList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = String.valueOf(dataItemList.get(position).getName());

            }
        });





    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView nameForm;
        public ViewHolder(View itemView) {
            super(itemView);
            nameForm = itemView.findViewById(R.id.nameForm);

        }
    }

}
