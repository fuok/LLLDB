package com.example.utils;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Sqlite3
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

	/** 必须执行此方法，否则没有DB实例，直接崩溃了。同时有建表作用 */
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
		buffer.append("create table if not exists " + ClassUtil.getObjShortClassName(object) + "(");
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
	@SuppressWarnings({ "rawtypes" })
	public static void insert(Object object) {
		Log.i(TAG, "db_insert");

		boolean dataIsArray = object.getClass().getCanonicalName().equals("java.util.ArrayList");// 如果是ArrayList，则需要使用相应方法判断循环插入
		Log.i("liuy", "是队列：" + dataIsArray);
		open();
		for (int i = 0; i < (dataIsArray ? ((ArrayList) object).size() : 1); i++) {
			String tableName = ClassUtil.getObjShortClassName(dataIsArray ? ((ArrayList) object).get(i) : object);// 表名
			List<HashMap<String, ?>> fieldList = ClassUtil.saveObj2List(dataIsArray ? ((ArrayList) object).get(i) : object);
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
				} else if (tempTypeList.get(j).equals("double")) {
					contentValues.put(tempNameList.get(j), (Double) (tempValueList.get(j)));
				} else if (tempTypeList.get(j).equals("text")) {
					contentValues.put(tempNameList.get(j), (String) (tempValueList.get(j)));
				} else if (tempTypeList.get(j).equals("")) {// ArrayList
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
		String tableName = ClassUtil.getObjShortClassName(object);// 表名
		open();
		db.delete(tableName, null, null);
		close();
	}

	/** 重载以上方法 */
	public static void delete(Object object, String deleteCondition, String[] deleteArgs) {
		String tableName = ClassUtil.getObjShortClassName(object);// 表名
		open();
		db.delete(tableName, deleteCondition, deleteArgs);
		close();
		// return db.delete(tableName, deleteCondition, deleteArgs) > 0;
	}

	/** 查找内容（新版） */
	public static List<Object> lookFor(Object object) {
		String tableName = ClassUtil.getObjShortClassName(object);// 表名
		List<HashMap<String, ?>> fieldList = ClassUtil.saveObj2List(object);
		ArrayList<String> tempNameList = new ArrayList<String>();
		ArrayList<String> tempTypeList = new ArrayList<String>();
		// ArrayList<Object> tempValueList = new ArrayList<Object>();
		for (HashMap<String, ?> map : fieldList) {
			String name = (String) map.get("fieldName");
			tempNameList.add(name);
			String type = (String) map.get("fieldType");
			tempTypeList.add(type);
			// Object value = map.get("fieldValue");
			// tempValueList.add(value);
		}
		String[] nameArray = tempNameList.toArray(new String[tempNameList.size()]);
		Log.w("liuy", Arrays.toString(nameArray));

		open();
		Cursor cursor = db.query(tableName, nameArray, null, null, null, null, nameArray[0]);// + " desc");//这里我用了nameArray[0]作标识，但具体作用还没看出来
		List<HashMap<String, Object>> uList = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {

			HashMap<String, Object> umap = new HashMap<String, Object>();
			for (int i = 0; i < tempNameList.size(); i++) {

				if (tempTypeList.get(i).equals("smallint")) {
					Log.w("liuy", "获取到smallint");
					umap.put(tempNameList.get(i), cursor.getInt(cursor.getColumnIndex(tempNameList.get(i))));
				} else if (tempTypeList.get(i).equals("integer")) {
					Log.w("liuy", "获取到integer");
					umap.put(tempNameList.get(i), cursor.getLong(cursor.getColumnIndex(tempNameList.get(i))));
				} else if (tempTypeList.get(i).equals("double")) {
					Log.w("liuy", "获取到double");
					umap.put(tempNameList.get(i), cursor.getDouble(cursor.getColumnIndex(tempNameList.get(i))));
				} else if (tempTypeList.get(i).equals("text")) {
					Log.w("liuy", "获取到text:" + cursor.getString(cursor.getColumnIndex(tempNameList.get(i))) + ",列名：" + tempNameList.get(i));// /////////////XXX
					umap.put(tempNameList.get(i), cursor.getString(cursor.getColumnIndex(tempNameList.get(i))));
				} else if (tempTypeList.get(i).equals("")) {
					Log.w("liuy", "获取到数组");
					umap.put(tempNameList.get(i), cursor.getString(cursor.getColumnIndex(tempNameList.get(i))));
				} else {
					Log.w("liuy", "获取到nothing");
				}

			}

			uList.add(umap);

		}
		cursor.close();
		close();

		// 类型转换
		Log.i("liuy", "数据长度：" + uList.size());

		return ClassUtil.changeData2ObjList(uList, object);

	}

	/** 修改,TODO */
	public static boolean update(Object object, String whereClause) {
		String tableName = ClassUtil.getObjShortClassName(object);// 表名
		// 获取修改的值
		ContentValues contentValues = new ContentValues();
		// contentValues.put(BLACK_NUMBER, (String) newValue[0]);

		// update(TABLE_NAME, contentValues, BLACK_ID + "=?", new String[] { oldValue.get(BLACK_ID).toString() });//参考

		open();
		int returnValue = db.update(tableName, contentValues, whereClause + "=?", new String[] { ClassUtil.getFieldValueByName(object, whereClause) });// 获取whereClause的属性值
		Log.i("liuy", "修改了");
		close();
		return returnValue > 0;
	}

	/** 查找，7个参数，暂时不用 */
	public Cursor findList(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;

	}

	/** 查找，9个参数，暂时不用 */
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
