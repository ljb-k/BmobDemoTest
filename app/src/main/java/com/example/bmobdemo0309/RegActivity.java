package com.example.bmobdemo0309;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 日不落 on 2017/3/9.
 */
public class RegActivity extends AppCompatActivity{
    private EditText edtName;
    private EditText edtPass;
    private EditText edtPhone;
    private EditText edtCode;
    private Button btnGetCode;
    private Button btnReg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtPass = (EditText) findViewById(R.id.edt_pass);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtCode = (EditText) findViewById(R.id.edt_code);

        btnGetCode = (Button) findViewById(R.id.btn_getcode);
        btnReg = (Button) findViewById(R.id.btn_reg);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                BmobSMS.requestSMSCode(phone, "bmobtest:", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if(e == null){
                            Log.i("smile", "短信id："+integer);
                        }
                    }
                });
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtCode.getText().toString();
                final String phone = edtPhone.getText().toString();
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Log.i("smile", "验证成功");

                            String name = edtName.getText().toString();
                            String pass = edtPass.getText().toString();

                            BmobUser user = new BmobUser();
                            user.setUsername(name);
                            user.setPassword(pass);
                            user.setMobilePhoneNumber(phone);
                            user.setMobilePhoneNumberVerified(true);
                            user.signUp(new SaveListener<BmobUser>() {

                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if(e == null){
                                        Log.i("smile", "注册成功");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(RegActivity.this,"注册成功", Toast.LENGTH_LONG).show();


                                            }
                                        });
                                    }else {
                                        Log.i("smile", "注册失败");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(RegActivity.this,"注册失败", Toast.LENGTH_LONG).show();


                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            Log.i("smile", "验证失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                        }
                    }
                });
            }
        });


    }
}
