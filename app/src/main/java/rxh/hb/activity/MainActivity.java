package rxh.hb.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int flag = 0;
    private TextView tv;
    private Button btn_one, btn_two, btn_three, btn_four;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv.setText("FixedThreadPool线程池被开启");
                    Log.e("FixedThreadPool", "FixedThreadPool线程池被开启");
                    break;
                case 2:
                    tv.setText("CachedThreadPool线程池被开启");
                    Log.e("CachedThreadPool", "CachedThreadPool线程池被开启");
                    break;
                case 3:
                    tv.setText("ScheduledThreadPool线程池被开启");
                    Log.e("ScheduledThreadPool", "ScheduledThreadPool线程池被开启");
                    break;
                case 4:
                    tv.setText("SingleThreadPool线程池被开启");
                    Log.e("SingleThreadPool", "SingleThreadPool线程池被开启");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    public void initview() {
        tv = (TextView) findViewById(R.id.tv);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_four.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                flag = 1;
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
                fixedThreadPool.execute(command);
                break;
            case R.id.btn_two:
                flag = 2;
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
                cachedThreadPool.execute(command);
                break;
            case R.id.btn_three:
                flag = 3;
                ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
                //2000ms后执行
                scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
                //延迟10ms后，每隔1000ms执行一次command
                scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);
                break;
            case R.id.btn_four:
                flag = 4;
                ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
                singleThreadPool.execute(command);
                break;
            default:
                break;
        }
    }

    //新建一个线程
    Runnable command = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(flag);
        }
    };

}
