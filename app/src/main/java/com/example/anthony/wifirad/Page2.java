package com.example.anthony.wifirad;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Page2 extends AppCompatActivity {

    ListView lv;
    Button button;
    WifiManager wifiManager;
    String[] nwifi;
    scanner scn;
    List<ScanResult> scanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        button=(Button)findViewById(R.id.button);
        lv=(ListView)findViewById(R.id.listView);
    }

    public void selectwifi(View v)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (scn == null) {
            scn = new scanner();
        }
        registerReceiver(scn, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Page2.this,Page3.class);
                intent.putExtra("present",nwifi[position]);
                intent.putExtra("level",scanList.get(position).level);
                startActivity(intent);
            }
        });
    }


    private class scanner extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            scanList=wifiManager.getScanResults();
            int size=scanList.size();
            nwifi=new String[size];
            for(int i=0;i<size;i++)
            {
                nwifi[i]=scanList.get(i).SSID;
            }
            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,nwifi));
            
            try {
                unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                // Receiver not registered
            }
        }
    }

}
