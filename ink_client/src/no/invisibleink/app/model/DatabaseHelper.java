package no.invisibleink.app.model;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    @SuppressWarnings("unused")
	private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "inkCliet";
 
    // Table Names
    private static final String TABLE_INKS = "inks_message";
 
    // Common column names
    private static final String KEY_ID = "id";
 
    // INKS Table - column names
	private static final String KEY_EXPIRES = "expires";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_LOCATION_LAT = "location_lat";
    private static final String KEY_LOCATION_LON = "location_lon";
    private static final String KEY_RADIUS = "radius";
 
    // Table Create Statements
    // Inks table create statement
    private static final String CREATE_TABLE_INKS = "CREATE TABLE "
            + TABLE_INKS + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY,"
    		+ KEY_EXPIRES + " DATE,"
    		+ KEY_USER_ID + " INTEGER,"
    		+ KEY_TEXT + " TEXT,"
    		+ KEY_LOCATION_LAT + " DOUBLE,"
    		+ KEY_LOCATION_LON + " DOUBLE,"
            + KEY_RADIUS + " DOUBLE)";
  
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
/*    @Override
    public void onOpen(SQLiteDatabase db) {
        int iversion = db.getVersion();
        if (iversion == 1) {
            onUpgrade(db, 1, 2);
        }
    }    
  */  
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.w(this.getClass().getName(), "create db");
        // creating required tables
        db.execSQL(CREATE_TABLE_INKS);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INKS);
 
        // create new tables
        onCreate(db);
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }    
    
	/**
	 * Inserts an Ink the database.
	 * 
	 * @param ink
	 *            Ink
	 * @return True, if insert was successful
	 */
    public boolean insertInk(Ink ink) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_ID, ink.getID());
        values.put(KEY_TEXT, ink.getMessage());
        values.put(KEY_RADIUS, ink.getRadius());
        values.put(KEY_LOCATION_LAT, ink.getLocation().getLatitude());
        values.put(KEY_LOCATION_LON, ink.getLocation().getLongitude());
        
        // insert row, returns: the row ID of the newly inserted row, or -1 if an error occurred 
        try {
            long row_id = db.insert(TABLE_INKS, null, values);
            return row_id > 0;
        } catch (Exception e) {
        	Log.e(this.getClass().getName(), "SQLiteConstraintException, can't insert ink");
        }

        return false;

    }
    

    /**
     * Get all stored Inks.
     * 
     * @return All stored Inks
     */
    public InkList getInkList() {
        InkList inkList = new InkList();
        String selectQuery = "SELECT  * FROM " + TABLE_INKS;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        Location location = new Location("");
        if (c.moveToFirst()) {
            do {
            	int id = c.getInt((c.getColumnIndex(KEY_ID)));
            	location.setLatitude(c.getDouble(c.getColumnIndex(KEY_LOCATION_LAT)));
            	location.setLongitude(c.getDouble(c.getColumnIndex(KEY_LOCATION_LON)));
            	double radius = c.getDouble(c.getColumnIndex(KEY_RADIUS));
            	String title = "";
            	String message = c.getString(c.getColumnIndex(KEY_TEXT));
            	String author = "";
            	Date date = null;
            	Ink ink = new Ink(id, location, radius, title, message, author, date);     
            	inkList.add(ink);
            } while (c.moveToNext());
        }
     
        return inkList;
    }
    

    public void clearTableInk() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INKS, KEY_ID + " >= ?", new String[] { String.valueOf(0) });
    }
    
    /**
     * Delete specific Iink.
     * 
     * @param ink_id Delete an Ink
     */
    public void deleteInk(int ink_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INKS, KEY_ID + " = ?",
                new String[] { String.valueOf(ink_id) });
    }    
}
