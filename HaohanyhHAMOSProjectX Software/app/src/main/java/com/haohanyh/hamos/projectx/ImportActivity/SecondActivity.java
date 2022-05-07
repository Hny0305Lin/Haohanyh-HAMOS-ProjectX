// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx.ImportActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.haohanyh.hamos.projectx.R.*;

import androidx.annotation.NonNull;

import com.haohanyh.hamos.projectx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SecondActivity extends Activity {

    private String HUAWEITOKEN = "";
    public TextView txtLuminance;
    public TextView txtLightStatus;
    private String param = "Led";
    private MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

    int haohanyhtime = 0;

    //连接超时5秒、响应数据超时5秒
    private OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();

    final Handler handler = new Handler();
    final Timer timer2nd = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_second_new);

        Button btnnew = findViewById(id.kaiqi);
        //灰度测试 按钮控制
        Button btnctrl = findViewById(id.buttonctrl);
        ImageButton btnexit = findViewById(id.exit);
        txtLuminance = findViewById(id.txtlight);
        txtLightStatus = findViewById(id.txtstatus);

        String status = txtLightStatus.toString();

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() { Huawei("******","******"); }
                    };
                    timer2nd.schedule(task, 0, 1000);
                    Toast.makeText(SecondActivity.this, "开始读取福州智能数据机房华为IotA接口的光照传感器数值", Toast.LENGTH_SHORT).show();
                    btnnew.setClickable(false);
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
                onBackPressed();
                timer2nd.cancel();
            }
        });

        btnctrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //控制传感器，新增计数器
                haohanyhtime++;
                if((haohanyhtime % 2 != 0))
                {
                    if(!status.contains("开着呢"))
                    {
                        ControlSenderHUAWEIBetaOn(view,"******","******","Light_Control_Led",param);
                    } else {
                        ControlSenderHUAWEIBetaOff(view,"******","******","Light_Control_Led",param);
                    }
                } else {
                    ControlSenderHUAWEIBetaOff(view,"******","******","Light_Control_Led",param);
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            onBackPressed();
            return true;
        } else {
            return onKeyDown(keyCode,event);
        }
    }

    public void onBackPressed() {
        Toast.makeText(SecondActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        timer2nd.cancel();
    }

    @SuppressLint("SetTextI18n")
    public void Huawei(String project_id,String device_id) {
        post();
        String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
        try {
            JSONObject jsonObj = new JSONObject(result);
            //System.out.println("浩瀚银河Huawei函数灰度测试:result=====" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("shadow");
            System.out.println("浩瀚银河Huawei函数灰度测试:shadow=====" + jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                //System.out.println("浩瀚银河Huawei函数灰度测试:reported=====");
                Object reported = obj.get("reported");

                JSONObject Two = new JSONObject(String.valueOf(reported));
                String properties = Two.getString("properties");
                //System.out.println("浩瀚银河Huawei函数灰度测试:properties=====" + properties);

                JSONObject Lumi = new JSONObject(properties);
                String Lumiresult = Lumi.getString("Luminance");
                //System.out.println("浩瀚银河Huawei函数灰度测试:Luminance=====" + Lumiresult);

                JSONObject Light = new JSONObject(properties);
                String Lightresult = Light.getString("LightStatus");
                //System.out.println("浩瀚银河Huawei函数灰度测试:Luminance=====" + Lightresult);

                handler.post(new Runnable() {

                    ImageView imagelight = findViewById(id.light);
                    ImageView imagestatus = findViewById(id.lamp);

                    @Override
                    public void run() {
                        txtLuminance.setText("当前光照值：" + "\n\t" + Lumiresult);

                        String str201 = "ON";
                        String str202 = "OFF";
                        if (Lightresult.equals(str201)) {
                            txtLightStatus.setText("\t是否开灯： " + "\n\t" + "开着呢");
                            imagestatus.setImageResource(drawable.lamp_shine);
                        } else if (Lightresult.equals(str202)) {
                            txtLightStatus.setText("\t是否开灯： " + "\n\t" + "关了呢");
                            imagestatus.setImageResource(drawable.lamp);
                        } else {
                            txtLightStatus.setText("\t是否开灯： " + "\n\t" + Lightresult);
                            imagestatus.setImageResource(drawable.lamp);
                        }

                        int alight = Integer.parseInt(Lumiresult);
                        if(alight > 150) {
                            imagelight.setImageResource(drawable.light_high);
                        } else {
                            imagelight.setImageResource(drawable.light);
                        }

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String get(String url) {
        String content = "";
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Auth-Token", HUAWEITOKEN);
            //连接
            connection.connect();
            int responseCode = connection.getResponseCode();
            //System.out.println("浩瀚银河Get函数灰度测试:responseCode=====" + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder bs = new StringBuilder();
                String l;
                while ((l = bufferedReader.readLine()) != null) {
                    bs.append(l).append("\n");
                }
                content = bs.toString();
            } else if (responseCode == 401) {
                System.out.println("浩瀚银河Get函数灰度测试:failed");
            }
            //System.out.println("浩瀚银河Get函数灰度测试:content=====" + content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private void post() {
        String jsonwenben = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"******\"},\"name\":\"******\",\"password\":\"******\"}}},\"scope\":{\"domain\":{\"name\":\"******\"}}}}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonwenben);
        Request request = new Request.Builder()
                .url("https://iam.cn-north-4.myhuaweicloud.com/v3/auth/tokens")
                .post(body)
                .build();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("浩瀚银河post函数灰度测试: " + e.getLocalizedMessage() + "，灰度测试失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("浩瀚银河post函数链接灰度测试: " + response.body().string());
                //System.out.println("浩瀚银河post函数头部读取灰度测试: " + response.header("x-subject-token"));
                HUAWEITOKEN = response.header("x-subject-token");
            }
        });
    }

    private void ControlSenderHUAWEIBetaOn(View v,String project_id,String device_id,String command_name,String command_param) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Button toggle = ((Button) v);
        String json = "";

        JSONObject object = new JSONObject();//我们需要修改值~这个没办法哦~

        String ONorOFF = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "Light");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:service_id=====" + jsonObject);
            jsonObject.put("command_name", "Light_Control_Led");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:command_name=====" + jsonObject);
            object.put(command_param, toggle.isClickable() ? "ON" : "ON");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:开关情况=====" + object);
            ONorOFF = String.valueOf(object);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:传参情况=====" + ONorOFF);
            jsonObject.put("paras", object);
            String jsonParams = jsonObject.toString();
            Log.d("TAG", toggle.getText().toString() + ":" + jsonParams);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:总=====" + jsonParams);
            json = jsonParams;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ONorOFF.contains("ON")) {
            Toast.makeText(this,"灯即将打开",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"灯即将关闭",Toast.LENGTH_SHORT).show();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder()
                .url("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/commands")
                .addHeader("X-Auth-Token",HUAWEITOKEN)
                .post(body)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试: " + e.getLocalizedMessage() + "，灰度测试失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数链接灰度测试: " + response.body().string());
                System.out.println("出现以上信息，说明传值到华为云，再到开发板，同时到APP上显示情况，成功");
            }
        });

    }

    private void ControlSenderHUAWEIBetaOff(View v,String project_id,String device_id,String command_name,String command_param) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Button toggle = ((Button) v);
        String json = "";

        JSONObject object = new JSONObject();//我们需要修改值~这个没办法哦~

        String ONorOFF = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "Light");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:service_id=====" + jsonObject);
            jsonObject.put("command_name", "Light_Control_Led");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:command_name=====" + jsonObject);
            object.put(command_param, toggle.isClickable() ? "OFF" : "OFF");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:开关情况=====" + object);
            ONorOFF = String.valueOf(object);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:传参情况=====" + ONorOFF);
            jsonObject.put("paras", object);
            String jsonParams = jsonObject.toString();
            Log.d("TAG", toggle.getText().toString() + ":" + jsonParams);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:总=====" + jsonParams);
            json = jsonParams;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ONorOFF.contains("OFF")) {
            Toast.makeText(this,"灯即将关闭",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"灯即将打开",Toast.LENGTH_SHORT).show();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder()
                .url("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/commands")
                .addHeader("X-Auth-Token",HUAWEITOKEN)
                .post(body)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试: " + e.getLocalizedMessage() + "，灰度测试失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数链接灰度测试: " + response.body().string());
                System.out.println("出现以上信息，说明传值到华为云，再到开发板，同时到APP上显示情况，成功");
            }
        });
    }
}
