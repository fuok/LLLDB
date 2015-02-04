/**
 * 
 */
package com.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;

/**
 * @author Administrator
 * 
 */
public abstract class UserBean {
	// ������ṹSQL
	abstract public String sql$createTable();

	// �Ƴ��ṹSQL
	abstract public String sql$dropTable();

	// ��ȡ������ݼ���
	abstract public List<HashMap<String, Object>> findList(DBHelper dbHelper);

	// ����
	abstract public long save(DBHelper dbHelper, ContentValues contentValues);

	// ɾ��
	abstract public boolean remove(DBHelper dbHelper, String deleteCondition, HashMap<String, Object> map);

	// �޸�
	abstract public boolean update(DBHelper dbHelper, Map<String, Object> oldValue, Object[] newValue);
}
