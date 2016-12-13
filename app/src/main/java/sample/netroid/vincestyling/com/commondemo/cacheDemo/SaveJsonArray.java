package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveJsonArrayActivity.java
 * @创建时间 : 2015年9月5日 下午1:48:00
 * @文件描述 : 缓存jsonarray
 * *****************************************
 */
public class SaveJsonArray extends Activity {

	private TextView tvJsonarray, tvJsonarrayRes;
	private JSONArray jsonArray;

	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_jsonarray);
		init();

		mCache = Cache.get(this);
		jsonArray = new JSONArray();
		JSONObject yosonJsonObject = new JSONObject();

		try {
			yosonJsonObject.put("name", "Yoson");
			yosonJsonObject.put("age", 18);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject michaelJsonObject = new JSONObject();
		try {
			michaelJsonObject.put("name", "Michael");
			michaelJsonObject.put("age", 25);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		jsonArray.put(yosonJsonObject);
		jsonArray.put(michaelJsonObject);

		tvJsonarray.setText("原数据：" + jsonArray.toString());
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		tvJsonarray = (TextView) findViewById(R.id.tv_jsonarray);
		tvJsonarrayRes = (TextView) findViewById(R.id.tv_jsonarray_res);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		mCache.put("testJsonArray", jsonArray);
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		JSONArray testJsonArray = mCache.getAsJSONArray("testJsonArray");
		if (testJsonArray == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			tvJsonarrayRes.setText(null);
			return;
		}
		tvJsonarrayRes.setText(testJsonArray.toString());
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testJsonArray");
	}
}