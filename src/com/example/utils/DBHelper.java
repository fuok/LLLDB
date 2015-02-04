package com.example.utils;

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

	private final String TAG = "DBHelper";
	// DDL
	private static DataBaseHelper dataBaseHelper;
	// DML
	private static SQLiteDatabase db;
	// ��ݿ���
	private static final String DATABASE_NAME = "ly.db3";
	// ��ݰ汾��
	private static final int DATABASE_VERSION = 1;
	// �����Ļ���
	private final Context mCtx;

	// ���캯��
	public DBHelper(Context context) {
		this.mCtx = context;
	}

	// ��д�Ĺ��캯��,�ֱ��������ĺ�ҵ���߼�bean���ɵ�����,����һ���Դ������б��
	// ����ע��:�����ʱ������
	// public DBHelper(Context context, UserBean[] bean) {
	// this.mCtx = context;
	// DBHelper.bean = bean;
	// }

	// ��̬�ڲ���̳г�����SQLiteOpenHelper��������ݿ�Ĵ����Ͱ汾�Ĺ���
	private static class DataBaseHelper extends SQLiteOpenHelper {

		DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		// ������ṹ,�˷�����app��ݿⴴ��ʱ��ִ��һ��
		@Override
		public void onCreate(SQLiteDatabase db) {
			// ��ʼ���û���Ϣ��
			// Ϊ����һ���Դ������,���������bean�������,�ؼ��������ھ�̬�ڲ���ֻ��ʵ��һ��,���Դ�����Ķ�������һ�������.
			// �Ҿ�����"SmsBean"����ľ�������ǲ�Ӧ���ڹ�������ֵ�,����������?
			UserBean[] bean = new UserBean[] { BlackListBean.getInstance() };
			for (UserBean i : bean) {
				String sql = i.sql$createTable();
				db.execSQL(sql);
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			System.out.println("upgrade a database");

		}

	}

	/** ����ݿ� */
	public DBHelper open() throws SQLException {
		dataBaseHelper = new DataBaseHelper(mCtx);
		db = dataBaseHelper.getWritableDatabase();
		Log.v(TAG, "��ݿ��");
		return this;
	}

	/** �ر���ݿ� */
	public void close() {
		db.close();
		dataBaseHelper.close();
		Log.v(TAG, "��ݿ�ر�");
	}

	/**
	 * �������
	 * 
	 * @param tableName
	 *            ����
	 * @param valuesҪ
	 *            ������ж�Ӧֵ
	 */
	public long insert(String tableName, ContentValues values) {
		Log.i(TAG, "��ݿ����");
		return db.insert(tableName, null, values);

	}

	/**
	 * ɾ��
	 * 
	 * @param tableName
	 *            ����
	 * @param deleteCondition
	 *            ɾ�������
	 * @param deleteArgs
	 *            ���deleteCondition����"?"�����ô�������ֵ�滻
	 */
	public boolean delete(String tableName, String deleteCondition, String[] deleteArgs) {
		return db.delete(tableName, deleteCondition, deleteArgs) > 0;
	}

	/**
	 * ����
	 * 
	 * @param table
	 *            the table to update in
	 * @param values
	 *            a map from column names to new column values. null is a valid value that will be translated to NULL.
	 * @param whereClause
	 *            the optional WHERE clause to apply when updating. Passing null will update all rows.
	 * @param whereArgs
	 *            ���updateCondition����"?"�����ô�������ֵ�滻
	 * @return the number of rows affected
	 */
	public boolean update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		int returnValue = db.update(table, values, whereClause, whereArgs);
		return returnValue > 0;
	}

	/**
	 * ��ѯ
	 * 
	 * @param tableName
	 *            �����,The table name to compile the query against.
	 * @param columns
	 *            ���������,A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
	 * @param selection
	 *            �����־䣬�൱��where,A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param selectionArgs
	 *            �����־䣬��������,You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param groupBy
	 *            ������,A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
	 * @param having
	 *            ��������,A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
	 * @param orderBy
	 *            ������,How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 */
	public Cursor findList(String tableName, String[] columns,// ////////////////// 7������
			String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;

	}

	/**
	 * ��ȷ��ѯ������һ�����
	 * 
	 * @param distinct
	 * @param tableName
	 *            �����
	 * @param columns
	 *            ���������
	 * @param selection
	 *            �����־䣬�൱��where
	 * @param selectionArgs
	 *            �����־䣬��������
	 * @param groupBy
	 *            ������
	 * @param having
	 *            ��������
	 * @param orderBy
	 *            ������
	 * @param limit
	 *            ��ҳ��ѯ����
	 */
	public Cursor findInfo(boolean distinct, String tableName, String[] columns,// ///////////////// 9������
			String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) throws SQLException {
		Cursor cursor = db.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/** ִ��SQL */
	public void execSQL(String sql) {
		db.execSQL(sql);
	}

	/** �ж�ĳ�ű��Ƿ���� */
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

	/** ��ȡ�?�� */
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
	 * �ж�ĳ�ű����Ƿ����ĳ�ֶ� �÷����޷��жϱ��Ƿ���� Ҫ��isTableExistsһ��ʹ��
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
