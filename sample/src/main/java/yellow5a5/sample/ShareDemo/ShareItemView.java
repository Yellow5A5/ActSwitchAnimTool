package yellow5a5.sample.ShareDemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yellow5a5.sample.R;

/**
 * Created by Yellow5A5 on 16/9/17.
 */
public class ShareItemView extends RelativeLayout {

    private int ANIM_START = 0;
    private int ANIM_REVERSE = 1;

    private int mAnimMode = 0;

    private ImageView mImageV;
    private TextView mTextV;

    private ValueAnimator animator;

    public ShareItemView(Context context) {
        this(context, null);
    }

    public ShareItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShareItemView, 0, 0);
        int imgId = array.getResourceId(R.styleable.ShareItemView_share_img, 0);
        String text = array.getString(R.styleable.ShareItemView_share_text);
        LayoutInflater.from(context).inflate(R.layout.share_item, this, true);
        mImageV = (ImageView) findViewById(R.id.img_left);
        mTextV = (TextView) findViewById(R.id.tv_right);
        setImageShare(imgId);
        setTextShare(text);
        initAnimation();
        setAlpha(0);
    }

    private void initAnimation() {
        animator = ValueAnimator.ofFloat(0, 1).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                setTranslationY((1 - factor) * 100);
                setAlpha(factor);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(mAnimMode == ANIM_START) {

                }else if(mAnimMode == ANIM_REVERSE){
                    setVisibility(GONE);
                }
            }
        });
    }

    public void setTextShare(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTextV.setText(text);
        }
    }

    public void setImageShare(int resId) {
        if (resId != 0) {
            mImageV.setImageResource(resId);
        }
    }

    public void showAnimation() {
        setVisibility(View.VISIBLE);
        mAnimMode = 0;
        animator.start();
    }

    public void hideAnimation() {
        mAnimMode = 1;
        animator.reverse();
    }
}
