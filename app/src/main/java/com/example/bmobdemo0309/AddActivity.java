package com.example.bmobdemo0309;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 日不落 on 2017/3/9.
 */
public class AddActivity extends AppCompatActivity{
    private EditText edtName;
    private EditText edtAge;
    private Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtAge = (EditText) findViewById(R.id.edt_age);
        btnAdd = (Button) findViewById(R.id.btn_add);

        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if(ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,1001);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void add() {
        String path = "/storage/5F99-7626/DCIM/girl.PNG";
        File jpgFile = new File(path);
        if(jpgFile.exists()){
            Toast.makeText(AddActivity.this,"文件地址正确", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(AddActivity.this, "文件地址错误", Toast.LENGTH_LONG).show();
        }

        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(AddActivity.this, "文件上传成功", Toast.LENGTH_LONG).show();
                    Person p1 = new Person();
                    p1.setHeadImg(bmobFile);
                    String name = edtName.getText().toString();
                    p1.setName(name);
                    int age = Integer.parseInt(edtAge.getText().toString());
                    p1.setAge(age);
                    p1.setAddress("广州天河");
                    p1.setScore(89);
                    p1.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                }else {
                    Toast.makeText(AddActivity.this, "文件上传成功", Toast.LENGTH_LONG).show();
                    Log.i("Bmob",e.toString());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                Log.i("Bmob","正在上传"+value);
            }
        });
    }
}
