package info.androidhive.customlistviewvolley;







import java.util.ArrayList;

import com.android.volley.toolbox.NetworkImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;


import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class maps extends FragmentActivity  {
    
    private LocationManager locationManager;
	GPSTracker gps;
	private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
	
        setContentView(R.layout.location);
       
        listner();
        /*
        gps = new GPSTracker(gpsemergency.this);

		// check if GPS enabled		
        if(gps.canGetLocation()){
        	
        	double latitude = gps.getLatitude();
        	double longitude = gps.getLongitude();
        	
        	// \n is for new line
        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
       mylon=longitude;
       mylat=latitude;
        }else{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gps.showSettingsAlert();
        	Intent gpsOptionsIntent = new Intent(  
        		    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
        		startActivity(gpsOptionsIntent);
        }
        */

        
       

	 mylon= Double.parseDouble(list.lon);
     mylat=Double.parseDouble(list.lat);
     
 	try {
		// Loading map
		initilizeMap();

		// Changing map type
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

		// Showing / hiding your current location
		googleMap.setMyLocationEnabled(true);

		// Enable / Disable zooming controls
		googleMap.getUiSettings().setZoomControlsEnabled(false);

		// Enable / Disable my location button
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// Enable / Disable Compass icon
		googleMap.getUiSettings().setCompassEnabled(true);

		// Enable / Disable Rotate gesture
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		googleMap.getUiSettings().setZoomGesturesEnabled(true);

	//	double latitude = 17.385044;
		//double longitude = 78.486671;

		// lets place some 10 random markers

			
		
			//------ my location tag
			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(mylat, mylon)).title(list.placename);

			/*marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			googleMap.addMarker(marker);
			*/
			marker.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.hicon));
			googleMap.addMarker(marker);
			
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(new LatLng(mylat,mylon)).zoom(12).build();

	googleMap.animateCamera(CameraUpdateFactory
			.newCameraPosition(cameraPosition));
			// mylocation tag 
			
			// Adding a marker
		
			
			
			googleMap.addMarker(marker);

			// Move the camera to last position with a zoom level
	
				cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(mylat,mylon)).zoom(15).build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			
		

	} catch (Exception e) {
		e.printStackTrace();
	}
    }
public Double mylon,mylat;
    
    public static TextView listner1;
    public void listner()
    {

 	   listner1=new TextView(this);
 	   listner1.addTextChangedListener(new TextWatcher() {
 	   			
 	   			@Override
 	   			public void onTextChanged(CharSequence s, int start, int before, int count) {
 	   				// TODO Auto-generated method stub
 	   				//Toast.makeText(getApplicationContext(), question.getText().toString(),Toast.LENGTH_SHORT).show();
 	   				    
	   		Toast.makeText(getApplicationContext(), listner1.getText(),Toast.LENGTH_SHORT).show();
 	   		   			
	   		
	   		
	   		
	   	
 	   			
 	   			}
 	   			@Override
 	   			public void beforeTextChanged(CharSequence s, int start, int count,
 	   					int after) {
 	   				// TODO Auto-generated method stub
 	   				
 	   			}
 	   			
 	   			@Override
 	   			public void afterTextChanged(Editable s) {
 	   				// TODO Auto-generated method stub
 	   				
 	   			}
 	   		});
    }
    private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * creating random postion around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}
    

   
}