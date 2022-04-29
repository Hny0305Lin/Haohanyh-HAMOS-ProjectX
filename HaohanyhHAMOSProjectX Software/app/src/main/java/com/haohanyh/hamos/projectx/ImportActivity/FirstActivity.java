// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx.ImportActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haohanyh.hamos.projectx.util.Json;
import com.haohanyh.hamos.projectx.*;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstActivity extends Activity {
    static String haohanyhtime;//时间
    static Double haohanyhtemp;//温度
    static Double haohanyhhumi;//湿度

    private static final String DeviceID = "927881450";
    private static final String ApiKey = "HtgHMEIpPoWPWaQFi8DjL9qFMP8=";
    private static final String HaohanyhTemp = "temperature";//onenet平台上对应设备的其中一个数据流的名字
    private static final String HaohanyhHumi = "humidity";//onenet平台上对应设备的其中一个数据流的名字

    private TextView txtwendu,txtwendu1;
    private TextView txtshidu,txtshidu1;
    private TextView shijian;
    private Button kaiqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        txtwendu = findViewById(R.id.txtwendu);
        txtwendu1 = findViewById(R.id.txtwendu1);

        txtshidu = findViewById(R.id.txtshidu);
        txtshidu1 = findViewById(R.id.txtshidu1);

        shijian = findViewById(R.id.shijian);

        Button btnnew = findViewById(R.id.kaiqi);

        /*
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Get();
            }
        };
        timer.schedule(task,0,1000);
        */
        btnnew.setOnClickListener(new View.OnClickListener() {
            /*
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            */
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() { Get(); }
                    };
                    timer.schedule(task,0,1000);
                    Toast.makeText(FirstActivity.this,"开始读取福州智能数据机房传感器数值",Toast.LENGTH_SHORT).show();
                    btnnew.setClickable(false);
                }
            }
        });
    }

    public void Get() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=" + HaohanyhTemp)
                            .header("api-key", ApiKey)
                            .addHeader("Content-Type","application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.w("test", responseData);
                    JsonRootBean app1 = Json.gson.fromJson(responseData,JsonRootBean.class);
                    List<Datastreams> streams1 = app1.getData().getDatastreams();
                    List<Datapoints> points1 = streams1.get(0).getDatapoints();
                    haohanyhtemp = Double.valueOf(points1.get(0).getValue().toString());
                    txtwendu.post(new Runnable() {
                        @Override
                        public void run() {
                            txtwendu.setText("当前温度："+"\t"+String.format("%.2f°C",haohanyhtemp));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=" + HaohanyhHumi)
                            .header("api-key", ApiKey)
                            .addHeader("Content-Type","application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Log.w("test", responseData);

                    JsonRootBean app1 = Json.gson.fromJson(responseData,JsonRootBean.class);
                    List<Datastreams> streams1 = app1.getData().getDatastreams();
                    List<Datapoints> points1 = streams1.get(0).getDatapoints();
                    haohanyhhumi = Double.valueOf(points1.get(0).getValue().toString());
                    txtshidu.post(new Runnable() {
                        @Override
                        public void run() {
                            txtshidu.setText("当前湿度："+"\t"+String.format("%.2fhPa",haohanyhhumi));
                        }
                    });
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    int counttxt = 0;
    private void parseJSONWithGSON(String jsonData) {
        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        List<Datapoints> points = streams.get(0).getDatapoints();
        int count = app.getData().getCount();//获取数据的数量
        for (int i = 0; i < points.size(); i++) {
            String time = points.get(i).getAt();
            //haohanyhtime = points.get(i).getAt();
            //String value = points.get(i).getValue();
            //haohanyhtemp = Double.valueOf(points.get(i).getValue().toString());
            //haohanyhhumi = Double.valueOf(points.get(i).getValue().toString());
            //Log.w("temp","value="+value);
            //Log.w("humi","value="+value);
            Log.w("time","time="+time);
            haohanyhtime = points.get(i).getAt().toString();
            shijian.post(new Runnable() {
                @Override
                public void run() {
                    shijian.setText(" 时间: "+"\t"+haohanyhtime);
                }
            });
        }
    }

}