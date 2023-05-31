package com.example.alahamar.AlahamarActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.alahamar.Adapter.EventAdapter;
import com.example.alahamar.Adapter.EventImageAdapter;
import com.example.alahamar.R;
import com.example.alahamar.StaticKey;
import com.example.alahamar.apimodel.EventData;
import com.example.alahamar.apimodel.EventGallery;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity {
    ImageView back, GroupedImage,imageLife;
    TextView date, description,heading;
   String strDate,strDescription, strHeading,id;
   String gallery;






    private static final int SELECT_PICTURE = 1;
    private RecyclerView rcv_event_details_image_list;

    private String selectedImagePath;
    private List<EventData> eventDataList;

    private Object EventDetailsModel;
    EventImageAdapter eventImageAdapter;
    private ArrayList<EventGallery> eventGalleryArrayList = new ArrayList<>();
    private RecyclerView recycler_Event;
    private EventAdapter eventAdapter;
    private String data,headingData,descriptionData;
    private Uri imageUri;

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticKey.eventGalleryListStatic.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StaticKey.eventGalleryListStatic.clear();
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);
        back = findViewById(R.id.back);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        heading = findViewById(R.id.heading);
        GroupedImage = findViewById(R.id.GroupedImage);


        rcv_event_details_image_list = findViewById(R.id.rcv_event_details_image_list);
      /*  recycler_Event = findViewById(R.id.recycler_Event);
*/



        data = getIntent().getStringExtra("date");
        headingData = getIntent().getStringExtra("heading");
        descriptionData = getIntent().getStringExtra("description");


        heading.setText(headingData);
        description.setText(descriptionData);
        date.setText(data);

        Toast.makeText(EventDetailsActivity.this,data+" : "+headingData+" : "+descriptionData+"ok" , Toast.LENGTH_SHORT).show();
      //  Log.e("test_sam", data+" : "+headingData+" : "+descriptionData+"ok");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EventDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rcv_event_details_image_list.setLayoutManager(layoutManager);
        eventImageAdapter = new EventImageAdapter(EventDetailsActivity.this, StaticKey.eventGalleryListStatic);
        rcv_event_details_image_list.setAdapter(eventImageAdapter);


        Toast.makeText(EventDetailsActivity.this, "ok"+StaticKey.eventGalleryListStatic.size(), Toast.LENGTH_SHORT).show();
       // Log.e("test_sam", StaticKey.eventGalleryListStatic.size()+"ok");

       back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                finish();
            }
        });


        showImage();


    }

    public void showImage() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageURI(imageUri);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }




    }
