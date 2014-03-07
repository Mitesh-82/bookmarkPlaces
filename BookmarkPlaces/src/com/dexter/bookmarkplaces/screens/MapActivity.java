package com.dexter.bookmarkplaces.screens;

import android.app.Activity;
import android.os.Bundle;

import com.dexter.bookmarkplaces.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity {
	  static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	 private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		   setContentView(R.layout.mapactivity);
		    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
//		    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//		        .title("Hamburg"));
//		    Marker kiel = map.addMarker(new MarkerOptions()
//		        .position(KIEL)
//		        .title("Kiel")
//		        .snippet("Kiel is cool")
//		        .icon(BitmapDescriptorFactory
//		            .fromResource(R.drawable.ic_launcher)));
//
//		    // Move the camera instantly to hamburg with a zoom of 15.
//		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
//
//		    // Zoom in, animating the camera.
//		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}
	

}
