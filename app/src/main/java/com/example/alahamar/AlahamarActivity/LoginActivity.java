package com.example.alahamar.AlahamarActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.alahamar.R;

import com.example.alahamar.Response.LoginModel;
import com.example.alahamar.Response.Token;
import com.example.alahamar.retrofit.Api_Client;
import com.example.alahamar.sideMenu1.SideMenuActivity;


import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton login;
    TextView register,forget_Password,notMember;
    ImageView hiddenPassword,showPassword;
    EditText password,userNameEmail;
    String userId;

    String refreshToken,accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inits();



        hiddenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword.setVisibility(View.VISIBLE);
                hiddenPassword.setVisibility(View.GONE);
                password.setTransformationMethod(null);
                password.setSelection(password.getText().length());


            }
        });
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword.setVisibility(View.GONE);
                hiddenPassword.setVisibility(View.VISIBLE);
                password.setTransformationMethod(new PasswordTransformationMethod());
                password.setSelection(password.getText().length());




            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             /*   Intent intent = new Intent(LoginActivity.this,HomeFragment.class);
                intent.putExtra("userId","userId");
                intent.putExtra("userName","userName");
                startActivity(intent);*/
                // validation
                if(validation()){

                    // login api
                    login_api();

                }

            }
        });
        forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        notMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void login_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<LoginModel> call = Api_Client.getClient().LOGIN_MODEL_CALL(userNameEmail.getText().toString(),password.getText().toString());

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(retrofit2.Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {



                        String success = response.body().getSuccess();
                        String message =response.body().getMsg();


                        if (success.equals("true") || success.equals("True")) {

                            Log.e("test", message);

                            LoginModel loginModel = response.body();


                            Token token = loginModel.getToken();
                            userId = String.valueOf(loginModel.getUserId());

                            refreshToken = token.getRefresh();
                            accessToken = token.getAccess();


                            Log.d("test_sam",refreshToken);
                            Log.d("test_sam",accessToken);

                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("accessToken", String.valueOf(accessToken)).apply();
                            editor.putString("UserID", String.valueOf(userId));
                            editor.putString("UserID", String.valueOf(userId));

                            Intent intent = new Intent(LoginActivity.this, SideMenuActivity.class);
                            intent.putExtra("UserID",userId);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();






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
            public void onFailure(Call<LoginModel> call, Throwable t) {
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
    }


    private boolean validation() {

        if(userNameEmail.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, "Please enter user name.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void inits() {
        userNameEmail = findViewById(R.id.userNameEmail);
        login  = findViewById(R.id.login);
        forget_Password = findViewById(R.id.forget_Password);
        register = findViewById(R.id.register);
        notMember = findViewById(R.id.notMember);
        hiddenPassword = findViewById(R.id.hiddenPassword);
        showPassword = findViewById(R.id.showPassword);
        password = findViewById(R.id.password);
    }
}