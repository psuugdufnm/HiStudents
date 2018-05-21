package org.birdback.histudents.net;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.core.CoreBaseContract;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.RequestVo;
import org.birdback.histudents.entity.ResponseEntity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.helper.RetrofitHelper;
import org.birdback.histudents.utils.LogUtil;


public class HttpServer {
    public static final int SUCCESS = 1;
    public static final int FAILURE = Integer.MIN_VALUE;

    public static <T> void getDataFromServer(RequestVo<T> reqVo, OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure) {
        startService(reqVo.observable, new MyObserver<>(onSuccess, onFailure));
    }

    public static <T> void getDataFromServer(CoreBaseActivity activity, RequestVo<T> reqVo, OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure) {
        Observer<T> subscriber;
        if (reqVo.hasDialog) {
            if (activity != null && !activity.isFinishing())
                activity.showProgressDialog();
            subscriber = new MyObserver<>(onSuccess, onFailure, activity);
        } else {
            subscriber = new MyObserver<>(onSuccess, onFailure);
        }
        startService(reqVo.observable, subscriber);
    }

    public static <T> void getDataFromServer(CoreBaseFragment fragment, RequestVo<T> reqVo, OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure) {
        Observer<T> subscriber;
        if (reqVo.hasDialog) {
            if (fragment != null)
                fragment.showProgressDialog();
            subscriber = new MyObserver<>(onSuccess, onFailure, fragment);
        } else {
            subscriber = new MyObserver<>(onSuccess, onFailure);
        }
        startService(reqVo.observable, subscriber);
    }

    public static <T> void getDataFromServer(CoreBaseContract.CoreBasePresenter presenter,
                                             RequestVo reqVo,
                                             OnSuccessCallBack<T> onSuccess,
                                             OnFailureCallBack onFailure) {
        Observer<T> subscriber;
        if (reqVo.hasDialog) {
            presenter.showProgressDialog();
            subscriber = new MyObserver<>(onSuccess, onFailure, presenter);
        } else {
            subscriber = new MyObserver<>(onSuccess, onFailure);
        }
        startService(reqVo.observable, subscriber);
    }


    private static <T> void startService(Observable<ResponseEntity<T>> observable, Observer<T> observer) {
        observable.compose(HttpServer.<T>applySchedulers())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private static class MyObserver<T> implements Observer<T> {
        private OnSuccessCallBack<T> onSuccess;
        private OnFailureCallBack onFailure;
        private CoreBaseActivity activity;
        private CoreBaseFragment fragment;
        private CoreBaseContract.CoreBasePresenter presenter;

        private MyObserver(OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
        }

        private MyObserver(OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure, CoreBaseActivity activity) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
            this.activity = activity;
        }

        private MyObserver(OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure, CoreBaseFragment fragment) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
            this.fragment = fragment;
        }

        private MyObserver(OnSuccessCallBack<T> onSuccess, OnFailureCallBack onFailure, CoreBaseContract.CoreBasePresenter presenter) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
            this.presenter = presenter;
        }

        @Override
        public void onError(Throwable e) {
            int code = Integer.MIN_VALUE;
            String msg = "网络错误!";
            StringBuffer sb = new StringBuffer();
            sb.append("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════")
                    .append("\n║ ").append("HTTP_FAILURE : ").append(e.getClass().getSimpleName()).append(" : ").append(e.getMessage())
                    .append("\n╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            LogUtil.i(sb.toString());
            LogUtil.e(e);
            if (e instanceof APIException) {
                APIException apiException = (APIException) e;
                code = apiException.code;
                msg = apiException.message;
                LogUtil.i("APIException : code = " + code + " , msg = " + msg);
            } else if (e instanceof HttpException) {
                HttpException he = (HttpException) e;
                he.code();
                LogUtil.i("HttpException : code = " + he.code() + " , msg = " + he.getMessage());
            }

            if (onFailure != null) {
                onFailure.onFailure(code, msg);
            }
            closeDialog();
        }


        @Override
        public void onComplete() {
            onSuccess = null;
            onFailure = null;
            closeDialog();
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T t) {
            LogUtil.i("HTTP_SUCCESS : " + t.getClass().getName());
            if (onSuccess != null) {
                try {
                    onSuccess.onSuccess(t);
                } catch (ClassCastException e1) {
                    LogUtil.e(e1);
                    onError(new APIException(FAILURE, "解析实体映射异常"));
                }
            }
        }

        private void closeDialog() {
            if (presenter != null) {
                presenter.closeProgressDialog();
                presenter = null;
            } else if (activity != null) {
                if (activity.isFinishing() || activity.isDestroyed()) return;
                activity.closeProgressDialog();
                activity = null;
            } else if (fragment != null) {
                if (fragment.getActivity()==null||fragment.getActivity().isFinishing()||fragment.getActivity().isDestroyed()) return;
                fragment.closeProgressDialog();
                fragment = null;
            }

            onSuccess = null;
            onFailure = null;
        }

    }


    public static <T> T getService(final Class<T> service) {
        return RetrofitHelper.getService(false, service);
    }

    private static <T> ObservableTransformer<ResponseEntity<T>, T> applySchedulers() {
        return new ObservableTransformer<ResponseEntity<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseEntity<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<ResponseEntity<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(ResponseEntity<T> tResponseEntity) throws Exception {
                                return flatResponse(tResponseEntity);
                            }
                        });
            }
        };
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response 响应体的包装类 code,msg,data
     * @param <T>      响应体data 的解析泛型
     * @return Observable
     */
    private static <T> Observable<T> flatResponse(final ResponseEntity<T> response) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {

                int code = response.getOk();
               /* if (code >= 200300 && code < 200400) {
                    if (FrameWorkConfig.frameworkSupport != null && !FrameWorkConfig.isTokenInvalid) {
                        FrameWorkConfig.isTokenInvalid = true;
                        FrameWorkConfig.frameworkSupport.onTokenInvalid();
                    }
                    //如果是token的问题,显示的错误的message统一为登录过期
                    response.setMessage("登录过期");
                    Session.logout();
                }*/
                if (response != null && response.isSuccess()) {
                    T t = response.getData();
                    t = t == null ? (T) "" : t;
                    e.onNext(t);
                }else {
                    e.onError(new APIException(response.getOk(), response.getMessage()));
                }
                e.onComplete();
            }
        });
    }


    /**
     * 自定义异常，当接口返回的{@link ResponseEntity#ok}不为{@link HttpServer#SUCCESS}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    private static class APIException extends Exception {
        public int code;
        String message;

        APIException(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

}
