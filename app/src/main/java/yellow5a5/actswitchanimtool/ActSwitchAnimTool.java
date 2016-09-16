package yellow5a5.actswitchanimtool;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by Yellow5A5 on 16/9/16.
 */
public class ActSwitchAnimTool {

    private int ANIM_DURATION = 1000;

    private ViewGroup mDecorView;
    private SwitchAnimView mSwitchAnimView;
    private Activity mStartAct;
    private int mColorStart;
    private int mColorEnd;

    private int targetViewWidth;
    private int targetViewHeight;
    private int[] targetLocation = new int[2];

    private ValueAnimator scaleAnim;

    public ActSwitchAnimTool(Activity activity) {
        mStartAct = activity;
        mSwitchAnimView = new SwitchAnimView(mStartAct);
        mDecorView = (ViewGroup) mStartAct.getWindow().getDecorView();
    }

    public ActSwitchAnimTool startActivity(final Intent intent, final boolean isNeedFinish) {
        intent.putExtra("location", targetLocation);
        intent.putExtra("color", mColorEnd);
        mSwitchAnimView.setSwitchAnimCallback(new SwitchAnimCallback() {
            @Override
            public void onAnimationEnd() {
                mStartAct.startActivity(intent);
                if (isNeedFinish == true) {
                    mStartAct.finish();
                } else {
                    mDecorView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDecorView.removeView(mSwitchAnimView);
                        }
                    }, 200);
                }
            }

