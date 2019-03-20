package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//Main Activity class

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//class implements click listener and search with text
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener
{

    //var
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private HandleXML handleXML = new HandleXML();
    private ArrayList<ListItemObject> items;
    private ArrayList<ListItemObject> itemstemp;
    private ArrayList<SpinnerItems> spinnerItems;
    private SpinnerItemsAdapter spinnerItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //make the app fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set header on the activity (Action Bar)
        getSupportActionBar().setTitle("British Geological Survey");
        getSupportActionBar().setSubtitle("Expert | Impartial | Innovative");

        if (!isNetworkAvailable()){
            Log.d("MyTag", "device is Not Online");
            //Enable  internet
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Network Error");
            alertDialog.setMessage("Please enable your internet services");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }else {
            Log.d("MyTag", "device is Online network is ready");
            //start new thread
            new Thread(new Task()).start();
        }
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //adding the search menu and enabling search with text.
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    //default method implementation (not used)
    @Override
    public boolean onQueryTextSubmit(String query) { return false; }

    //searching the array with user input text and updating the listView
    @Override
    public boolean onQueryTextChange(String newText)
    {

        String input = newText.toLowerCase();
        items = new ArrayList<>();

        int i = 0;
        while ( i < itemstemp.size()) {
            ListItemObject getitem = itemstemp.get(i);
            getitem.getTitle();

            if (getitem.getTitle().toLowerCase().contains(input))
            {
                items.add(getitem);
            }
            i++;
        }

        if (items.isEmpty()){
            items = new ArrayList<>();
            listView(items);
            return true;
        }
        else {
            listView(items);
            return true;
        }

    }

    //default setting for spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private class Task implements Runnable
    {

        @Override
        public void run()
        {
            try
            {
                Log.e("MyTag","in try");
                URL url=new URL(urlSource);
                HttpURLConnection http=(HttpURLConnection)url.openConnection();
                http.setDoInput(true);
                http.connect();
                InputStream is=http.getInputStream();
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(is,null);
                handleXML.ProcessData(xmlPullParser);

            }
            catch (IOException e) { Log.e("MyTag", "ioexception"); }
            catch (XmlPullParserException e) { e.printStackTrace(); }

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    System.out.println(handleXML.listitem.size());
                    System.out.println(handleXML.itemObjects.size());
                    items = new ArrayList<>(handleXML.listitem);
                    itemstemp = items;
                    spinner();
                    listView(items);
                }
            });
        }
    }

    //display the listView elements
    public void listView(final ArrayList<ListItemObject> item)
    {
        ListView listView = findViewById(R.id.listView);
        ListAdapter listAdapter = new CustomAdapter(MainActivity.this,item);
        listView.setAdapter(listAdapter);
        //if item is click in the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //open map if google services are ready
                if (isServicesOK()){ OpenMap(position);}
            }
        });

    }

    //if acknowledgement button is click display AlertDialog with Back option
    public void acknowledgement(View v)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Acknowledgement");
        alertDialog.setMessage("This app contains British Geological Survey materials ©NERC 2019");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //check if google services are ready and ok to be used.
    public boolean isServicesOK()
    {
        final int ERROR_DIALOG_REQUEST = 9001;
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //user can make map requests
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Google error dialog
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            //Toast message
            Toast.makeText(this, "Can't Make Map Requests", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    public void OpenMap(int pos)
    {
        ListItemObject getitem = items.get(pos);
        String T = getitem.getTitle();
        ItemObject getitemObjects;


        int i = 0;
        while ( i < handleXML.listitem.size()){
            getitemObjects = handleXML.itemObjects.get(i);

            if (getitemObjects.getTitle() == T)
            {

                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra("Object", handleXML.itemObjects.get(i));
                startActivity(intent);
                break;
            }
            i++;
        }
    }

    public void spinner()
    {

        spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItems("Alphabetical", R.drawable.ic_alphabetical));
        spinnerItems.add(new SpinnerItems("Date", R.drawable.ic_date));
        spinnerItems.add(new SpinnerItems("Depth", R.drawable.ic_depth));
        spinnerItems.add(new SpinnerItems("Magnitude", R.drawable.ic_magnitude));
        spinnerItems.add(new SpinnerItems("Scotland", R.drawable.ic_scotland));
        spinnerItems.add(new SpinnerItems("England", R.drawable.ic_england));
        spinnerItems.add(new SpinnerItems("Wales", R.drawable.ic_wales));
        spinnerItems.add(new SpinnerItems("Other", R.drawable.ic_other));

        Spinner spinner = findViewById(R.id.list_spinner);
        spinnerItemsAdapter = new SpinnerItemsAdapter(this, spinnerItems);
        spinner.setAdapter(spinnerItemsAdapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (position == 0 ){
            items = itemstemp;
            Collections.sort(items , Alphabetical);
            listView(items);

        }else if (position == 1){
            items = itemstemp;
            listView(handleXML.listitem);

        }else if (position == 2){
            items = itemstemp;
            Collections.sort(items , Depth);
            Collections.reverse(items);
            listView(items);

        }else if (position == 3){
            items = itemstemp;
            Collections.sort(items , Magnitude);
            Collections.reverse(items);
            listView(items);
        } else if (position == 4){

            //ic_scotland
            items = new ArrayList<>();
            ItemObject getitemObjects;
            boolean Scotland=false;
            int i = 0;
            while ( i < handleXML.listitem.size()){

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    ListItemObject item = new ListItemObject();
                    getitemObjects = handleXML.itemObjects.get(i);

                    double lat = Double.parseDouble(getitemObjects.getgLat());
                    double lng = Double.parseDouble(getitemObjects.getgLong());

                    List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                    if (addresses.size() > 0)
                    {
                        String AdminArea=addresses.get(0).getAdminArea();
                        if (AdminArea == null){Scotland = false;}
                        else { Scotland = AdminArea.equals("Scotland");}

                        if (Scotland) {

                            String mString = getitemObjects.getMagnitude();
                            mString = mString.replace("Magnitude: ", "");
                            double lMagnitude = Double.parseDouble(mString);


                            int D = Integer.parseInt(getitemObjects.getDepth());
                            item.setTitle(getitemObjects.getTitle());
                            item.setDate(getitemObjects.getDate());
                            item.setMagnitude(lMagnitude);
                            item.setDepth(D);
                            items.add(item);
                        }
                    }
                } catch (IOException e) { e.printStackTrace(); }

                i++;
            }
            System.out.println(items.size());
            listView(items);

        }
        else if (position == 5){
            //England
            items = new ArrayList<>();
            ItemObject getitemObjects;
            boolean England=false;
            int i = 0;
            while ( i < handleXML.listitem.size()){

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    ListItemObject item = new ListItemObject();
                    getitemObjects = handleXML.itemObjects.get(i);

                    double lat = Double.parseDouble(getitemObjects.getgLat());
                    double lng = Double.parseDouble(getitemObjects.getgLong());

                    List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                    if (addresses.size() > 0)
                    {
                        String AdminArea=addresses.get(0).getAdminArea();
                        if (AdminArea == null){England = false;}
                        else { England = AdminArea.equals("England");}

                        if (England) {

                            String mString = getitemObjects.getMagnitude();
                            mString = mString.replace("Magnitude: ", "");
                            double lMagnitude = Double.parseDouble(mString);


                            int D = Integer.parseInt(getitemObjects.getDepth());
                            item.setTitle(getitemObjects.getTitle());
                            item.setDate(getitemObjects.getDate());
                            item.setMagnitude(lMagnitude);
                            item.setDepth(D);
                            items.add(item);
                        }
                    }
                } catch (IOException e) { e.printStackTrace(); }

                i++;
            }
            System.out.println(items.size());
            listView(items);

        }
        else if (position == 6){
            //Wales
            items = new ArrayList<>();
            ItemObject getitemObjects;
            boolean Wales=false;
            int i = 0;
            while ( i < handleXML.listitem.size()){

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    ListItemObject item = new ListItemObject();
                    getitemObjects = handleXML.itemObjects.get(i);

                    double lat = Double.parseDouble(getitemObjects.getgLat());
                    double lng = Double.parseDouble(getitemObjects.getgLong());

                    List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                    if (addresses.size() > 0)
                    {
                        String AdminArea=addresses.get(0).getAdminArea();
                        if (AdminArea == null){Wales = false;}
                        else { Wales = AdminArea.equals("Wales");}

                        if (Wales) {

                            String mString = getitemObjects.getMagnitude();
                            mString = mString.replace("Magnitude: ", "");
                            double lMagnitude = Double.parseDouble(mString);



                            int D = Integer.parseInt(getitemObjects.getDepth());
                            item.setTitle(getitemObjects.getTitle());
                            item.setDate(getitemObjects.getDate());
                            item.setMagnitude(lMagnitude);
                            item.setDepth(D);
                            items.add(item);
                        }
                    }
                } catch (IOException e) { e.printStackTrace(); }

                i++;
            }
            System.out.println(items.size());
            listView(items);
        }
        else if (position == 7){
            //Other
            items = new ArrayList<>();
            ItemObject getitemObjects;

            int i = 0;
            while ( i < handleXML.listitem.size()){

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    ListItemObject item = new ListItemObject();
                    getitemObjects = handleXML.itemObjects.get(i);

                    double lat = Double.parseDouble(getitemObjects.getgLat());
                    double lng = Double.parseDouble(getitemObjects.getgLong());

                    List<Address> addresses = gcd.getFromLocation(lat, lng, 1);


                        if (addresses.isEmpty())
                        {
                            String mString = getitemObjects.getMagnitude();
                            mString = mString.replace("Magnitude: ", "");
                            double lMagnitude = Double.parseDouble(mString);
                            int D = Integer.parseInt(getitemObjects.getDepth());
                            item.setTitle(getitemObjects.getTitle());
                            item.setDate(getitemObjects.getDate());
                            item.setMagnitude(lMagnitude);
                            item.setDepth(D);
                            items.add(item);
                        }
                        else {
                            String AdminArea=addresses.get(0).getAdminArea();

                            if (AdminArea == null){
                                String mString = getitemObjects.getMagnitude();
                                mString = mString.replace("Magnitude: ", "");
                                double lMagnitude = Double.parseDouble(mString);
                                int D = Integer.parseInt(getitemObjects.getDepth());
                                item.setTitle(getitemObjects.getTitle());
                                item.setDate(getitemObjects.getDate());
                                item.setMagnitude(lMagnitude);
                                item.setDepth(D);
                                items.add(item);
                            }
                        }

                } catch (IOException e) { e.printStackTrace(); }
                i++;
            }
            System.out.println(items.size());
            listView(items);
        }

    }

    java.util.Comparator<ListItemObject> Alphabetical = new java.util.Comparator<ListItemObject>() {
        @Override
        public int compare(ListItemObject o1, ListItemObject o2) {
            int compareInt = o1.getTitle().compareTo(o2.getTitle());
            if (compareInt < 0) {return -1;}
            if (compareInt > 0) {return 1;}
            return 0; }};

    java.util.Comparator<ListItemObject> Depth = new java.util.Comparator<ListItemObject>() {
        @Override
        public int compare(ListItemObject o1, ListItemObject o2) {
                if (o1.getDepth() > o2.getDepth()){ return 1;}
                else if (o1.getDepth() < o2.getDepth()){ return -1;}
            return 0; }};

    java.util.Comparator<ListItemObject> Magnitude = new java.util.Comparator<ListItemObject>() {
        @Override
        public int compare(ListItemObject o1, ListItemObject o2) {
            if (o1.getMagnitude() > o2.getMagnitude()){ return 1;}
            else if (o1.getMagnitude() < o2.getMagnitude()){ return -1;}
            return 0; }};


}

//temp code

//itemObject = processXML.itemObjects.get(1);
//System.out.println(itemObject.getTitle());

//Collections.sort(processXML.itemObjects ,);
//for (ItemObject i : processXML.itemObjects){
//   System.out.println(i.getTitle()); }

//ic_scotland 56.490, -4.202
//England  52.355, -1.174
//Wales    52.130, -3.783
//Ireland  54.787, -6.492