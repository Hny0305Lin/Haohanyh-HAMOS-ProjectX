package com.haohanyh.hamos.projectx.LianDong;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.haohanyh.hamos.projectx.R.*;


public class ExceedActivity extends Activity {

    int autotimer = 0;

    CheckBox cbKey;

    Button btnTemp,btnHumi,btnLight,btnInfraned;
    Button btndayu,btndengyu,btnxiaoyu;
    EditText txtnum,txtIO;

    Button btnLamp,btnLED,btnFan;
    Button btnoff,btnon;

    boolean btnsensorI,btnsensorII,btnsensorIII,btnsensorIV,btnbijiaoI,btnbijiaoII,btnbijiaoIII;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_liandong_all);

        //复选框，勾选为开启线程，非勾选为关闭线程
        cbKey = findViewById(id.checkBoxKey);

        btnTemp = findViewById(id.rb1);
        btnHumi = findViewById(id.rb2);
        btnLight = findViewById(id.rb3);
        btnInfraned = findViewById(id.rb4);
        btnxiaoyu = findViewById(id.rrb1);
        btndengyu = findViewById(id.rrb2);
        btndayu = findViewById(id.rrb3);

        btnLamp = findViewById(id.rb5);
        btnLED = findViewById(id.rb6);
        btnFan = findViewById(id.rb7);
        btnoff = findViewById(id.rrb4);
        btnon = findViewById(id.rrb5);

        txtnum = findViewById(id.editText1);
        txtIO = findViewById(id.editText2);
        
        cbKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autotimer++;
                if(autotimer % 2 != 0) {
                    Toast.makeText(ExceedActivity.this,"开启自定义联动功能",Toast.LENGTH_SHORT).show();
                    if(ExceedCheck() && ExceedCheck2())
                    {
                        Toast.makeText(ExceedActivity.this, "正在加载中...！", Toast.LENGTH_SHORT).show();
                        Log.w("浩瀚银河灰度测试，函数为：", String.valueOf(ExceedGetInformation()));
                        if(ExceedGetInformation() == 502)
                        {
                            Toast.makeText(ExceedActivity.this, "灰度测试：您有一项没设置，但没有关系!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ExceedActivity.this,ExceedRunActivity.class);
                            startActivity(i);
                        }
                        if(ExceedGetInformation() == 200)
                        {
                            Toast.makeText(ExceedActivity.this, "灰度测试：您有零项没设置，非常好!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ExceedActivity.this,ExceedRunActivity.class);
                            startActivity(i);
                        }
                        if(ExceedGetInformation() != 404)
                        {
                            Intent i = new Intent(ExceedActivity.this,ExceedRunActivity.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(ExceedActivity.this, "您还没设置完毕！", Toast.LENGTH_SHORT).show();
                        cbKey.setChecked(false);
                        autotimer--;//还没设置完毕就想跑联动？做梦！
                    }
                } else {
                    Context context = ExceedActivity.this;
                    int bottom = 24;
                    int top = 12;
                    TextView textView = new TextView(context);
                    textView.setText("您已关闭自定义联动功能，是否离开当前页面？\n\n\n");
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    textView.setPadding(0, top, 0, bottom);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setCustomTitle(textView)
                            .setPositiveButton("我要保留", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(ExceedActivity.this,"好的目前您还在此页面！",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("我要离开", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onBackPressed();
                                }
                            });
                    builder.show();
                }
            }
        });

        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnsensorI = true;
                boolean btnsensorII = false;
                boolean btnsensorIII = false;
                boolean bensensorIV = false;
            }
        });

        btnHumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnsensorI = false;
                boolean btnsensorII = true;
                boolean btnsensorIII = false;
                boolean bensensorIV = false;
            }
        });

        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnsensorI = false;
                boolean btnsensorII = false;
                boolean btnsensorIII = true;
                boolean bensensorIV = false;
            }
        });

        btnInfraned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnsensorI = false;
                boolean btnsensorII = false;
                boolean btnsensorIII = false;
                boolean bensensorIV = true;
            }
        });

        btnxiaoyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnbijiaoI = true;
                boolean btnbijiaoII = false;
                boolean btnbijiaoIII = false;
            }
        });

        btndengyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnbijiaoI = false;
                boolean btnbijiaoII = true;
                boolean btnbijiaoIII = false;
            }
        });

        btndayu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean btnbijiaoI = false;
                boolean btnbijiaoII = false;
                boolean btnbijiaoIII = true;
            }
        });
    }

    private boolean ExceedCheck() {

        String num = txtnum.getText().toString();
        String IorO = txtIO.getText().toString();

        if(btnTemp.isClickable() || btnHumi.isClickable() || btnLight.isClickable() || btnInfraned.isClickable()) {
            //第一层 按钮肯定按了嘛,所以接下来是剩下的第一行的控件
            if(btnxiaoyu.isClickable() || btndengyu.isClickable() || btndayu.isClickable()) {
                //第二层 大于小于等于肯定按了一个嘛,所以接下来是剩下的
                if(num.equals("") && IorO.equals(""))
                {
                    Toast.makeText(ExceedActivity.this,"目前您还没设置数值，如果是人体感应联动，您还没设置0和1的触发条件！",Toast.LENGTH_SHORT).show();
                    return false;
                }
                Toast.makeText(ExceedActivity.this,"正在检查....",Toast.LENGTH_SHORT).show();
                cbKey.setChecked(false);
                return true;
            }
            Toast.makeText(ExceedActivity.this,"您还未选择大于、小于、等于的其中一个!",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(ExceedActivity.this,"您还未选择传感器的其中一个!",Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean ExceedCheck2() {
        if(btnLamp.isClickable() || btnLED.isClickable() || btnFan.isClickable())
        {
            //第一层 按钮肯定按了嘛,所以接下来是剩下的第二行的控件
            if(btnoff.isClickable() || btnon.isClickable())
            {
                Toast.makeText(ExceedActivity.this,"检查完成，您的设置毫无问题!",Toast.LENGTH_SHORT).show();
                cbKey.setChecked(true);
                return true;
            }
            Toast.makeText(ExceedActivity.this,"您还未选择On和Off的其中一个!",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(ExceedActivity.this,"您还未选择传感器的其中一个!",Toast.LENGTH_SHORT).show();
        return false;
    }

    private int ExceedGetInformation() {

        String numstr = txtnum.getText().toString();
        String IorOstr = txtIO.getText().toString();

        int num = Integer.parseInt(numstr.equals("") ? "502":numstr);
        Log.w("浩瀚银河灰度测试,num为：", String.valueOf(num));
        int IorO = Integer.parseInt(IorOstr.equals("") ? "502":IorOstr);
        Log.w("浩瀚银河灰度测试,IorO为：", String.valueOf(IorO));

        if(btnsensorI || btnsensorII || btnsensorIII)
        {
            if(num < 60 && num > -20)
            {
                return num;
            }
        }

        if(btnsensorIV)
        {
            if(IorO < 2 && IorO > -1)
            {
                return IorO;
            }
        }

        return ((numstr.equals("") && (IorOstr.equals("")) ? 404:200));
        //404 Not Found，233333~~~
        //404 为找不到设置是否正常
        //502 为没有填写的正常返回值
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
        Toast.makeText(ExceedActivity.this,"正在完全退出进程...",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

}
