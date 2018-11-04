package com.kaminski.artur.wifi_rifi;


import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    TextView txt;
    TextView txt2;
    Button buton;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.textView);
        txt2 = (TextView) findViewById(R.id.textViewR);
        buton = (Button) findViewById(R.id.but);

    }

    public void getWifiInfo(View view){

        WifiManager wifi_manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifi_info = wifi_manager.getConnectionInfo();
        int ip = wifi_info.getIpAddress();
        String mac = getMacAddr();
        String bssid = wifi_info.getBSSID();
        int rssi = wifi_info.getRssi();
        int link_speed = wifi_info.getLinkSpeed();
        String ssid = wifi_info.getSSID();
        int network_id = wifi_info.getNetworkId();
        String ip_address = android.text.format.Formatter.formatIpAddress(ip);

        if (wifi_manager.isWifiEnabled()) {
            status = "POŁĄCZONO";
        } else { status = "BRAK POŁĄCZENIA";}

        int dns1 = wifi_manager.getDhcpInfo().dns1;
        int dns2 = wifi_manager.getDhcpInfo().dns2;
        int gate = wifi_manager.getDhcpInfo().gateway;
        int mask = wifi_manager.getDhcpInfo().netmask;
        int dur = wifi_manager.getDhcpInfo().leaseDuration;


        String dns1_address = android.text.format.Formatter.formatIpAddress(dns1);
        String dns2_address = android.text.format.Formatter.formatIpAddress(dns2);
        String gate_address = android.text.format.Formatter.formatIpAddress(gate);
        String mask_address = android.text.format.Formatter.formatIpAddress(mask);
        String dur_time =  String.format("%d:%02d:%02d", dur / 3600, (dur % 3600) / 60, (dur % 60));

        String infoR =  status +
                "\n" + ip_address +
                "\n" + mac +
                "\n" + bssid +
                "\n" + rssi +
                "\n" + link_speed + " Mbps" +
                "\n" + ssid +
                "\n" + dns1_address +
                "\n" + dns2_address +
                "\n" + gate_address +
                "\n" + mask_address +
                "\n" + dur_time + " Hrs" +
                "\n" + network_id ;
            txt2.setText(infoR);

        String infoL = "status: " +
                "\n" + "Ip: "  +
                "\n" +"Mac: "  +
                "\n" +"Bssid: "  +
                "\n" +"Rssi: "  +
                "\n" +"Prędkość łącza: " +
                "\n" +"Ssid: "  +
                "\n" +"Dns1: " +
                "\n" +"Dns2: " +
                "\n" +"Gateway: " +
                "\n" +"Mask: " +
                "\n" +"Duration: " +
                "\n" +"Id: " ;
        txt.setText(infoL.toUpperCase());

    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "Permission Denied";
    }


}
