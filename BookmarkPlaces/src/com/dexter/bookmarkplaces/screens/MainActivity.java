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

/**
 * This class takes care of capturing location data from GPS and its relevant
 * data provided by user
 * 
 * @author mitesh.patel
 * 
 */
public class MainActivity extends Activity implements OnClickListener,
		LocationListener {

	/**
	 * Location Manager Object
	 */
	private LocationManager locationManager;

	/**
	 * context object to hold the context
	 */
	private Context context;

	/**
	 * Location object to hold the last fix location
	 */
	private Location lastfixLocation;

	/**
	 * Submit button object
	 */
	private Button btnSubmit;

	/**
	 * edit text object for getting price size and remarks
	 */
	private EditText edttextTitle, edttextSize, edttextPrice, edttextRemarks;

	private Button btnMapView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		btnSubmit = (Button) findViewById(R.id.buttonSave);
		edttextTitle = (EditText) findViewById(R.id.editTextTitle);
		edttextSize = (EditText) findViewById(R.id.editTextSize);
		edttextPrice = (EditText) findViewById(R.id.editTextPrice);
		edttextRemarks = (EditText) findViewById(R.id.editTextRemarks);
		btnMapView = (Button) findViewById(R.id.buttonMapView);

		btnSubmit.setOnClickListener(this);
		btnMapView.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		// Check for GPS is enabled or not
		isGpsEnabled();
		startGPS();

	}

	/**
	 * Starts GPS
	 */
	private void startGPS() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);

	}

	/**
	 * Check whether GPS is enabled. If GPS is not enabled, shows a popup to
	 * allow user to enable GPS
	 */
	private void isGpsEnabled() {
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("GPS is not Enabled!\n Go to Settings?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									startActivity(new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									// dialog.cancel();
								}
							})

					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Activity activity = (Activity) context;
									activity.finish();
									dialog.cancel();
								}
							})

					.setCancelable(false);

			builder.create().show();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (v == btnSubmit) {
			if (lastfixLocation != null) {
				saveDatatoDB();
			}
		} else if (v == btnMapView) {
			Intent intent = new Intent(this,
					com.dexter.bookmarkplaces.screens.MapActivity.class);

			startActivity(intent);
		}
	}

	/**
	 * Save User input data along with user data to PlacesList DB using DB
	 * helper
	 */
	private void saveDatatoDB() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ContentValues values = new ContentValues();
				values.put(PlacesContract.PlacesList.COLUMN_NAME_TITLE,
						edttextTitle.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_PRICE,
						edttextPrice.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_SIZE,
						edttextSize.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_REMARKS,
						edttextRemarks.getText().toString());
				values.put(PlacesContract.PlacesList.COLUMN_NAME_LATTITUDE,
						Double.toString(lastfixLocation.getLatitude()));
				values.put(PlacesContract.PlacesList.COLUMN_NAME_LONGITUDE,
						Double.toString(lastfixLocation.getLongitude()));
				values.put(PlacesContract.PlacesList.COLUMN_NAME_ALTITUDE,
						Double.toString(lastfixLocation.getAltitude()));

				PlacesDatabaseHelper dbhelper = new PlacesDatabaseHelper(
						context);
				SQLiteDatabase db = dbhelper.getWritableDatabase();

				// edttextPrice.setText("");
				// edttextSize.setText("");
				// edttextRemarks.setText("");

				db.insert(PlacesContract.PlacesList.TABLE_NAME, null, values);
				locationManager.removeUpdates((LocationListener) context);
				lastfixLocation = null;
			}
		});

		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location.
	 * Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		lastfixLocation = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
