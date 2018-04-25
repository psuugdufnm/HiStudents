package org.birdback.histudents;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import org.birdback.histudents.Fragment.MyFragment;
import org.birdback.histudents.Fragment.OrderManagerFragment;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.VerifyUtil;
import org.birdback.histudents.web.WebFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private RadioButton radio0,radio1,radio2;
    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    private CoreBaseFragment mCacheFragment;
    private OrderManagerFragment managerFragment;
    private MyFragment myFragment;
    private WebFragment searchFragment;


    public static void start(Context context) {
        context.startActivity(new Intent(context,MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (VerifyUtil.isEmpty(Session.getCookie())) {
            LoginActivity.start(MainActivity.this);
            finish();
        }
        setContentView(R.layout.activity_main);

        initView();

        initListener();

        switchFragment(0);

        checkAudioInfo();

        requestPermission();
    }

    private void checkAudioInfo() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int max = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int current = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        if (max-2 > current) {
            //把音量设置到最大
            am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, max,1);
        }
    }

    private void initListener() {
        radio0.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
    }

    private void initView() {
        radio0 = findViewById(R.id.radio_0);
        radio1 = findViewById(R.id.radio_1);
        radio2 = findViewById(R.id.radio_2);
        mFrameLayout = findViewById(R.id.container);
        mFragmentManager = getSupportFragmentManager();
    }

    private void switchFragment(int index) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mCacheFragment != null) {
            transaction.hide(mCacheFragment);
        }
        switch (index) {
            case 0:
                if (managerFragment == null) {
                    managerFragment = new OrderManagerFragment();
                    transaction.add(R.id.container, managerFragment, OrderManagerFragment.class.getSimpleName());
                } else {
                    transaction.show(managerFragment);
                }
                mCacheFragment = managerFragment;
                break;
            case 1:
                if (searchFragment == null) {
                    searchFragment = new WebFragment();
                    searchFragment.setUrl("http://store.birdback.org/test/shop");
                    transaction.add(R.id.container, searchFragment, MyFragment.class.getSimpleName());
                } else {
                    transaction.show(searchFragment);
                }
                mCacheFragment = searchFragment;
                break;
            case 2:
                if (myFragment == null) {
                    myFragment = new MyFragment();
//                    myFragment.setUrl("http://store.birdback.org/home/home");
                    transaction.add(R.id.container, myFragment, MyFragment.class.getSimpleName());
                } else {
                    transaction.show(myFragment);
                }
                mCacheFragment = myFragment;
                break;
            default:
                break;
        }
        transaction.commit();
    }

//

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radio_0:
                switchFragment(0);
                break;
            case R.id.radio_1:
                switchFragment(1);
                break;
            case R.id.radio_2:
                switchFragment(2);
                break;
            default:
                break;
        }
    }

    private int REQUEST_PERMISSION_ACCESS_LOCATION = 102;
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkAccessFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_ACCESS_LOCATION);
                Log.e(TAG, "没有权限，请求权限");
                return;
            }
            Log.e(TAG, "已有定位权限");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(getPackageName(), "开启权限permission granted!");
            } else {
                Log.e(TAG, "没有定位权限，请先开启!");
            }
        }
    }


}
