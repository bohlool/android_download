/**
 * AsyLoadImageService.java
 * created at:2011-5-11����04:07:46
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.utility;

import com.appdear.client.db.GalleryAdDb;
import com.appdear.client.db.ImageDB;
import com.appdear.client.service.AppContext;
  
/** 
 * ͼƬ����
 * 
 * @author zqm 
 */
public class AsyLoadImageService {

	private ImageCache imageCache;
	private ImageDB imageDb;
	public static String TAG = "AsyLoadImageService";
	public static AsyLoadImageService instance;
	public static GalleryAdDb galleryDb = null;
	public  GalleryAdDb getGalleryDb() {
		return galleryDb;
	}

	public void setGalleryDb(GalleryAdDb galleryDb) {
		AsyLoadImageService.galleryDb = galleryDb;
	}

	public AsyLoadImageService() { 
		imageCache = new ImageCache();
		imageDb = new ImageDB(AppContext.getInstance().appContext);
		galleryDb = new GalleryAdDb(AppContext.getInstance().appContext);
	}
	
	/**
	 * @return the instance
	 */
	public static AsyLoadImageService getInstance() {
		if (instance == null)
			instance = new AsyLoadImageService();
		return instance;
	}
	
	/**
	 * @return the imageCache
	 */
	public ImageCache getImageCache() {
		return imageCache;
	}
	
	/**
	 * @return the imageDb
	 */
	public ImageDB getImageDb() {
		return imageDb;
	}
}

 