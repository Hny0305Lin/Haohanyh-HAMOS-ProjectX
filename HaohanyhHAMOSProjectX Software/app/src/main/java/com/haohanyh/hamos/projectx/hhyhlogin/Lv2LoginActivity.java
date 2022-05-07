package com.haohanyh.hamos.projectx.hhyhlogin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.haohanyh.hamos.projectx.R;

import com.haohanyh.hamos.projectx.*;

public class Lv2LoginActivity extends Activity {

    EditText user,pass;
    Button btn_signup,btn_signin;
    SQLiteDatabase db;
    ContentValues values;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int haohanyhtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //以上和以下代码即将价值1000万 谢谢各路老板~
        db=openOrCreateDatabase("userinfo.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists t_user(name text,pass text)");
        values = new ContentValues();
        user= (EditText) findViewById(R.id.EditTextTextYonghuming);
        pass= (EditText) findViewById(R.id.EditTextTextMima);

        btn_signup = (Button) findViewById(R.id.signup);//登录
        btn_signin = (Button) findViewById(R.id.signin);//注册

        //btn_signup.setOnClickListener((View.OnClickListener) this);
        db=openOrCreateDatabase("userinfo.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists t_user(name text,pass text)");
        values = new ContentValues();

        sharedPreferences=this.getSharedPreferences("user.info",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String userString = sharedPreferences.getString("username","");
        String passString = sharedPreferences.getString("pas1","");

        if (userString.equals("")&&passString.equals("")){

        } else {
            user.setText(userString);
            pass.setText(passString);
        }
        editor.putString("username",user.getText().toString());
        editor.putString("pas1",pass.getText().toString());
        editor.commit();

        Button btn1 = (Button) findViewById(R.id.signup);
        btn1.setOnClickListener(new View.OnClickListener() {  //点击按钮监听
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(user.getText().toString())){
                    Toast.makeText(Lv2LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass.getText().toString())){
                    Toast.makeText(Lv2LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cursor =db.query("t_user",null,"name = ? and pass = ?",new String[]{user.getText().toString(),pass.getText().toString()},null,null,null);
                if (cursor.getCount()>0){
                    ShowClick1();
                } else {
                    Toast.makeText(Lv2LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
            }});
    }

    private void ShowClick1() {
        if(haohanyhtime >= 1){
            Intent i = new Intent(Lv2LoginActivity.this, MainActivity.class);
            startActivity(i);
            haohanyhtime = haohanyhtime + 1;
        } else if(haohanyhtime == 0) {
            Intent i = new Intent(Lv2LoginActivity.this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
            haohanyhtime = haohanyhtime + 1;
        }
    }

    public void Click2(View view) {
        switch (view.getId()) {
            case R.id.signin:
                Intent i = new Intent(Lv2LoginActivity.this, Lv2RegisterActivity.class); //切换窗口
                startActivity(i);
        }
    }

    public void Click1(View view) {
        switch (view.getId()) {
            case R.id.signup: Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();//提示
        }
    }
}
