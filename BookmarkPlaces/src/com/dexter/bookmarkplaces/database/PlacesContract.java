package com.dexter.bookmarkplaces.database;

import android.provider.BaseColumns;

public class PlacesContract {
	
	/* Inner Class that defines Table contents */
	public static abstract class PlacesList implements BaseColumns {
		public static final String TABLE_NAME = "PlacesList";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_LATTITUDE = "lat";
		public static final String COLUMN_NAME_LONGITUDE = "long";
		public static final String COLUMN_NAME_ALTITUDE = "altitude";
		public static final String COLUMN_NAME_SIZE = "size";
		public static final String COLUMN_NAME_PRICE = "price";
		public static final String COLUMN_NAME_REMARKS = "remarks";
	}
	

}
