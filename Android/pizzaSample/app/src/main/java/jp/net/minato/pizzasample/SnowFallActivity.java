package jp.net.minato.pizzasample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by admin-new on 10/22/15.
 */
public class SnowFallActivity extends Activity {
    BitmapDrawable giftbox;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winter);
        SnowFallView snowFall = (SnowFallView) findViewById(R.id.winterView);
        imgView = (ImageView) findViewById(R.id.imgGiftBox);
        imgView.setVisibility(View.GONE);
        snowFall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Animation slide = new TranslateAnimation(0, 0, 0,1000);
                slide.setDuration(2500);
                slide.setZAdjustment(TranslateAnimation.ZORDER_TOP);
                slide.setFillAfter(true);
                slide.setInterpolator(new BounceInterpolator());
                slide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        imgView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imgView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imgView.startAnimation(slide);
                //imgView.setVisibility(View.GONE);
                return false;

            }
        });
    }

    private class ImageAnimation extends ImageView{

        public ImageAnimation(Context context) {
            super(context);
        }

        @Override
        protected void onAnimationEnd() {
            super.onAnimationEnd();
            this.setVisibility(View.GONE);
        }
    }
}
