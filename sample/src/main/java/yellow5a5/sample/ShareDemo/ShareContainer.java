package yellow5a5.sample.ShareDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import yellow5a5.sample.R;

/**
 * Created by Yellow5A5 on 16/9/17.
 */
public class ShareContainer extends LinearLayout{
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

    }
}
