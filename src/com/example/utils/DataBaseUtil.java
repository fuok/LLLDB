package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class DataBaseUtil {

	/**
	 * 
	 * 测试
	 * 
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void iniClass(Class clazz) {

		// 获取属性

		Map<String, Class> map = getClassFields(clazz, false);

		for (Object key : map.keySet())

		{

			Log.w("liuy", "<field=" + key.toString() + "> <Type=" + map.get(key) + ">");

		}

		// 获取方法

		List<Method> methods = getMothds(clazz, false);

		for (Method method : methods)

		{

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
	public static Map<String, Class> getClassFields(Class clazz, boolean includeParentClass)

	{

		Map<String, Class> map = new HashMap<String, Class>();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields)

		{

			map.put(clazz.getName() + "." + field.getName(), field.getType());

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
	private static Map<String, Class> getParentClassFields(Map<String, Class> map, Class clazz)

	{

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields)

		{

			map.put(clazz.getName() + "." + field.getName(), field.getType());

		}

		if (clazz.getSuperclass() == null)

		{

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
	public static List<Method> getMothds(Class clazz, boolean includeParentClass)

	{

		List<Method> list = new ArrayList<Method>();

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods)

		{

			list.add(method);

		}

		if (includeParentClass)

		{

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
	private static List<Method> getParentClassMothds(List<Method> list, Class clazz)

	{

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods)

		{

			list.add(method);

		}

		if (clazz.getSuperclass() == Object.class)

		{

			return list;

		}

		getParentClassMothds(list, clazz.getSuperclass());

		return list;

	}

}
