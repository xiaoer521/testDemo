package sample.netroid.vincestyling.com.commondemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sample.netroid.vincestyling.com.commondemo.R;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private boolean mQuit;
    private CustomThread customThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initView();
        customThread = new CustomThread();
        customThread.start();

    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                customThread.interrupt();
            //    mQuit=true;
                break;
            case R.id.button2:
                customThread.stop();
                break;
            case R.id.button3:
                customThread.destroy();
                break;
        }
    }

    public class CustomThread extends Thread {
        @Override
        public void run() {
            while (!mQuit) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    mQuit=true;
                }
            }
        }
    }
}
