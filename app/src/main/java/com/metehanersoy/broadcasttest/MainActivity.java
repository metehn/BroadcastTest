package com.metehanersoy.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Company:NewLand
 * Author:Alan
 * Description:NewLand Scanner Broadcast Demo
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_broadcast_result;
    private BroadcastReceiver mReceiver;
    private static String ACTION_SCANNER_RESULT = "nlscan.action.SCANNER_RESULT";
    private static String ACTION_SCANNER_TRIG = "nlscan.action.SCANNER_TRIG";
    private static String ACTION_BAR_SCANCFG = "ACTION_BAR_SCANCFG";
    private static String ACTION_BAR_SCANCFG_EXTRA = "EXTRA_SCAN_MODE";
    private static String SCAN_BARCODE1 = "SCAN_BARCODE1";
    private static String SCAN_STATE = "SCAN_STATE";
    private static String SCAN_STATE_OK = "ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_broadcast_result = (TextView) findViewById(R.id.scanResult);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult_1 = intent.getStringExtra(SCAN_BARCODE1);
                final String scanStatus = intent.getStringExtra(SCAN_STATE);
                if (SCAN_STATE_OK.equals(scanStatus)) {
                    //Success
                    tv_broadcast_result.setText(scanResult_1);
                } else {
                    //Failure, e.g. operation timed out
                }
            }
        };
    }

    /**
     * set output mode to "Output via API"
     */
    private void setAPIOutput() {
        Intent intent = new Intent(ACTION_BAR_SCANCFG);
        intent.putExtra(ACTION_BAR_SCANCFG_EXTRA, 3);
        sendBroadcast(intent);
    }

    /**
     * register the scan result receiver
     */
    private void registerReceiver() {
        IntentFilter mFilter = new IntentFilter(ACTION_SCANNER_RESULT);
        registerReceiver(mReceiver, mFilter);
        // Output via API
        setAPIOutput();
    }

    /**
     * unregister the scan result receiver
     */
    private void unRegisterReceiver() {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    /**
     * Start to scan
     *
     * @param view
     */
    public void clickButton(View view) {
        Intent intent = new Intent(ACTION_SCANNER_TRIG);
        sendBroadcast(intent);
    }
}
