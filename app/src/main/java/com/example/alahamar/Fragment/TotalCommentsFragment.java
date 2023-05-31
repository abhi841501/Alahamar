package com.example.alahamar.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alahamar.Adapter.TotalCommentAdapter;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.TotalComments;
import com.example.alahamar.apimodel.TotalCommentsModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class TotalCommentsFragment extends Fragment {
    RecyclerView recyclerTotalComments;
    TotalCommentAdapter totalCommentAdapter;
    List<TotalComments>totalCommentsList = new ArrayList<>();
    String temp,accessToken,id;
    String community_post;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


    View view  = inflater.inflate(R.layout.fragment_total_comments, container, false);


    recyclerTotalComments = view.findViewById(R.id.recyclerTotalComments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerTotalComments.setLayoutManager(layoutManager);



        total_Comments_api();

        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        id = sharedPreferences.getString("id", "");
        accessToken = StaticKey.tokenPrefex+temp;
        Log.d("test_sam_access",accessToken+"ok");


        return view;
    }



    private void total_Comments_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<TotalCommentsModel> call = Api_Client.getClient().TOTAL_COMMENTS_MODEL_CALL(accessToken,
                id);

        call.enqueue(new Callback<TotalCommentsModel>() {
            @Override
            public void onResponse(retrofit2.Call<TotalCommentsModel> call, retrofit2.Response<TotalCommentsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        TotalCommentsModel totalCommentsModel = response.body();
                        String success = totalCommentsModel.getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            totalCommentsList = response.body().getData();
                            totalCommentAdapter = new TotalCommentAdapter(getActivity(),totalCommentsList);
                            recyclerTotalComments.setAdapter(totalCommentAdapter);



                            Toast.makeText(getActivity(),"TeamsList shows successfully",Toast.LENGTH_SHORT).show();


                        } else {
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e("user_id", "    Message");
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                            Log.e("user_id", "    Exception");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("user_id", "    Exception  "+e.getMessage()+"  "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<TotalCommentsModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
}