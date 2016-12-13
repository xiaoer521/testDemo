package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveStringActivity.java
 * @创建时间 : 2015年9月5日 下午1:47:39
 * @文件描述 : 缓存string
 * *****************************************
 */
public class SaveString extends Activity {

	private EditText etString;
	private TextView tvString;

	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_string);
		init();

		mCache = Cache.get(this);
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		etString = (EditText) findViewById(R.id.et_string);
		tvString = (TextView) findViewById(R.id.tv_string);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		if (etString.getText().toString().trim().length() == 0) {
			Toast.makeText(this, "输入为空", Toast.LENGTH_SHORT).show();
		}
		mCache.put("testString", etString.getText().toString());
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		String testString = mCache.getAsString("testString");
		if (testString == null) {
			Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
			tvString.setText(null);
			return;
		}
		tvString.setText(testString);
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		if (mCache.remove("testString")) {
			Toast.makeText(this, "移除成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "已经移除", Toast.LENGTH_SHORT).show();
		}
	}
}