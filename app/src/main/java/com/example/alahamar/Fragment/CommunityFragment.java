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

import com.example.alahamar.Adapter.CommunityAdapter;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.CommunityData;
import com.example.alahamar.apimodel.CommunityDetailsModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment implements RefreshInterface{
    RecyclerView recyclerCommunity;
    CommunityAdapter communityAdapter;
    List<CommunityData> communityDataList = new ArrayList<>();
    String temp, accessToken;
    RefreshInterface refreshInterface;


    @Override
    public void onStart() {
        super.onStart();
        refreshInterface.onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_community, container, false);


        try {
            refreshInterface = this;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        recyclerCommunity = view.findViewById(R.id.recyclerCommunity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerCommunity.setLayoutManager(layoutManager);

       // addData();
        SharedPreferences sharedPreferences;
        sharedPreferences =getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        accessToken = StaticKey.tokenPrefex+temp;
            Log.d("test_sam_access",accessToken+"ok");
       // Toast.makeText(getActivity(), accessToken+"ok", Toast.LENGTH_SHORT).show();
      //  auth =  StaticKey.tokenPrefex + temp;

      //  communityList();

        return view;
    }


    private void communityList() {

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Call<com.example.alahamar.apimodel.CommunityDetailsModel> call = Api_Client.getClient().COMMUNITY_DETAILS_MODEL_CALL(accessToken);

        call.enqueue(new Callback<CommunityDetailsModel>() {
            @Override
            public void onResponse(Call<CommunityDetailsModel> call, Response<CommunityDetailsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());


                        if (success.equals("true") || success.equals("True")) {

                            for (int i=0; i<communityDataList.size(); i++)
                            {

                            }

                            communityDataList  = response.body().getData();
                            String listSize = String.valueOf(communityDataList.size());
                            communityAdapter = new CommunityAdapter(getActivity(), communityDataList,refreshInterface);
                            recyclerCommunity.setAdapter(communityAdapter);


                            Log.e("listSize", "list......" + listSize);

                         /*   Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/
                            // Calling another activity

                        } else {
                          /*  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/

                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
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
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommunityDetailsModel> call, Throwable t) {
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


    @Override
    public void onRefresh() {
        communityList();
        Toast.makeText(getActivity(), "refresh calling...@@@@@@", Toast.LENGTH_SHORT).show();
    }
}