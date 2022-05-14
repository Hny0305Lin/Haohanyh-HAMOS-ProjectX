// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
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
    String num,IorO;

    Button btnLamp,btnLED,btnFan;
    Button btnoff,btnon;

    public boolean btnsensorI;
    public int sensorI;
    public boolean btnsensorII;
    public int sensorII;
    public boolean btnsensorIII;
    public int sensorIII;
    public boolean btnsensorIV;
    public int sensorIV;

    public boolean btnbijiaoI;
    public int bijiaoI;
    public boolean btnbijiaoII;
    public int bijiaoII;
    public boolean btnbijiaoIII;
    public int bijiaoIII;

    public boolean btnuseI;
    public int useI;
    public boolean btnuseII;
    public int useII;
    public boolean btnuseIII;
    public int useIII;

    public boolean btnbooloff;
    public int booloff;
    public boolean btnboolon;
    public int boolon;

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
                            tp();
                        }
                        if(ExceedGetInformation() == 200)
                        {
                            Toast.makeText(ExceedActivity.this, "灰度测试：您有零项没设置，非常好!", Toast.LENGTH_SHORT).show();
                            tp();
                        }
                        if(ExceedGetInformation() != 404)
                        {
                            Toast.makeText(ExceedActivity.this, "灰度测试：您有一项没设置，但没有关系!", Toast.LENGTH_SHORT).show();
                            tp();
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
    }

    private void tp() {
        Intent i = new Intent(ExceedActivity.this,ExceedRunActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("sensorI",sensorI);
        bundle.putInt("sensorII",sensorII);
        bundle.putInt("sensorIII",sensorIII);
        bundle.putInt("sensorIV",sensorIV);
        bundle.putInt("bijiaoI",bijiaoI);
        bundle.putInt("bijiaoII",bijiaoII);
        bundle.putInt("bijiaoIII",bijiaoIII);
        bundle.putInt("useI",useI);
        bundle.putInt("useII",useII);
        bundle.putInt("useIII",useIII);
        bundle.putInt("booloff",booloff);
        bundle.putInt("boolon",boolon);
        bundle.putInt("num", Integer.parseInt(num));
        bundle.putInt("IorO", Integer.parseInt(IorO));
        i.putExtra("bundle", bundle);
        startActivity(i);
    }
    public void TempLiandongClick(View view) {
        btnsensorI = true;
        sensorI = 1;
        btnsensorII = false;
        sensorII = 0;
        btnsensorIII = false;
        sensorIII = 0;
        btnsensorIV = false;
        sensorIV = 0;
        Toast.makeText(ExceedActivity.this,"触发温度按钮",Toast.LENGTH_SHORT).show();
    }

    public void HumiLiandongClick(View view) {
        btnsensorI = false;
        sensorI = 0;
        btnsensorII = true;
        sensorII = 1;
        btnsensorIII = false;
        sensorIII = 0;
        btnsensorIV = false;
        sensorIV = 0;
        Toast.makeText(ExceedActivity.this,"触发湿度按钮",Toast.LENGTH_SHORT).show();
    }

    public void LightLiandongClick(View view) {
        btnsensorI = false;
        sensorI = 0;
        btnsensorII = false;
        sensorII = 0;
        btnsensorIII = true;
        sensorIII = 1;
        btnsensorIV = false;
        sensorIV = 0;
        Toast.makeText(ExceedActivity.this,"触发光照按钮",Toast.LENGTH_SHORT).show();
    }

    public void InfranedLiandongClick(View view) {
        btnsensorI = false;
        sensorI = 0;
        btnsensorII = false;
        sensorII = 0;
        btnsensorIII = false;
        sensorIII = 0;
        btnsensorIV = true;
        sensorIV = 1;
        Toast.makeText(ExceedActivity.this,"触发人体按钮",Toast.LENGTH_SHORT).show();
    }

    public void XiaoyuLiandongClick(View view) {
        btnbijiaoI = true;
        bijiaoI = 1;
        btnbijiaoII = false;
        bijiaoII = 0;
        btnbijiaoIII = false;
        bijiaoIII = 0;
        Toast.makeText(ExceedActivity.this,"触发小于",Toast.LENGTH_SHORT).show();
    }

    public void DengyuLiandongClick(View view) {
        btnbijiaoI = false;
        bijiaoI = 0;
        btnbijiaoII = true;
        bijiaoII = 1;
        btnbijiaoIII = false;
        bijiaoIII = 0;
        Toast.makeText(ExceedActivity.this,"触发等于",Toast.LENGTH_SHORT).show();
    }

    public void DayuLiandongClick(View view) {
        btnbijiaoI = false;
        bijiaoI = 0;
        btnbijiaoII = false;
        bijiaoII = 0;
        btnbijiaoIII = true;
        bijiaoIII = 1;
        Toast.makeText(ExceedActivity.this,"触发大于",Toast.LENGTH_SHORT).show();
    }

    public void LampLiandongCheck(View view) {
        btnuseI = true;
        useI = 1;
        Toast.makeText(ExceedActivity.this,"触发按钮台灯",Toast.LENGTH_SHORT).show();
    }

    public void LEDLiandongCheck(View view) {
        btnuseII = true;
        useII = 1;
        Toast.makeText(ExceedActivity.this,"触发按钮LED",Toast.LENGTH_SHORT).show();
    }

    public void FanLiandongCheck(View view) {
        btnuseIII = true;
        useIII = 1;
        Toast.makeText(ExceedActivity.this,"触发按钮风扇",Toast.LENGTH_SHORT).show();
    }

    public void ONLiandongCheck(View view) {
        btnbooloff = false;
        btnboolon = true;
        booloff = 0;
        boolon = 1;
    }

    public void OFFLiandongCheck(View view) {
        btnbooloff = true;
        btnboolon = false;
        booloff = 1;
        boolon = 0;
    }

    private boolean ExceedCheck() {

        num = txtnum.getText().toString();
        IorO = txtIO.getText().toString();

        if(btnsensorI || btnsensorII || btnsensorIII || btnsensorIV) {
            //第一层 按钮肯定按了嘛,所以接下来是剩下的第一行的控件
            if(btnbijiaoI || btnbijiaoII || btnbijiaoIII) {
                //第二层 大于小于等于肯定按了一个嘛,所以接下来是剩下的
                if(num.equals("") && IorO.equals(""))
                {
                    Toast.makeText(ExceedActivity.this,"目前您还没设置数值，如果是人体感应联动，您还没设置0和1的触发条件！",Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(ExceedActivity.this,"正在检查....",Toast.LENGTH_SHORT).show();
                    cbKey.setChecked(false);
                    return true;
                }
            }
            Toast.makeText(ExceedActivity.this,"您还未选择大于、小于、等于的其中一个!",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(ExceedActivity.this,"您还未选择传感器的其中一个!",Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean ExceedCheck2() {
        if(btnuseI || btnuseII || btnuseIII)
        {
            //第一层 按钮肯定按了嘛,所以接下来是剩下的第二行的控件
            if(btnboolon || btnbooloff)
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
