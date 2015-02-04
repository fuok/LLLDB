package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

/* 
 * ��ȡ��UserBean���������ҵ���߼�
 * MVC�ṹ
 * ����ϸ��
 * 
 * �����SmsBean���ƹ��������������о��Ǳߺ���
 * */
public class BlackListBean extends UserBean {
	// �����
	public final static String TABLE_NAME = "blacklist";
	// �Զ����
	public final static String BLACK_ID = "blackid";
	// �������
	public final static String BLACK_NUMBER = "blacknumber";
	// �Ƿ����ض���
	public final static String BLOCK_SMS = "blocksms";
	// �Ƿ����ص绰
	public final static String BLOCK_PHONE_CALL = "blockphonecall";
	private static BlackListBean instance;

	// ����(��������)
	public final static BlackListBean getInstance() {
		if (null == instance) {
			instance = new BlackListBean();
		}
		return instance;
	}

	// ������ṹSQL
	public final String sql$createTable() {
		Log.w("liuy", "���������");
		StringBuffer buffer = new StringBuffer();
		buffer.append("create table if not exists " + TABLE_NAME + "(" + BLACK_ID + " integer primary key," + BLACK_NUMBER + " text,"// ������ĳ���integer,֮ǰ��long
				+ BLOCK_SMS + " boolean," + BLOCK_PHONE_CALL + " boolean)");
		// buffer.append("create table if not exists ").append(TABLE_NAME).append("(");
		// buffer.append(UID).append(" long primary key,");
		// buffer.append(UNMAE).append(" text,");
		// buffer.append(UDATA).append(" text").append(")");
		return buffer.toString();
	}

	// �Ƴ��ṹSQL
	public final String sql$dropTable() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("create table if exists ").append(TABLE_NAME);
		return buffer.toString();
	}

	// ��ȡ������ݼ���
	public final List<HashMap<String, Object>> findList(final DBHelper dbHelper) {
		dbHelper.open();
		Cursor cursor = dbHelper.findList(TABLE_NAME, new String[] { BLACK_ID, BLACK_NUMBER, BLOCK_SMS, BLOCK_PHONE_CALL }, null, null, null, null, BLACK_ID);// + " desc");//����Ҫ��������
		List<HashMap<String, Object>> uList = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> umap = new HashMap<String, Object>();
			umap.put(BLACK_ID.toString(), cursor.getInt(cursor.getColumnIndex(BLACK_ID)));
			umap.put(BLACK_NUMBER, cursor.getString(cursor.getColumnIndex(BLACK_NUMBER)));
			umap.put(BLOCK_SMS, cursor.getString(cursor.getColumnIndex(BLOCK_SMS)));
			umap.put(BLOCK_PHONE_CALL, cursor.getString(cursor.getColumnIndex(BLOCK_PHONE_CALL)));
			uList.add(umap);
		}
		cursor.close();
		dbHelper.close();
		return uList;
	}

	/** ����һ������Ƿ���� */
	public final boolean isRowExist(final DBHelper dbHelper, final String incomeNumber) {

		dbHelper.open();
		Cursor cursor = dbHelper.findList(TABLE_NAME, new String[] { BLACK_ID, BLACK_NUMBER, BLOCK_SMS, BLOCK_PHONE_CALL }, BLACK_NUMBER + "=?", new String[] { incomeNumber }, null, null, BLACK_ID);// +
																																																		// " desc");//����Ҫ��������

		// ���ѯ���Ľ���������,�ж��費��Ҫ����
		boolean hasOrNo = false;

		while (cursor.moveToNext()) { // ���жϺ����Ƿ����(��tƥ��),���ж��Ƿ��O�����������
			if (Pattern.matches(cursor.getString(cursor.getColumnIndex(BLACK_NUMBER)), incomeNumber) && cursor.getString(cursor.getColumnIndex(BLOCK_SMS)).equals("1")) {
				Log.w("liuy", "����һ�����ض���");
				hasOrNo = true;
			}
		}
		cursor.close();
		dbHelper.close();

		return hasOrNo;
	}

	// ����
	public final long save(final DBHelper dbHelper, ContentValues contentValues) {
		dbHelper.open();
		long result = dbHelper.insert(TABLE_NAME, contentValues);
		dbHelper.close();
		return result;
	}

	// ɾ��
	public final boolean remove(final DBHelper dbHelper, String deleteCondition, HashMap<String, Object> map) {
		dbHelper.open();
		boolean result = dbHelper.delete(TABLE_NAME, deleteCondition, new String[] { map.get(BLACK_ID).toString() });// ɾ����߼���,ɾ�����������(deleteArgs)�ض���(deleteCondition)
		dbHelper.close();
		return result;
	}

	/**
	 * �޸�
	 * 
	 * @param dbHelper
	 *            �㶮��
	 * @param oldValue
	 *            ��Ҫ�޸ĵĶ���,Map����,�����Ӧ�кŻ�ȡ
	 * @param newValue
	 *            �޸ĺ�����,������ʽ,����һ��item��Ҫ�޸ĵĸ����������
	 */

	public final boolean update(final DBHelper dbHelper, Map<String, Object> oldValue, Object[] newValue) {// ���������ɷ�����һ��,�ϱߵ�Add������ֱ�Ӵ���ContentValues,���޸��������Ǵ���������ת��ContentValues
																											// �������ַ����û�Ҫ�ٿ�һ��
		dbHelper.open();
		// ������ݿ���޸�����
		ContentValues contentValues = new ContentValues();
		contentValues.put(BLACK_NUMBER, (String) newValue[0]);
		contentValues.put(BLOCK_SMS, (Boolean) newValue[1]);
		contentValues.put(BLOCK_PHONE_CALL, (Boolean) newValue[2]);
		boolean result = dbHelper.update(TABLE_NAME, contentValues, BLACK_ID + "=?", new String[] { oldValue.get(BLACK_ID)// ��oldValue������Ƭ��������HashMap��,��һ����Ŀ��Ϣ
				.toString() });
		dbHelper.close();
		return result;

	}
}
