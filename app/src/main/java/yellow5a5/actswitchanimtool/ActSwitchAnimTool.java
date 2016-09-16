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
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Yellow5A5 on 16/9/16.
 */
public class ActSwitchAnimTool {

    private int ANIM_DURATION = 1000;

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
    }

    public void startActivity(final Intent intent, final boolean isNeedFinish) {
        intent.putExtra("location", targetLocation);
        intent.putExtra("color", mColorEnd);
        mSwitchAnimView.setSwitchAnimCallback(new SwitchAnimCallback() {
            @Override
            public void onAnimationEnd() {
                mStartAct.startActivity(intent);
                if (isNeedFinish == true){
                    mStartAct.finish();
                }
            }
        });
        mSwitchAnimView.startSpreadAnim();
    }

    public ActSwitchAnimTool target(final View view) {
        final ViewGroup decor = (ViewGroup) mStartAct.getWindow().getDecorView();
        final ViewGroup.LayoutParams bgParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //background image.
        decor.addView(mSwitchAnimView, bgParams);

        view.getLocationInWindow(targetLocation);
        mSwitchAnimView.setCenter(targetLocation[0], targetLocation[1]);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                targetViewWidth = view.getWidth();
                targetViewHeight = view.getHeight();
            }
        });
        decor.removeView(view);
//        decor.addView(view);
        return this;
    }

    public ActSwitchAnimTool setAnimType(int type){
        mSwitchAnimView.setmAnimType(type);
        return this;
    }

    public void builder() {

    }

    public void setContainer(View container) {

    }

    public int getmColorStart() {
        return mColorStart;
    }

    public void setmColorStart(int mColorStart) {
        this.mColorStart = mColorStart;
    }

    public int getmColorEnd() {
        return mColorEnd;
    }

    public void setmColorEnd(int mColorEnd) {
        this.mColorEnd = mColorEnd;
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
        private int mAlpha;
        private Paint mPaint;
        private ValueAnimator mAnimator;

        private boolean isAnimationStart = false;

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
            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            mDuration = 2000;
        }

        private void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        private void initAnimation() {
            mAnimator = ValueAnimator.ofInt(0, 3000);
            mAnimator.setDuration(mDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRadius = (int) animation.getAnimatedValue();
                    invalidate();
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
            if (!isAnimationStart) {
                initAnimation();
            }
            switch (getmAnimType()) {
                case MODE_SPREAD:
                    canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                    break;
                case MODE_SHRINK:
                    canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                    break;
            }
        }

        public int getmSpreadColor() {
            return mSpreadColor;
        }

        public void setmSpreadColor(int mSpreadColor) {
            this.mSpreadColor = mSpreadColor;
        }

        public int getmShrinkColor() {
            return mShrinkColor;
        }

        public void setmShrinkColor(int mShrinkColor) {
            this.mShrinkColor = mShrinkColor;
        }

        public int getmAnimType() {
            return mAnimType;
        }

        public void setmAnimType(int mAnimType) {
            this.mAnimType = mAnimType;
        }
    }
}

