package com.example.alahamar.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alahamar.AlahamarActivity.LoginActivity;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.ChangePasswordModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {
    EditText  oldPassword,newPassword,confirmNewPassword;
    ImageView hiddenPassword,hiddenPassword1,showPassword,showPassword1;
    TextView updatePassword;
     String   strOldPassword,strNeWPassword;
     String authorization, accessToken;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        oldPassword = view.findViewById(R.id.oldPassword);
        newPassword  = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmNewPassword);
        updatePassword = view.findViewById(R.id.updatePassword);
        hiddenPassword = view.findViewById(R.id.hiddenPassword);
        showPassword = view.findViewById(R.id.showPassword);
        hiddenPassword1 = view.findViewById(R.id.hiddenPassword1);
        showPassword1 = view.findViewById(R.id.showPassword1);

hiddenPassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        showPassword.setVisibility(View.VISIBLE);
        hiddenPassword.setVisibility(View.GONE);
        newPassword.setTransformationMethod(null);
        newPassword.setSelection(newPassword.getText().length());

    }
});

        showPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPassword.setVisibility(View.GONE);
            hiddenPassword.setVisibility(View.VISIBLE);
            newPassword.setTransformationMethod(new PasswordTransformationMethod());
            newPassword.setSelection(newPassword.getText().length());
        }});
        hiddenPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword1.setVisibility(View.VISIBLE);
                hiddenPassword1.setVisibility(View.GONE);
                confirmNewPassword.setTransformationMethod(null);
                confirmNewPassword.setSelection(confirmNewPassword.getText().length());


            }
        });
        showPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword1.setVisibility(View.GONE);
                hiddenPassword1.setVisibility(View.VISIBLE);
                confirmNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                confirmNewPassword.setSelection(confirmNewPassword.getText().length());




            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation
              strOldPassword =  oldPassword.getText().toString();
              strNeWPassword = newPassword.getText().toString();

              if (validation())
              {
                  update_password_api();
              }
/*
                if (validation())
                {
                    //Change_api
                update_password_api();
                }*/
            }
        });

        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
      //  UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("accessToken", "");
        accessToken = StaticKey.tokenPrefex+authorization;

        return view;
    }

  /*  private void ChangePassword_Api() {
        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<ChangePasswordModel> call = Api_Client.getClient().CHANGE_PASSWORD_MODEL_CALL( strOldPassword, strNeWPassword);

        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(retrofit2.Call<ChangePasswordModel> call, retrofit2.Response<ChangePasswordModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        ChangePasswordModel ChangePasswordModel = response.body();
                        String success = ChangePasswordModel.getSuccess();
                        String message = ChangePasswordModel.getMsg();


                        if (success.equals("true") || success.equals("True")) {

                            refreshToken = token.getRefresh();
                            accessToken = token.getAccess();

                            Log.d("test_sam",refreshToken);
                            Log.d("test_sam",accessToken);

                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("accessToken", String.valueOf(accessToken)).apply();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(),"Password changed successfully",Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
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

    }*/

    private void update_password_api() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<ChangePasswordModel> call = Api_Client.getClient().CHANGE_PASSWORD_MODEL_CALL(accessToken ,strOldPassword,strNeWPassword);

        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMsg();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(),"Password Change Successfully",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
//                            startActivity(intent);
//                            finish();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);

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
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
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

    private boolean validation() {
        if (oldPassword.getText().toString().equals("")){
            Toast.makeText(getActivity(), "please enter old password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (newPassword.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please enter new password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (confirmNewPassword.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
        }

    }
