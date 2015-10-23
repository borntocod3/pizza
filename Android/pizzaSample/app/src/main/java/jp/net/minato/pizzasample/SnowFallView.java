package jp.net.minato.pizzasample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.Attributes;

/**
 * Created by admin-new on 10/23/15.
 */
public class SnowFallView extends View {
    int x = -1;
    int y = -1;
    private int xVelocity = 10;
    private int yVelocity = 5;

    private int snow_flake_count = 10;
    private final List<Drawable> drawables = new ArrayList<Drawable>();
    private int[][] coords;
    private final Drawable snow_flake;

    private Context mContex;
    public SnowFallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        snow_flake = context.getResources().getDrawable(R.drawable.snow_sprite_1);
        snow_flake.setBounds(0, 0, snow_flake.getIntrinsicWidth(), snow_flake.getIntrinsicHeight());
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        Random random = new Random();
        Interpolator interpolator = new LinearInterpolator();

        snow_flake_count = Math.max(width, height) / 10;
        coords = new int[snow_flake_count][];
        drawables.clear();
        for (int i = 0; i < snow_flake_count; i++) {

            //Log.e("sizeChange", " ang width = " + width + " the height = " + height);
            Animation animation = new TranslateAnimation(0, height / 10
                    - random.nextInt(height / 5), 0, height + 30);
            animation.setDuration(10 * height + random.nextInt(5 * height));
            animation.setRepeatCount(-1);
            animation.initialize(10, 10, 10, 10);
            animation.setInterpolator(interpolator);

            coords[i] = new int[] { random.nextInt(width - 30), -30 };

            int h =  snow_flake.getIntrinsicHeight();
            int w = snow_flake.getIntrinsicWidth();
            snow_flake.setBounds(0,0,w,h);
            drawables.add(new AnimateDrawable(snow_flake, animation));
            animation.setStartOffset(random.nextInt(20 * height));
            animation.startNow();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//            BitmapDrawable ball = (BitmapDrawable) getResources().getDrawable(R.drawable.giftbox);
//            if (x<0 && y <0) {
//                x = this.getWidth()/2;
//                y = this.getHeight()/2;
//            } else {
//                x += xVelocity;
//                y += yVelocity;
//                if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
//                    xVelocity = xVelocity*-1;
//                }
//                if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
//                    yVelocity = yVelocity*-1;
//                }
//            }
//            canvas.drawBitmap(ball.getBitmap(), x, y, null);
        for (int i = 0; i < snow_flake_count; i++) {

            Drawable drawable = drawables.get(i);

            drawable.setBounds(0,0,23,23);
            canvas.save();
            canvas.translate(coords[i][0], coords[i][1]);
            drawable.draw(canvas);
            canvas.restore();
            //Log.e("sizeChange", " ang width = " + coords[i][0] + " the height = " + coords[i][0]);
        }
        invalidate();
    }

}