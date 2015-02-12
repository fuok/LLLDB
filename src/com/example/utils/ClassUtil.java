package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class ClassUtil {

	@SuppressWarnings("rawtypes")
	public static List<HashMap<String, ?>> saveObj2List(Object object) {

		// iniClass(object.getClass());

		Class clazz = object.getClass();
		List<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			// 声明保存每个属性的map
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 保存属性名，同时也就是表中的列名
			map.put("fieldName", field.getName());// clazz.getName()
			// 保存属性类型，用于匹配SOL语法
			String type = field.getType().toString();
			if (type.equals("int")) {// 数字型
				map.put("fieldType", "smallint");
			} else if (type.equals("long")) {
				map.put("fieldType", "integer");
			} else if (type.equals("class java.lang.String")) {// 字符型
				map.put("fieldType", "text");
			} else {// 目前处理不了的，以下待补充
				map.put("fieldType", "text");
			}
			// 保存属性值
			try {
				Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
				// Log.w("liuy", "获取方法：" + "get" +
				// getMethodName(field.getName()));
				if (field.getGenericType().toString().equals("int")) {
					// Log.e("liuy", "触发了");
					int val = (Integer) m.invoke(object);
					map.put("fieldValue", val);
				} else if (field.getGenericType().toString().equals("long")) {
					long val = (Long) m.invoke(object);
					map.put("fieldValue", val);
				} else if (field.getGenericType().toString().equals("double")) {
					double val = (Double) m.invoke(object);
					map.put("fieldValue", val);
				} else if (field.getGenericType().toString().equals("boolean")) {
					boolean val = (Boolean) m.invoke(object);
					map.put("fieldValue", val);
				} else if (field.getGenericType().toString().equals("class java.lang.String")) {
					String val = (String) m.invoke(object);
					map.put("fieldValue", val);
				} else if (field.getGenericType().toString().equals("class java.util.ArrayList")) {
					ArrayList val = (ArrayList) m.invoke(object);
					map.put("fieldValue", val);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 保存完毕，添加至属性队列
			// Log.w("liuy", "属性名：" + map.get("fieldName") + "属性类型:" +
			// map.get("fieldType") + ",属性值：" + map.get("fieldValue"));
			list.add(map);
		}
		return list;
	}

	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	@SuppressWarnings("rawtypes")
	public static String getObjClassName(Object object) {
		Class clazz = object.getClass();
		return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
	}

	/**
	 * 
	 * 测试方法
	 * 
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	private static void iniClass(Class clazz) {

		// 获取属性

		Map<String, Class> map = getClassFields(clazz, false);

		for (Object key : map.keySet()) {

			Log.w("liuy", "<field=" + key.toString() + "> <Type=" + map.get(key) + ">");

		}

		// 获取方法

		List<Method> methods = getMothds(clazz, false);

		for (Method method : methods) {

			Log.w("liuy", method.getName());

		}
		// Log.w("liuy", "方法总数：" + methods.size());

	}

	/**
	 * 
	 * 获取类实例的属性值
	 * 
	 * @param clazz
	 * 
	 *            类名
	 * 
	 * @param includeParentClass
	 * 
	 *            是否包括父类的属性值
	 * 
	 * @return 类名.属性名=属性类型
	 */

	@SuppressWarnings("rawtypes")
	private static Map<String, Class> getClassFields(Class clazz, boolean includeParentClass) {

		Map<String, Class> map = new HashMap<String, Class>();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			map.put(clazz.getName() + "." + field.getName(), field.getType());// field.getType()

		}

		if (includeParentClass)

			getParentClassFields(map, clazz.getSuperclass());

		return map;

	}

	/**
	 * 
	 * 获取类实例的父类的属性值
	 * 
	 * @param map
	 * 
	 *            类实例的属性值Map
	 * 
	 * @param clazz
	 * 
	 *            类名
	 * 
	 * @return 类名.属性名=属性类型
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Class> getParentClassFields(Map<String, Class> map, Class clazz) {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			map.put(clazz.getName() + "." + field.getName(), field.getType());

		}

		if (clazz.getSuperclass() == null) {

			return map;

		}

		getParentClassFields(map, clazz.getSuperclass());

		return map;

	}

	/**
	 * 
	 * 获取类实例的方法
	 * 
	 * @param clazz
	 * 
	 *            类名
	 * 
	 * @param includeParentClass
	 * 
	 *            是否包括父类的方法
	 * 
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private static List<Method> getMothds(Class clazz, boolean includeParentClass) {

		List<Method> list = new ArrayList<Method>();

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {

			list.add(method);

		}

		if (includeParentClass) {

			getParentClassMothds(list, clazz.getSuperclass());

		}

		return list;

	}

	/**
	 * 
	 * 获取类实例的父类的方法
	 * 
	 * @param list
	 * 
	 *            类实例的方法List
	 * 
	 * @param clazz
	 * 
	 *            类名
	 * 
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private static List<Method> getParentClassMothds(List<Method> list, Class clazz) {

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {

			list.add(method);

		}

		if (clazz.getSuperclass() == Object.class) {

			return list;

		}

		getParentClassMothds(list, clazz.getSuperclass());

		return list;

	}

}
