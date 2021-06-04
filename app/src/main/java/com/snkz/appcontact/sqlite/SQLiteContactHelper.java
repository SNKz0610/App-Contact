package com.snkz.appcontact.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.snkz.appcontact.model.Contact;

import java.util.ArrayList;

public class SQLiteContactHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Contact.db";
    private static final int DATABASE_VERSION = 1;
    private static final String column_id = "id";
    private static final String column_username = "username";
    private static final String column_password = "password";
    private static final String column_avatar = "avatar";
    private static final String column_name = "name";
    private static final String column_birthday = "birthday";
    private static final String column_phone = "phone";
    private static final String column_email = "enail";
    private static final String column_address = "address";

    public SQLiteContactHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE contacts ("
                +column_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +column_username+ " TEXT, "
                +column_password+ " TEXT, "
                +column_avatar+ " BLOB, "
                +column_name+ " TEXT, "
                +column_birthday+ " TEXT, "
                +column_phone+ " TEXT, "
                +column_email+ " TEXT, "
                +column_address+ " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(Contact contact){
        String query = "INSERT INTO contacts"
                + "(" +column_username+ ", " +column_password+ ", "+column_avatar+ ", " +column_name+ ", "
                 +column_birthday+ ", " +column_phone+ ", " +column_email+ ", " +column_address+ ") "
                + " VALUES (?, ? , ?, ? , ? , ?, ?, ?)";
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();

        statement.bindString(1,contact.getUsername());
        statement.bindString(2,contact.getPassword());
        statement.bindBlob(3,contact.getAvatar());
        statement.bindString(4,contact.getName());
        statement.bindString(5,contact.getBirthday());
        statement.bindString(6,contact.getPhone());
        statement.bindString(7,contact.getEmail());
        statement.bindString(8,contact.getAddress());

        statement.executeInsert();
    }

    public void add2(Contact contact){
        String query = "INSERT INTO contacts"
                + "(" +column_avatar+ ", " +column_name+ ", "
                +column_birthday+ ", " +column_phone+ ", " +column_email+ ", " +column_address+ ") "
                + " VALUES (?, ? , ? , ?, ?, ?)";
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();

        statement.bindBlob(1,contact.getAvatar());
        statement.bindString(2,contact.getName());
        statement.bindString(3,contact.getBirthday());
        statement.bindString(4,contact.getPhone());
        statement.bindString(5,contact.getEmail());
        statement.bindString(6,contact.getAddress());

        statement.executeInsert();
    }

    public Contact checkLogin(String username){
        String whereClause = "username=?";
        String[] whereArgs = {String.valueOf(username)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String password = resultSet.getString(2);
            Contact contact = new Contact(id,password);
            return contact;
        }
        return null;
    }

    public String checkPassword(int id){
        String oldPassword = "";
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("contacts", null, whereClause,whereArgs,null,null, null);
        while (resultSet != null && resultSet.moveToNext()){
            oldPassword = resultSet.getString(2);
            return oldPassword;
        }
        resultSet.close();
        return null;
    }


    public boolean checkUsername(String username){
        String whereClause = "username=?";
        String[] whereArgs = {String.valueOf(username)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        String whereClause = "phone=?";
        String[] whereArgs = {String.valueOf(phoneNumber)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean checkNameContact(String nameContact){
        String whereClause = "name=?";
        String[] whereArgs = {String.valueOf(nameContact)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean checkBirhday(String birthday){
        String whereClause = "birthday=?";
        String[] whereArgs = {String.valueOf(birthday)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            return true;
        }
        return false;
    }
    public boolean changePassword(int id, String newPassword){
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()) {
            SQLiteDatabase statement = getWritableDatabase();
            String query = "UPDATE contacts SET "
                    + column_password + " = '" + newPassword + "' "
                    + "WHERE id = " + id;
            statement.execSQL(query);
            return true;
        }
        return false;
    }

    public int getIdByName(String name){
        String whereClause = "name=?";
        String[] whereArgs = {String.valueOf(name)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            return id;
        }
        return 0;
    }

    public String getPhoneByName(String name) {
        String whereClause = "name=?";
        String[] whereArgs = {String.valueOf(name)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            String phoneNumber = resultSet.getString(6);
            return phoneNumber;
        }
        return null;
    }

    public Contact getContactByID(int id){
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null,null, null);
        if(resultSet.moveToNext()){
            byte[] avatar = resultSet.getBlob(3);
            String name = resultSet.getString(4);
            String birthday = resultSet.getString(5);
            String phone = resultSet.getString(6);
            String email = resultSet.getString(7);
            String address = resultSet.getString(8);
            Contact contact = new Contact(avatar, name, birthday, phone, email, address);
            return contact;
        }
        return null;
    }

    public boolean editProfile(int id, Contact contact) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.query("contacts", null, whereClause, whereArgs, null, null, null);
        if (resultSet.moveToNext()) {
            SQLiteDatabase statement = getWritableDatabase();
            String query = "UPDATE contacts SET avatar = ?, name = ?, birthday = ?, phone = ?, enail = ?, address = ? WHERE id = ?";
            SQLiteStatement sqLiteStatement = statement.compileStatement(query);
            sqLiteStatement.bindBlob(1,contact.getAvatar());
            sqLiteStatement.bindString(2,contact.getName());
            sqLiteStatement.bindString(3,contact.getBirthday());
            sqLiteStatement.bindString(4,contact.getPhone());
            sqLiteStatement.bindString(5,contact.getEmail());
            sqLiteStatement.bindString(6,contact.getAddress());
            sqLiteStatement.bindLong(7,Long.parseLong(id+""));
            sqLiteStatement.executeInsert();
            return true;
        }
        return false;
    }

    public ArrayList<Contact> loadingContactIntoList() {
        ArrayList<Contact>  arrContact = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("contacts", null, null,null,null,null, null);
        while (resultSet!= null && resultSet.moveToNext()){
            byte[] avatar = resultSet.getBlob(3);
            String name = resultSet.getString(4);
            Contact contact = new Contact(avatar,name);
            arrContact.add(contact);
        }
        resultSet.close();
        return arrContact;
    }

    public ArrayList<Contact> getAllContactBirthday(){
        ArrayList<Contact>  arrContact = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("contacts", null, null,null,null,null, null);
        while (resultSet!= null && resultSet.moveToNext()){
            byte[] avatar = resultSet.getBlob(3);
            String name = resultSet.getString(4);
            String birthday = resultSet.getString(5);
            Contact contact = new Contact(avatar,name, birthday);
            arrContact.add(contact);
        }
        resultSet.close();
        return arrContact;
    }

    public ArrayList<Contact> getContactByName(String name){
        ArrayList<Contact> arrContact = new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" +name+ "%"};
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("contacts",null,whereClause,whereArgs,null,null,null,null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(column_id));
            byte[] imageAvatar = cursor.getBlob(cursor.getColumnIndex(column_avatar));
            String nameContact = cursor.getString(cursor.getColumnIndex(column_name));
            arrContact.add(new Contact(id, imageAvatar, nameContact));
        }
        cursor.close();
        return arrContact;
    }

    public int delete(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("contacts", whereClause, whereArgs);
    }
}
