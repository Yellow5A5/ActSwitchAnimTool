package yellow5a5.sample.ShareDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import yellow5a5.sample.R;

/**
 * Created by Yellow5A5 on 16/9/17.
 */
public class ShareContainer extends LinearLayout implements IShare {


    private ShareItemView mFaceBookItem;
    private ShareItemView mTwitterItem;
    private ShareItemView mGooglePlusItem;
    private ImageView mCancelV;

    public interface IShareCallback {
        void onCancel();
    }

    private IShareCallback mIShareCallback;

    public void setIShareCallback(IShareCallback l) {
        mIShareCallback = l;
    }

    public ShareContainer(Context context) {
        this(context, null);
    }

    public ShareContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.share_container_layout, this, true);
        init();
    }

    private void init() {
        mFaceBookItem = (ShareItemView) findViewById(R.id.item_facebook);
        mTwitterItem = (ShareItemView) findViewById(R.id.item_twitter);
        mGooglePlusItem = (ShareItemView) findViewById(R.id.item_google);
        mCancelV = (ImageView) findViewById(R.id.img_cancel);
        mCancelV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIShareCallback != null) {
                    mIShareCallback.onCancel();
                }
            }
        });
    }

    public void showShareBtn() {
        mFaceBookItem.setVisibility(VISIBLE);
        mTwitterItem.setVisibility(VISIBLE);
        mGooglePlusItem.setVisibility(VISIBLE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mFaceBookItem.showAnimation();
            }
        }, 100);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mTwitterItem.showAnimation();
            }
        }, 200);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mGooglePlusItem.showAnimation();
            }
        }, 300);
    }



    public void hideShareBtn(){
        mFaceBookItem.hideAnimation();
        mTwitterItem.hideAnimation();
        mGooglePlusItem.hideAnimation();
    }

    @Override
    public void faceBookShareClick() {

    }

    @Override
    public void twitterShareClick() {

    }

    @Override
    public void googlePlusShareClick() {

    }

}
