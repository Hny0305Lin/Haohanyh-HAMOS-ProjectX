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
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FourthActivity extends Activity {

    private String HUAWEITOKEN = "";
    public TextView txtLongitude;//经度
    public TextView txtLatitude;//纬度

    final Handler handler = new Handler();
    final Timer timer4th = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_new);

        Button btnnew = findViewById(R.id.kaiqi);
        ImageButton btnexit = findViewById(R.id.exit);
        txtLongitude = findViewById(R.id.txtL1);
        txtLatitude = findViewById(R.id.txtL2);

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() { Huawei("******","******"); }
                    };
                    timer4th.schedule(task, 0, 1000);
                    Toast.makeText(FourthActivity.this, "开始读取福州智能数据机房华为IotA接口的光照传感器数值", Toast.LENGTH_SHORT).show();
                    btnnew.setClickable(false);
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FourthActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
                onBackPressed();
                timer4th.cancel();
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
        Toast.makeText(FourthActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        timer4th.cancel();
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
                System.out.println("浩瀚银河Huawei函数灰度测试:reported=====");
                Object reported = obj.get("reported");
                System.out.println(reported);

                JSONObject Two = new JSONObject(String.valueOf(reported));
                String properties = Two.getString("properties");
                System.out.println("浩瀚银河Huawei函数灰度测试:properties=====" + properties);

                JSONObject AAA = new JSONObject(properties);
                String Longituderesult = AAA.getString("Longitude");
                System.out.println("浩瀚银河Huawei函数灰度测试:Longitude经度=====" + Longituderesult);
                String Latituderesult = AAA.getString("Latitude");
                System.out.println("浩瀚银河Huawei函数灰度测试:Latitude纬度=====" + Latituderesult);

                handler.post(new Runnable() {

                    ImageView gpspic = findViewById(R.id.gpsgpspic);

                    @Override
                    public void run() {
                        txtLongitude.setText("\t经度： " + "\n\t" + Longituderesult);
                        txtLatitude.setText("\t纬度： " + "\n\t" + Latituderesult);
                        gpspic.setImageResource(R.drawable.gps_searching);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        gpspic.setImageResource(R.drawable.gps_working);

                        double aL1 = Double.parseDouble(Longituderesult);
                        double aL2 = Double.parseDouble(Longituderesult);
                        DecimalFormat df = new DecimalFormat("#.00000");//保留五位小数点
                        String Longituderesult1 = df.format(aL1);
                        aL1 = Double.parseDouble(Longituderesult1);
                        String Latituderesult2 = df.format(aL2);
                        aL2 = Double.parseDouble(Latituderesult2);

//                        double aL1 = Double.valueOf(Longituderesult);
//                        double aL2 = Double.valueOf(Latituderesult);

                        if((aL1 > 0.0 && aL2 > 0.0) || (aL1 < 0.0 && aL2 < 0.0))
                        {
                            gpspic.setImageResource(R.drawable.gps_working2);
                        } else {
                            gpspic.setImageResource(R.drawable.gps_searching2);
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
                System.out.println("浩瀚银河post函数链接灰度测试: " + response.body().string());
                System.out.println("浩瀚银河post函数头部读取灰度测试: " + response.header("x-subject-token"));
                HUAWEITOKEN = response.header("x-subject-token");
            }
        });
    }
}
