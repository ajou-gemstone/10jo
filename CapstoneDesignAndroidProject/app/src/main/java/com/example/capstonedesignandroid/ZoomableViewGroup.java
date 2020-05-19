package com.example.capstonedesignandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ZoomableViewGroup extends ScrollView {

    private static final int INVALID_POINTER_ID = 1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private float mScaleFactor = 1;
    private ScaleGestureDetector mScaleDetector;
    private Matrix mScaleMatrix = new Matrix();
    private Matrix mScaleMatrixInverse = new Matrix();

    private float mPosX;
    private float mPosY;
    private Matrix mTranslateMatrix = new Matrix();
    private Matrix mTranslateMatrixInverse = new Matrix();

    private float mLastTouchX;
    private float mLastTouchY;

    private float mFocusY;

    private float mFocusX;

    private float[] mInvalidateWorkingArray = new float[6];
    private float[] mDispatchTouchEventWorkingArray = new float[2];
    private float[] mOnTouchEventWorkingArray = new float[2];

    public ZoomableViewGroup(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mTranslateMatrix.setTranslate(0, 0);
        mScaleMatrix.setScale(1, 1);
    }

    //자식들의 위치 배치
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(l, t, l+child.getMeasuredWidth(), t + child.getMeasuredHeight());
            }
        }
    }

    //자식들의 크기 측정
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d("onMeasure", "width: "+widthMeasureSpec+"  height: "+heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    //ViewGroup이 다시 그려져야 할 경우에 자동으로 호출된다.
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("Focusss", "x,y: " + mFocusX + "  " + mFocusY);
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor, mFocusX, mFocusY);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    //ViewGroup의 터치 이벤트 리스너다.
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("dispatchTouchEvent", ": "+ev.getAction());
        mDispatchTouchEventWorkingArray[0] = ev.getX();
        mDispatchTouchEventWorkingArray[1] = ev.getY();
        mDispatchTouchEventWorkingArray = screenPointsToScaledPoints(mDispatchTouchEventWorkingArray);
        ev.setLocation(mDispatchTouchEventWorkingArray[0],
                mDispatchTouchEventWorkingArray[1]);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Although the docs say that you shouldn't override this, I decided to do
     * so because it offers me an easy way to change the invalidated area to my
     * likening.
     */
    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {

        mInvalidateWorkingArray[0] = dirty.left;
        mInvalidateWorkingArray[1] = dirty.top;
        mInvalidateWorkingArray[2] = dirty.right;
        mInvalidateWorkingArray[3] = dirty.bottom;

        mInvalidateWorkingArray = scaledPointsToScreenPoints(mInvalidateWorkingArray);
        dirty.set(Math.round(mInvalidateWorkingArray[0]), Math.round(mInvalidateWorkingArray[1]),
                Math.round(mInvalidateWorkingArray[2]), Math.round(mInvalidateWorkingArray[3]));

        location[0] *= mScaleFactor;
        location[1] *= mScaleFactor;
        return super.invalidateChildInParent(location, dirty);
    }

    //
    private float[] scaledPointsToScreenPoints(float[] a) {
        mScaleMatrix.mapPoints(a);
        mTranslateMatrix.mapPoints(a);
        return a;
    }

    //
    private float[] screenPointsToScaledPoints(float[] a){
        mTranslateMatrixInverse.mapPoints(a);
        mScaleMatrixInverse.mapPoints(a);
        return a;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //터치한 곳의 좌표를 가져온다.
        mOnTouchEventWorkingArray[0] = ev.getX();
        mOnTouchEventWorkingArray[1] = ev.getY();

        //스케일 된 포인트로 바꿔준다. (매트릭스 이용)
        mOnTouchEventWorkingArray = scaledPointsToScreenPoints(mOnTouchEventWorkingArray);

        //스케일된 포인트를 다시 motionEvent에 넣어서 스케일을 detect할 수 있도록 한다.
        ev.setLocation(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1]);
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        Log.d("onTouchViewGroup", ""+action);
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;

                // Save the ID of this pointer
                mActivePointerId = ev.getPointerId(0);
                Log.d("mActivePointerId", ": "+mActivePointerId);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;
                Log.d("DEBUG", "X: "+mPosX+" Y: "+mPosY);
                //이동 좌표 길이 만큼 matrix를 변환한다.
                mTranslateMatrix.preTranslate(dx, dy);
                mTranslateMatrix.invert(mTranslateMatrixInverse);

                mLastTouchX = x;
                mLastTouchY = y;

                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    //처음 그릴 때 화면의 크기에 맞춰준다. onWindowFocusChanged 이후에 viewgroup의 dispatchdraw가 실행된다.
    public void firstScale(float f) {
        mScaleFactor = f;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            if (detector.isInProgress()) {
                mFocusX = detector.getFocusX();
                mFocusY = detector.getFocusY();
            }
            Log.d("Scale", "mScaleFactor,x,y: " + mScaleFactor + "  " +  mFocusX + "  " + mFocusY);
            //0.1배~5배만큼 크기 조정이 가능하다.
            mScaleFactor = Math.max(0.45f, Math.min(mScaleFactor, 2.5f));
            mScaleMatrix.setScale(mScaleFactor, mScaleFactor,
                    mFocusX, mFocusY);
            mScaleMatrix.invert(mScaleMatrixInverse);
            invalidate();
            requestLayout();

            return true;
        }
    }

}