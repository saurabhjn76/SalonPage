package com.saurabhjn76.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String address=null;
    private String salon_name=null;
    private ImageButton navigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(getIntent().hasExtra("salon_address"))
        {
            Bundle bundle =getIntent().getExtras();
            address=bundle.getString("salon_address");
        }
        if(getIntent().hasExtra("salon_name"))
        {
            Bundle bundle =getIntent().getExtras();
            salon_name=bundle.getString("salon_name");
        }

        // mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*findDirections(SGTasksListAppObj.getInstance().currentUserLocation.getLatitude(),
SGTasksListAppObj.getInstance().currentUserLocation.getLongitude(),
clickMarkerLatLng.latitude, clickMarkerLatLng.longitude, GMapV2Direction.MODE_DRIVING );
*/


        /*navigate=(ImageButton)findViewById(R.id.action_navigate);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=28.5888925,77.06163949999996&daddr=28.4551923, 77.28936060000001");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   /* public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }
    public void handleGetDirectionsResult(ArrayList directionPoints)
    {
        Polyline newPolyline;
        GoogleMap mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        newPolyline = mMap.addPolyline(rectLine);
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Marker marker;

        // Add a marker in Sydney and move the camera
        LatLng gurgaon = new LatLng(28.481216, 77.019135);
        LatLng current = new LatLng(28.481216, 77.019135);
        gurgaon=getLocationFromAddress(getApplicationContext(),address);
        Toast.makeText(getApplicationContext(),gurgaon.toString(),Toast.LENGTH_LONG).show();
        try {
           marker= mMap.addMarker(new MarkerOptions().position(gurgaon).title(salon_name));
            marker.showInfoWindow();
        }
        catch (Exception e)
        {
            gurgaon = new LatLng(28.481216, 77.019135);
          marker=  mMap.addMarker(new MarkerOptions().position(gurgaon).title(salon_name));
            marker.showInfoWindow();
        }
        mMap.addMarker(new MarkerOptions().position(current).title("You")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gurgaon,12.0f));


    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return new LatLng(28.481216, 77.019135);
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        if(p1==null)
            return new LatLng(28.481216, 77.019135);
        else
            return p1;
    }

}