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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Page3 extends AppCompatActivity
{
    List<ScanResult> scanList;
    ListView lv;
    WifiManager wifiT;
    String str;
    String[] srcwifi;
    TextView textView;
    Testerscn scn2;
    ArrayList<String> al;
    int lev,max_rssi=-90,min_rssi=-30,size=0,loc=0;

    float per=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        lv=(ListView)findViewById(R.id.listView2);
        textView=(TextView)findViewById(R.id.textView);
        Intent in = getIntent();
        str = in.getStringExtra("present");
        lev=in.getIntExtra("level",0);
        al=new ArrayList<>();
        textView.setText(str);
    }

    // here you can design your functions and work and call it anywhere you want it to
    public void measure(View v)
    {
        wifiT = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        scn2=new Testerscn();
        wifiT.startScan();
        registerReceiver(scn2,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        srcwifi=new String[size];

        for(int i=0;i<size;i++)
        {
            srcwifi[i]=scanList.get(i).SSID;
        }

        for(int i=0;i<size;i++)
            if (str.compareTo(srcwifi[i]) == 0)
            {
                lev=scanList.get(i).level;
            }

        lev = calc(lev);
        per(lev);

    }



    private class Testerscn extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            scanList=wifiT.getScanResults();
            size=scanList.size();
        }


    }

    public int calc(int level)//keeping signal level in limits
    {
        if(level>-30)//above -30 dBm is the best signal strength
            return -30;
        if(level<-90)//below -90 dBm is the worst signal strength
            return -89;
        return level;
    }

    public void per(int lev)//calculating and printing output percentage
    {
        per=Math.abs((float)(lev-max_rssi)*99/(max_rssi-min_rssi));
        al.add(0,"Location" + (++loc) + ":\t" + per + "%");
        lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, al));
    }
}



