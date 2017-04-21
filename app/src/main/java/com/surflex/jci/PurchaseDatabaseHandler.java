package com.surflex.jci;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class PurchaseDatabaseHandler extends SQLiteOpenHelper {
    String KEY_DATE = "";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "jci";
    private static final String TABLE_PURCHASE= "purchase";
    private static final String KEY_ID = "id";
    private static final String KEY_FORM_NO = "form_no";
    private static final String KEY_BASIS = "basis";
    private static final String KEY_BIN_NO = "bin_no";
    private static final String KEY_JUTE_VARIETY = "jute_variety";
    private static final String KEY_GROSS_QUANTITY = "gross_quantity";
    private static final String KEY_DEDUCTION_QUANTITY = "deduction_quantity";
    private static final String KEY_NET_QUANTITY = "net_quantity";
    private static final String KEY_AVG_NAME = "avgRate";
    private static final String KEY_EST_GRADE_COMP = "est_grade_comp";
    private static final String KEY_ALL_GRADE = "all_grade";
    private static final String KEY_IMAGE_PATH = "image_path";
   private static final String KEY_ENTRY_DATE = "entry_date";



    public PurchaseDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PURCHASE_TABLE = "CREATE TABLE " + TABLE_PURCHASE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FORM_NO + " TEXT,"
                + KEY_BASIS + " TEXT," + KEY_BIN_NO + " TEXT," + KEY_JUTE_VARIETY + " TEXT,"
                + KEY_GROSS_QUANTITY + " TEXT," + KEY_DEDUCTION_QUANTITY + " TEXT," + KEY_NET_QUANTITY + " TEXT,"
                + KEY_AVG_NAME + " TEXT," + KEY_EST_GRADE_COMP + " TEXT,"
                + KEY_ALL_GRADE + " TEXT,"+ KEY_IMAGE_PATH + " TEXT,"+KEY_ENTRY_DATE + " TEXT" +")";
        db.execSQL(CREATE_PURCHASE_TABLE);
        Log.e("MyDB","all is well create");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE);

        // Create tables again
        onCreate(db);
        Log.e("MyDB","all is well upgrade");
    }

    // code to add the new Purchase
    void addPurchase(PurchaseModel purchaseModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FORM_NO, purchaseModel.getFormNo());//form no
        values.put(KEY_BASIS, purchaseModel.getBasis());
        values.put(KEY_BIN_NO, purchaseModel.getBinNo());
        values.put(KEY_JUTE_VARIETY, purchaseModel.getJuteVariety());
        values.put(KEY_GROSS_QUANTITY, purchaseModel.getGrossQuantity());
        values.put(KEY_DEDUCTION_QUANTITY, purchaseModel.getDeductionQuantity());
        values.put(KEY_NET_QUANTITY, purchaseModel.getNetQuantity());
        values.put(KEY_AVG_NAME, purchaseModel.getAvgRate());
        values.put(KEY_EST_GRADE_COMP, purchaseModel.getEstGradeComp());
        values.put(KEY_ALL_GRADE, purchaseModel.getAllGrade());
        values.put(KEY_IMAGE_PATH, purchaseModel.getImgPath());
        values.put(KEY_ENTRY_DATE, purchaseModel.getEntryDate());




        // Inserting Row
        db.insert(TABLE_PURCHASE, null, values);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection

    }

    // code to get the single contact
    PurchaseModel getSinglePurchaseData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PURCHASE, new String[] { KEY_FORM_NO ,KEY_BASIS , KEY_BIN_NO  , KEY_JUTE_VARIETY ,
                KEY_GROSS_QUANTITY , KEY_DEDUCTION_QUANTITY  , KEY_NET_QUANTITY,
                KEY_AVG_NAME  , KEY_EST_GRADE_COMP , KEY_ALL_GRADE, KEY_IMAGE_PATH, KEY_ENTRY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PurchaseModel singleData = new PurchaseModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),
                cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12));
        // return contact
        return singleData;
    }

    // code to get all contacts in a list view
    public List<PurchaseModel> getAllPurchases() {
        List<PurchaseModel> PurchaseList = new ArrayList<PurchaseModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PURCHASE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PurchaseModel purchaseModel = new PurchaseModel();

                purchaseModel.setId(Integer.parseInt(cursor.getString(0)));
                purchaseModel.setFormNo(cursor.getString(1));
                purchaseModel.setBasis(cursor.getString(2));
                purchaseModel.setBinNo(cursor.getString(3));
                purchaseModel.setJuteVariety(cursor.getString(4));
                purchaseModel.setGrossQuantity(cursor.getString(5));
                purchaseModel.setDeductionQuantity(cursor.getString(6));
                purchaseModel.setNetQuantity(cursor.getString(7));
                purchaseModel.setAvgRate(cursor.getString(8));
                purchaseModel.setEstGradeComp(cursor.getString(9));
                purchaseModel.setEstGradeComp(cursor.getString(10));
                purchaseModel.setImgPath(cursor.getString(11));
                purchaseModel.setEntryDate(cursor.getString(12));

                // Adding Purchase to list
                PurchaseList.add(purchaseModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return PurchaseList;
    }

    // code to update the single singleData
    public int updateSinglePurchaseData(PurchaseModel purchaseModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FORM_NO, purchaseModel.getFormNo());
        values.put(KEY_BASIS, purchaseModel.getBasis());
        values.put(KEY_BIN_NO, purchaseModel.getBinNo());
        values.put(KEY_JUTE_VARIETY, purchaseModel.getJuteVariety());
        values.put(KEY_GROSS_QUANTITY, purchaseModel.getGrossQuantity());
        values.put(KEY_DEDUCTION_QUANTITY, purchaseModel.getDeductionQuantity());
        values.put(KEY_NET_QUANTITY, purchaseModel.getNetQuantity());
        values.put(KEY_AVG_NAME, purchaseModel.getAvgRate());
        values.put(KEY_EST_GRADE_COMP, purchaseModel.getEstGradeComp());
        values.put(KEY_ALL_GRADE, purchaseModel.getAllGrade());
        values.put(KEY_IMAGE_PATH, purchaseModel.getImgPath());
        values.put(KEY_ENTRY_DATE, purchaseModel.getEntryDate());

        // updating row
        return db.update(TABLE_PURCHASE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(purchaseModel.getId()) });
    }

    // Deleting single contact
    public void deleteContact(PurchaseModel purchaseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PURCHASE, KEY_ID + " = ?",
                new String[] { String.valueOf(purchaseModel.getId()) });
        db.close();
    }

    // Getting contacts Count
    public int getPurchasesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PURCHASE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}