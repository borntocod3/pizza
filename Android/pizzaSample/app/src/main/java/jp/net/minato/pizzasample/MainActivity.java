package jp.net.minato.pizzasample;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;


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
import android.widget.ImageView;

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

        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snowFall = new Intent(MainActivity.this,SnowFallActivity.class);
                startActivity(snowFall);
            }
        });

        Button qrScanner = (Button) findViewById(R.id.btnStartScanner);
        qrScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRReaderActivity.class);
                startActivity(intent);

//                DialogPlus diaglog = DialogPlus.newDialog(MainActivity.this)
//                        .setOnItemClickListener(new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(DialogPlus dialogPlus, Object o, View view, int i) {
//
//                            }
//                        }).setExpanded(true, 300)
//                        .setAdapter()
//                        .setContentHolder(new ViewHolder(R.layout.activity_main))
//                        .setMargin(15, 5, 15, 0)
//                        .setFooter(R.layout.menu_footer)
//                        .setCancelable(true)
//                        .create();
//
//                diaglog.show();
            }
        });
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
