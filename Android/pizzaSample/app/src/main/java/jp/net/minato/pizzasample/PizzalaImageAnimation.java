package jp.net.minato.pizzasample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin-new on 10/23/15.
 */
public class PizzalaImageAnimation extends ImageView {

    public PizzalaImageAnimation(Context context) {
        super(context);
    }

    public PizzalaImageAnimation(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public  PizzalaImageAnimation(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        this.setVisibility(View.GONE);
    }
}