            @Override
            public void onAnimationUpdate(int progress) {
                Log.e(ActSwitchAnimTool.class.getName(), "onAnimationUpdate: " + progress);
            }
        });
        return this;
    }

    public ActSwitchAnimTool receiveIntent(Intent intent) {
        targetLocation = intent.getIntArrayExtra("location");
        mColorStart = intent.getIntExtra("color", Color.BLACK/*FIXME*/);
        return this;
    }

    public ActSwitchAnimTool target(final View view) {
        final ViewGroup.LayoutParams bgParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //background image.
        mDecorView.addView(mSwitchAnimView, bgParams);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getLocationInWindow(targetLocation);
                targetViewWidth = view.getWidth();
                targetViewHeight = view.getHeight();
                int circleRadius = (targetViewHeight > targetViewWidth ? targetViewWidth : targetViewHeight) / 2;
                mSwitchAnimView.setmTargetCircleRadius(circleRadius);
                mSwitchAnimView.setCenter(targetLocation[0] + targetViewWidth / 2, targetLocation[1] + targetViewHeight / 2);
            }
        });
        return this;
    }

    public void build() {
        if (mSwitchAnimView.getmAnimType() == 0) {
            mSwitchAnimView.startSpreadAnim();
        } else if (mSwitchAnimView.getmAnimType() == 1) {
            mSwitchAnimView.startShrinkAnim();
        }
    }

    public ActSwitchAnimTool setAnimType(int type) {
        mSwitchAnimView.setmAnimType(type);
        return this;
    }

    public ActSwitchAnimTool setmColorStart(int color) {
        mSwitchAnimView.setmSpreadColor(color);
        return this;
    }

    public ActSwitchAnimTool setmColorEnd(int color) {
        mSwitchAnimView.setmShrinkColor(color);
        return this;
    }

    public int getTargetViewWidth() {
        return targetViewWidth;
    }

    public void setTargetViewWidth(int targetViewWidth) {
        this.targetViewWidth = targetViewWidth;
    }

    public int getTargetViewHeight() {
        return targetViewHeight;
    }

    public void setTargetViewHeight(int targetViewHeight) {
        this.targetViewHeight = targetViewHeight;
    }

    public interface SwitchAnimCallback {
        void onAnimationEnd();

        void onAnimationUpdate(int progress);
    }

    public class SwitchAnimView extends View {

        private final int MODE_UNINIT = -1;
        private final int MODE_SPREAD = 0;
        private final int MODE_SHRINK = 1;

        private int mCenterX;
        private int mCenterY;
        private int mSpreadColor;
        private int mShrinkColor;
        private int mAnimType;
        private int mRadius;
        private int mDuration;
        private Paint mSpreadPaint;
        private Paint mShrinkPaint;
        private ValueAnimator mAnimator;
        private int mTargetCircleRadius;
        private int mScreenLength;


        private boolean isAnimationReady = false;

        private SwitchAnimCallback mSwitchAnimCallback;

        public void setSwitchAnimCallback(SwitchAnimCallback callback) {
            mSwitchAnimCallback = callback;
        }

        public SwitchAnimView(Context context) {
            this(context, null);
        }

        public SwitchAnimView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public SwitchAnimView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
            initAnimation();
        }

        private void init() {
            mAnimType = MODE_UNINIT;
            mSpreadPaint = new Paint();
            mSpreadPaint.setStrokeWidth(1);
            mSpreadPaint.setStyle(Paint.Style.STROKE);
            mSpreadPaint.setColor(Color.BLUE);
            mShrinkPaint = new Paint();
            mShrinkPaint.setStrokeWidth(1);
            mShrinkPaint.setStyle(Paint.Style.STROKE);
            mShrinkPaint.setColor(Color.BLUE);
            mDuration = 700;
            mScreenLength = (int) Math.sqrt(Math.pow(getResources().getDisplayMetrics().heightPixels, 2)
                    + Math.pow(getResources().getDisplayMetrics().widthPixels, 2));
        }

        private void setAlpha(int alpha) {
            mSpreadPaint.setAlpha(alpha);
            mShrinkPaint.setAlpha(alpha);
        }

        private void initAnimation() {
            isAnimationReady = true;
            mAnimator = ValueAnimator.ofInt(0, mScreenLength);
            mAnimator.setDuration(mDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                int lastFactor = 0;

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int factor = (int) animation.getAnimatedValue();
                    mRadius = factor;
                    invalidate();
                    if (mSwitchAnimCallback != null && lastFactor != factor) {
                        mSwitchAnimCallback.onAnimationUpdate(factor * 100 / mScreenLength);
                        lastFactor = factor;
                    }
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mSwitchAnimCallback != null) {
                        mSwitchAnimCallback.onAnimationEnd();
                    }
                }
            });
        }

        private void startSpreadAnim() {
            mAnimator.start();
        }

        private void startShrinkAnim() {
            mAnimator.reverse();
        }

        public void setCenter(int centerX, int centerY) {
            mCenterX = centerX;
            mCenterY = centerY;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (getmAnimType() == MODE_UNINIT) {
                return;
            }
            if (!isAnimationReady) {
                initAnimation();
            }
            switch (getmAnimType()) {
                case MODE_SPREAD:
                    canvas.drawCircle(mCenterX, mCenterY, mRadius / 2 + mTargetCircleRadius, mSpreadPaint);
                    mSpreadPaint.setStrokeWidth(mRadius);
                    break;
                case MODE_SHRINK:
                    canvas.drawCircle(mCenterX, mCenterY, mRadius / 2 + mTargetCircleRadius, mShrinkPaint);
                    mShrinkPaint.setStrokeWidth(mRadius);
                    break;
            }
        }

        public int getmRadius() {
            return mRadius;
        }

        public void setmRadius(int mRadius) {
            this.mRadius = mRadius;
        }

        public int getmDuration() {
            return mDuration;
        }

        public void setmDuration(int mDuration) {
            this.mDuration = mDuration;
            isAnimationReady = false;
        }

        public int getmSpreadColor() {
            return mSpreadColor;
        }

        public void setmSpreadColor(int color) {
            mSpreadPaint.setColor(color);
            this.mSpreadColor = color;
        }

        public int getmShrinkColor() {
            return mShrinkColor;
        }

        public void setmShrinkColor(int color) {
            mShrinkPaint.setColor(color);
            this.mShrinkColor = color;
        }

        public int getmAnimType() {
            return mAnimType;
        }

        public void setmAnimType(int mAnimType) {
            this.mAnimType = mAnimType;
        }

        public int getmTargetCircleRadius() {
            return mTargetCircleRadius;
        }

        public void setmTargetCircleRadius(int mTargetCircleRadius) {
            this.mTargetCircleRadius = mTargetCircleRadius;
        }
    }
}

