package com.appdear.client.db;

import java.util.ArrayList;
import java.util.List;

import com.appdear.client.model.CatalogListInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ͳ��ǩ�б�
 * @author User
 *
 */
public class LabellistDB extends BaseDB {

	public LabellistDB(Context context) {
		super(context);
	}
	
	/**
	 * �������ͻ�ȡ�б�
	 * 
	 * @param type 0--������Ⱥ�� 1--ʹ�ó����� 2--ְҵ
	 * @return
	 */
	public List<CatalogListInfo> getLabellist(String type) {
		SQLiteDatabase db=null;
		List<CatalogListInfo> list = new ArrayList<CatalogListInfo>();
		try {
		    db = dbHelper.getReadableDatabase();
			db.beginTransaction();
			Cursor cursor = db.query("labellist", new String[]{
					"labelid", "labelname", "icon", "type", "ctype"},
					"type=?",new String[]{type}, null, null, null, null);
			int numRows = cursor.getCount();
			
			cursor.moveToFirst();
			for (int i = 0; i < numRows; i ++) {
				CatalogListInfo info = new CatalogListInfo();
				info.catalogid = cursor.getInt(0)+"";
				info.catalogname = cursor.getString(1);
				info.catalogicon = cursor.getString(2);
				info.ctype = cursor.getString(4);
				
				list.add(info);
				cursor.moveToNext();
			}
			cursor.close();
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
		} finally {
			if(db!=null)
			db.close();
		}
		return list;
	}
	
	/**
	 * �����ǩ�б�����
	 * @param info �б���Ϣ
	 * @param type �б����� 0 -- ������Ⱥ��1--ʹ�ó�����2--ְҵ
	 */
	public void insertLabellist(CatalogListInfo info, String type) {
		SQLiteDatabase db=null;
		try {
			db = dbHelper.getWritableDatabase();
			db.beginTransaction(); 
			db.execSQL(
					"insert into labellist(labelid, labelname, " +
					"icon, type, ctype) values(?,?,?,?,?)",                  
					new Object[] {info.catalogid, info.catalogname,
							info.catalogicon, type, info.ctype});
			db.setTransactionSuccessful();   
			if(db!=null)db.endTransaction();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(db!=null)db.close();
		}
	}
	
	/**
	 * ��������ɾ����ǩ�б�����
	 * @param type 0--������Ⱥ��1--ʹ�ó�����2--ְҵ
	 */
	public void deleteAll(String type) {
		SQLiteDatabase db=null;
		try {
			db= dbHelper.getWritableDatabase();
			db.beginTransaction();
			db.delete("labellist", "type=?", new String[]{type});
			db.setTransactionSuccessful();
			db.endTransaction();
		}catch(Exception e) {
		} finally {
			if(db!=null)
			db.close();
		}
	}
}
