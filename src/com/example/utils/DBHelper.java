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

	public static void iniDB(Context context, Object... object) {
		new DBHelper(context);
		for (Object obj : object) {
			createTable(obj);
		}
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

		open();
		execSQL(buffer.toString());
		close();
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

	/** 插入表数据 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void insert(Object object) {
		Log.i(TAG, "db_insert");
		if (object.getClass().getCanonicalName().equals("java.util.ArrayList")) {// 如果是ArrayList，则需要使用相应方法
			insertArray((ArrayList) object);
			return;
		}
		open();

		String tableName = ClassUtil.getObjClassName(object);// 表名
		List<HashMap<String, ?>> fieldList = ClassUtil.saveObj2List(object);
		ArrayList<String> tempNameList = new ArrayList<String>();
		ArrayList<String> tempTypeList = new ArrayList<String>();
		ArrayList<Object> tempValueList = new ArrayList<Object>();
		for (HashMap<String, ?> map : fieldList) {
			String name = (String) map.get("fieldName");
			tempNameList.add(name);
			String type = (String) map.get("fieldType");
			tempTypeList.add(type);
			Object value = map.get("fieldValue");
			tempValueList.add(value);
		}

		ContentValues contentValues = new ContentValues();

		for (int i = 0; i < tempNameList.size(); i++) {

			if (tempTypeList.get(i).equals("smallint")) {
				contentValues.put(tempNameList.get(i), (Integer) (tempValueList.get(i)));
			} else if (tempTypeList.get(i).equals("integer")) {
				contentValues.put(tempNameList.get(i), (Long) (tempValueList.get(i)));
			} else if (tempTypeList.get(i).equals("text")) {
				contentValues.put(tempNameList.get(i), (String) (tempValueList.get(i)));
			} else {

			}

		}
		long p = db.insert(tableName, null, contentValues);
		if (p < 0) {
			Log.w("liuy", "添加失败");
		}
		close();

	}

	/** 针对ArrayList */
	private static void insertArray(ArrayList<Object> list) {
		Log.i(TAG, "db_insert");
		open();

		for (int i = 0; i < list.size(); i++) {

			String tableName = ClassUtil.getObjClassName(list.get(i));// 表名
			List<HashMap<String, ?>> fieldList = ClassUtil.saveObj2List(list.get(i));
			ArrayList<String> tempNameList = new ArrayList<String>();
			ArrayList<String> tempTypeList = new ArrayList<String>();
			ArrayList<Object> tempValueList = new ArrayList<Object>();
			for (HashMap<String, ?> map : fieldList) {
				String name = (String) map.get("fieldName");
				tempNameList.add(name);
				String type = (String) map.get("fieldType");
				tempTypeList.add(type);
				Object value = map.get("fieldValue");
				tempValueList.add(value);
			}

			ContentValues contentValues = new ContentValues();

			for (int j = 0; j < tempNameList.size(); j++) {

				if (tempTypeList.get(j).equals("smallint")) {
					contentValues.put(tempNameList.get(j), (Integer) (tempValueList.get(j)));
				} else if (tempTypeList.get(j).equals("integer")) {
					contentValues.put(tempNameList.get(j), (Long) (tempValueList.get(j)));
				} else if (tempTypeList.get(j).equals("text")) {
					contentValues.put(tempNameList.get(j), (String) (tempValueList.get(j)));
				} else {

				}

			}
			long p = db.insert(tableName, null, contentValues);
			if (p < 0) {
				Log.w("liuy", "添加失败");
			}
		}

		close();

	}

	/** 删除表 */
	public static void delete(Object object) {
		String tableName = ClassUtil.getObjClassName(object);// 表名
		open();
		db.delete(tableName, null, null);
		close();
	}

	/** 重载以上方法 */
	public static void delete(Object object, String deleteCondition, String[] deleteArgs) {
		String tableName = ClassUtil.getObjClassName(object);// 表名
		open();
		db.delete(tableName, deleteCondition, deleteArgs);
		close();
		// return db.delete(tableName, deleteCondition, deleteArgs) > 0;
	}

	/** 修改 */
	public boolean update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		int returnValue = db.update(table, values, whereClause, whereArgs);
		return returnValue > 0;
	}

	/** 查找，7个参数 */
	public Cursor findList(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;

	}

	/** 查找，9个参数 */
	public Cursor findInfo(boolean distinct, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
			throws SQLException {
		Cursor cursor = db.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	// ---------------------------------私有区----------------------------------

	/** */
	private static void execSQL(String sql) {
		db.execSQL(sql);
	}

	/** */
	private boolean isTableExist(String tableName) {
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
	private long getCount(String tableName) {
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
	private boolean isColumnExist(String tableName, String columnName) {
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
