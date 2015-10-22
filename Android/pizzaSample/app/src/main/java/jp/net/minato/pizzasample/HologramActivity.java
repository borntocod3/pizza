package jp.net.minato.pizzasample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;


public class HologramActivity extends Activity {
    private AlertDialog yesNoDialog;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String qrURl = getIntent().getExtras().getString("qr_resutl");
        AlertDialog.Builder yesNO = new AlertDialog.Builder(HologramActivity.this);
        yesNO.setMessage(qrURl).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setContentView(R.layout.activity_hologram);
                mWebView = (WebView) findViewById(R.id.wvElement);
                String data ="<body style=\"width:100%;height:100%;padding:0px;margin:0px;\"><img style=\"width:100%;height:100%;padding:0px;margin:0px;\" src=\"pikachu.gif\"/></body>";
                mWebView.loadDataWithBaseURL("file:///android_asset/",data,"text/html","utf-8",null);

                dialogInterface.dismiss();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(HologramActivity.this, QRReaderActivity.class);
                startActivity(intent);
            }
        });

        yesNoDialog = yesNO.create();
        yesNoDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hologram, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yesNoDialog.dismiss();
        yesNoDialog.hide();
    }
}
