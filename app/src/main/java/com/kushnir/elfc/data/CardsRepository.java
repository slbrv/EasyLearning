package com.kushnir.elfc.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kushnir.elfc.pojo.CardInfo;

import java.util.ArrayList;

public class CardsRepository extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EasyLearning.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_LANG_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + CardsContract.LangEntry.TABLE_NAME + " (" +
                    CardsContract.LangEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CardsContract.LangEntry.COLUMN_NAME_LANG + " TEXT)";

    public static final String SQL_CREATE_SUBJECT_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + CardsContract.SubjectEntry.TABLE_NAME + " (" +
                    CardsContract.SubjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + " INTEGER, " +
                    CardsContract.SubjectEntry.COLUMN_NAME_SUBJECT + " TEXT, " +
                    "FOREIGN KEY(" + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + ") REFERENCES " +
                    CardsContract.LangEntry.TABLE_NAME + "(" + CardsContract.LangEntry._ID + "))";

    public static final String SQL_CREATE_CARD_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + CardsContract.CardEntry.TABLE_NAME + " (" +
                    CardsContract.CardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + " INTEGER, " +
                    CardsContract.CardEntry.COLUMN_NAME_WORD + " TEXT, " +
                    CardsContract.CardEntry.COLUMN_NAME_TRANSCRIPTION + " TEXT, " +
                    CardsContract.CardEntry.COLUMN_NAME_IMAGE_URI + " TEXT, " +
                    "FOREIGN KEY(" + CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + ") REFERENCES " +
                    CardsContract.SubjectEntry.TABLE_NAME + "(" + CardsContract.SubjectEntry._ID + "))";

    public CardsRepository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("APP", "SQL LANG: " + SQL_CREATE_LANG_ENTRIES);
        Log.i("APP", "SQL SUBJECT: " + SQL_CREATE_SUBJECT_ENTRIES);
        Log.i("APP", "SQL CARD: " + SQL_CREATE_CARD_ENTRIES);

        db.execSQL(SQL_CREATE_LANG_ENTRIES);
        db.execSQL(SQL_CREATE_SUBJECT_ENTRIES);
        db.execSQL(SQL_CREATE_CARD_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getLangId(String lang) {
        SQLiteDatabase db = getReadableDatabase();
        String getLangIdSql = "SELECT * FROM " + CardsContract.LangEntry.TABLE_NAME
                + " WHERE " + CardsContract.LangEntry.COLUMN_NAME_LANG + " = '" + lang + "'";
        Log.i("APP", "Get lang id: " + getLangIdSql);
        Cursor langIdQuery = db.rawQuery(getLangIdSql, null);
        int id = -1;
        if(langIdQuery.moveToNext()) {
            id = langIdQuery.getInt(0);
        }
        langIdQuery.close();
        db.close();

        return id;
    }

    public int getSubjectId(String lang, String subject) {
        int langId = getLangId(lang);

        SQLiteDatabase db = getReadableDatabase();
        String getSubjectIdSql = "SELECT * FROM " + CardsContract.SubjectEntry.TABLE_NAME +
                " WHERE " + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + " = " + langId +
                " AND " + CardsContract.SubjectEntry.COLUMN_NAME_SUBJECT + " = '" + subject + "'";
        Log.i("APP", "Get subject id: " + getSubjectIdSql);
        Cursor subjectIdQuery = db.rawQuery(getSubjectIdSql, null);
        int id = -1;
        if(subjectIdQuery.moveToNext()) {
            id = subjectIdQuery.getInt(0);
        }
        subjectIdQuery.close();
        db.close();

        return id;
    }

    public int getSubjectCount(String lang) {
        int id = getLangId(lang);

        SQLiteDatabase db = getReadableDatabase();
        String selSubjectSQL = "SELECT * FROM " + CardsContract.SubjectEntry.TABLE_NAME
                + " WHERE " + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + " = " + id;
        Cursor subjectQuery = db.rawQuery(selSubjectSQL, null);
        int count = subjectQuery.getCount();
        subjectQuery.close();
        db.close();

        return count;
    }

    public int getCardCount(String lang, String subject) {
        int id = getSubjectId(lang, subject);

        SQLiteDatabase db = getReadableDatabase();
        String selCardSql = "SELECT * FROM " + CardsContract.CardEntry.TABLE_NAME
                + " WHERE " + CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + " = " + id;
        Cursor cardQuery = db.rawQuery(selCardSql, null);
        int count = cardQuery.getCount();
        cardQuery.close();
        db.close();

        return count;
    }

    public boolean insertLang(String lang) {
        SQLiteDatabase db = getReadableDatabase();
        String selQuerySql = "SELECT * FROM " + CardsContract.LangEntry.TABLE_NAME +
                " WHERE " + CardsContract.LangEntry.COLUMN_NAME_LANG + " = '" + lang + "'";
        Log.i("APP", "Select query: " + selQuerySql);
        Cursor selQuery = db.rawQuery(selQuerySql, null);
        int count = selQuery.getCount();
        selQuery.close();
        db.close();

        if(count > 0)
            return false;

        db = getWritableDatabase();
        String istQuerySql = "INSERT INTO " + CardsContract.LangEntry.TABLE_NAME +
                "(" + CardsContract.LangEntry.COLUMN_NAME_LANG + ") VALUES('" +
                lang + "')";
        Log.i("APP", "Lang insert: " + istQuerySql);
        db.execSQL(istQuerySql);
        db.close();

        return true;
    }

    public boolean insertSubject(String lang, String subject) {
        int id = getLangId(lang);

        SQLiteDatabase db = getReadableDatabase();
        String selQuerySql = "SELECT * FROM " + CardsContract.SubjectEntry.TABLE_NAME +
                " WHERE " + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + " = " + id +
                " AND " + CardsContract.SubjectEntry.COLUMN_NAME_SUBJECT + " = '" + subject + "'";
        Log.i("APP", "Select query: " + selQuerySql);
        Cursor selQuery = db.rawQuery(selQuerySql, null);
        int count = selQuery.getCount();
        selQuery.close();
        db.close();
        if(count > 0)
            return false;

        db = getWritableDatabase();
        String sqlQuery = "INSERT INTO " + CardsContract.SubjectEntry.TABLE_NAME +
                "(" + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + ", "
                    + CardsContract.SubjectEntry.COLUMN_NAME_SUBJECT + ") VALUES(" + id +
                ", '" + subject + "')";
        Log.i("APP", "Subject insert: " + sqlQuery);
        db.execSQL(sqlQuery);
        db.close();

        return true;
    }

    public boolean insertCard(String lang,
                              String subject,
                              CardInfo info) {
        int id = getSubjectId(lang, subject);

        SQLiteDatabase db = getReadableDatabase();
        String selQuerySql = "SELECT * FROM " + CardsContract.CardEntry.TABLE_NAME +
                " WHERE " + CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + " = " + id +
                " AND " + CardsContract.CardEntry.COLUMN_NAME_WORD + " = '" + info.getWord() + "'";
        Log.i("APP", "Select query: " + selQuerySql);
        Cursor selQuery = db.rawQuery(selQuerySql, null);
        int count = selQuery.getCount();
        selQuery.close();
        db.close();
        if(count > 0)
            return false;


        db = getWritableDatabase();
        String sqlQuery = "INSERT INTO " + CardsContract.CardEntry.TABLE_NAME +
                "(" + CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + ", " +
                CardsContract.CardEntry.COLUMN_NAME_WORD + ", " +
                CardsContract.CardEntry.COLUMN_NAME_TRANSCRIPTION + ", " +
                CardsContract.CardEntry.COLUMN_NAME_IMAGE_URI + ") VALUES(" + id +
                ", '" + info.getWord() +
                "', '" + info.getTranscription() +
                "', '" + info.getImageUri() + "')";
        Log.i("APP", "Card insert: " + sqlQuery);
        db.execSQL(sqlQuery);
        db.close();

        return true;
    }

    public ArrayList<String> getLangs() {
        ArrayList<String> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String getLangsSql = "SELECT * FROM " + CardsContract.LangEntry.TABLE_NAME;
        Cursor query = db.rawQuery(getLangsSql, null);
        while(query.moveToNext()) {
            String lang = query.getString(1);
            items.add(lang);
        }
        query.close();
        db.close();

        return items;
    }

    public ArrayList<String> getSubjects(String lang) {
        int langId = getLangId(lang);

        ArrayList<String> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String getSubjectsSql = "SELECT * FROM " + CardsContract.SubjectEntry.TABLE_NAME +
                " WHERE " + CardsContract.SubjectEntry.COLUMN_NAME_LANG_ID + " = " + langId;
        Cursor query = db.rawQuery(getSubjectsSql, null);
        while(query.moveToNext()) {
            String subject = query.getString(2);
            items.add(subject);
        }
        query.close();
        db.close();

        return items;
    }

    public ArrayList<CardInfo> getCards(String lang, String subject) {
        int id = getSubjectId(lang, subject);

        ArrayList<CardInfo> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String getCardsSql = "SELECT * FROM " + CardsContract.CardEntry.TABLE_NAME +
                " WHERE " + CardsContract.CardEntry.COLUMN_NAME_SUBJECT_ID + " = " + id;
        Cursor query = db.rawQuery(getCardsSql, null);
        while(query.moveToNext()) {
            String word = query.getString(2);
            String transcription = query.getString(3);
            String uri = query.getString(4);
            items.add(new CardInfo(word, transcription, uri));
        }
        query.close();
        db.close();

        return items;
    }

    public void dropTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE langs");
        db.execSQL("DROP TABLE subjects");
        db.execSQL("DROP TABLE cards");
        db.close();
    }

    public void dropCards() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE cards");
        db.close();
    }

    public void createCards() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_CREATE_CARD_ENTRIES);
        db.close();
    }
}
