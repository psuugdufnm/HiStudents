package org.birdback.histudents;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import org.birdback.histudents.Fragment.MyFragment;
import org.birdback.histudents.Fragment.OrderManagerFragment;
import org.birdback.histudents.Fragment.OrderSearchFragment;
import org.birdback.histudents.core.CoreBaseFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private RadioButton radio0,radio1,radio2;
    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    private CoreBaseFragment mCacheFragment;
    private OrderManagerFragment managerFragment;
    private MyFragment myFragment;
    private OrderSearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();

        switchFragment(0);

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
                    searchFragment = new OrderSearchFragment();
                    transaction.add(R.id.container, searchFragment, MyFragment.class.getSimpleName());
                } else {
                    transaction.show(searchFragment);
                }
                mCacheFragment = searchFragment;
                break;
            case 2:
                if (myFragment == null) {
                    myFragment = new MyFragment();
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


}
