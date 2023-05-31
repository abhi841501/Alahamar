package com.example.alahamar.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alahamar.R;
import com.example.alahamar.apimodel.ContactUsModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactUsFragment extends Fragment {
    EditText name,email,PhoneNumber,Suggestion;
    TextView submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        PhoneNumber = view.findViewById(R.id.PhoneNumber);
        Suggestion = view.findViewById(R.id.Suggestion);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation
                if (validation())
                {


                    contactUs_api();




                }


            }

        });




        return view;
    }
    private void contactUs_api() {

        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<ContactUsModel> call = Api_Client.getClient().CONTACT_US_MODEL_CALL(name.getText().toString(),email.getText().toString(),
                PhoneNumber.getText().toString(),Suggestion.getText().toString());

        call.enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        ContactUsModel contactUsModel = response.body();
                        String success = contactUsModel.getSuccess();
                        String message = contactUsModel.getMsg();


                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getActivity(),"User contact Enquiry Form Submit Successfully",Toast.LENGTH_LONG).show();

                            name.setText("");
                            email.setText("");
                            PhoneNumber.setText("");
                            Suggestion.setText("");



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
            public void onFailure(Call<ContactUsModel> call, Throwable t) {
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
        if (name.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (email.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }/*else if ( Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_SHORT).show();


            return false;
        }*/
        else if (PhoneNumber.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Suggestion.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}