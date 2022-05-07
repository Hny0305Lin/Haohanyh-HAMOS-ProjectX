// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx.ImportActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ThirdActivity extends Activity {

    private String HUAWEITOKEN = "";
    
    public TextView txtPeople;

    final Handler handler = new Handler();
    final Timer timer3rd = new Timer();

    private Button btnauto = findViewById(id.auto);

    int haohanyhtime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_third_new);

        Button btnnew = findViewById(id.kaiqi);
        ImageButton btnexit = findViewById(id.exit);
        txtPeople = findViewById(id.txtpeoplestatus);

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() { Huawei("******","******"); }
                    };
                    timer3rd.schedule(task,0,1000);
                    Toast.makeText(ThirdActivity.this,"开始读取福州智能数据机房华为IotA接口的人体传感器数值",Toast.LENGTH_SHORT).show();
                    btnnew.setClickable(false);
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThirdActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
                onBackPressed();
                timer3rd.cancel();
            }
        });
    }

    void auto() {
        btnauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //控制传感器，新增计数器
                haohanyhtime++;
                if((haohanyhtime % 2 != 0))
                {
                    ;
                } else {
                    ;
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
        Toast.makeText(ThirdActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        timer3rd.cancel();
    }

    @SuppressLint("SetTextI18n")
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
                System.out.println("浩瀚银河灰度测试:reported=====");
                Object reported = obj.get("reported");
                System.out.println(reported);

                JSONObject Two = new JSONObject(String.valueOf(reported));
                String properties = Two.getString("properties");
                System.out.println("浩瀚银河灰度测试:properties====="+properties);

                JSONObject People = new JSONObject(properties);
                String Peopleresult = People.getString("Infrared_Status");
                System.out.println("浩瀚银河灰度测试:Infrared_Status====="+Peopleresult);

                handler.post(new Runnable() {

                    ImageView peoplestatus = findViewById(id.people);

                    @Override
                    public void run() {
                        String str1 = "Safe";
                        String str2 = "Intrude";
                        if(Peopleresult.equals(str1))
                        {
                            txtPeople.setText("\t当前状态： " + "\n\t" +  "安全");
                            peoplestatus.setImageResource(drawable.infraned_security);
                        } else if(Peopleresult.equals(str2)) {
                            txtPeople.setText("\t当前状态： " + "\n\t" +  "有人闯入");
                            peoplestatus.setImageResource(drawable.infraned_warning);
                        } else {
                            txtPeople.setText("\t当前状态： " + "\n\t" +  Peopleresult);
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
}
