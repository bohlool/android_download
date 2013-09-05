/**
 * BaseDB.java
 * created at:2011-5-11����04:15:34
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.db;

import com.appdear.client.service.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
  
/** 
 * DB�洢
 * 
 * @author zqm 
 */
public class BaseDB {

	/**
	 * ���ݿ�
	 */
	public SQLiteOpenHelper dbHelper;
	
	public BaseDB(Context context) {
		dbHelper = new MyDBOpenHelper(context);
	}
	
	/**
	 * @return the dbHelper
	 */
	public SQLiteOpenHelper getDbHelper() {
		return dbHelper;
	}
}

 