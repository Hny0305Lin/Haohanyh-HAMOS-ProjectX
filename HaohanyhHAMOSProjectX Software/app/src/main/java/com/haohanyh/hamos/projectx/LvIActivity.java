// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx;

import static com.haohanyh.hamos.projectx.R.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.haohanyh.hamos.projectx.hhyhlogin.Lv2LoginActivity;

public class LvIActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 两秒后进入系统
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(layout.activity_lv1);

        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(3000);//使程序休眠五秒
                    Intent i=new Intent(getApplicationContext(), Lv2LoginActivity.class);//启动MainActivity
                    //2022.5.6 1：43 改写成进入登录页面
                    startActivity(i);
                    finish();//关闭当前活动

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程

    }
}
