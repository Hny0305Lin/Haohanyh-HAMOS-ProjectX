package com.haohanyh.hamos.projectx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.haohanyh.hamos.projectx.ImportActivity.FirstActivity;
import com.haohanyh.hamos.projectx.ImportActivity.FourthActivity;
import com.haohanyh.hamos.projectx.ImportActivity.NewFirstActivity;
import com.haohanyh.hamos.projectx.ImportActivity.SecondActivity;
import com.haohanyh.hamos.projectx.ImportActivity.ThirdActivity;
import com.haohanyh.hamos.projectx.LianDong.ExceedActivity;
import com.haohanyh.hamos.projectx.R.*;

public class MainNewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_thirdnew);

        Toast.makeText(MainNewActivity.this,"HAMOS新页面，4.0版本",Toast.LENGTH_SHORT).show();

        ImageButton btntoold = findViewById(id.imageButtonClickToOld);
        btntoold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = "回到旧页面";
                ClickToOld(view,a);
            }
        });
    }

    public void ClickToOld(View view,String a) {
        Toast.makeText(MainNewActivity.this,"即将"+a,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainNewActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void ClickMain1(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("温湿度场景 应用于家居阳台、卫生间、大厅等");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, NewFirstActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/blob/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/ImportActivity/NewFirstActivity.java");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    public void ClickMain2(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("温湿度场景 应用于家居阳台、卫生间、大厅等");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, FirstActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/blob/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/ImportActivity/FirstActivity.java");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    public void ClickMain3(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("电灯控制场景 应用于家居装饰和照明系统等");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/blob/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/ImportActivity/SecondActivity.java");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    public void ClickMain4(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("人体感应场景 应用于防盗系统和距离感应等");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, ThirdActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/blob/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/ImportActivity/ThirdActivity.java");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    public void ClickMain5(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("家车和定位场景 应用于GPS家用车定位、儿童防丢可穿戴设备等");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, FourthActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/blob/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/ImportActivity/FourthActivity.java");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    public void ClickMain6(View view) {
        Context context = MainNewActivity.this;
        int bottom = 16;
        int top = 8;
        TextView textView = new TextView(context);
        textView.setText("自定义联动 可自定义使用场景");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setPadding(0, top, 0, bottom);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(context, color.Pink_is_fancy));
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setPositiveButton("进入该场景", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainNewActivity.this, ExceedActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("查看开源代码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://gitee.com/light-harmonyOS/Haohanyh-HAMOS-ProjectX/tree/master/HaohanyhHAMOSProjectX%20Software/app/src/main/java/com/haohanyh/hamos/projectx/LianDong");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        builder.show();
    }
}
