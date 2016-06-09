package com.alecjarett.ballardcs.rpgu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Alec Yeasting on 6/9/2016.
 */
public class AchievementsDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "achievementsDB.db";
    public static final String TABLE_ACHIEVEMENTS = "achievements";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUANTITYTODO = "quantitytodo";
    public static final String COLUMN_QUANTITYDONE = "quantitydone";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_XP = "xp";
    public static final String COLUMN_CATEGORYLABEL = "categorylabel";
    public static final String COLUMN_STRINGID = "stringid";

    public AchievementsDBHandler(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACHIEVEMENTS_TABLE = "CREATE TABLE " + TABLE_ACHIEVEMENTS
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_QUANTITYTODO
                + " INTEGER," + COLUMN_QUANTITYDONE + " INTEGER," + COLUMN_LABEL
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_XP
                + " INTEGER," + COLUMN_CATEGORYLABEL + " TEXT," + COLUMN_STRINGID
                + " TEXT" + ")";
        db.execSQL(CREATE_ACHIEVEMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHIEVEMENTS);

        onCreate(db);
    }

    public ArrayList<RPGuAchievement> getAllAchievements() {
        ArrayList<RPGuAchievement> achievements = new ArrayList<RPGuAchievement>();

        String selectQuery = "SELECT * FROM " + TABLE_ACHIEVEMENTS;

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
                        String stringId = cursor.getString(7);

                        RPGuAchievement achievement =
                                new RPGuAchievement(quantityToDo,
                                        quantityDone,
                                        label,
                                        description,
                                        xp,
                                        categoryLabel,
                                        stringId);

                        achievements.add(achievement);
                    } while(cursor.moveToNext());
                }
            } finally {
                try { cursor.close(); } catch(Exception e) {}
            }
        } finally {
            try { db.close(); }catch(Exception e) {}
        }

        return achievements;
    }

    public void addAchievement(RPGuAchievement achievement) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_QUANTITYTODO, achievement.getQuantityToDo());
        values.put(COLUMN_QUANTITYDONE, achievement.getQuantityDone());
        values.put(COLUMN_LABEL, achievement.getLabel());
        values.put(COLUMN_DESCRIPTION, achievement.getDescription());
        values.put(COLUMN_XP, achievement.getXp());
        values.put(COLUMN_CATEGORYLABEL, achievement.getCategoryLabel());
        values.put(COLUMN_STRINGID, achievement.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ACHIEVEMENTS, null, values);
        db.close();
    }

    public void updateAchievementProgress(String id, int quantityDone) {
        SQLiteDatabase db = this.getWritableDatabase();

        String strFilter = COLUMN_STRINGID + " = \"" + id + "\"";
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUANTITYDONE, quantityDone);
        db.update(TABLE_ACHIEVEMENTS, args, strFilter, null);

        db.close();
    }

    public RPGuAchievement findAchievement(String id) {
        String findQuery = "SELECT * FROM " + TABLE_ACHIEVEMENTS
                + " WHERE " + COLUMN_STRINGID + " = \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(findQuery, null);

        RPGuAchievement achievement;

        if(cursor.moveToFirst()){
            cursor.moveToFirst();

            int quantityToDo = cursor.getInt(1);
            int quantityDone = cursor.getInt(2);
            String label = cursor.getString(3);
            String description = cursor.getString(4);
            int xp = cursor.getInt(5);
            String categoryLabel = cursor.getString(6);
            String stringId = cursor.getString(7);

            achievement = new RPGuAchievement(quantityToDo,
                    quantityDone,
                    label,
                    description,
                    xp,
                    categoryLabel,
                    stringId);
            cursor.close();
        } else {
            achievement = null;
        }
        db.close();
        return achievement;
    }
}
