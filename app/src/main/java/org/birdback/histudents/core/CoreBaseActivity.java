package org.birdback.histudents.core;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.utils.ReflectUtil;


/**
 * base activity
 */

public abstract class CoreBaseActivity<P extends CoreBaseContract.CoreBasePresenter, M extends CoreBaseContract.CoreBaseModel> extends AppCompatActivity{



    public P mPresenter;
    public M mModel;
    private String mTAG;
    protected Dialog mProgressDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        init(savedInstanceState);
    }



    private void init(Bundle savedInstanceState) {
        mTAG = getClass().getSimpleName();
        mPresenter = ReflectUtil.getTypeInstance(this, 0);
        mModel = ReflectUtil.getTypeInstance(this, 1);
        if (this instanceof CoreBaseContract.CoreBaseView) {
            mPresenter.attachVM(this, mModel, this);
        }
        initView(savedInstanceState);
        initListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        closeProgressDialog();
    }

    public abstract int getLayoutId();
    public abstract void initListener();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
                return true;
            } else {
                try {
                    getSupportFragmentManager().popBackStackImmediate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    /**
     * 点击屏幕收起键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager;
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 点击屏幕收起键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 点击屏幕收起键盘
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public final void showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = getProgressDialog();
        if (mProgressDialog == null) {
            ProgressDialog dialog = new ProgressDialog(this, R.style.dialog_bg_transparent);
            // dialog.setMessage("请稍候,加载中...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            mProgressDialog = dialog;
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.dialog_loading);
        } else {
            mProgressDialog.show();
        }
    }

    public final void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected Dialog getProgressDialog() {
        return null;
    }
}
