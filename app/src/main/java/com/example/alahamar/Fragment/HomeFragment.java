package com.example.alahamar.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alahamar.Adapter.NonNull;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.AboutTeamsModel;
import com.example.alahamar.apimodel.EditProfileModel;
import com.example.alahamar.apimodel.EditprofileData;
import com.example.alahamar.retrofit.Api_Client;
import com.example.alahamar.sideMenu1.SideMenuActivity;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    TextView userName_txt,email_txt;
    CircleImageView homeProfile;
    FrameLayout frameLayout1;
    TextView event,relativeEmail;
    EditText password,userName;
    String UserID;
    File profile_fileImage;
    String temp, accessToken;
    String struserName_txt,stremail_txt;
   /* private Object EditProfileResponse;*/
    private EditprofileData EditProfileResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout relativeEvent, relativeCommunity, relativeForms, relativeDonate, relativeAbout, relativeTeam;


        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_home, container, false);

        relativeEvent =view.findViewById(R.id.relativeEvent);
        relativeCommunity =  view.findViewById(R.id.relativeCommunity);
        relativeForms = view.findViewById(R.id.relativeForms);
        relativeDonate =  view.findViewById(R.id.relativeDonate);
        relativeAbout =  view.findViewById(R.id.relativeAbout);
        relativeTeam = view. findViewById(R.id.relativeTeam);

        userName_txt= view.findViewById(R.id.userName_txt);
        email_txt = view.findViewById(R.id.email_txt);
        homeProfile = view.findViewById(R.id.homeProfile);








        /*  getFragmentManager().beginTransaction().replace(R.id.frameLayout, new EventFragment ()).commit();*/


        relativeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EventFragment fragment = new EventFragment();
                FragmentManager fragmentManager = getFragmentManager();
                /*((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("Event");

            }
        });

        relativeCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityFragment fragment1 = new CommunityFragment();
                FragmentManager fragmentManager = getFragmentManager();
             /*   ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment1);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("Community");



            }
        });
        relativeForms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormsFragment fragment2 = new FormsFragment();
                FragmentManager fragmentManager = getFragmentManager();
             /*   ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment2);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("Forms");


            }
        });

        relativeDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonateFragment fragment3 = new DonateFragment();
                FragmentManager fragmentManager = getFragmentManager();
         /*       ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment3);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("Donate");


            }
        });

        relativeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AboutTeamFragment fragment4 = new AboutTeamFragment();
                FragmentManager fragmentManager = getFragmentManager();
              /*  ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment4);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("About Us");

            }

        });

        relativeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsFragment fragment5 = new   AboutUsFragment();
                FragmentManager fragmentManager = getFragmentManager();
               /* ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment5);
                fragmentTransaction.commit();
                SideMenuActivity.title.setText("About Team");



            }
        });




        editProfileApi();
        struserName_txt = userName_txt.getText().toString();
        stremail_txt = email_txt.getText().toString();

        SharedPreferences sharedPreferences;
        sharedPreferences =getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        UserID = sharedPreferences.getString("UserID", "");
        struserName_txt = sharedPreferences.getString("userName", "");
        stremail_txt = sharedPreferences.getString("Email", "");
        accessToken = StaticKey.tokenPrefex+temp;
        Log.d("test_sam_access",accessToken+"ok");
        userHomeApi();
        return view;


    }



    private void userHomeApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<com.example.alahamar.apimodel.EditProfileModel> call = Api_Client.getClient().EDIT_PROFILE_MODEL_CALL(accessToken);

        call.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMsg();
                        String success = (response.body().getSuccess());



                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(getActivity(),"User Profile updated Successfully",Toast.LENGTH_LONG).show();



                            EditProfileModel EditProfileModel = response.body();
                            EditProfileResponse = EditProfileModel.getData();




                            struserName_txt = EditProfileResponse.getUsername();
                            stremail_txt = EditProfileResponse.getEmail();
                            userName_txt.setText(struserName_txt);
                            email_txt.setText(stremail_txt);



                            // stringProfile = userProfileResponse.getImageUrl();


                            Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGE2 + EditProfileResponse.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(homeProfile);


                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
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
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
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
    private void editProfileApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<com.example.alahamar.apimodel.EditProfileModel> call = Api_Client.getClient().EDIT_PROFILE_MODEL_CALL(accessToken);

        call.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMsg();
                        String success = (response.body().getSuccess());



                        if (success.equals("true") || success.equals("True")) {
                          /*  Toast.makeText(getActivity(),"User Profile updated Successfully",Toast.LENGTH_LONG).show();
*/


                            EditProfileModel EditProfileModel = response.body();
                            EditProfileResponse = EditProfileModel.getData();



                            struserName_txt = EditProfileResponse.getUsername();
                            stremail_txt = EditProfileResponse.getEmail();
                            // stringProfile = userProfileResponse.getImageUrl();

                            userName_txt.setText(struserName_txt);
                            email_txt.setText(stremail_txt);
                           /* Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGE2 + EditProfileResponse.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(homeProfile);*/


                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
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
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
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


    private class EditProfileResponse {
    }
}