package com.dexter.bookmarkplaces.screens;

import com.dexter.bookmarkplaces.R;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;


public class MainActivity extends Activity {

	private LocationManager locationManager;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
		checkGPS();

	}
	
	private void checkGPS() {
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

}
