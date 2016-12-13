package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveObjectActivity.java
 * @创建时间 : 2015年9月5日 下午1:48:41
 * @文件描述 : 缓存jsonobject
 * *****************************************
 */
public class SaveObject extends Activity {

	private TextView tvObject, TvObjectRes;
	private UserBean userBean;

	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_object);
		init();

		mCache = Cache.get(this);
		userBean = new UserBean();
		userBean.setAge("18");
		userBean.setName("HaoYoucai");
		tvObject.setText(userBean.toString());
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		tvObject = (TextView) findViewById(R.id.tv_object_original);
		TvObjectRes = (TextView) findViewById(R.id.tv_object_res);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		mCache.put("testObject", userBean);
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		UserBean testObject = (UserBean) mCache.getAsObject("testObject");
		if (testObject == null) {
			Toast.makeText(this, "Object是空的", Toast.LENGTH_SHORT).show();
			TvObjectRes.setText(null);
			return;
		}
		TvObjectRes.setText(testObject.toString());
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testObject");
	}

	public class UserBean implements Serializable {

		private static final long serialVersionUID = 1L;

		private String name;
		private String age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "UserBean [name=" + name + ", age=" + age + "]";
		}
	}
}