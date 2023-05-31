package com.example.alahamar.AlahamarActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alahamar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class DashboardActivity extends AppCompatActivity {
    AppCompatButton button;
    AppCompatEditText user_mobile_number;
    public FirebaseAuth mAuth;
    private Context context;
    Dialog dialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user_mobile_number = findViewById(R.id.user_mobile_number);
        button = findViewById(R.id.button);
        FirebaseApp.initializeApp(DashboardActivity.this);
        context = DashboardActivity.this;
        FirebaseApp.initializeApp(DashboardActivity.this);
        FirebaseApp.getApps(context);
        mAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user_mobile_number.getText().toString().equals("")) {
                    Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationCode("+91" + user_mobile_number.getText().toString());
                }
            }
        });



    }
    private void sendVerificationCode(String mobileNumber) {

        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                this, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }

    private String phoneVerificationId;
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
           /* Intent intent=new Intent(getApplicationContext(),Verification_Activity.class);
            startActivity(intent);*/
            phoneVerificationId = s;
            //viewFlipper.setDisplayedChild(1);

            alert_dialog();


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
            Toast.makeText(DashboardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(DashboardActivity.this, "Please try again", Toast.LENGTH_LONG).show();
        }
    };

    private void alert_dialog() {



        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_dialog);

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
                .addOnCompleteListener(DashboardActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(DashboardActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);

                            startActivity(intent);

                        } else {
                            Toast.makeText(DashboardActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }
}
