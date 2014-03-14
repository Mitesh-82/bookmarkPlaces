package com.dexter.bookmarkplaces.screens;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.dexter.bookmarkplaces.R;
import com.dexter.bookmarkplaces.database.PlacesContract;
import com.dexter.bookmarkplaces.database.PlacesDatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	private Context context;

	MarkerOptions[] loadMapMakers(Context context) {
		SQLiteDatabase placesDatabase;
		MarkerOptions mapMarkerOptions[] = null;

		PlacesDatabaseHelper dbhelper = new PlacesDatabaseHelper(context);
		placesDatabase = dbhelper.getReadableDatabase();

		Cursor cursor = placesDatabase.query(
				PlacesContract.PlacesList.TABLE_NAME, null, null, null, null,
				null, null);

		int rows = cursor.getCount();
		int index = 0;
		while (rows > index) {
			if (mapMarkerOptions == null) {
				mapMarkerOptions = new MarkerOptions[rows];
				cursor.moveToFirst();
			}
			MarkerOptions markerOption = new MarkerOptions();

			double lat, lng;
			String title;

			lat = Double
					.parseDouble(cursor.getString(cursor
							.getColumnIndex(PlacesContract.PlacesList.COLUMN_NAME_LATTITUDE)));
			lng = Double
					.parseDouble(cursor.getString(cursor
							.getColumnIndex(PlacesContract.PlacesList.COLUMN_NAME_LONGITUDE)));
			title = cursor
					.getString(cursor
							.getColumnIndex(PlacesContract.PlacesList.COLUMN_NAME_TITLE));

			LatLng position = new LatLng(lat, lng);

			markerOption.position(position);
			markerOption.title(title);
			mapMarkerOptions[index] = markerOption;

			index++;
			cursor.moveToNext();

		}

		cursor.close();

		return mapMarkerOptions;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;

		setContentView(R.layout.mapactivity);
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		MarkerOptions[] markers = loadMapMakers(context);

		int index = 0;
		if ((markers != null) && (markers.length > 0)) {
			while (index < markers.length) {
				googleMap.addMarker(markers[index]);
				index++;
			}
		}
		
		
		//zoom into last marker if available
		if ((markers != null) && (markers.length > 0)) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers[index -1].getPosition(), 15));
		}

		// Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
		// .title("Hamburg"));
		// Marker kiel = map.addMarker(new MarkerOptions()
		// .position(KIEL)
		// .title("Kiel")
		// .snippet("Kiel is cool")
		// .icon(BitmapDescriptorFactory
		// .fromResource(R.drawable.ic_launcher)));
		//
		// // Move the camera instantly to hamburg with a zoom of 15.
		// map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		//
		// // Zoom in, animating the camera.
		// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

}
