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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.alahamar.Adapter.AboutTeamsAdapter;
import com.example.alahamar.Adapter.FormsListAdapter;
import com.example.alahamar.R;
import com.example.alahamar.apimodel.AboutTeamsModel;
import com.example.alahamar.apimodel.DataItem;
import com.example.alahamar.apimodel.FormsListModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FormsFragment extends Fragment {
    RecyclerView recyclerForms;
    FormsListAdapter formsListAdapter;
    List<DataItem> dataItemList = new ArrayList<>();
    WebView webView1;
    String name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forms, container, false);
        recyclerForms = view.findViewById(R.id.recyclerForms);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerForms.setLayoutManager(layoutManager);

        formsListAdapter = new FormsListAdapter(getActivity(), dataItemList);
        recyclerForms.setAdapter(formsListAdapter);


        FormsList_api();

        webView1 = view.findViewById(R.id.webView1);

        ProgressDialog pd = ProgressDialog.show(getActivity(),"message", "Please wait",true);
        pd.setCancelable(false);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                pd.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }
        });

        webView1.loadUrl(" http://69.49.235.253:8004/api/forms-lists");

        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        name =sharedPreferences.getString("id","");
        Log.d("test_sam_access",name+"ok");



        return view;
    }

    private void FormsList_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<FormsListModel> call = Api_Client.getClient().FORMS_LIST_MODEL_CALL();

        call.enqueue(new Callback<FormsListModel>() {
            @Override
            public void onResponse(retrofit2.Call<FormsListModel> call, retrofit2.Response<FormsListModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        FormsListModel formsListModel = response.body();
                        String success = formsListModel.getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            dataItemList = response.body().getData();
                            String listSize = String.valueOf(dataItemList.size());
                            formsListAdapter = new FormsListAdapter(getActivity(),dataItemList);
                            recyclerForms.setAdapter(formsListAdapter);



                            Toast.makeText(getActivity(),"FormsList shows successfully",Toast.LENGTH_SHORT).show();


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
            public void onFailure(Call<FormsListModel> call, Throwable t) {
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