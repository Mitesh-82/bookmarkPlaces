package com.dexter.bookmarkplaces.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlacesDatabaseHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "placesdatabase.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_PLACESLIST =
			"CREATE TABLE " + PlacesContract.PlacesList.TABLE_NAME+ " (" +

        PlacesContract.PlacesList.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_LATTITUDE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_ALTITUDE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_SIZE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
        PlacesContract.PlacesList.COLUMN_NAME_REMARKS + TEXT_TYPE +         
        // Any other options for the CREATE command
        " )";

	private static final String SQL_DELETE_PLACESLIST =
			"DROP TABLE IF EXISTS " + PlacesContract.PlacesList.TABLE_NAME;

	public PlacesDatabaseHelper(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PLACESLIST);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
