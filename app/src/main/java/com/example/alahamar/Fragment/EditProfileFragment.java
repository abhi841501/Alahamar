package com.example.alahamar.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.alahamar.util.Permission.checkPermissionCamera;
import static com.example.alahamar.util.Permission.checkPermissionReadExternal;
import static com.example.alahamar.util.Permission.checkPermissionReadExternal2;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.alahamar.Adapter.NonNull;
import com.example.alahamar.AlahamarActivity.RequiresApi;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.EditProfileModel;
import com.example.alahamar.apimodel.EditprofileData;
import com.example.alahamar.retrofit.Api_Client;
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


public class EditProfileFragment extends Fragment {
    CircleImageView userProfile1;
    ImageView addImage1;
    EditText userName,Email,phoneNo;
    TextView updateProfile;
    String auth;
    String temp, accessToken;
    String id;
    String authorization;
    MultipartBody.Part filePart;
    private ContentValues values6;
    private Uri imageUri6;
    File profile_fileImage;
    String UserId;
    String struserName,strEmail,strPhoneNumber;

    private static final int pickImage = 1025;
    private static final int MY_CAMERA_REQUEST_CODE = 12;
    private static final int CAMERA_PIC_REQUEST_R = 10;
    private EditprofileData EditProfileResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        userProfile1 =view.findViewById(R.id.userProfile1);
        addImage1 = view.findViewById(R.id.addImage1);
        userName = view.findViewById(R.id.userName);
        Email = view.findViewById(R.id.Email);
        phoneNo = view.findViewById(R.id.phoneNo);
       updateProfile = view.findViewById(R.id.updateProfile);





       addImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog addProfileUpdate = new Dialog(getActivity());
                addProfileUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addProfileUpdate.setContentView(R.layout.gallary_camera_popup);

                TextView gallaryDialog = addProfileUpdate.findViewById(R.id.galary_layout);
                TextView cameraDialog = addProfileUpdate.findViewById(R.id.camera_layout);

                addProfileUpdate.show();
                Window window = addProfileUpdate.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                gallaryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  openGallery(PICK_IMAGE);
                        gallery();
                        addProfileUpdate.dismiss();
                    }
                });

                cameraDialog.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        profile_camera_open();
                        addProfileUpdate.dismiss();

                    }
                });
            }
        });
        SharedPreferences sharedPreferences;
        sharedPreferences =getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        temp = sharedPreferences.getString("accessToken", "");
        id = sharedPreferences.getString("UserID", "");
        accessToken = StaticKey.tokenPrefex+temp;
       // Log.d("test_sam_access",accessToken+"ok");

        sharedPreferences.edit().clear().apply();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation());
                {
                    updateProfileApi();

                }



            }



        });

        userProfileApi();


      /*  userName.setText("");
        Email.setText("");
        phoneNo.setText("");

*/

        struserName = userName.getText().toString();
         strEmail = Email.getText().toString();
         strPhoneNumber = phoneNo.getText().toString();
        return view;
    }


    private void updateProfileApi() {

            MultipartBody.Part profileImage = null;
            try {
                try {
                    if (profile_fileImage == null) {
                        profileImage = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("image/*"), ""));

                    } else {
                        profileImage = MultipartBody.Part.createFormData("image", profile_fileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profile_fileImage));
                    }

                } catch (Exception e) {
                    Log.e("conversionException", "error" + e.getMessage());
                }
            } catch (Exception e) {
                Log.v("dash", "***********************************************" + e);
                //Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }



            RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"),id);
            RequestBody UserNameUpdate = RequestBody.create(MediaType.parse("text/plain"), userName.getText().toString());
            RequestBody EmailIdUpdate = RequestBody.create(MediaType.parse("text/plain"), Email.getText().toString());
            RequestBody UpdatePhoneNumber = RequestBody.create(MediaType.parse("text/plain"),phoneNo.getText().toString());


            Call<EditProfileModel> call = Api_Client.getClient().EDIT_PROFILE_MODEL_CALL(accessToken,UserId, UserNameUpdate, EmailIdUpdate, UpdatePhoneNumber, profileImage);
            call.enqueue(new Callback<EditProfileModel>() {

                @Override
                public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {


                    try {
                        if (response.isSuccessful()) {

                            String message = response.body().getMsg();
                            String success = response.body().getSuccess();


                            if (success.equalsIgnoreCase("true")) {


                              /*  userName.setText("");
                                Email.setText("");
                                phoneNo.setText("");*/
                                //getHunt Details Api
                                userProfileApi();


                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            }

                        } else {

                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());
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
                            }
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (JsonSyntaxException exception) {
                        exception.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }

                @Override
                public void onFailure(@NonNull Call<EditProfileModel> call, Throwable t) {

                    if (t instanceof IOException) {

                        Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    } else {

                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }



    private void userProfileApi() {

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



                            EditProfileModel EditProfileModel = response.body();
                            EditProfileResponse = EditProfileModel.getData();

                            struserName = EditProfileResponse.getUsername();
                              strEmail = EditProfileResponse.getEmail();
                             strPhoneNumber = EditProfileResponse.getPhoneNumber();
                            // stringProfile = userProfileResponse.getImageUrl();

                            userName.setText(struserName);
                            Email.setText(strEmail);
                            phoneNo.setText(strPhoneNumber);
                          Glide.with(getActivity()).load(Api_Client.BASE_URL_IMAGE2 + EditProfileResponse.getImage()).placeholder(R.drawable.ic_launcher_foreground).into(userProfile1);


                          //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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


    private boolean validation() {
        if (userName.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please Enter Username",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (Email.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please Enter Email",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (phoneNo.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please Enter Your PhoneNo",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (addImage1.getDrawable().equals(""))
        {
            Toast.makeText(getActivity(),"Please upload image",Toast.LENGTH_LONG).show();
            return false;
        }
        else
            return true;

    }

    private void gallery() {
        boolean readExternal = checkPermissionReadExternal(getActivity());
        boolean writeExternal = checkPermissionReadExternal2(getActivity());
        boolean camera = checkPermissionCamera(getActivity());
        if (readExternal && camera) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, pickImage);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getActivity().getPackageManager();
        boolean readExternal = checkPermissionReadExternal(getActivity());
        boolean writeExternal = checkPermissionReadExternal2(getActivity());
        boolean camera = checkPermissionCamera(getActivity());

        if (camera && writeExternal && readExternal) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri6 = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri6);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);
            }
        } else {
            Toast.makeText(getActivity(), "camera and write permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK) {
            // Getting Real uri path
            profile_fileImage = new File(getRealPathFromURIs(imageUri6));

            try {
                Log.e("filename", "filename is: " + getRealPathFromURIs(imageUri6));
                Log.e("filename", "filename is: " + profile_fileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Glide.with(getActivity())
                    .load(profile_fileImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(userProfile1);

        } else if (resultCode == RESULT_OK && requestCode == pickImage) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profile_fileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profile_fileImage));
            String selectedImagePath1 = getPath(selectedImageUri);

            Log.v("Deatils_path", "***" + selectedImagePath1);
            Glide.with(getActivity()).load(selectedImagePath1).placeholder(R.drawable.ic_launcher_background).into(userProfile1);
            Log.e("userImage1", "BB");
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }




}