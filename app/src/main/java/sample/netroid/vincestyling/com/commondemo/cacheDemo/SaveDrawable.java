package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveDrawableActivity.java
 * @创建时间 : 2015年9月5日 下午1:48:31
 * @文件描述 : 缓存drawable
 * *****************************************
 */
public class SaveDrawable extends Activity {

	private ImageView ivDrawableRes;

	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_drawable);
		init();

		mCache = Cache.get(this);
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		ivDrawableRes = (ImageView) findViewById(R.id.iv_drawable_res);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		Resources res = getResources();
		Drawable drawable = res.getDrawable(R.mipmap.ic_launcher);
		mCache.put("testDrawable", drawable);
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		Drawable testDrawable = mCache.getAsDrawable("testDrawable");
		if (testDrawable == null) {
			Toast.makeText(this, "Drawable是空的", Toast.LENGTH_SHORT).show();
			ivDrawableRes.setImageDrawable(null);
			return;
		}
		ivDrawableRes.setImageDrawable(testDrawable);
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testDrawable");
	}
}