package com.dexter.bookmarkplaces.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.dexter.bookmarkplaces.R;
import com.dexter.bookmarkplaces.database.PlacesContract;
import com.dexter.bookmarkplaces.database.PlacesDatabaseHelper;


public class MainActivity extends Activity implements OnClickListener, LocationListener {

	private LocationManager locationManager;
	private Context context;	
	private Location lastfixLocation;
	private Button btnSubmit;
	private EditText edttextSize, edttextPrice, edttextRemarks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		btnSubmit = (Button) findViewById(R.id.button1);
		edttextSize = (EditText) findViewById(R.id.editText1);
		edttextPrice = (EditText) findViewById(R.id.editText2);
		edttextRemarks = (EditText) findViewById(R.id.editText3);
		
		btnSubmit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		//Check for GPS is enabled or not
		isGpsEnabled();
		startGPS();

	}

	private void startGPS() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

	}

	private void isGpsEnabled() {
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("GPS is not Enabled!\n Go to Settings?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					//					dialog.cancel();
				}
			})

			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Activity activity = (Activity) context;
					activity.finish();
					dialog.cancel();
				}
			})

			.setCancelable(false);

			builder.create().show();
		}
	}

	@Override
	public void onClick(View v) {
		if(v == btnSubmit) {
			if(lastfixLocation != null){
				saveDatatoDB();

				
			}
		}

	}
	
	
	private void saveDatatoDB() {

		Thread thread = new Thread (new Runnable() {
			@Override
			public void run() {
				ContentValues values = new ContentValues();
				values.put(PlacesContract.PlacesList.COLUMN_NAME_PRICE, edttextPrice.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_SIZE, edttextSize.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_REMARKS, edttextRemarks.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_LATTITUDE, Double.toString(lastfixLocation.getLatitude()));
				values.put(PlacesContract.PlacesList.COLUMN_NAME_LONGITUDE, Double.toString(lastfixLocation.getLongitude()));
				values.put(PlacesContract.PlacesList.COLUMN_NAME_ALTITUDE, Double.toString(lastfixLocation.getAltitude()));
				
				PlacesDatabaseHelper dbhelper = new PlacesDatabaseHelper(context);
				SQLiteDatabase db = dbhelper.getWritableDatabase();
				
				edttextPrice.setText("");
				edttextSize.setText("");
				edttextRemarks.setText("");
				
				db.insert(PlacesContract.PlacesList.TABLE_NAME, null, values);
				locationManager.removeUpdates((LocationListener) context);
				lastfixLocation = null;
			}
		});
		
		thread.start();		
	}
	
	

	@Override
	public void onLocationChanged(Location location) {
		lastfixLocation = location;
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
