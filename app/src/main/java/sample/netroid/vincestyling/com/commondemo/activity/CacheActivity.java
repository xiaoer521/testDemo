package sample.netroid.vincestyling.com.commondemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveBitmap;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveDrawable;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveJsonArray;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveJsonObject;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveMedia;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveObject;
import sample.netroid.vincestyling.com.commondemo.cacheDemo.SaveString;

public class CacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
    }
    public void string(View v) {
        startActivity(new Intent().setClass(this, SaveString.class));
    }

    public void jsonobject(View v) {
        startActivity(new Intent().setClass(this, SaveJsonObject.class));
    }

    public void jsonarray(View v) {
        startActivity(new Intent().setClass(this, SaveJsonArray.class));
    }

    public void bitmap(View v) {
        startActivity(new Intent().setClass(this, SaveBitmap.class));
    }

    public void media(View v) {
        startActivity(new Intent().setClass(this, SaveMedia.class));
    }

    public void drawable(View v) {
        startActivity(new Intent().setClass(this, SaveDrawable.class));
    }

    public void object(View v) {
        startActivity(new Intent().setClass(this, SaveObject.class));
    }
}
