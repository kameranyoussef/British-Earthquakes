package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


//this is activity 2 is used to implement google maps and display the xml data
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //vars
    private GoogleMap mMap;
    private boolean PermissionGranted = false;
    private static final int code_location = 4321;
    private ItemObject ObjGet = new ItemObject();

    private float zoom = 12f;
    private String Title;
    private String Magnitude;
    private String Depth;
    private String Date;
    private String Time;
    private String Category;
    private String gLat;
    private String gLong;
    private double DLat;
    private double DLong;

    private TextView PageTitle;

    private ImageView mTitle;
    private ImageView mMagnitude;
    private ImageView mDepth;
    private ImageView mDate;
    private ImageView mTime;
    private ImageView mCategory;
    private ImageView mCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //make the app full screen.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //run methods
        SetID();
        PassedObject();
        mButtons();
        getPermission();

    }

    //get the user permission to get location and start google maps.
    private void getPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                PermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, code_location);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, code_location);
        }
    }

    //checking the result Request Permissions Result
    //display the map either way
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGranted = false;
        switch (requestCode) {
            case code_location: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            PermissionGranted = false;
                            initMap();
                            return;
                        }
                    }
                    PermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    //start map and set it in the fragment.
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    //move the view of the map to specific location and add maker with title.
    private void moveCamera(LatLng latLng, float zoom, String Title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions().position(latLng).title(Title);
        mMap.addMarker(options);
    }

    //setting up all the IDs for elements in the layout
    private void SetID() {
        PageTitle = (TextView) findViewById(R.id.PageTitle);

        mTitle = (ImageView) findViewById(R.id.ic_title);
        mMagnitude = (ImageView) findViewById(R.id.ic_magnitude);
        mDepth = (ImageView) findViewById(R.id.ic_depth);
        mDate = (ImageView) findViewById(R.id.ic_date);
        mTime = (ImageView) findViewById(R.id.ic_time);
        mCategory = (ImageView) findViewById(R.id.ic_category);
        mCopyright = (ImageView) findViewById(R.id.ic_copyright);
    }

    //getting the parceled object from MainActiviry (the click item in the listView)
    private void PassedObject() {
        Intent intent = getIntent();
        ItemObject ObjGet = intent.getParcelableExtra("Object");

        Title = ObjGet.getTitle();
        Magnitude = ObjGet.getMagnitude();
        Depth = ObjGet.getDepth();
        Date = ObjGet.getDate();
        Time = ObjGet.getTime();
        Category = ObjGet.getCategory();
        gLat = ObjGet.getgLat();
        gLong = ObjGet.getgLong();


        DLat = Double.parseDouble(gLat);
        DLong = Double.parseDouble(gLong);

        PageTitle.setText(Title);

    }

    //button to return to the MainActiviy usng intent.
    public void Backbtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //click listener for each image in the MapActivity layout.
    //when click is true the page title text is updated.
    private void mButtons() {
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageTitle.setText(Title);
            }
        });

        mMagnitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageTitle.setText(Magnitude);
            }
        });

        mDepth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { PageTitle.setText("Depth: " +Depth+" km");
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageTitle.setText(Date);
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageTitle.setText("Time: " + Time);
            }
        });

        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { PageTitle.setText("Category: " + Category);
            }
        });

        mCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageTitle.setText("©NERC 2019");
            }
        });
    }

    //when map is ready to be displayed
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e("Tag", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Tag", "Can't find style. Error: ", e);
        }

        //create Toast
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        //move map view to specific location and set title for maker.
        moveCamera(new LatLng(DLat, DLong), zoom, Title);
        //checking permission (Manifest file)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //enable ZoomControls,AllGestures(touch),Compass
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }
}
























