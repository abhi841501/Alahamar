package com.example.alahamar.AlahamarActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alahamar.R;
import com.example.alahamar.apimodel.ForgetPasswordModel;
import com.example.alahamar.retrofit.Api_Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText phoneNumber, newPassword, confirmNewPassword;
    TextView submitButton;
    ImageView hiddenPassword, showPassword, showPassword1, hiddenPassword1;
    public String phoneVerificationId;
    public FirebaseAuth mAuth;
    private Context context;
    Dialog dialog2;
    String data;
    AuthCredential credential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        inits();
        FirebaseApp.initializeApp(ForgetPasswordActivity.this);
        context = ForgetPasswordActivity.this;
        FirebaseApp.initializeApp(ForgetPasswordActivity.this);


        FirebaseApp.getApps(context);
        mAuth = FirebaseAuth.getInstance();


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


            }
        });


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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneNumber.getText().toString().equals("")) {
                    Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    sendVerificationCode("+91" + phoneNumber.getText().toString());

                }
                //validation
             /*  if(validation()) {
                    //rest api
                   forget_api();


                }*/




            }
        });


    }

    private void sendVerificationCode(String phoneNumber) {

        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                this, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
/*            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            */
            phoneVerificationId = s;
            //viewFlipper.setDisplayedChild(1);

            alert_dialogg();


        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            signInWithPhoneAuthCredential(phoneAuthCredential);
            // checking if the code
            // is null or not.

            if (code != null) {
                //   Forget_Login();
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
//                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
//                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.

              /*  Intent intent = new Intent(MainActivity.this, NameEmailActivity.class);
                startActivity(intent);*/
            Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(ForgetPasswordActivity.this, "Please try again", Toast.LENGTH_LONG).show();
        }
    };

    private void alert_dialogg() {



        final Dialog dialog = new Dialog(ForgetPasswordActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_dialogg);

        OtpTextView otp = dialog.findViewById(R.id.otp);
        AppCompatButton submit_otp_button = dialog.findViewById(R.id.submit_otp_button);

        submit_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp.getOTP().toString();
                Log.e("test_sam_otp",code);
                verifyCode(code);
                dialog.dismiss();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    }
    private void verifyCode(String data) {
        String code = data;
        if ((!code.equals("")) && (code.length() == 6)) {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else if (code.length() != 6) {
            Toast.makeText(this, "Please enter six digit valid otp", Toast.LENGTH_SHORT).show();

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ForgetPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(ForgetPasswordActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();

                         /*   Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);

                            startActivity(intent);*/
                            if(validation()){
                                forget_api();
                            }


                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }


    private boolean validation() {
        if (phoneNumber.getText().toString().equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter phone Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword.getText().toString().equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confirmNewPassword.getText().toString().equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
            Toast.makeText(this, "password did not match", Toast.LENGTH_SHORT).show();
            return false;
        }

            return true;

    }

    private void forget_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(ForgetPasswordActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<ForgetPasswordModel> call = Api_Client.getClient().FORGET_PASSWORD_MODEL_CALL(phoneNumber.getText().toString(), newPassword.getText().toString());

        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(retrofit2.Call<ForgetPasswordModel> call, retrofit2.Response<ForgetPasswordModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        ForgetPasswordModel ForgetPasswordModel = response.body();
                        String success = ForgetPasswordModel.getSuccess();
                        String message = ForgetPasswordModel.getMsg();


                        if (success.equals("true") || success.equals("True")) {
                           /* sendVerificationCode("+91" + phoneNumber.getText().toString());*/
                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ForgetPasswordActivity.this, "Forget password Successfully", Toast.LENGTH_LONG).show();



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

                    Log.e("user_id", "    Exception  " + e.getMessage() + "  " + e.toString());
                }
            }



            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
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



    private void inits() {
        phoneNumber = findViewById(R.id.phoneNumber);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        submitButton = findViewById(R.id.submitButton);
        hiddenPassword = findViewById(R.id.hiddenPassword);
        showPassword = findViewById(R.id.showPassword);
        showPassword1 = findViewById(R.id.showPassword1);
        hiddenPassword1 = findViewById(R.id.hiddenPassword1);

    }


}

