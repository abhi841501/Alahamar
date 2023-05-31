package com.example.alahamar.Adapter;

import static com.example.alahamar.R.layout.community_recycler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahamar.AlahamarActivity.CommentsActivity;
import com.example.alahamar.Fragment.RefreshInterface;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.CommunityData;
import com.example.alahamar.apimodel.CommunityLIkeModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {
    Context context;
    List<CommunityData> communityDataList;
    String id;
    String accessToken,user,community,temp;
    Boolean liked;
    RefreshInterface refreshInterface;

    public CommunityAdapter(Context context, List<CommunityData> communityDataList, RefreshInterface refreshInterface) {
        this.context = context;
        this.communityDataList = communityDataList;
        this.refreshInterface = refreshInterface;
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(community_recycler, viewGroup, Boolean.parseBoolean("false"));
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.headdings.setText(communityDataList.get(position).getHeaddings());
        holder.topic_description.setText(communityDataList.get(position).getTopicDescription());
        holder.date1.setText(communityDataList.get(position).getDate1());
        holder.likes.setText(String.valueOf(communityDataList.get(position).getLikes()));
       /* Glide.with(context).load(communityDataList.get(position).getIsLiked()).into(holder.likeimg);
        Glide.with(context).load(communityDataList.get(position).getLikes()).into(holder.likesimg);*/
        Glide.with(context).load(Api_Client.BASE_URL_IMAGE1 +communityDataList.get(position).getImage2()).into(holder.image2);
        holder.comments.setText(String.valueOf(communityDataList.get(position).getComment()));





        id = String.valueOf(communityDataList.get(position).getId());

        holder.commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = String.valueOf(communityDataList.get(position).getId());
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);

            }
        });

      /*  community = String.valueOf(communityDataList.get(position).getId());*/

        holder.likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                community = String.valueOf(communityDataList.get(position).getId());

                Integer totalLike = communityDataList.get(position).getLikes();
                String likeStatus = String.valueOf(communityDataList.get(position).getIsLiked());
                String finalLikeStatus;
                if(likeStatus.equals("1")){
                    finalLikeStatus="False";
                }else if(likeStatus.equals("0")){
                    finalLikeStatus="True";
                }else{
                    Toast.makeText(context, "Something went wrong.............", Toast.LENGTH_SHORT).show();
                    finalLikeStatus=null;
                }



                likeApi(finalLikeStatus,holder,totalLike);

            }
        });

        SharedPreferences sharedPreferences;
        sharedPreferences =context.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        user = sharedPreferences.getString("userId", "");
       /* community = sharedPreferences.getString("id", "");*/
        accessToken = StaticKey.tokenPrefex+temp;
        Log.d("test_sam_access",accessToken+"ok");

    }



    private void likeApi(String finalLikeStatus, ViewHolder holder, Integer totalLikes) {

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Call<com.example.alahamar.apimodel.CommunityLIkeModel> call = Api_Client.getClient().COMMUNITY_LIKE_MODEL_CALL(accessToken,
                finalLikeStatus,
                community,
                user);

        call.enqueue(new Callback<CommunityLIkeModel>() {
            @Override
            public void onResponse(Call<CommunityLIkeModel> call, Response<CommunityLIkeModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());
                        String msg = response.body().getMsg();


                        if (success.equals("true") || success.equals("True")) {

                            String listSize = String.valueOf(communityDataList.size());
                            refreshInterface.onRefresh();



                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                        } else {
                            /*  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/

                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommunityLIkeModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return communityDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headdings,topic_description, date1,commentbtn,comments,is_likedbtn,likes,txtLikes;
        ImageView image2,likesimg,likeimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headdings = itemView.findViewById(R.id.headdings);
            topic_description = itemView.findViewById(R.id.topic_description);
            date1 = itemView.findViewById(R.id.date1);
            likes = itemView.findViewById(R.id.likes);
            is_likedbtn = itemView.findViewById(R.id.is_likedbtn);
            commentbtn = itemView.findViewById(R.id.commentbtn);
            image2 = itemView.findViewById(R.id.image2);
            comments = itemView.findViewById(R.id.comments);
            likesimg = itemView.findViewById(R.id.likesimg);
            likeimg = itemView.findViewById(R.id.likeimg);
            txtLikes = itemView.findViewById(R.id.txtLikes);
        }
    }
}
