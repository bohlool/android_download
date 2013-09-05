/**
 * BitmapTemp.java
 * created at:2011-5-11����04:10:46
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.utility;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
  
/** 
 * 
 * @author zqm 
 */
public class BitmapTemp extends PhantomReference{
	public static ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();   
	public boolean isRequested;
	public Bitmap bitmap;
	private boolean isflag=false;
	
	public boolean isIsflag() {
		return isflag;
	}

	public void setIsflag(boolean isflag) {
		this.isflag = isflag;
	}

	public BitmapTemp() {
		super(null, referenceQueue);
		isRequested = false;
		bitmap = null;
	}
	
	public BitmapTemp(Bitmap bitmap) {
		super(bitmap,referenceQueue);
		isRequested = false;
		this.bitmap = bitmap;
	}
	
	public BitmapTemp(boolean isRequested) {
		super(null,referenceQueue);
		this.isRequested = isRequested;
		bitmap = null;
	}
}

 