package com.example.alahamar.AlahamarActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.CommentModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    ImageView send,circleBack;
     String community_post;
     String comment;
     String UserID;
    String temp, accessToken;
     EditText editComment;
    private MediaMetadataCompat communityDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        send = findViewById(R.id.send);
        circleBack = findViewById(R.id.circleBack);
        editComment = findViewById(R.id.editComment);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_api();
             // Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                comment = editComment.getText().toString();

                editComment.setText("");


            }



        });
     /*   total_comment_api();*/
        circleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        community_post=getIntent().getStringExtra("id");

        SharedPreferences sharedPreferences;
        sharedPreferences =CommentsActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        UserID = sharedPreferences.getString("UserID", "");
        accessToken = StaticKey.tokenPrefex+temp;
       // Log.d("test_sam_access",accessToken+"ok");

    }

    private void comment_api() {
      final   ProgressDialog pd = new ProgressDialog(CommentsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading....");
        pd.show();

        Call<com.example.alahamar.apimodel.CommentModel> call = Api_Client.getClient().COMMENT_MODEL_CALL(accessToken,
                community_post,
                comment,
                UserID);

        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = (response.body().getSuccess());


                        if (success.equals("true") || success.equals("True")) {
                            String listSize = String.valueOf(communityDataList.size());


                            Toast.makeText(CommentsActivity.this,"Community Comment Add  Successfully!",Toast.LENGTH_LONG).show();

                            /*   Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/
                            // Calling another activity

                        } else {
                            /*  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/

                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(CommentsActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(CommentsActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(CommentsActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(CommentsActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(CommentsActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(CommentsActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                            Toast.makeText(CommentsActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(CommentsActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(CommentsActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(CommentsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(CommentsActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(CommentsActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });



    }

}