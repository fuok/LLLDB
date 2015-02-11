package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Sqlite3��ݿ����������
 * */
public class DBHelper {

	private final static String TAG = "DBHelper";
	// DDL
	private static DataBaseHelper dataBaseHelper;
	// DML
	private static SQLiteDatabase db;
	//
	private static final String DATABASE_NAME = "ly.db3";
	//
	private static final int DATABASE_VERSION = 1;
	//
	private static Context mCtx;

	//

	public static void iniDB(Context context) {
		new DBHelper(context);
	}

	public DBHelper(Context context) {
		mCtx = context;
	}

	//
	private static class DataBaseHelper extends SQLiteOpenHelper {

		DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			System.out.println("upgrade a database");

		}

	}

	private static final void createTable(Object object) {
		List<HashMap<String, ?>> fieldList = ClassUtil.saveObj2List(object);
		ArrayList<String> tempNameList = new ArrayList<String>();
		ArrayList<String> tempTypeList = new ArrayList<String>();
		for (HashMap<String, ?> map : fieldList) {
			String name = (String) map.get("fieldName");
			tempNameList.add(name);
			String type = (String) map.get("fieldType");
			tempTypeList.add(type);
		}
		// String fieldName[] = tempNameList.toArray(new String[fieldList.size() * 2]);
		// Log.w("liuy", Arrays.toString(fieldName));

		StringBuffer buffer = new StringBuffer();
		// buffer.append("create table if not exists " + TABLE_NAME + "(" + BLACK_ID + " integer primary key," + BLACK_NUMBER + " text," + BLOCK_SMS + " boolean," + BLOCK_PHONE_CALL + " boolean)");

		// //
		buffer.append("create table if not exists " + ClassUtil.getObjClassName(object) + "(");
		for (int i = 0; i < tempNameList.size(); i++) {
			buffer.append(tempNameList.get(i));
			buffer.append(" ");
			buffer.append(tempTypeList.get(i));
			if (i < tempNameList.size() - 1) {
				buffer.append(",");
			}

		}
		buffer.append(")");
		Log.w("liuy", buffer.toString());

		execSQL(buffer.toString());

	}

	/**  */
	private static void open() throws SQLException {
		dataBaseHelper = new DataBaseHelper(mCtx);
		db = dataBaseHelper.getWritableDatabase();
		Log.v(TAG, "db_open");
	}

	/**  */
	private static void close() {
		db.close();
		dataBaseHelper.close();
		Log.v(TAG, "db_close");
	}

	/** */
	public long insert(String tableName, ContentValues values) {
		Log.i(TAG, "��ݿ����");
		return db.insert(tableName, null, values);

	}

	/** */
	public boolean delete(String tableName, String deleteCondition, String[] deleteArgs) {
		return db.delete(tableName, deleteCondition, deleteArgs) > 0;
	}

	/** */
	public boolean update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		int returnValue = db.update(table, values, whereClause, whereArgs);
		return returnValue > 0;
	}

	/** */
	public Cursor findList(String tableName, String[] columns,// //////////////////
																// 7������
			String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;

	}

	/** */
	public Cursor findInfo(boolean distinct, String tableName, String[] columns,// /////////////////
																				// 9������
			String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) throws SQLException {
		Cursor cursor = db.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/** */
	private static void execSQL(String sql) {
		open();
		db.execSQL(sql);
		close();
	}

	/** */
	public boolean isTableExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where" + "type='table' and name='" + tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					return true;
				}
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(TAG, "�жϱ����ʧ��");
			e.printStackTrace();
		}
		return result;

	}

	/** */
	public long getCount(String tableName) {
		long result = 0;
		if (tableName == null) {
			return 0;
		}
		try {
			Cursor cursor = null;
			String sql = "select count(*) from '" + tableName.trim() + "'";
			// ��������
			open();// ���������Ҫ��ȥȷ��һ��
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = count;
					Log.i(TAG, "��ȡ�?��=" + result);
				}
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(TAG, "��ȡ�?��ʧ��");
			e.printStackTrace();
		}
		close();
		return result;
	}

	/*
	 * 
	 */
	public boolean isColumnExist(String tableName, String columnName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where" + "type='table' and name='" + tableName.trim() + "' and sql like '%" + columnName.trim() + "%'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					return true;
				}
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(TAG, "�ж��ֶ�ʧ��");
			e.printStackTrace();
		}
		return result;

	}

}
