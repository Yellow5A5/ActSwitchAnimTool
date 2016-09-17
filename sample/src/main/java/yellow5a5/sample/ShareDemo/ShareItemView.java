package yellow5a5.sample.ShareDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yellow5a5.sample.R;

/**
 * Created by Yellow5A5 on 16/9/17.
 */
public class ShareItemView extends RelativeLayout {

    private ImageView mImageV;
    private TextView mTextV;

    public ShareItemView(Context context) {
        this(context, null);
    }

    public ShareItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShareItemView, 0, 0);
        int imgId = array.getResourceId(R.styleable.ShareItemView_share_img,0);
        String text = array.getString(R.styleable.ShareItemView_share_text);
        setImageShare(imgId);
        setTextShare(text);
        LayoutInflater.from(context).inflate(R.layout.share_item, this, true);
        mImageV = (ImageView) findViewById(R.id.img_left);
        mTextV = (TextView) findViewById(R.id.tv_right);
    }

    public void setTextShare(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTextV.setText(text);
        }
    }

    public void setImageShare(int resId) {
        if (resId != 0){
            mImageV.setImageResource(resId);
        }
    }
}
