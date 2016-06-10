package com.alecjarett.ballardcs.rpgu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Alec Yeasting on 6/8/2016.
 */
public class ActivitiesDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "activitiesDB.db";
    public static final String TABLE_DAILIES = "dailies";
    public static final String TABLE_WEEKLIES = "weeklies";
    public static final String TABLE_MONTHLIES = "monthlies";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUANTITYTODO = "quantitytodo";
    public static final String COLUMN_QUANTITYDONE = "quantitydone";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_XP = "xp";
    public static final String COLUMN_CATEGORYLABEL = "categorylabel";
    public static final String COLUMN_STRINGID = "stringid";

    public ActivitiesDBHandler(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DAILIES_TABLE = "CREATE TABLE " + TABLE_DAILIES
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_QUANTITYTODO
                + " INTEGER," + COLUMN_QUANTITYDONE + " INTEGER," + COLUMN_LABEL
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_XP
                + " INTEGER," + COLUMN_CATEGORYLABEL + " TEXT," + COLUMN_STRINGID
                + " TEXT" + ")";
        db.execSQL(CREATE_DAILIES_TABLE);
        String CREATE_WEEKLIES_TABLE = "CREATE TABLE " + TABLE_WEEKLIES
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_QUANTITYTODO
                + " INTEGER," + COLUMN_QUANTITYDONE + " INTEGER," + COLUMN_LABEL
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_XP
                + " INTEGER," + COLUMN_CATEGORYLABEL + " TEXT," + COLUMN_STRINGID
                + " TEXT" + ")";
        db.execSQL(CREATE_WEEKLIES_TABLE);
        String CREATE_MONTHLIES_TABLE = "CREATE TABLE " + TABLE_MONTHLIES
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_QUANTITYTODO
                + " INTEGER," + COLUMN_QUANTITYDONE + " INTEGER," + COLUMN_LABEL
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_XP
                + " INTEGER," + COLUMN_CATEGORYLABEL + " TEXT," + COLUMN_STRINGID
                + " TEXT" + ")";
        db.execSQL(CREATE_MONTHLIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEKLIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLIES);

        onCreate(db);
    }

    public ArrayList<RPGuActivity> getAllDailies() {
        return getActivities(ActivitiesAdapter.ActivityType.Daily);
    }

    public ArrayList<RPGuActivity> getAllWeeklies() {
        return getActivities(ActivitiesAdapter.ActivityType.Weekly);
    }

    public ArrayList<RPGuActivity> getAllMonthlies() {
        return getActivities(ActivitiesAdapter.ActivityType.Monthly);
    }

    private ArrayList<RPGuActivity> getActivities(ActivitiesAdapter.ActivityType type) {
        String tableName = TABLE_DAILIES;
        switch (type) {
            case Daily:
                tableName = TABLE_DAILIES;
                break;
            case Weekly:
                tableName = TABLE_WEEKLIES;
                break;
            case Monthly:
                tableName = TABLE_MONTHLIES;
                break;
        }

        ArrayList<RPGuActivity> activities = new ArrayList<RPGuActivity>();

        String selectQuery = "SELECT * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if(cursor.moveToFirst()){
                    do {
                        int quantityToDo = cursor.getInt(1);
                        int quantityDone = cursor.getInt(2);
                        String label = cursor.getString(3);
                        String description = cursor.getString(4);
                        int xp = cursor.getInt(5);
                        String categoryLabel = cursor.getString(6);
                        ActivitiesAdapter.ActivityType activityType = type;
                        String stringID = cursor.getString(7);

                        RPGuActivity activity =
                                new RPGuActivity(quantityToDo,
                                        quantityDone,
                                        label,
                                        description,
                                        xp,
                                        categoryLabel,
                                        activityType,
                                        stringID);

                        activities.add(activity);
                    } while(cursor.moveToNext());
                }
            } finally {
                try { cursor.close(); }catch (Exception e){}
            }
        } finally {
            try { db.close(); }catch (Exception e){}
        }

        return activities;
    }

    public void addDaily(RPGuActivity activity) {
        addActivity(activity, ActivitiesAdapter.ActivityType.Daily);
    }

    public void addWeekly(RPGuActivity activity) {
        addActivity(activity, ActivitiesAdapter.ActivityType.Weekly);
    }

    public void addMonthly(RPGuActivity activity) {
        addActivity(activity, ActivitiesAdapter.ActivityType.Monthly);
    }

    private void addActivity(RPGuActivity activity, ActivitiesAdapter.ActivityType type) {
        String tableName = TABLE_DAILIES;
        switch (type) {
            case Daily:
                tableName = TABLE_DAILIES;
                break;
            case Weekly:
                tableName = TABLE_WEEKLIES;
                break;
            case Monthly:
                tableName = TABLE_MONTHLIES;
                break;
        }

        ContentValues values = new ContentValues();

        values.put(COLUMN_QUANTITYTODO, activity.getQuantityToDo());
        values.put(COLUMN_QUANTITYDONE, activity.getQuantityDone());
        values.put(COLUMN_LABEL, activity.getLabel());
        values.put(COLUMN_DESCRIPTION, activity.getDescription());
        values.put(COLUMN_XP, activity.getXp());
        values.put(COLUMN_CATEGORYLABEL, activity.getCategoryLabel());
        values.put(COLUMN_STRINGID, activity.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(tableName, null, values);
        db.close();
    }

    public void updateDaily(String id, int quantityDone) {
        updateActivityCompletion(id, quantityDone, ActivitiesAdapter.ActivityType.Daily);
    }

    public void updateWeekly(String id, int quantityDone) {
        updateActivityCompletion(id, quantityDone, ActivitiesAdapter.ActivityType.Weekly);
    }

    public void updateMonthly(String id, int quantityDone) {
        updateActivityCompletion(id, quantityDone, ActivitiesAdapter.ActivityType.Monthly);
    }

    private void updateActivityCompletion (String id, int quantityDone, ActivitiesAdapter.ActivityType type){
        String tableName = TABLE_DAILIES;
        switch (type) {
            case Daily:
                tableName = TABLE_DAILIES;
                break;
            case Weekly:
                tableName = TABLE_WEEKLIES;
                break;
            case Monthly:
                tableName = TABLE_MONTHLIES;
                break;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        String strFilter = COLUMN_STRINGID + " = \"" + id + "\"";
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUANTITYDONE, quantityDone);
        db.update(tableName, args, strFilter, null);

        db.close();
    }

    public RPGuActivity findDaily(String id){
        return findActivity(id, ActivitiesAdapter.ActivityType.Daily);
    }

    public RPGuActivity findWeekly(String id){
        return findActivity(id, ActivitiesAdapter.ActivityType.Weekly);
    }

    public RPGuActivity findMonthly(String id){
        return findActivity(id, ActivitiesAdapter.ActivityType.Monthly);
    }

    private RPGuActivity findActivity(String id, ActivitiesAdapter.ActivityType type) {
        String tableName = TABLE_DAILIES;
        switch (type) {
            case Daily:
                tableName = TABLE_DAILIES;
                break;
            case Weekly:
                tableName = TABLE_WEEKLIES;
                break;
            case Monthly:
                tableName = TABLE_MONTHLIES;
                break;
        }

        String query = "Select * FROM " + tableName
                + " WHERE " + COLUMN_STRINGID + " = \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        RPGuActivity activity;

        if(cursor.moveToFirst()){
            cursor.moveToFirst();

            int quantityToDo = cursor.getInt(1);
            int quantityDone = cursor.getInt(2);
            String label = cursor.getString(3);
            String description = cursor.getString(4);
            int xp = cursor.getInt(5);
            String categoryLabel = cursor.getString(6);
            ActivitiesAdapter.ActivityType activityType = type;
            String stringID = cursor.getString(7);

            activity = new RPGuActivity(quantityToDo,
                    quantityDone,
                    label,
                    description,
                    xp,
                    categoryLabel,
                    activityType,
                    stringID);
            cursor.close();
        } else {
            activity = null;
        }
        db.close();
        return activity;
    }

    public boolean deleteDaily(String id) {
        return deleteActivity(id, ActivitiesAdapter.ActivityType.Daily);
    }

    public boolean deleteWeekly(String id) {
        return deleteActivity(id, ActivitiesAdapter.ActivityType.Weekly);
    }

    public boolean deleteMonthly(String id) {
        return deleteActivity(id, ActivitiesAdapter.ActivityType.Monthly);
    }

    private boolean deleteActivity(String id, ActivitiesAdapter.ActivityType type) {
        String tableName = TABLE_DAILIES;
        switch (type) {
            case Daily:
                tableName = TABLE_DAILIES;
                break;
            case Weekly:
                tableName = TABLE_WEEKLIES;
                break;
            case Monthly:
                tableName = TABLE_MONTHLIES;
                break;
        }

        boolean result = false;
        String query = "Select * FROM " + tableName + " WHERE " + COLUMN_STRINGID +
                " = \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            db.delete(tableName, COLUMN_ID + " = ?",
                    new String[] {cursor.getString(0)});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}
