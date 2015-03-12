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
	abstract public List<HashMap<String, Object>> findList(LYDB dbHelper);

	// ����
	abstract public long save(LYDB dbHelper, ContentValues contentValues);

	// ɾ��
	abstract public boolean remove(LYDB dbHelper, String deleteCondition, HashMap<String, Object> map);

	// �޸�
	abstract public boolean update(LYDB dbHelper, Map<String, Object> oldValue, Object[] newValue);
}
