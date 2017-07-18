package rs.fon.dontforgetyourmedicine.data.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.model.UserModel;

/**
 * Created by radovan.bogdanic on 3/28/2017.
 */

public class UserManager {

    public static final String U_ID = "userid";
    public static final String U_NAME = "userName";
    public static final String U_EMAIL = "userEmail";
    public static final String U_PIC = "userImg";
    public static final String U_VISITOR = "isVisitor";

    public static UserModel getInfo(Cursor c) {
        UserModel model = new UserModel();
        model.setId(c.getInt(c.getColumnIndex(U_ID)));
        model.setUserName(c.getString(c.getColumnIndex(U_NAME)));
        model.setUserEmail(c.getString(c.getColumnIndex(U_EMAIL)));
        model.setImgUrl(c.getString(c.getColumnIndex(U_PIC)));
        model.setVisitor(c.getInt(c.getColumnIndex(U_VISITOR))==1);
        return model;
    }

    public static long insert(UserModel user, SQLiteDatabase db) {
        ContentValues content = new ContentValues();

        content.put(U_NAME,user.getUserName());
        content.put(U_EMAIL,user.getUserEmail());
        content.put(U_PIC,user.getImgUrl());
        content.put(U_VISITOR,false);

        return db.insert(DBOpenHelper.USER_TABLE_NAME,null,content);
    }

    public static ArrayList<UserModel> getAllUsers(SQLiteDatabase db) {
        ArrayList<UserModel> users = new ArrayList<>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + DBOpenHelper.USER_TABLE_NAME, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                users.add(getInfo(c));
                c.moveToNext();
            }
        }
        return users;
    }

    public static boolean isUserInDB(SQLiteDatabase db) {
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + DBOpenHelper.USER_TABLE_NAME, null);
            if (c != null) {
                c.moveToFirst();
                if (!c.isAfterLast()) {
                    return true;
                }
                c.close();
            }
        }
        return false;
    }
}
