package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveJsonObjectActivity.java
 * @创建时间 : 2015年9月5日 下午1:47:50
 * @文件描述 : 缓存jsonobject
 * *****************************************
 */
public class SaveJsonObject extends Activity {

	private TextView tvJsonobject, tvJsonobjectRes;
	private JSONObject jsonObject;

	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_jsonobject);
		// 初始化控件
		initView();

		mCache = Cache.get(this);
		jsonObject = new JSONObject();
		try {
			jsonObject.put("name", "Yoson");
			jsonObject.put("age", 18);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		tvJsonobject.setText("原数据" + jsonObject.toString());
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void initView() {
		tvJsonobject = (TextView) findViewById(R.id.tv_jsonobject);
		tvJsonobjectRes = (TextView) findViewById(R.id.tv_jsonobject_res);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		mCache.put("testJsonObject", jsonObject);
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		JSONObject testJsonObject = mCache.getAsJSONObject("testJsonObject");
		if (testJsonObject == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			tvJsonobjectRes.setText(null);
			return;
		}
		tvJsonobjectRes.setText(testJsonObject.toString());
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testJsonObject");
	}
}