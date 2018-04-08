package org.birdback.histudents.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 标题栏
 * Created by meixin.song on 2018/4/6.
 */

public class TitleView extends FrameLayout implements View.OnClickListener {

    //
    LinearLayout cusTitleViewBackImgLayout;
    //
    ImageView cusTitleViewBackImg;
    //
    TextView cusTitleViewLabel;
    //
    RadioButton cusTitleViewRbtLeft;
    //
    RadioButton cusTitleViewRbtRight;
    //
    RadioGroup cusTitleViewRgp;
    //
    ImageView cusTitleViewRightImg;
    //
    TextView cusTitleViewNumTip;
    //
    RelativeLayout cusTitleViewRightRl;
    //
    TextView cusTitleViewRightTv;
    //
    RelativeLayout cusTitleViewHead;

    private boolean backImgShow;
    private int backImgSrc;
    private String centerText;
    private boolean isRgpShow;
    private String leftRbtnText;
    private String rightRbtnText;
    private String rightLableText;
    private boolean rightLabelShow;
    private boolean rightRlShow;
    private boolean rightImgShow;
    private int rightImgSrc;
    private int rightLableImgSrc;
    private boolean rightNumShow;
    private String rightNumText;
    /* ID */
    private static final int ID_LEFT_BUTTON = 0x100001;
    private static final int ID_RIGHT_BUTTON = 0x100002;
    private OnTitleClickListener mOnTitleClickListener;
    private int rightLabelColor;


    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initFromAttributes(context,attrs);

        viewInit(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initFromAttributes(context,attrs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ID_LEFT_BUTTON:
                if (mOnTitleClickListener != null) {
                    mOnTitleClickListener.onLeftClick(v);
                }
                sendKeyBackEvent();
                break;
            case ID_RIGHT_BUTTON:
                if (mOnTitleClickListener != null) {
                    mOnTitleClickListener.onRightClick(v);
                }
                break;
            default:

                // do nothing
                break;
        }
    }

    private void sendKeyBackEvent() {
        final Context context = getContext();
        if (context instanceof Activity) {
            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
            ((Activity) context).onKeyDown(KeyEvent.KEYCODE_BACK, keyEvent);
        }
    }



    public interface OnTitleClickListener {
        void onLeftClick(View v);

        void onRightClick(View v);
    }

    /**
     * 设置控件点击响应的通知listener
     *
     * @param listener 响应事件对象
     */
    public void setOnTitleClickListener(OnTitleClickListener listener) {
        mOnTitleClickListener = listener;
    }

