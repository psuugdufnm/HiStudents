package org.birdback.histudents.core;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.birdback.histudents.R;
import org.birdback.histudents.utils.ReflectUtil;


/**
 * Created by meixin.song on 2018/3/9
 */

public abstract class CoreBaseFragment<P extends CoreBaseContract.CoreBasePresenter, M extends CoreBaseContract.CoreBaseModel> extends Fragment{


    public String TAG = getClass().getSimpleName();
    public P mPresenter;
    public M mModel;
    public Activity mActivity;
    private Dialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mPresenter = ReflectUtil.getTypeInstance(this, 0);
        mModel = ReflectUtil.getTypeInstance(this, 1);
        if (this instanceof CoreBaseContract.CoreBaseView) {
            mPresenter.attachVM(this, mModel, getActivity());
        }
        initView(view, savedInstanceState);
        getBundle(getArguments());
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initListener() {
    }

    public void getBundle(Bundle arguments) {
    }

    public View getLayoutView() {
        return null;
    }

    public abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param view               view
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
    }

    public final void showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = getProgressDialog();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (mProgressDialog == null) {
            ProgressDialog dialog = new ProgressDialog(activity, R.style.dialog_bg_transparent);
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
