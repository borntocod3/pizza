package jp.net.minato.pizzasample;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class MainActivity extends Activity{
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // SnowFallView snowFallView = new SnowFallView(this);
        setContentView(R.layout.activity_main);
        //snowFallView.setBackgroundDrawable(getResources().getDrawable(R.drawable.snow_bg));

        mWebView = (WebView) findViewById(R.id.gif_animation);
        String data ="<body style=\"width:100%;height:100%;padding:0px;margin:0px;\"><img style=\"width:100%;height:100%;padding:0px;margin:0px;\" src=\"pizza.gif\"/></body>";
        mWebView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "utf-8", null);

        Button qrScanner = (Button) findViewById(R.id.btnStartScanner);
        qrScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRReaderActivity.class);
                startActivity(intent);
            }
        });



    }

    private class SnowFallView extends View {
        private int snow_flake_count = 10;
        private final List<Drawable> drawables = new ArrayList<Drawable>();
        private int[][] coords;
        private final Drawable snow_flake;

        public SnowFallView(Context context) {
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);

            snow_flake = context.getResources().getDrawable(R.drawable.snow_sprite_1);
            snow_flake.setBounds(0, 0, snow_flake.getIntrinsicWidth(), snow_flake
                    .getIntrinsicHeight());
        }

        @Override
        protected void onSizeChanged(int width, int height, int oldw, int oldh) {
            super.onSizeChanged(width, height, oldw, oldh);
            Random random = new Random();
            Interpolator interpolator = new LinearInterpolator();

            snow_flake_count = Math.max(width, height) / 20;
            coords = new int[snow_flake_count][];
            drawables.clear();
            for (int i = 0; i < snow_flake_count; i++) {
                Animation animation = new TranslateAnimation(0, height / 10
                        - random.nextInt(height / 5), 0, height + 30);
                animation.setDuration(10 * height + random.nextInt(5 * height));
                animation.setRepeatCount(-1);
                animation.initialize(10, 10, 10, 10);
                animation.setInterpolator(interpolator);

                coords[i] = new int[] { random.nextInt(width - 30), -30 };

                drawables.add(new AnimateDrawable(snow_flake, animation));
                animation.setStartOffset(random.nextInt(20 * height));
                animation.startNow();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < snow_flake_count; i++) {
                Drawable drawable = drawables.get(i);
                canvas.save();
                canvas.translate(coords[i][0], coords[i][1]);
                drawable.draw(canvas);
                canvas.restore();
            }
            invalidate();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
