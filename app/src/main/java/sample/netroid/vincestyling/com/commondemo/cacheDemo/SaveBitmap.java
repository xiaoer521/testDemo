package sample.netroid.vincestyling.com.commondemo.cacheDemo;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveBitmapActivity.java
 * @创建时间 : 2015年9月5日 下午1:48:13
 * @文件描述 : 缓存bitmap
 * *****************************************
 */
public class SaveBitmap extends Activity {

	private ImageView ivBitmapRes;
	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_bitmap);
		init();

		mCache = Cache.get(this);
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		ivBitmapRes = (ImageView) findViewById(R.id.iv_bitmap_res);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res,
				R.mipmap.ic_launcher);
		mCache.put("testBitmap", bitmap);
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		Bitmap testBitmap = mCache.getAsBitmap("testBitmap");
		if (testBitmap == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			ivBitmapRes.setImageBitmap(null);
			return;
		}
		ivBitmapRes.setImageBitmap(testBitmap);
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testBitmap");
	}
}