package sample.netroid.vincestyling.com.commondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sample.netroid.vincestyling.com.commondemo.activity.AdActivity;
import sample.netroid.vincestyling.com.commondemo.activity.CacheActivity;
import sample.netroid.vincestyling.com.commondemo.activity.ThreadActivity;
import sample.netroid.vincestyling.com.commondemo.mediation.PingStartVideo;
import sample.netroid.vincestyling.com.commondemo.network.request.StringRequest;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;
import sample.netroid.vincestyling.com.commondemo.utils.VolleyUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private PingStartVideo mPingStartVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button1:
                intent = new Intent(MainActivity.this, VideoViewActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                StringRequest stringRequest = new StringRequest("http://d3ahdvlqntqhyj.cloudfront.net/video.zip", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                stringRequest.setTag("ghpppp");
                VolleyUtil.getDateRequestQueue().add(stringRequest);
                //   ;
                break;
            case R.id.button3:
                intent = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent);
                break;
            case R.id.button4:
                intent = new Intent(MainActivity.this, AdActivity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                intent = new Intent(MainActivity.this, CacheActivity.class);
                startActivity(intent);
                break;
        }
    }
}
