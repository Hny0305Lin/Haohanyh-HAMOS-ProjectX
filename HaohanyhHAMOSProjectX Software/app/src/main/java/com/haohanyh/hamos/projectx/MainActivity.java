// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx;

import static com.haohanyh.hamos.projectx.R.*;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.haohanyh.hamos.projectx.ImportActivity.FifthActivity;
import com.haohanyh.hamos.projectx.ImportActivity.FirstActivity;
import com.haohanyh.hamos.projectx.ImportActivity.FourthActivity;
import com.haohanyh.hamos.projectx.ImportActivity.SecondActivity;
import com.haohanyh.hamos.projectx.ImportActivity.ThirdActivity;


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
                if (btnnew7.isClickable()) { //如果是可以点击的，则执行方法
                    Click7(view);
                }
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
        Toast.makeText(MainActivity.this,"检测网络中",Toast.LENGTH_SHORT).show();
    }

    void Click7(View v)
    {
        try {
            sleep(300);
            Toast.makeText(MainActivity.this,"使用浩瀚银河项目开源协议，助力浩瀚银河项目质量提升!",Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://raw.githubusercontent.com/Hny0305Lin/LICENSE/main/LICENSE");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            //finish();//关闭当前活动
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}