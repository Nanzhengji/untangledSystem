package com.myapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;

import com.myapp.R;

import java.util.ArrayList;


public class PlayView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;
    //使mStrs从外部传入
    private ArrayList<String> mStrs = new ArrayList<String>();
    private int[] mColor = new int[]{0XFFFF7F50,0XFFFF7256,0XFFFF6347,0XFFFF8C69,0XFFFF7256,0XFFFF6347,0XFFFF7F50,0XFFFF7256,0XFFFF8C69,0XFFFF6347};
    private int mItemCount = 1;
    private int mRadius;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private RectF mRange = new RectF();
    private double mSpeed;
    private volatile int mStartAngle = 0;
    private boolean isShouldEnd;
    private int mCenter;
    private int mPadding;


    //背景图片
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.yun);
   //文字大小20sp
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());

    public PlayView(Context context) {
        this(context,null);
    }

    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = width-mPadding*2;
        mCenter = width/2;
        setMeasuredDimension(width,width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //初始化
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mItemCount = mStrs.size();
        mTextPaint = new Paint();
        mTextPaint.setColor(0Xff000000);
        mTextPaint.setTextSize(mTextSize);
        //盘快绘制的范围
        mRange = new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning =false;
    }

    @Override
    public void run() {
        while (isRunning){
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            if(end - start <50){
                try{
                    Thread.sleep(50-(end - start));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void draw(){
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //绘制转盘
                drawBg();
                float tmpAngle = mStartAngle;
                float sweepAngle = 360/mItemCount;
                for(int i=0;i<mItemCount;i++){
                    mArcPaint.setColor(mColor[i]);
                    mCanvas.drawArc(mRange,tmpAngle,sweepAngle,true,mArcPaint);
                    //绘制文本
                    drawText(tmpAngle,sweepAngle,mStrs.get(i));
                    tmpAngle += sweepAngle;
                }
                mStartAngle += mSpeed;
                if(isShouldEnd){
                    mSpeed -=1;
                }
                if(mSpeed<=0){
                    mSpeed=0;
                    isShouldEnd=false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放canvas
            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
public void playGo(){
        mSpeed=50;
        mSpeed-=1;
        if(mSpeed<=0){
            mSpeed=0;
            isShouldEnd=false;
        }else{
            isShouldEnd=true;
        }
}
    public void playStart(){
        mSpeed = 80;
        isShouldEnd = false;
    }
    public void playEnd(){
        isShouldEnd = true;
    }

    public boolean isStart() {
        return mSpeed != 0;
    }

    public boolean isShouldEnd() {
        return isShouldEnd;
    }
    private void drawBg(){
        mCanvas.drawBitmap(mBgBitmap,null,new Rect(-20,-20,getMeasuredWidth()+mPadding,getMeasuredHeight()),null);
    }
    private  void drawText(float tmpAngle,float sweepAngle,String string){
        Path path = new Path();
        path.addArc(mRange,tmpAngle,sweepAngle);
        float textWidth = mTextPaint.measureText(string);

        int hOffset = (int)(mRadius*Math.PI/mItemCount/2-textWidth/2);
        int vOffset = mRadius/2/5;
        mCanvas.drawTextOnPath(string,path,hOffset,vOffset,mTextPaint);
    }
    //从外部获取选项个数和内容
    public void setStrs(ArrayList<String> strs, int mItemCount){
        this.mStrs = strs;
        this.mItemCount = mItemCount;
    }

}
