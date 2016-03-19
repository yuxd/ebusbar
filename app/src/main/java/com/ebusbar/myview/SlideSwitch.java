package com.ebusbar.myview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ebusbar.pile.R;

/**
 * 自定义Switch
 * @author Jelly
 */
public class SlideSwitch extends View
{
    public static final String TAG = "SlideSwitch";
    public static final int SWITCH_OFF = 0;//关闭状态
    public static final int SWITCH_ON = 1;//打开状态
    public static final int SWITCH_SCROLING = 2;//滚动状态
    /**
     * 开关的状态
     */
    private int mSwitchStatus = SWITCH_OFF;
    /**
     * 是否可以点击，如果设置为不可点击，那么也就不可滑
     */
    private boolean isClick = true;
    /**
     * 是否可以滑动
     */
    private boolean isSlide = false;
    /**
     * 按下的x坐标
     */
    private int downX;
    /**
     * 移动时的x坐标
     */
    private int moveX;
    /**
     * 抬起时的x坐标
     */
    private int upX;
    /**
     * 当前滑块中心的x坐标
     */
    private int thumbCenterX;

    private OnSwitchChangedListener mOnSwitchChangedListener = null;

    //开关状态图
    Bitmap mSwitch_off, mSwitch_on, mSwitch_thumb;
 
    public SlideSwitch(Context context)
    {
        this(context, null);
    }
 
    public SlideSwitch(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
 
    public SlideSwitch(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
 
    //初始化三幅图片
    private void init()
    {
        Resources res = getResources();
        mSwitch_off = BitmapFactory.decodeResource(res, R.drawable.switch_close);
        mSwitch_on = BitmapFactory.decodeResource(res, R.drawable.switch_on);
        mSwitch_thumb = BitmapFactory.decodeResource(res, R.drawable.oval);
    }

    /**
     * 设置LayoutParams
     * @param params
     */
    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params)
    {
        params.width = mSwitch_on.getWidth();
        params.height = mSwitch_on.getHeight();
        super.setLayoutParams(params);
    }
     
    /**
     * 为开关控件设置状态改变监听函数
     * @param onSwitchChangedListener 参见 {@link OnSwitchChangedListener}
     */
    public void setOnSwitchChangedListener(OnSwitchChangedListener onSwitchChangedListener)
    {
        mOnSwitchChangedListener = onSwitchChangedListener;
    }
     
    /**
     * 设置开关的状态
     * @param on 是否打开开关 打开为true 关闭为false
     */
    public void setStatus(boolean on)
    {
        mSwitchStatus = ( on ? SWITCH_ON : SWITCH_OFF);
    }

    /**
     * 设置是否可以点击
     * @param isClick
     */
    public void setClick(boolean isClick) {
        this.isClick = isClick;
    }

    /**
     * 获得是否可以点击状态
     * @return
     */
    public boolean isClick() {
        return isClick;
    }

    /**
     *  设置是否可以滑动
     * @param isSlide
     */
    public void setSlide(boolean isSlide) {
        this.isSlide = isSlide;
    }

    /**
     * 获得是否可以滑动状态
     * @return
     */
    public boolean isSlide() {
        return isSlide;
    }

    /**
     * 改变开关状态
     */
    public void changeSwitchStatus(){
        if(mSwitchStatus == SWITCH_ON)
            setStatus(false);
        else
            setStatus(true);
        invalidate(); //重画
        //状态改变的时候 回调事件函数
        if(mOnSwitchChangedListener != null)
        {
            mOnSwitchChangedListener.onSwitchChanged(this, mSwitchStatus);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(!isClick) return true;//如果不能点击直接返回
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if(!isSlide) return true; // 不可滑动，直接返回
            downX = (int)event.getX();
            if((mSwitchStatus == SWITCH_ON && downX>mSwitch_on.getWidth()-mSwitch_thumb.getWidth()/2) || (mSwitchStatus == SWITCH_OFF)&&downX<mSwitch_thumb.getWidth()/2) return true;//如果点击的位置不能滑动直接返回
            mSwitchStatus = SWITCH_SCROLING;
            thumbCenterX = downX;
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE: //移动
            if(!isSlide) return true; //不可滑动，返回
            moveX = (int) event.getX();
            if((moveX > 0 &&moveX<mSwitch_thumb.getWidth()/2)||(moveX>(mSwitch_on.getWidth()-mSwitch_thumb.getWidth()/2)&&moveX<mSwitch_on.getWidth())) return true;
            mSwitchStatus = SWITCH_SCROLING;
            if(moveX < downX){ //左滑
                thumbCenterX--;
            }else{
                thumbCenterX++;
            }
            if(thumbCenterX > (mSwitch_on.getWidth()-mSwitch_thumb.getWidth()/2) || thumbCenterX < (mSwitch_thumb.getWidth()/2)){
                return true;
            }
            invalidate();
            downX = thumbCenterX;
            break;
        case MotionEvent.ACTION_UP: //手指抬起
            upX = (int) event.getX();
            if(upX > mSwitch_on.getWidth()/2){
                mSwitchStatus = SWITCH_ON;
            }else{
                mSwitchStatus = SWITCH_OFF;
            }
            invalidate();
            //状态改变的时候 回调事件函数
            if(mOnSwitchChangedListener != null)
            {
                mOnSwitchChangedListener.onSwitchChanged(this, mSwitchStatus);
            }
            break;
        default:
            break;
        }
        return true;
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
    }
 
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //绘图的时候 内部用到了一些数值的硬编码，其实不太好，
        //主要是考虑到图片的原因，图片周围有透明边界，所以要有一定的偏移
        //硬编码的数值只要看懂了代码，其实可以理解其含义，可以做相应改进。
        if(mSwitchStatus == SWITCH_OFF)
        {
            drawBitmap(canvas, null, null, mSwitch_off); //画关闭的框
            drawBitmap(canvas, null, null, mSwitch_thumb); //画关闭的圆
        }
        else if(mSwitchStatus == SWITCH_ON)
        {
            drawBitmap(canvas, null, null, mSwitch_on);
            Rect rect = new Rect(mSwitch_on.getWidth()-mSwitch_thumb.getWidth(),0,mSwitch_on.getWidth(),mSwitch_thumb.getHeight()); //把打开的点移动到后面
            drawBitmap(canvas, null, rect, mSwitch_thumb);
        }
        else //SWITCH_SCROLING
        {
            Rect onRect = new Rect(0,0,thumbCenterX,mSwitch_on.getHeight());
            drawBitmap(canvas,null,onRect,mSwitch_on);
            Rect closeRect = new Rect(thumbCenterX,0,mSwitch_off.getWidth(),mSwitch_off.getHeight());
            drawBitmap(canvas,null,closeRect,mSwitch_off);
            Rect thumbRect = new Rect(thumbCenterX-mSwitch_thumb.getWidth()/2,0,thumbCenterX+mSwitch_thumb.getWidth()/2,mSwitch_thumb.getHeight());
            drawBitmap(canvas,null,thumbRect,mSwitch_thumb);
        }
 
    }
 
    public void drawBitmap(Canvas canvas, Rect src, Rect dst, Bitmap bitmap)
    {
        dst = (dst == null ? new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) : dst);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, src, dst, paint);
    }
 
    public static interface OnSwitchChangedListener
    {
        /**
         * 状态改变 回调函数
         * @param status  SWITCH_ON表示打开 SWITCH_OFF表示关闭
         */
        public abstract void onSwitchChanged(SlideSwitch obj, int status);
    }
 
}