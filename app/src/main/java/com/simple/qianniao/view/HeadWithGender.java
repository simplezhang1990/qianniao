package com.simple.qianniao.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.simple.qianniao.R;

/**
 * Created by Baiming.Zhang on 4/12/2016.
 */
public class HeadWithGender implements BitmapDisplayer {

    protected final Integer strokeColor;
    protected final float strokeWidth;
    protected int mGender;
    protected Context mContext;

    public HeadWithGender(Context context,Integer strokeColor, int gender) {
        this(context,strokeColor, 0, gender);
    }

    public HeadWithGender(Context context,Integer strokeColor, float strokeWidth, int gender) {
        mContext = context;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        mGender = gender;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected");
        }

        imageAware.setImageDrawable(new CircleDrawable( mContext ,bitmap,strokeColor,strokeWidth,mGender ));
    }

    public static class CircleDrawable extends Drawable{
        protected float radius;
        protected final RectF mRect = new RectF();
        protected final RectF mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint mPaint;
        protected final Paint strokePaint;
        protected final Paint mGenderIconPaint;
        protected final float strokeWidth;
        protected float strokeRadius;

        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;

        protected int mGender;
        protected Bitmap mGenderBitmap = null;
        protected int mMarginLeft;
        protected Context mContext;

        public CircleDrawable(Context context,Bitmap bitmap,Integer strokeColor,float strokeWidth,int gender){
            mContext = context;
            radius = Math.min(bitmap.getWidth(),bitmap.getHeight())/2;
//            mMarginLeft = (int)context.getResources().getDimension(R.dimen.px_91);
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
            mGender = gender;
            if(mGender!=GENDER_MALE||mGender!=GENDER_FEMALE){
                mGender=GENDER_MALE;
            }

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setShader(bitmapShader);
            mPaint.setFilterBitmap(true);
            mPaint.setDither(true);

            mGenderIconPaint = new Paint();
            mGenderIconPaint.setAntiAlias(true);

            if(strokeColor!=null){
                strokePaint = new Paint();
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setColor(strokeColor);
                strokePaint.setStrokeWidth(strokeWidth);
                strokePaint.setAntiAlias(true);
            }else {
                strokePaint = null;
            }

            this.strokeWidth = strokeWidth;
            strokeRadius = radius-strokeWidth/2;
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            mRect.set(0,0,bounds.width(),bounds.height());
            radius = Math.min(bounds.width(),bounds.height())/2;
            strokeRadius = radius-strokeWidth/2;

            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(mBitmapRect,mRect,Matrix.ScaleToFit.FILL);
            bitmapShader.setLocalMatrix(shaderMatrix);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(radius,radius,radius,mPaint);
            if(strokePaint!=null){
                canvas.drawCircle(radius,radius,strokeRadius,strokePaint);
            }

            if(mGender==GENDER_MALE){
                if (mGenderBitmap==null){
                    mGenderBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.man);
                }
            }else if(mGender==GENDER_FEMALE){
                if(mGenderBitmap==null){
                    mGenderBitmap=BitmapFactory.decodeResource(mContext.getResources(), R.drawable.woman);
                }
            }
            canvas.drawBitmap(mGenderBitmap,mMarginLeft,0,mGenderIconPaint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
    }
}
