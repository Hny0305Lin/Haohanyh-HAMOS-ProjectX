// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx.LianDong;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.haohanyh.hamos.projectx.R;
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

public class ExceedRunActivity extends Activity {

    ImageView imgtemp,imghumi,imglight,imgpeople,imglamp,imgled,imgfan;
    TextView txttemp,txthumi,txtlight,txtpeople,txtlamp,txtled,txtfan;

    private String HUAWEITOKEN = "";

    int sensor1 = 0;
    int sensor2 = 0;
    int sensor3 = 0;
    int sensor4 = 0;
    int xiaoyu = 0;
    int dengyu = 0;
    int dayu = 0;
    int useI = 0;
    int useII = 0;
    int useIII = 0;
    int booloff = 0;
    int boolon = 0;

    public int usenum;
    public int usebijiao;
    public int useonoroff;

    int gnum = 0;
    int gIorO = 0;

    final Timer newliandongtimer = new Timer();
    final Handler handler = new Handler();
    int liandongcount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_liandong_start);

        imgtemp = findViewById(id.imgtempliandong);
        imghumi = findViewById(id.imghumiliandong);
        imglight = findViewById(id.imglightliandong);
        imgpeople = findViewById(id.imgpeopleliandong);
        imglamp = findViewById(id.imglampliandong);
        imgled = findViewById(id.imgledliandong);
        imgfan = findViewById(id.imgfanliandong);

        txttemp = findViewById(id.txtwenduliandong);
        txthumi = findViewById(id.txtshiduliandong);
        txtlight = findViewById(id.txtlightliandong);
        txtpeople = findViewById(id.txtpeopleliandong);
        txtlamp = findViewById(id.txtlampliandong);
        txtled = findViewById(id.txtLEDliandong);
        txtfan = findViewById(id.txtFanliandong);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                liandongcount++;
                Log.w("浩瀚银河灰度测试中:", String.valueOf(liandongcount));
                if(liandongcount == 5)
                {
                    Log.w("浩瀚银河灰度测试中:", "开启联动！");
                }
                if(liandongcount >= 5)
                {
                    GetSomeInformation();
                }
            }
        };
        newliandongtimer.schedule(task,100,1000);
    }

    private void GetSomeInformation() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        sensor1 = bundle.getInt("sensorI");
        sensor2 = bundle.getInt("sensorII");
        sensor3 = bundle.getInt("sensorIII");
        sensor4 = bundle.getInt("sensorIV");
        xiaoyu = bundle.getInt("bijiaoI");
        dengyu = bundle.getInt("bijiaoII");
        dayu = bundle.getInt("bijiaoIII");
        useI = bundle.getInt("useI");
        useII = bundle.getInt("useII");
        useIII = bundle.getInt("useIII");
        booloff = bundle.getInt("booloff");
        boolon = bundle.getInt("boolon");
        gnum = bundle.getInt("num");
        gIorO = bundle.getInt("IorO");

        int usenum = Checkusenum();
        int usebijiao = Checkusebijiao();
        int useonoroff = Checkuseonoroff();

        Log.w("浩瀚银河灰度测试中,usenum:", String.valueOf(usenum));
        Log.w("浩瀚银河灰度测试中,usebijiao:", String.valueOf(usebijiao));
        Log.w("浩瀚银河灰度测试中,useonoroff:", String.valueOf(useonoroff));

        if(sensor1 == 1) {
            int a = 1;
            String project_id = "0d119fff0980102a2fb1c010bcd3cc73";
            String device_id = "625d28cfecf9c41c38216abe_2454refwefw428";
            String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
            Log.w("浩瀚银河灰度测试中:网络结果为：",result);
            Huawei(project_id,device_id,a,usenum,usebijiao,useonoroff);
        }

        if(sensor2 == 1) {
            int a = 2;
            String project_id = "0d119fff0980102a2fb1c010bcd3cc73";
            String device_id = "625d28cfecf9c41c38216abe_2454refwefw428";
            String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
            Log.w("浩瀚银河灰度测试中:网络结果为：",result);
            Huawei(project_id,device_id,a,usenum,usebijiao,useonoroff);
        }

        if(sensor3 == 1) {
            int a = 3;
            String project_id = "0d119fff0980102a2fb1c010bcd3cc73";
            String device_id = "625d28cfecf9c41c38216abe_2454refwefw425";
            String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
            Log.w("浩瀚银河灰度测试中:网络结果为：",result);
            Huawei(project_id,device_id,a,usenum,usebijiao,useonoroff);
        }

        if(sensor4 == 1) {
            int a = 4;
            String project_id = "0d119fff0980102a2fb1c010bcd3cc73";
            String device_id = "625d28cfecf9c41c38216abe_2454refwefw426";
            String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
            Log.w("浩瀚银河灰度测试中:网络结果为：",result);
            Huawei(project_id,device_id,a,usenum,usebijiao,useonoroff);
        }
    }

    int Checkusenum() {
        if(useI + useII + useIII == 3) {
            return 7;
        }
        if(useII + useIII == 2) {
            return 6;
        }
        if(useI + useIII == 2) {
            return 5;
        }
        if(useIII == 1) {
            return 4;
        }
        if(useI + useII == 2) {
            return 3;
        }
        if(useII == 1) {
            return 2;
        }
        if(useI == 1) {
            return 1;
        }
        return 0;
    }

    int Checkusebijiao() {
        if(dayu == 1) {
            return 3;
        }
        if(dengyu == 1) {
            return 2;
        }
        if(xiaoyu == 1) {
            return 1;
        }
        return 0;
    }

    int Checkuseonoroff() {
        if(booloff == 1) {
            return 2;
        }
        if(boolon == 1) {
            return 1;
        }
        return 0;
    }

    @SuppressLint("SetTextI18n")
    void Huawei(String project_id, String device_id, int a, int usenum, int usebijiao, int useonoroff) {
        post();
        String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/" + project_id + "/devices/" + device_id + "/shadow");
        try {
            JSONObject jsonObj = new JSONObject(result);
            //System.out.println("浩瀚银河Huawei函数灰度测试:result=====" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("shadow");
            //System.out.println("浩瀚银河Huawei函数灰度测试:shadow=====" + jsonArray);
            for(int i = 0;i < jsonArray.length(); i++) {
                Log.w("浩瀚银河灰度测试中:三个家电设置情况为:", String.valueOf(usenum));
                if(a == 1) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Temp = new JSONObject(properties);
                    String TemperatureResult = Temp.getString("Temperature");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txttemp.setText("温度"+TemperatureResult);
                            Log.w("浩瀚银河灰度测试，txttemp为", String.valueOf(txttemp));
                            txthumi.setText("未选择");
                            txtlight.setText("未选择");
                            txtpeople.setText("未选择");
                        }
                    });

                    if(usebijiao == 3) {
                        if(parseInt(TemperatureResult) > gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 2) {
                        if(parseInt(TemperatureResult) == gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 1) {
                        if(parseInt(TemperatureResult) < gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                }
                if(a == 2) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Humi = new JSONObject(properties);
                    String HumidityResult = Humi.getString("Humidity");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txthumi.setText("湿度"+HumidityResult);
                            Log.w("浩瀚银河灰度测试，txthumi为", String.valueOf(txthumi));
                            txttemp.setText("未选择");
                            txtlight.setText("未选择");
                            txtpeople.setText("未选择");
                        }
                    });

                    if(usebijiao == 3) {
                        if(parseInt(HumidityResult) > gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 2) {
                        if(parseInt(HumidityResult) == gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 1) {
                        if(parseInt(HumidityResult) < gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                }
                if(a == 3) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Lumi = new JSONObject(properties);
                    String LuminanceResult = Lumi.getString("Luminance");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlight.setText("光照"+LuminanceResult);
                            Log.w("浩瀚银河灰度测试，txtlight为", String.valueOf(txtlight));
                            txthumi.setText("未选择");
                            txttemp.setText("未选择");
                            txtpeople.setText("未选择");
                        }
                    });

                    if(usebijiao == 3) {
                        if(parseInt(LuminanceResult) > gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 2) {
                        if(parseInt(LuminanceResult) == gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }
                    if(usebijiao == 1) {
                        if(parseInt(LuminanceResult) < gnum) {
                            if(usenum == 7) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 6) {
                                ReadySend(2,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 5) {
                                ReadySend(1,useonoroff);
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 4) {
                                ReadySend(4,useonoroff);
                            }
                            if(usenum == 3) {
                                ReadySend(1,useonoroff);
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 2) {
                                ReadySend(2,useonoroff);
                            }
                            if(usenum == 1) {
                                ReadySend(1,useonoroff);
                            }
                        }
                    }

                }
                if(a == 4) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject People = new JSONObject(properties);
                    String Peopleresult = People.getString("Infrared_Status");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtpeople.setText("当前"+Peopleresult);
                            Log.w("浩瀚银河灰度测试，txtpeople为", String.valueOf(txtpeople));
                            txthumi.setText("未选择");
                            txtlight.setText("未选择");
                            txttemp.setText("未选择");
                        }
                    });
                }
                if(usenum == 7) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Lamp = new JSONObject(properties);
                    String LampStatusResult = Lamp.getString("LightStatus");

                    JSONObject Light = new JSONObject(properties);
                    String LightStatusResult = Light.getString("LightStatus");

                    JSONObject Fan = new JSONObject(properties);
                    String MotorStatusResult = Fan.getString("MotorStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("台灯"+LampStatusResult);
                            txtled.setText("LED"+LightStatusResult);
                            txtfan.setText("风扇"+MotorStatusResult);
                        }
                    });
                }
                if(usenum == 6) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Light = new JSONObject(properties);
                    String LightStatusResult = Light.getString("LightStatus");

                    JSONObject Fan = new JSONObject(properties);
                    String MotorStatusResult = Fan.getString("MotorStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("未选择");
                            txtled.setText("LED"+LightStatusResult);
                            txtfan.setText("风扇"+MotorStatusResult);
                        }
                    });
                }
                if(usenum == 5) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Lamp = new JSONObject(properties);
                    String LampStatusResult = Lamp.getString("LightStatus");

                    JSONObject Fan = new JSONObject(properties);
                    String MotorStatusResult = Fan.getString("MotorStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("台灯"+LampStatusResult);
                            txtled.setText("未选择");
                            txtfan.setText("风扇"+MotorStatusResult);
                        }
                    });
                }
                if(usenum == 4) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Fan = new JSONObject(properties);
                    String MotorStatusResult = Fan.getString("MotorStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("未选择");
                            txtled.setText("未选择");
                            txtfan.setText("风扇"+MotorStatusResult);
                        }
                    });
                }
                if(usenum == 3) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Lamp = new JSONObject(properties);
                    String LampStatusResult = Lamp.getString("LightStatus");

                    JSONObject Light = new JSONObject(properties);
                    String LightStatusResult = Light.getString("LightStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("台灯"+LampStatusResult);
                            txtled.setText("LED"+LightStatusResult);
                            txtfan.setText("未选择");
                        }
                    });
                }
                if(usenum == 2) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Light = new JSONObject(properties);
                    String LightStatusResult = Light.getString("LightStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("未选择");
                            txtled.setText("LED"+LightStatusResult);
                            txtfan.setText("未选择");
                        }
                    });
                }
                if(usenum == 1) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Object reported = obj.get("reported");

                    JSONObject Two = new JSONObject(String.valueOf(reported));
                    String properties = Two.getString("properties");

                    JSONObject Lamp = new JSONObject(properties);
                    String LampStatusResult = Lamp.getString("LightStatus");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtlamp.setText("台灯"+LampStatusResult);
                            txtled.setText("未选择");
                            txtfan.setText("未选择");
                        }
                    });
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void ReadySend(int sensor,int b) {
        //准备自动化，那么开始吧~
        // sensor 是 a       传感器选择                   确认device_id、service_id、command_name、command_param
        // b 是 useonoroff   开关结果                    确认触发条件、command_value

        String device_id = "";
        String service_id = "";
        String command_name = "";
        String command_param = "";
        String command_value = "";

        String project_id = "0d119fff0980102a2fb1c010bcd3cc73";

        if(sensor == 1) {
            device_id = "625d28cfecf9c41c38216abe_2454refwefw428";
            service_id = "Agriculture";
            command_name = "Agriculture_Control_light";
            if(b == 1) {
                command_param = "Light";
                command_value = "ON";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","LED开");
            }
            if(b == 2) {
                command_param = "Light";
                command_value = "OFF";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","LED关");
            }
        }
        if(sensor == 2) {
            device_id = "625d28cfecf9c41c38216abe_2454refwefw425";
            service_id = "Light";
            command_name = "Light_Control_Led";
            if(b == 1) {
                command_param = "Led";
                command_value = "ON";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","台灯开");
            }
            if(b == 2) {
                command_param = "Led";
                command_value = "OFF";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","台灯关");
            }
        }
        if(sensor == 4) {
            device_id = "625d28cfecf9c41c38216abe_2454refwefw428";
            service_id = "Agriculture";
            command_name = "Agriculture_Control_Motor";
            if(b == 1) {
                command_param = "Motor";
                command_value = "ON";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","风扇开");
            }
            if(b == 2) {
                command_param = "Motor";
                command_value = "OFF";
                ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
                Log.w("TAG","风扇关");
            }
        }
    }
    private void ControlSenderHUAWEI(String project_id,String device_id,String service_id,String command_name,String command_param,String command_value) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        //Button toggle = ((Button) v);
        String json = "";

        JSONObject object = new JSONObject();//我们需要修改值~这个没办法哦~

        String ONorOFF = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("service_id", service_id);
            //System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试:service_id=====" + jsonObject);
            jsonObject.put("command_name", command_name);
            //System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试:command_name=====" + jsonObject);
            //object.put(command_param, toggle.isClickable() ? "ON" : "ON");
            object.put(command_param, command_value);
            //System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试:开关情况=====" + object);
            ONorOFF = String.valueOf(object);
            //System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试:传参情况=====" + ONorOFF);
            jsonObject.put("paras", object);
            String jsonParams = jsonObject.toString();
            //Log.d("TAG", toggle.getText().toString() + ":" + jsonParams);
            //System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试:总=====" + jsonParams);
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
                System.out.println("浩瀚银河ControlSenderHUAWEI函数灰度测试: " + e.getLocalizedMessage() + "，灰度测试失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("浩瀚银河ControlSenderHUAWEI函数链接灰度测试: " + response.body().string());
                System.out.println("出现以上信息，说明传值到华为云，再到开发板，同时到APP上显示情况，成功");
            }
        });
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
        String jsonwenben = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"zhonghuayouwei_guozhironyao\"},\"name\":\"haohanyhhuawei\",\"password\":\"wodeshijie0305\"}}},\"scope\":{\"domain\":{\"name\":\"zhonghuayouwei_guozhironyao\"}}}}";
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

    public void ExitClick(View view) {
        onBackPressed();
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
        Toast.makeText(ExceedRunActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        newliandongtimer.cancel();
    }

}

