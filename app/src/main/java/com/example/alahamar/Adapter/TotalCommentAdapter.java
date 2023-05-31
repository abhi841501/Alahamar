package com.example.alahamar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alahamar.R;
import com.example.alahamar.apimodel.TotalComments;

import java.util.List;

public class TotalCommentAdapter extends RecyclerView.Adapter<TotalCommentAdapter.ViewHolder> {

    Context context;
    List<TotalComments> totalCommentsList;
    String id;

    public TotalCommentAdapter(Context context, List<TotalComments> totalCommentsList) {
        this.context = context;
        this.totalCommentsList = totalCommentsList;
    }



    @Override
    public TotalCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.totalcomments_list,viewGroup, Boolean.parseBoolean("false"));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalCommentAdapter.ViewHolder holder, int position) {












    }

    @Override
    public int getItemCount() {
        return totalCommentsList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
