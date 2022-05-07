package com.haohanyh.hamos.projectx.hhyhlogin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.haohanyh.hamos.projectx.R;

public class Lv2RegisterActivity extends Activity {

    EditText newuser,newpass,newpass1;
    Button btzc,bttc;
    SQLiteDatabase db;
    ContentValues values;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //以上和以下代码即将价值1000万 谢谢各路老板~
        db=openOrCreateDatabase("userinfo.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists t_user(name text,pass text)");
        values = new ContentValues();
        newuser= (EditText) findViewById(R.id.editTextTextPersonNameAccountName);
        newpass= (EditText)findViewById(R.id.editTextTextPasswordPassword);
        newpass1= (EditText)findViewById(R.id.editTextTextPasswordPassword2);

        btzc=(Button)findViewById(R.id.buttonzhuce);
        bttc=(Button)findViewById(R.id.button2exit);

        btzc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor= db.query("t_user",null,"name = ? and pass= ?",new String[]{newuser.getText().toString(),newpass.getText().toString()},null,null,null);
                if (cursor.getCount()>0){
                    Toast.makeText(Lv2RegisterActivity.this,"用户名已注册",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Lv2RegisterActivity.this, Lv2LoginActivity.class); //切换窗口
                    startActivity(i);
                }else{
                    if (newpass.getText().toString().equals(newpass1.getText().toString())){
                        values.clear();
                        values.put("name",newuser.getText().toString());
                        values.put("pass",newpass1.getText().toString());
                        db.insert("t_user",null,values);
                        Toast.makeText(Lv2RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Lv2RegisterActivity.this, Lv2LoginActivity.class); //切换窗口
                        startActivity(i);
                    }else{
                        Toast.makeText(Lv2RegisterActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void Click2(View view) {
        switch (view.getId()) {
            case R.id.button2exit:
                Toast.makeText(this, "已取消注册", Toast.LENGTH_SHORT).show();//提示
                Intent i = new Intent(Lv2RegisterActivity.this, Lv2LoginActivity.class); //切换窗口
                startActivity(i);
        }
    }
}
