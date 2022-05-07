// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx;

import static com.haohanyh.hamos.projectx.R.*;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.haohanyh.hamos.projectx.ImportActivity.FifthActivity;
import com.haohanyh.hamos.projectx.ImportActivity.FirstActivity;
import com.haohanyh.hamos.projectx.ImportActivity.FourthActivity;
import com.haohanyh.hamos.projectx.ImportActivity.NewFirstActivity;
import com.haohanyh.hamos.projectx.ImportActivity.SecondActivity;
import com.haohanyh.hamos.projectx.ImportActivity.ThirdActivity;

import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Button btnnew = findViewById(id.kaiqi);
        Button btnnew2 = findViewById(id.kaiqi2);
        Button btnnew3 = findViewById(id.kaiqi3);
        Button btnnew4 = findViewById(id.kaiqi4);
        Button btnnew5 = findViewById(id.kaiqi5);
        Button btnnew6 = findViewById(id.kaiqi6);
        Button btnnew7 = findViewById(id.kaiqi7);
        Button btnnew8 = findViewById(id.kaiqinew);

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnnew.isClickable()) { //如果是可以点击的，则执行方法
                    Click1(v);
                }
            }
        });

        btnnew2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnnew2.isClickable()) { //如果是可以点击的，则执行方法
                    Click2(view);
                }
            }
        });

        btnnew3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnnew3.isClickable()) { //如果是可以点击的，则执行方法
                    Click3(view);
                }
            }
        });

        btnnew4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnnew4.isClickable()) { //如果是可以点击的，则执行方法
                    Click4(view);
                }
            }
        });

        btnnew5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnnew5.isClickable()) { //如果是可以点击的，则执行方法
                    Click5(view);
                }
            }
        });

        btnnew6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnnew6.isClickable()) { //如果是可以点击的，则执行方法
                    Click6(view);
                }
            }
        });

        btnnew7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity.this;
                int bottom = 16;
                int top = 8;
                TextView textView = new TextView(context);
                textView.setText("使用本软件，即认为您允许我们软件的使用协议和开源协议。");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setPadding(0, top, 0, bottom);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(ContextCompat.getColor(context, color.purple_200));
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setCustomTitle(textView)
                        .setPositiveButton("我要看看开源协议", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Click7Kaiyuanxieyi(view);
                            }
                        })
                        .setNegativeButton("我要看看使用协议", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Click7Yonghuxieyi(view);
                            }
                        });
                builder.show();
            }
        });

        btnnew8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click8(view);
            }
        });
    }

    void Click1(View v)
    {
        Toast.makeText(MainActivity.this,"即将前往使用中国移动Onenet接口的温湿度采集",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
    }

    void Click2(View v)
    {
        Toast.makeText(MainActivity.this,"即将前往使用华为IotA接口的Light",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    void Click3(View v)
    {
        Toast.makeText(MainActivity.this,"即将前往使用华为IotA接口的Infraned",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        startActivity(intent);
    }

    void Click4(View v)
    {
        Toast.makeText(MainActivity.this,"即将前往使用华为IotA接口的GPS",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, FourthActivity.class);
        startActivity(intent);
    }

    void Click5(View v)
    {
        Toast.makeText(MainActivity.this,"系统设置",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, FifthActivity.class);
        startActivity(intent);
    }


    void Click6(View v)
    {
        boolean result = ChecknetworkHaohanyh();
        boolean result2 = ChecknetworkHUAWEI();
        Toast.makeText(MainActivity.this,"检测网络中",Toast.LENGTH_SHORT).show();
        if ((result) == (result2))
        {
            Toast.makeText(MainActivity.this,"与浩瀚银河服务器和华为服务器连接通畅!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,"请尝试重新连接网络后再检测!",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ChecknetworkHaohanyh(){
        Runtime run = Runtime.getRuntime();
        try {
            Process pro = run.exec("ping -c 3 haohanyh.ovh");
            int res = pro.waitFor();
            Log.w("TAG", "浩瀚银河灰度测试网络链接ChecknetworkHaohanyh函数结果:" + res);
            return (res==0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean ChecknetworkHUAWEI(){
        Runtime run = Runtime.getRuntime();
        try {
            Process pro = run.exec("ping -c 3 iotda.cn-north-4.myhuaweicloud.com");
            int res = pro.waitFor();
            Log.w("TAG", "浩瀚银河灰度测试网络链接ChecknetworkHUAWEI函数结果:" + res);
            return (res==0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    void Click7Kaiyuanxieyi(View v)
    {
        try {
            sleep(300);
            Toast.makeText(MainActivity.this,"使用浩瀚银河项目开源协议，助力浩瀚银河项目质量提升!",Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://gcore.jsdelivr.net/gh/Hny0305Lin/LICENSE@main/LICENSE ");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            //finish();//关闭当前活动
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void Click7Yonghuxieyi(View v)
    {
        try {
            sleep(300);
            Toast.makeText(MainActivity.this,"HAMOS用户协议，正在跳转中...",Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://gcore.jsdelivr.net/gh/Hny0305Lin/LICENSE@main/Haohanyh_protocol.md ");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            //finish();//关闭当前活动
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void Click8(View v)
    {
        Toast.makeText(MainActivity.this,"即将前往使用华为IotA接口的Agriculture",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, NewFirstActivity.class);
        startActivity(intent);
    }
}