package org.birdback.histudents.core;

import android.support.v4.app.FragmentActivity;

/**
 * Created by meixin.song on 2018/3/9
 */

public interface CoreBaseContract {

    abstract class CoreBasePresenter<V, M extends CoreBaseModel> {
        public V mView;
        public M mMode;
        public FragmentActivity mActivity;

        public void attachVM(V view, M mode, FragmentActivity activity) {

            this.mView = view;
            this.mMode = mode;
            this.mActivity = activity;
            this.mMode.onStart(activity, this);
            this.onCreate();

        }

        public void detachVM() {
            mView = null;
            mMode = null;
            this.onDestroy();
        }

        public void showProgressDialog() {
            if (mView instanceof CoreBaseActivity) {
                CoreBaseActivity activity = (CoreBaseActivity) mView;
                if (!activity.isFinishing()) {
                    activity.showProgressDialog();
                }
            } else if (mView instanceof CoreBaseFragment) {
                ((CoreBaseFragment) mView).showProgressDialog();
            }
        }

        public void closeProgressDialog() {
            if (mView instanceof CoreBaseActivity) {
                ((CoreBaseActivity) mView).closeProgressDialog();
            } else if (mView instanceof CoreBaseFragment) {
                ((CoreBaseFragment) mView).closeProgressDialog();
            }
        }

        public abstract void onCreate();

        public abstract void onDestroy();

        public abstract void showMessage(String msg);
    }

    interface CoreBaseView {
        void showMessage(String msg);
    }

    interface CoreBaseModel<P> {
        void onStart(FragmentActivity activity, P presenter);
    }

}
