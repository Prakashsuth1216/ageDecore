package com.agedecor.interiordesign.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "age_decor_vault.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Inquiries
    private static final String TABLE_INQUIRIES = "user_inquiries";
    private static final String COL_INQ_ID = "id";
    private static final String COL_CLIENT_NAME = "client_name";
    private static final String COL_PHONE = "phone";
    private static final String COL_EMAIL = "email";
    private static final String COL_PROJECT_TITLE = "project_title";
    private static final String COL_LOCALITY = "locality";
    private static final String COL_PROP_TYPE = "property_type";
    private static final String COL_CARPET_AREA = "carpet_area";
    private static final String COL_BUDGET = "budget";
    private static final String COL_SPECIAL_NOTES = "special_notes";
    private static final String COL_TIMESTAMP = "timestamp";
    private static final String COL_STATUS = "status";

    // Table: Favorites
    private static final String TABLE_FAVORITES = "user_favorites";
    private static final String COL_FAV_ID = "id";
    private static final String COL_PROJECT_ID = "project_id";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createInquiriesTable = "CREATE TABLE " + TABLE_INQUIRIES + " (" +
                COL_INQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CLIENT_NAME + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PROJECT_TITLE + " TEXT, " +
                COL_LOCALITY + " TEXT, " +
                COL_PROP_TYPE + " TEXT, " +
                COL_CARPET_AREA + " TEXT, " +
                COL_BUDGET + " TEXT, " +
                COL_SPECIAL_NOTES + " TEXT, " +
                COL_TIMESTAMP + " TEXT, " +
                COL_STATUS + " TEXT)";

        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COL_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PROJECT_ID + " INTEGER UNIQUE)";

        db.execSQL(createInquiriesTable);
        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUIRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // --- INQUIRY OPERATIONS ---
    public long insertInquiry(InquiryModel inquiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_CLIENT_NAME, inquiry.getClientName());
        cv.put(COL_PHONE, inquiry.getPhone());
        cv.put(COL_EMAIL, inquiry.getEmail());
        cv.put(COL_PROJECT_TITLE, inquiry.getProjectTitle());
        cv.put(COL_LOCALITY, inquiry.getMumbaiLocality());
        cv.put(COL_PROP_TYPE, inquiry.getPropertyType());
        cv.put(COL_CARPET_AREA, inquiry.getCarpetArea());
        cv.put(COL_BUDGET, inquiry.getBudgetRange());
        cv.put(COL_SPECIAL_NOTES, inquiry.getSpecialRequirements());
        cv.put(COL_TIMESTAMP, inquiry.getTimestamp());
        cv.put(COL_STATUS, inquiry.getStatus());
        long id = db.insert(TABLE_INQUIRIES, null, cv);
        return id;
    }

    public List<InquiryModel> getAllInquiries() {
        List<InquiryModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INQUIRIES, null, null, null, null, null, COL_INQ_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_INQ_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_CLIENT_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROJECT_TITLE));
                String locality = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCALITY));
                String propType = cursor.getString(cursor.getColumnIndexOrThrow(COL_PROP_TYPE));
                String carpet = cursor.getString(cursor.getColumnIndexOrThrow(COL_CARPET_AREA));
                String budget = cursor.getString(cursor.getColumnIndexOrThrow(COL_BUDGET));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_SPECIAL_NOTES));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIMESTAMP));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));

                list.add(new InquiryModel(id, name, phone, email, title, locality, propType, carpet, budget, notes, time, status));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public boolean deleteInquiry(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_INQUIRIES, COL_INQ_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    // --- FAVORITES OPERATIONS ---
    public boolean toggleFavorite(int projectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isFavorite(projectId)) {
            db.delete(TABLE_FAVORITES, COL_PROJECT_ID + "=?", new String[]{String.valueOf(projectId)});
            return false;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COL_PROJECT_ID, projectId);
            db.insertWithOnConflict(TABLE_FAVORITES, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            return true;
        }
    }

    public boolean isFavorite(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_FAVORITES, null, COL_PROJECT_ID + "=?", new String[]{String.valueOf(projectId)}, null, null, null);
        boolean exists = (c != null && c.getCount() > 0);
        if (c != null) c.close();
        return exists;
    }

    public List<Integer> getAllFavoriteIds() {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_FAVORITES, new String[]{COL_PROJECT_ID}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                list.add(c.getInt(c.getColumnIndexOrThrow(COL_PROJECT_ID)));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    // --- USER DATA SOVEREIGNTY / CONTROL ACTIONS ---
    public String exportAllUserDataAsJson() {
        try {
            JSONObject root = new JSONObject();
            root.put("app", "Age Décor Interior Design");
            root.put("location", "Mumbai, India");
            root.put("export_type", "User Data Portability Archive");
            root.put("export_timestamp", System.currentTimeMillis());

            // Export Inquiries
            JSONArray inquiriesArray = new JSONArray();
            List<InquiryModel> inqs = getAllInquiries();
            for (InquiryModel inq : inqs) {
                JSONObject obj = new JSONObject();
                obj.put("id", inq.getId());
                obj.put("clientName", inq.getClientName());
                obj.put("phone", inq.getPhone());
                obj.put("email", inq.getEmail());
                obj.put("projectTitle", inq.getProjectTitle());
                obj.put("locality", inq.getMumbaiLocality());
                obj.put("propertyType", inq.getPropertyType());
                obj.put("carpetArea", inq.getCarpetArea());
                obj.put("budgetRange", inq.getBudgetRange());
                obj.put("specialRequirements", inq.getSpecialRequirements());
                obj.put("timestamp", inq.getTimestamp());
                inquiriesArray.put(obj);
            }
            root.put("inquiries", inquiriesArray);

            // Export Favorites
            JSONArray favArray = new JSONArray();
            for (Integer favId : getAllFavoriteIds()) {
                favArray.put(favId);
            }
            root.put("favorite_project_ids", favArray);

            return root.toString(2);
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    public void wipeAllUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INQUIRIES, null, null);
        db.delete(TABLE_FAVORITES, null, null);
    }
}
