package sample.netroid.vincestyling.com.commondemo.cacheDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cache.Cache;

/**
 * *****************************************
 * @author  sing
 * @文件名称 : SaveMediaActivity.java
 * @创建时间 : 2015年9月5日 下午1:48:21
 * @文件描述 : 缓存bitmap
 * *****************************************
 */
public class SaveMedia extends Activity implements Runnable {
	private String mUrl = "http://www.largesound.com/ashborytour/sound/brobob.mp3";
	private static String CACHE_KEY = "brobob";

	private TextView text;
	private Cache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_file);

		init();

		mCache = Cache.get(this);
	}

	/**
	 * @描述 : 初始化控件
	 */
	private void init() {
		text = (TextView) findViewById(R.id.text);
	}

	/**
	 * @描述 : 点击save事件
	 * @param v
	 */
	public void save(View v) {
		text.setText("Loading...");
		new Thread(this).start();
	}

	/**
	 * @描述 : 点击read事件
	 * @param v
	 */
	public void read(View v) {
		InputStream stream = null;
		try {
			stream = mCache.get(CACHE_KEY);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (stream == null) {
			Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
			text.setText("没有找到数据");
			return;
		}
		try {
			text.setText("文件大小 : " + stream.available());
		} catch (IOException e) {
			text.setText("error " + e.getMessage());
		}
	}

	/**
	 * @描述 : 点击clear事件
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove(CACHE_KEY);
	}

	@Override
	public void run() {
		OutputStream ostream = null;
		try {
			ostream = mCache.put(CACHE_KEY);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (ostream == null) {
			Toast.makeText(this, "打开错误", Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			URL u = new URL(mUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.connect();
			InputStream stream = conn.getInputStream();

			byte[] buff = new byte[1024];
			int counter;

			while ((counter = stream.read(buff)) > 0) {
				ostream.write(buff, 0, counter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// cache update
				ostream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					text = (TextView) findViewById(R.id.text);
					text.setText("done...");
				}
			});
		}
	}
}