package com.example.bmobdemo0309;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

import static com.example.bmobdemo0309.R.id.btn;

public class MainActivity extends AppCompatActivity implements onDelListener{
    private RecyclerView mRecyclerView;
    private List<Person> personList = new ArrayList<>();
    private MyAdapter mAdapter;
    private EditText edtSearch;
    private Button btnSearch;
    private Button btnReg;
    private Button btnLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一：默认初始化
        Bmob.initialize(this,"93ec7140d40447497c857295e2f05595");
        findViews();
        if(BmobUser.getCurrentUser() == null){
            //打开LogActiviy
        }
        //退出当前用户
        BmobUser.logOut();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MyAdapter(personList,MainActivity.this);
        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
        findViewById(btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到添加数据界面
                startActivity(new Intent(MainActivity.this,AddActivity.class));

            }
        });

    }

    private void findViews() {
        edtSearch = (EditText) findViewById(R.id.edt_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnReg = (Button) findViewById(R.id.btn_reg);
        btnLog = (Button) findViewById(R.id.btn_log);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                startActivity(new Intent(MainActivity.this,RegActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age_key = Integer.parseInt(edtSearch.getText().toString());
                String bgl = "select * from Person where age ="+age_key;
                new BmobQuery<Person>().doSQLQuery(bgl, new SQLQueryListener<Person>() {
                    @Override
                    public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
                        if(e == null){
                            List<Person> result = bmobQueryResult.getResults();
                            mAdapter.changeData(result);
                        }else {
                            Log.e("Bmob",e.toString());
                        }
                    }
                });
            }
        });

    }

    private void queryAll(){
        BmobQuery<Person> query = new BmobQuery<>();
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e == null){
                    personList = list;
                    mAdapter.changeData(personList);
                    ((TextView)findViewById(R.id.txt)).setText("数据返回："+personList.size());
                }else {
                    Log.e("queryAll",e.toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryAll();
    }

    @Override
    public void del(String name) {

    }

    @Override
    public void refresh() {
        queryAll();

    }
}