    private void initFromAttributes(Context context,AttributeSet attrs){
        if (null!=attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CusTitleView);

            backImgShow = typedArray.getBoolean(R.styleable.CusTitleView_title_back_isShow,true);
            backImgSrc = typedArray.getResourceId(R.styleable.CusTitleView_title_back_src,R.drawable.btn_break);
            centerText = typedArray.getString(R.styleable.CusTitleView_title_center_text);
            isRgpShow = typedArray.getBoolean(R.styleable.CusTitleView_title_rgp_isShow,false);
            leftRbtnText = typedArray.getString(R.styleable.CusTitleView_title_left_rbtn_text);
            rightRbtnText = typedArray.getString(R.styleable.CusTitleView_title_right_rbth_text);
            rightLableText = typedArray.getString(R.styleable.CusTitleView_title_right_label);
            rightLabelShow = typedArray.getBoolean(R.styleable.CusTitleView_title_right_label_isShow,false);
            rightLabelColor =typedArray.getColor(R.styleable.CusTitleView_title_right_label_color, 0);
            rightRlShow = typedArray.getBoolean(R.styleable.CusTitleView_title_right_rl_isShow,false);
            rightImgSrc = typedArray.getResourceId(R.styleable.CusTitleView_title_right_img_src,0);
            rightLableImgSrc = typedArray.getResourceId(R.styleable.CusTitleView_title_right_label_img_src,0);
            rightImgShow = typedArray.getBoolean(R.styleable.CusTitleView_title_right_img_isShow,false);
            rightNumShow = typedArray.getBoolean(R.styleable.CusTitleView_title_right_num_isShow,false);
            rightNumText = typedArray.getString(R.styleable.CusTitleView_title_right_num_text);

            typedArray.recycle();
        }else{
            centerText = null;
            rightLableText = null;
            rightNumText = null;
        }
    }

    private void viewInit(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.title_view, this, true);

        cusTitleViewBackImgLayout = mView.findViewById(R.id.cus_title_view_back_img_ll);
        cusTitleViewBackImg = mView.findViewById(R.id.cus_title_view_back_img);
        cusTitleViewLabel = mView.findViewById(R.id.cus_title_view_label);
        cusTitleViewRbtLeft = mView.findViewById(R.id.cus_title_view_rbt_left);
        cusTitleViewRbtRight = mView.findViewById(R.id.cus_title_view_rbt_right);
        cusTitleViewRgp = mView.findViewById(R.id.cus_title_view_rgp);
        cusTitleViewRightImg = mView.findViewById(R.id.cus_title_view_right_img);
        cusTitleViewNumTip = mView.findViewById(R.id.cus_title_view_num_tip);
        cusTitleViewRightRl = mView.findViewById(R.id.cus_title_view_right_rl);
        cusTitleViewRightTv = mView.findViewById(R.id.cus_title_view_right_tv);
        cusTitleViewHead = mView.findViewById(R.id.cus_title_view_head);


        if (null!=cusTitleViewBackImg){
            cusTitleViewBackImg.setVisibility(backImgShow? VISIBLE:INVISIBLE);
            cusTitleViewBackImgLayout.setVisibility(backImgShow? VISIBLE:INVISIBLE);
            if (backImgShow){
                cusTitleViewBackImg.setImageResource(backImgSrc);
                cusTitleViewBackImgLayout.setId(ID_LEFT_BUTTON);
                cusTitleViewBackImgLayout.setOnClickListener(this);
            }
        }

        setTitleText(centerText);
        setRgpShow(isRgpShow);
        setLeftRbtnText(leftRbtnText);
        setLeftLabelShow(backImgShow);
        setRightRbtnText(rightRbtnText);
        setRightLableText(rightLableText);
        setRightLabelShow(rightLabelShow);
        setRightLabelColor(rightLabelColor);
        setRightLableSrc(rightLableImgSrc);
        setRightRlShow(rightRlShow);
        setRightImgShow(rightImgShow);
        setRightImgSrc(rightImgSrc);
        setRightNumShow(rightNumShow);
        setRightNumText(rightNumText);
    }
    /**
     * 设置标题Title
     * @param strTitle
     */
    public void setTitleText(String strTitle){
        if (null!=cusTitleViewLabel){
            cusTitleViewLabel.setText(strTitle);
        }
    }

    /**
     * 设置RadioGroup的隐现
     * @param isShow
     */
    public void setRgpShow(boolean isShow){
        if (null!=cusTitleViewRgp){
            cusTitleViewRgp.setVisibility(isShow? VISIBLE:GONE);
        }
    }

    /**
     * 设置RadioGroup左侧的RadioButton标签
     * @param text
     */
    public void setLeftRbtnText(String text){
        if (null!=cusTitleViewRbtLeft){
            cusTitleViewRbtLeft.setText(text);
        }
    }
    /**
     * 设置座边标签的隐现
     * @param isShow
     */
    public void setLeftLabelShow(boolean isShow){
        if (null!=cusTitleViewBackImg){
            cusTitleViewBackImg.setVisibility(isShow?VISIBLE:INVISIBLE);
            cusTitleViewBackImgLayout.setVisibility(isShow?VISIBLE:INVISIBLE);
        }
    }

    /**
     * 设置RadioGroup右侧的RadioButton标签
     * @param text 显示文本
     */
    public void setRightRbtnText(String text){
        if (null!=cusTitleViewRbtRight){
            cusTitleViewRbtRight.setText(text);
        }
    }

    /**
     * 设置右侧标签的显示
     * @param text 显示文本
     */
    public void setRightLableText(String text){
        if (null!=cusTitleViewRightTv){
            cusTitleViewRightTv.setText(text);
            cusTitleViewRightTv.setId(ID_RIGHT_BUTTON);
            cusTitleViewRightTv.setClickable(true);
            cusTitleViewRightTv.setOnClickListener(this);
        }
    }
    /**
     * 设置右侧标签的文字大小
     * @param size 文字大小
     */
    public void setRightLableTextSize(int size){
        if (null!=cusTitleViewRightTv){
            cusTitleViewRightTv.setTextSize(size);
        }
    }

    /**
     * 设置右侧标签图标
     * @param resourceId 资源id
     */
    public void setRightLableSrc(int resourceId){
        if (null!=cusTitleViewRightTv && rightLabelShow){
            cusTitleViewRightTv.setBackgroundResource(resourceId);
        }
    }

    /**
     * 设置右边标签的隐现
     * @param isShow 是否显示
     */
    public void setRightLabelShow(boolean isShow){
        if (null!=cusTitleViewRightTv){
            cusTitleViewRightTv.setVisibility(isShow?VISIBLE:GONE);
        }
    }
    /**
     * 设置右边标签的颜色
     * @param color 颜色
     */
    public void setRightLabelColor(int color){
        if (null!=cusTitleViewRightTv){
            cusTitleViewRightTv.setTextColor(color);
        }
    }

    /**
     * 设置右侧消息提示的布局隐现
     * @param isShow 是否显示
     */
    public void setRightRlShow(boolean isShow){
        if (null!=cusTitleViewRightRl){
            cusTitleViewRightRl.setVisibility(isShow? VISIBLE:GONE);
        }
    }

    /**
     * 设置右侧图标的隐现性
     * @param isShow 是否显示
     */
    public void setRightImgShow(boolean isShow){
        if (null!=cusTitleViewRightImg){
            cusTitleViewRightImg.setVisibility(isShow? VISIBLE:GONE);
        }

    }

    /**
     * 设置右侧图标
     * @param resourceId 资源id
     */
    public void setRightImgSrc(int resourceId){
        if (null!=cusTitleViewRightImg && rightImgShow){
            cusTitleViewRightImg.setImageResource(resourceId);
        }
    }

    /**
     * 设置右侧数字提示的
     * @param isShow 是否显示
     */
    public void setRightNumShow(boolean isShow){
        if (null!=cusTitleViewNumTip){
            cusTitleViewNumTip.setVisibility(isShow? VISIBLE:GONE);
        }
    }

    /**
     * 设置右侧数字提示文本
     * @param text 显示文本
     */
    public void setRightNumText(String text){
        if (null!=cusTitleViewNumTip && rightNumShow){
            cusTitleViewNumTip.setText(text);
        }
    }

}
