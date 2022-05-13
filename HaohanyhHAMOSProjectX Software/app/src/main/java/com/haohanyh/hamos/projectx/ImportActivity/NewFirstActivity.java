// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx.ImportActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.service.controls.templates.TemperatureControlTemplate;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.haohanyh.hamos.projectx.R.*;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewFirstActivity extends Activity {

    private String HUAWEITOKEN = "";
    public TextView txtTemperatureR;
    public TextView txtHumidityR;
    public TextView txtLightStatusR;
    public TextView txtMotorStatusR;

    final Handler handler = new Handler();
    final Timer newtimer1st = new Timer();

    final Timer autotimer1st = new Timer();

    private String param1 = "Light";
    private String param2 = "Motor";
    private MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

    int haohanyhtimer1 = 0;
    int haohanyhtimer2 = 0;
    int autotimer = 0;

    boolean iam = true;
    boolean iaq = false;
    boolean iaw = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_first_huawei);

        Button btnnew = findViewById(id.kaiqi);
        Button btnauto = findViewById(id.NewFirstbuttonauto);
        Button btnled = findViewById(id.buttonled);
        Button btnfan = findViewById(id.buttonfan);
        ImageButton btnexit = findViewById(id.exit);

        txtTemperatureR = findViewById(id.txtwendu);
        txtHumidityR = findViewById(id.txtshidu);
        txtLightStatusR = findViewById(id.txtled);
        txtMotorStatusR = findViewById(id.txtfan);

        String statusLight = txtLightStatusR.toString();
        String statusMotor = txtMotorStatusR.toString();

        JSONObject Temp = new JSONObject();
        JSONObject Humi;

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() { Huawei("******","******"); }
                    };
                    newtimer1st.schedule(task,0,1000);
                    Toast.makeText(NewFirstActivity.this,"开始读取福州智能数据机房华为IotA接口的温湿度传感器数值和LED、风扇情况",Toast.LENGTH_SHORT).show();
                    btnnew.setClickable(false);
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                newtimer1st.cancel();
            }
        });

        btnled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haohanyhtimer1++;
                if(haohanyhtimer1 % 2 != 0)
                {
                    if(!statusLight.contains("开着呢"))
                    {
                        ControlSenderHUAWEIBetaOn("******","******","Agriculture_Control_light",param1);
                    } else {
                        ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_light",param1);
                    }
                } else {
                    ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_light",param1);
                }
            }
        });

        btnfan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haohanyhtimer2++;
                if(haohanyhtimer2 % 2 != 0)
                {
                    if(!statusMotor.contains("开着呢"))
                    {
                        ControlSenderHUAWEIBetaOn("******","******","Agriculture_Control_Motor",param2);
                    } else {
                        ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_Motor",param2);
                    }
                } else {
                    ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_Motor",param2);
                }
            }
        });

        btnauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autotimer++;
                if(autotimer % 2 != 0)
                {
                    if (btnauto.isClickable()) { //如果是可以点击的，则执行方法
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                Huawei("******","******",iam);
                            }
                        };
                        autotimer1st.schedule(task,0,1000);
                        Toast.makeText(NewFirstActivity.this,"灰度测试自动化管理",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewFirstActivity.this,"取消测试自动化管理",Toast.LENGTH_SHORT).show();
                    autotimer1st.cancel();
                }
            }
        });
    }

    private void ControlSenderHUAWEIBetaOn(String project_id,String device_id,String command_name,String command_param) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        //Button toggle = ((Button) v);
        String json = "";

        JSONObject object = new JSONObject();//我们需要修改值~这个没办法哦~

        String ONorOFF = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "Agriculture");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:service_id=====" + jsonObject);
            jsonObject.put("command_name", command_name);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:command_name=====" + jsonObject);
            //object.put(command_param, toggle.isClickable() ? "ON" : "ON");
            object.put(command_param, "ON");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:开关情况=====" + object);
            ONorOFF = String.valueOf(object);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:传参情况=====" + ONorOFF);
            jsonObject.put("paras", object);
            String jsonParams = jsonObject.toString();
            //Log.d("TAG", toggle.getText().toString() + ":" + jsonParams);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:总=====" + jsonParams);
            json = jsonParams;
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void ControlSenderHUAWEIBetaOff(String project_id,String device_id,String command_name,String command_param) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        //Button toggle = ((Button) v);
        String json = "";

        JSONObject object = new JSONObject();//我们需要修改值~这个没办法哦~

        String ONorOFF = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", "Agriculture");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:service_id=====" + jsonObject);
            jsonObject.put("command_name", command_name);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:command_name=====" + jsonObject);
            //object.put(command_param, toggle.isClickable() ? "OFF" : "OFF");
            object.put(command_param, "OFF");
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:开关情况=====" + object);
            ONorOFF = String.valueOf(object);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:传参情况=====" + ONorOFF);
            jsonObject.put("paras", object);
            String jsonParams = jsonObject.toString();
            //Log.d("TAG", toggle.getText().toString() + ":" + jsonParams);
            System.out.println("浩瀚银河ControlSenderHUAWEIBeta函数灰度测试:总=====" + jsonParams);
            json = jsonParams;
        } catch (JSONException e) {
            e.printStackTrace();
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

    void Huawei(String project_id,String device_id,boolean iam) {
        post();
        String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
        try {
            JSONObject jsonObj = new JSONObject(result);
            System.out.println("浩瀚银河Huawei函数灰度测试:result=====" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("shadow");
            System.out.println("浩瀚银河Huawei函数灰度测试:shadow=====" + jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Object reported = obj.get("reported");

                JSONObject Two = new JSONObject(String.valueOf(reported));
                String properties = Two.getString("properties");

                JSONObject Temp = new JSONObject(properties);
                String TemperatureResult = Temp.getString("Temperature");
                Log.v("浩瀚银河灰度测试，Temp目前情况为：",TemperatureResult);

                JSONObject Humi = new JSONObject(properties);
                String HumidityResult = Humi.getString("Humidity");
                Log.v("浩瀚银河灰度测试，Humi目前情况为：",HumidityResult);

                JSONObject Lumi = new JSONObject(properties);
                String LuminanceResult = Lumi.getString("Luminance");
                Log.v("浩瀚银河灰度测试，Lumi目前情况为：",LuminanceResult);

                JSONObject Light = new JSONObject(properties);
                String LightStatusResult = Light.getString("LightStatus");
                Log.v("浩瀚银河灰度测试，LED目前情况为：",LightStatusResult);

                JSONObject Fan = new JSONObject(properties);
                String MotorStatusResult = Fan.getString("MotorStatus");
                Log.v("浩瀚银河灰度测试，风扇目前情况为：",MotorStatusResult);

                handler.post(new Runnable() {

                    ImageView imageLED = findViewById(id.IILED);
                    ImageView imageFan = findViewById(id.IIFan);

                    ImageView imageHumi = findViewById(id.IIHumi);
                    ImageView imageTemp = findViewById(id.IItemp);

                    @Override
                    public void run() {
                        txtTemperatureR.setText("温度：\n\t" + TemperatureResult);
                        txtHumidityR.setText("湿度：\n\t" + HumidityResult);

                        String str201 = "ON";
                        String str202 = "OFF";
                        if (LightStatusResult.equals(str201)) {
                            txtLightStatusR.setText("\tLED灯情况:" + "\n\t" + "开着呢");
                            imageLED.setImageResource(drawable.led_shine);
                        } else if (LightStatusResult.equals(str202)) {
                            txtLightStatusR.setText("\tLED灯情况： " + "\n\t" + "关了呢");
                            imageLED.setImageResource(drawable.led);
                        } else {
                            txtLightStatusR.setText("\tLED灯情况： " + "\n\t" + LightStatusResult);
                            imageLED.setImageResource(drawable.led);
                        }

                        if (MotorStatusResult.equals(str201)) {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + "开着呢");
                            imageFan.setImageResource(drawable.fan_working);
                        } else if (MotorStatusResult.equals(str202)) {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + "关了呢");
                            imageFan.setImageResource(drawable.fan);
                        } else {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + MotorStatusResult);
                            imageFan.setImageResource(drawable.fan);
                        }

                        int aHumi = Integer.parseInt(HumidityResult);
                        if(aHumi >= 60) {
                            imageHumi.setImageResource(drawable.humi_damp);
                            if(iam)
                            {
                                if(iaq = true)
                                {
                                    ControlSenderHUAWEIBetaOn("******","******","Agriculture_Control_light",param1);
                                    Log.v("浩瀚银河灰度测试，自动化目前情况为：","湿度超过60% 开");
                                    iaq = false;
                                }
                            }
                        } else if(aHumi < 59) {
                            imageHumi.setImageResource(drawable.humi);
                            ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_light",param1);
                            Log.v("浩瀚银河灰度测试，自动化目前情况为：","湿度未超过60% 关");
                            iaq = true;
                        }

                        int aTemp = Integer.parseInt(HumidityResult);
                        if(aTemp > 26) {
                            imageTemp.setImageResource(drawable.temp_hot);
                            if(iam)
                            {
                                if(iaw = true)
                                {
                                    ControlSenderHUAWEIBetaOn("******","******","Agriculture_Control_Motor",param2);
                                    Log.v("浩瀚银河灰度测试，自动化目前情况为：","温度未超过26°c 关");
                                    iaw = false;
                                }
                            }
                        } else {
                            imageTemp.setImageResource(drawable.temp);
                            ControlSenderHUAWEIBetaOff("******","******","Agriculture_Control_Motor",param2);
                            Log.v("浩瀚银河灰度测试，自动化目前情况为：","温度未超过26°c 关");
                            iaw = true;
                        }

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void Huawei(String project_id,String device_id) {
        post();
        String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
        try {
            JSONObject jsonObj = new JSONObject(result);
            System.out.println("浩瀚银河Huawei函数灰度测试:result=====" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("shadow");
            System.out.println("浩瀚银河Huawei函数灰度测试:shadow=====" + jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Object reported = obj.get("reported");

                JSONObject Two = new JSONObject(String.valueOf(reported));
                String properties = Two.getString("properties");

                JSONObject Temp = new JSONObject(properties);
                String TemperatureResult = Temp.getString("Temperature");
                Log.v("浩瀚银河灰度测试，Temp目前情况为：",TemperatureResult);

                JSONObject Humi = new JSONObject(properties);
                String HumidityResult = Humi.getString("Humidity");
                Log.v("浩瀚银河灰度测试，Humi目前情况为：",HumidityResult);

                JSONObject Lumi = new JSONObject(properties);
                String LuminanceResult = Lumi.getString("Luminance");
                Log.v("浩瀚银河灰度测试，Lumi目前情况为：",LuminanceResult);

                JSONObject Light = new JSONObject(properties);
                String LightStatusResult = Light.getString("LightStatus");
                Log.v("浩瀚银河灰度测试，LED目前情况为：",LightStatusResult);

                JSONObject Fan = new JSONObject(properties);
                String MotorStatusResult = Fan.getString("MotorStatus");
                Log.v("浩瀚银河灰度测试，风扇目前情况为：",MotorStatusResult);

                handler.post(new Runnable() {

                    ImageView imageLED = findViewById(id.IILED);
                    ImageView imageFan = findViewById(id.IIFan);

                    ImageView imageHumi = findViewById(id.IIHumi);
                    ImageView imageTemp = findViewById(id.IItemp);

                    @Override
                    public void run() {
                        txtTemperatureR.setText("温度：\n\t" + TemperatureResult);
                        txtHumidityR.setText("湿度：\n\t" + HumidityResult);

                        String str201 = "ON";
                        String str202 = "OFF";
                        if (LightStatusResult.equals(str201)) {
                            txtLightStatusR.setText("\tLED灯情况:" + "\n\t" + "开着呢");
                            imageLED.setImageResource(drawable.led_shine);
                        } else if (LightStatusResult.equals(str202)) {
                            txtLightStatusR.setText("\tLED灯情况： " + "\n\t" + "关了呢");
                            imageLED.setImageResource(drawable.led);
                        } else {
                            txtLightStatusR.setText("\tLED灯情况： " + "\n\t" + LightStatusResult);
                            imageLED.setImageResource(drawable.led);
                        }

                        if (MotorStatusResult.equals(str201)) {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + "开着呢");
                            imageFan.setImageResource(drawable.fan_working);
                        } else if (MotorStatusResult.equals(str202)) {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + "关了呢");
                            imageFan.setImageResource(drawable.fan);
                        } else {
                            txtMotorStatusR.setText("\t风扇情况:" + "\n\t" + MotorStatusResult);
                            imageFan.setImageResource(drawable.fan);
                        }


                        int aHumi = Integer.parseInt(HumidityResult);
                        if(aHumi > 60) {
                            imageHumi.setImageResource(drawable.humi_damp);
                        } else {
                            imageHumi.setImageResource(drawable.humi);
                        }

                        int aTemp = Integer.parseInt(HumidityResult);
                        if(aTemp > 26) {
                            imageTemp.setImageResource(drawable.temp_hot);
                        } else {
                            imageTemp.setImageResource(drawable.temp);
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
            System.out.println("浩瀚银河Get函数灰度测试:responseCode=====" + responseCode);
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
            System.out.println("浩瀚银河Get函数灰度测试:content=====" + content);
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
                System.out.println("浩瀚银河post函数链接灰度测试: " + response.body().string());
                System.out.println("浩瀚银河post函数头部读取灰度测试: " + response.header("x-subject-token"));
                HUAWEITOKEN = response.header("x-subject-token");
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            onBackPressed();
            return true;
        } else {
            return onKeyDown(keyCode,event);
        }
    }

    public void onBackPressed() {
        Toast.makeText(NewFirstActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        newtimer1st.cancel();
    }
}
