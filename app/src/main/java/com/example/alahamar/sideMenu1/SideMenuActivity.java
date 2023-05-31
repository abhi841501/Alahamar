package com.example.alahamar.sideMenu1;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alahamar.Adapter.NotificationAdapter;
import com.example.alahamar.AlahamarActivity.LoginActivity;
import com.example.alahamar.Fragment.AboutUsFragment;
import com.example.alahamar.Fragment.ChangePasswordFragment;
import com.example.alahamar.Fragment.CommunityFragment;
import com.example.alahamar.Fragment.ContactUsFragment;
import com.example.alahamar.Fragment.DonateFragment;
import com.example.alahamar.Fragment.EventFragment;
import com.example.alahamar.Fragment.HomeFragment;
import com.example.alahamar.Fragment.NotificationFragment;
import com.example.alahamar.Fragment.EditProfileFragment;
import com.example.alahamar.Fragment.TransactionHistoryFragment;
import com.example.alahamar.R;
import com.example.alahamar.Response.LoginModel;
import com.example.alahamar.Response.Token;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.EditProfileModel;
import com.example.alahamar.apimodel.EditprofileData;
import com.example.alahamar.apimodel.NotificationModel;
import com.example.alahamar.apimodel.TotalNotificationModel;
import com.example.alahamar.retrofit.Api_Client;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SideMenuActivity extends SlidingFragmentActivity {
    public static TextView title;
    ImageView menu;
    RelativeLayout homeRelative, communityRelative, changeRelative, donateRelative, aboutRelative, transactionRelative, eventRelative,contactRelative,
    logoutRelative,relativeEmail,profile1;
    TextView editProfile,nameUser,textnotification;
    String temp;
    String refreshToken,accessToken;
    String side;
    String authorization,id;
    String UserID;
    ImageView profileImage;
    TextView mailUser,Name;
    String strEmail,strName;
    String strprofileImage;

String userProfile1,userName,Email;


    ImageView notification,notificationCount;
    public static int navItemIndex = 0;
   // public static String CURRENT_TAG = TAG_DASHBOARD;
    private static final String TAG_DASHBOARD = "services";
    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView notificationModelDataList;
    private EditprofileData EditProfileResponse;
    private Object AppState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        setBehindContentView(R.layout.home_side_navigation);




       notification = (ImageView) findViewById(R.id.notification);
        notificationCount = (ImageView) findViewById(R.id.notificationCount);
        textnotification = (TextView) findViewById(R.id.textnotification);
       title = (TextView) findViewById(R.id.title);
        editProfile =(TextView) findViewById(R.id.editProfile);

        menu = (ImageView) findViewById(R.id.menu);
        homeRelative = (RelativeLayout) findViewById(R.id.homeRelative);
        communityRelative = (RelativeLayout)findViewById(R.id. communityRelative);
        donateRelative = (RelativeLayout)findViewById(R.id.donateRelative);
        aboutRelative = (RelativeLayout)findViewById(R.id.aboutRelative);
        eventRelative = (RelativeLayout)findViewById(R.id.eventRelative);
        changeRelative = (RelativeLayout)findViewById(R.id.changeRelative);
        transactionRelative = (RelativeLayout)findViewById(R.id.transactionRelative);
        contactRelative = (RelativeLayout)findViewById(R.id.contactRelative);
        logoutRelative = (RelativeLayout)findViewById(R.id.logoutRelative);
        relativeEmail = (RelativeLayout)findViewById(R.id.relativeEmail);
        profile1 = (RelativeLayout) findViewById(R.id.profile1);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        mailUser  = (TextView) findViewById(R.id.mailUser);
        Name  = (TextView) findViewById(R.id.Name);



        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        try {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            final Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            final int slidingmenuWidth = (int) (screenWidth - (screenWidth / 3.7) + 23);
            final int offset = Math.max(0, screenWidth - slidingmenuWidth);
            getSlidingMenu().setBehindOffset(offset);
            getSlidingMenu().toggle();
            getSlidingMenu().isVerticalFadingEdgeEnabled();
            getSlidingMenu().isHorizontalFadingEdgeEnabled();
            getSlidingMenu().setMode(SlidingMenu.LEFT);
            getSlidingMenu().setFadeEnabled(true);
            getSlidingMenu().setFadeDegree(0.8f);
            getSlidingMenu().setFadingEdgeLength(13);
            getSlidingMenu().setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("slidingexception", e.toString());
        }

        menu .setOnClickListener(v -> {
            try {
                showMenu();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                NotificationFragment  frag = new NotificationFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,frag);
                fragmentTransaction.commit();
                title.setText("Notification");


            }
        });

        notificationCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        side = getIntent().getStringExtra("userId");
        side = getIntent().getStringExtra("password");
        side = getIntent().getStringExtra("userName");



        UserID = getIntent().getStringExtra("UserID");





        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditProfileFragment fragment = new EditProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                title.setText("Profile");
                getSlidingMenu().toggle();


            }

        });






        homeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment fragment1 = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment1);
                fragmentTransaction.commit();
                title.setText("Home");
                getSlidingMenu().toggle();

                SharedPreferences sharedPreferences;
                sharedPreferences = SideMenuActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                temp = sharedPreferences.getString("accessToken", "");
                refreshToken = sharedPreferences.getString("refreshToken", "");
                UserID =sharedPreferences.getString("UserID"," UserID");
                accessToken = StaticKey.tokenPrefex+temp;
                Log.d("test_sam_access",accessToken+"ok");




            }
        });


        eventRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EventFragment fragment2 = new EventFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment2);
                fragmentTransaction.commit();
                title.setText("Event");
                getSlidingMenu().toggle();



            }
        });


       communityRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityFragment fragment3 = new CommunityFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment3);
                fragmentTransaction.commit();
                title.setText("Community");
                getSlidingMenu().toggle();

            }
        });


        changeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ChangePasswordFragment fragment4 = new ChangePasswordFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment4);
                fragmentTransaction.commit();
                title.setText("Change Password");
                getSlidingMenu().toggle();


            }
        });

        donateRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonateFragment fragment5 = new DonateFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment5);
                fragmentTransaction.commit();
                title.setText("Donate");
                getSlidingMenu().toggle();
            }
        });



       transactionRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionHistoryFragment fragment6 = new TransactionHistoryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment6);
                fragmentTransaction.commit();
                title.setText("Transaction History");
                getSlidingMenu().toggle();
            }
        });


        contactRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUsFragment fragment7 = new    ContactUsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment7);
                fragmentTransaction.commit();
                title.setText("Contact Us");
                getSlidingMenu().toggle();
            }
        });

        aboutRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsFragment fragment8 = new AboutUsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout)findViewById(R.id.frameLayout)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment8);
                fragmentTransaction.commit();
                title.setText("About Us");
                getSlidingMenu().toggle();
            }
        });

        logoutRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
               logout_api();*/
                final Dialog dialog = new Dialog(SideMenuActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_dialog);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogDelete);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogDelete);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences myPrefs = getSharedPreferences("MY",
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.clear();
                        editor.commit();


                        Intent intent = new Intent(SideMenuActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("finish", true);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "User logout successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        SharedPreferences sharedPreferences;
        sharedPreferences = SideMenuActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        refreshToken = sharedPreferences.getString("refreshToken", "");
        id =sharedPreferences.getString("UserID","");
        id =sharedPreferences.getString("UserID","");
        accessToken = StaticKey.tokenPrefex+temp;
        Log.d("test_sam_access",accessToken+"ok");
        totalNotification_Api();
        userProfileApi();

    }


    private void totalNotification_Api() {

        final ProgressDialog pd = new ProgressDialog(SideMenuActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<TotalNotificationModel> call = Api_Client.getClient().TOTAL_NOTIFICATION_MODEL_CALL( accessToken);

        call.enqueue(new Callback<TotalNotificationModel>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(retrofit2.Call<TotalNotificationModel> call, retrofit2.Response<TotalNotificationModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        TotalNotificationModel totalNotificationModel = response.body();
                        String success = totalNotificationModel.getSuccess();
                        String message = totalNotificationModel.getMsg();

                        int Data = totalNotificationModel.getTotalNotification();

                        if (success.equals("true") || success.equals("True")) {

                            textnotification.setText(String.valueOf(Data));




                            Log.e( "notification" ,textnotification.toString() );
                           // Toast.makeText(SideMenuActivity.this, "shows Total notification  list successfully", Toast.LENGTH_SHORT).show();




                        } else {
                            Toast.makeText(SideMenuActivity.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e("user_id", "    Message");
                            Toast.makeText(SideMenuActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(SideMenuActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(SideMenuActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(SideMenuActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(SideMenuActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(SideMenuActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(SideMenuActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(SideMenuActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(SideMenuActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(SideMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            Log.e("user_id", "    Exception");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("user_id", "    Exception  "+e.getMessage()+"  "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<TotalNotificationModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(SideMenuActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(SideMenuActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }
    private void userProfileApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(SideMenuActivity.this);
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
                            Toast.makeText(SideMenuActivity.this,"User Profile updated Successfully",Toast.LENGTH_LONG).show();



                            EditProfileModel EditProfileModel = response.body();
                            EditProfileResponse = EditProfileModel.getData();




                            strEmail = EditProfileResponse.getEmail();
                            strName = EditProfileResponse.getUsername();
                            // stringProfile = userProfileResponse.getImageUrl();

                            mailUser.setText(strEmail);
                            Name.setText(strName);

                            Glide.with(SideMenuActivity.this).load(Api_Client.BASE_URL_IMAGE2 + EditProfileResponse.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(profileImage);


                            Toast.makeText(SideMenuActivity.this, message, Toast.LENGTH_LONG).show();
                            // Calling another activity

                        } else {
                            Toast.makeText(SideMenuActivity.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(SideMenuActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(SideMenuActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(SideMenuActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(SideMenuActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(SideMenuActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(SideMenuActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(SideMenuActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(SideMenuActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(SideMenuActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(SideMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SideMenuActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(SideMenuActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    /*private void logout_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(SideMenuActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<LogoutModel> call = Api_Client.getClient().LOGOUT_MODEL_CALL(refreshToken, accessToken);

        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(retrofit2.Call<LogoutModel> call, retrofit2.Response<LogoutModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        LogoutModel LogoutModel = response.body();
                        String success = LogoutModel.getSuccess();
                        String message = LogoutModel.getMsg();


                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(SideMenuActivity.this, "logout Successfully", Toast.LENGTH_SHORT).show();


                            SharedPreferences sharedPreferences;
                            sharedPreferences = SideMenuActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            temp = sharedPreferences.getString("accessToken", "");
                            refreshToken = sharedPreferences.getString("refreshToken", "");
                            accessToken = StaticKey.tokenPrefex+temp;
                            Log.d("test_sam_access",accessToken+"ok");



                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e("user_id", "    Message");
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                            Log.e("user_id", "    Exception");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("user_id", "    Exception  "+e.getMessage()+"  "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        boolean shouldLoadHomeFragOnBackPress = true;
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                new HomeFragment();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
                return;
            }
        }
    }


}
