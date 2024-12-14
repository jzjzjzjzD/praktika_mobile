package com.example.warehousemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "warehouse.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_LOCATION + " TEXT)";
        db.execSQL(createTableQuery);
    }
    //Метод для удаления товара
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }
    // Метод для обновления данных товара
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Метод для добавления товара
    public boolean addProduct(String name, int quantity, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_LOCATION, location);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Метод для обновления товара
    public boolean updateProduct(int id, String name, int quantity, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_LOCATION, location);
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    // Метод для обновления количества товара (приёмка/выгрузка)
    public boolean updateQuantity(int id, int change) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_QUANTITY}, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            int newQuantity = currentQuantity + change;
            if (newQuantity < 0) {
                return false; // Количество не может быть отрицательным
            }
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, newQuantity);
            int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            cursor.close();
            return rowsAffected > 0;
        }
        cursor.close();
        return false;
    }

    // Метод для получения всех товаров
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}