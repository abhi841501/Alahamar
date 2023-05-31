package com.example.alahamar.AlahamarActivity;

import static com.example.alahamar.util.Permission.checkPermissionCamera;
import static com.example.alahamar.util.Permission.checkPermissionReadExternal;
import static com.example.alahamar.util.Permission.checkPermissionReadExternal2;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.alahamar.R;
import com.example.alahamar.apimodel.RegisterModel;
import com.example.alahamar.retrofit.Api_Client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    AppCompatButton register_Button;
    TextView signIn, already;
    ImageView hiddenPassword, showPassword, hiddenPassword1, showPassword1;
    CircleImageView userProfile;
    ImageView  addImage;
    EditText password,confirmPassword,userName,email,phone_Number;

    private ContentValues values6;
    private Uri imageUri6;
    File profile_fileImage;

    private static final int pickImage = 1025;
    private static final int MY_CAMERA_REQUEST_CODE = 12;
    private static final int CAMERA_PIC_REQUEST_R = 10;
    private MultipartBody.Part filePart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_Button = findViewById(R.id.register_Button);


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


        hiddenPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword1.setVisibility(View.VISIBLE);
                hiddenPassword1.setVisibility(View.GONE);
                confirmPassword.setTransformationMethod(null);
                confirmPassword.setSelection(confirmPassword.getText().length());
            }
        });

        showPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword1.setVisibility(View.GONE);
                hiddenPassword1.setVisibility(View.VISIBLE);
                confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                confirmPassword.setSelection(confirmPassword.getText().length());

            }
        });


        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation
                if (validation()) {

                    //register_api
                    register_api();

                }
            }

        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog addProfileUpdate = new Dialog(RegisterActivity.this);
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


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void register_api() {


        // show till load api data

        // Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        RequestBody userNameData = RequestBody.create(MediaType.parse("text/plain"), userName.getText().toString());
        RequestBody emailData = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody phone_NumberData = RequestBody.create(MediaType.parse("text/plain"),   phone_Number.getText().toString());
        RequestBody passwordData = RequestBody.create(MediaType.parse("text/plain"),password.getText().toString());



        if (profile_fileImage == null) {
            Toast.makeText(RegisterActivity.this, "null", Toast.LENGTH_SHORT).show();
            //RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            filePart = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("image/*"), ""));
        } else {
//          RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profileImage);
            Toast.makeText(RegisterActivity.this, "not null", Toast.LENGTH_SHORT).show();
            filePart = MultipartBody.Part.createFormData("image", profile_fileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profile_fileImage));

        }


        Call<RegisterModel> call = Api_Client.getClient().REGISTER_MODEL_CALL(userNameData,
                emailData,
                phone_NumberData,
                passwordData,
                filePart);

        Log.e("check","okay" +userNameData);
        Log.e("check","emailData" +emailData);
        Log.e("check","phone_NumberData" +phone_NumberData);
        Log.e("check","passwordData" +passwordData);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterModel> call, retrofit2.Response<RegisterModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {

                        RegisterModel RegisterModels = response.body();
                        String success = RegisterModels.getSuccess();
                        String message =  RegisterModels.getMsg();



                        if (success.equals("true") || success.equals("True")) {


                          Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                          startActivity(intent);
                            Toast.makeText(RegisterActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                         //   Log.e("user_id", "    False");
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
            public void onFailure(Call<RegisterModel> call, Throwable t) {
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
        if (userName.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "please enter username", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (email.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "please enter email id ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phone_Number.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this,"Please enter valid password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (confirmPassword.getText().toString().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (addImage.getDrawable().equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Please upload your image", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!confirmPassword.getText().toString().equals(password))
        {
            Toast.makeText(RegisterActivity.this, "Passwords  not matched", Toast.LENGTH_SHORT).show();

        }
        return true;
    }



    private void inits() {
        signIn = findViewById(R.id.signIn);
        already = findViewById(R.id.already);
        userProfile = findViewById(R.id.userProfile);
        addImage = findViewById(R.id.addImage);
        hiddenPassword = findViewById(R.id.hiddenPassword);
        showPassword = findViewById(R.id.showPassword);
        password = findViewById(R.id.password);
        hiddenPassword1 = findViewById(R.id.hiddenPassword1);
        showPassword1 = findViewById(R.id.showPassword1);
        confirmPassword = findViewById(R.id.confirmPassword);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phone_Number = findViewById(R.id.phone_Number);
        addImage = findViewById(R.id.addImage);

    }

    private void gallery() {
        boolean readExternal = checkPermissionReadExternal(RegisterActivity.this);
        boolean writeExternal = checkPermissionReadExternal2(RegisterActivity.this);
        boolean camera = checkPermissionCamera(RegisterActivity.this);
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

        PackageManager packageManager = RegisterActivity.this.getPackageManager();
        boolean readExternal = checkPermissionReadExternal(RegisterActivity.this);
        boolean writeExternal = checkPermissionReadExternal2(RegisterActivity.this);
        boolean camera = checkPermissionCamera(RegisterActivity.this);

        if (camera && writeExternal && readExternal) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri6 = RegisterActivity.this.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri6);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);
            }
        } else {
            Toast.makeText(RegisterActivity.this, "camera and write permission required", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
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

            Glide.with(RegisterActivity.this)
                    .load(profile_fileImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(userProfile);

        } else if (resultCode == RESULT_OK && requestCode == pickImage) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = RegisterActivity.this.managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profile_fileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profile_fileImage));
            String selectedImagePath1 = getPath(selectedImageUri);

            Log.v("Deatils_path", "***" + selectedImagePath1);
            Glide.with(RegisterActivity.this).load(selectedImagePath1).placeholder(R.drawable.ic_launcher_background).into(userProfile);
            Log.e("userImage1", "BB");
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = RegisterActivity.this.getContentResolver().query(uri, projection, null, null, null);
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
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}