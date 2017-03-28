package com.example.anthony.wifirad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        scn=new scanner();
        wifiManager.startScan();
        registerReceiver(scn, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
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
            scanList=wifiManager.getScanResults();
            int size=scanList.size();
            nwifi=new String[size];
            for(int i=0;i<size;i++)
            {
                nwifi[i]=scanList.get(i).SSID;
            }
            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,nwifi));
        }
    }

}

