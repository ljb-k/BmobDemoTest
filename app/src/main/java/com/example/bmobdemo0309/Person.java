package com.example.bmobdemo0309;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 日不落 on 2017/3/9.
 */

public class Person extends BmobObject{

    private String name;
    private int age;
    private String address;
    private int score;
    private BmobFile headImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BmobFile getHeadImg() {
        return headImg;
    }

    public void setHeadImg(BmobFile headImg) {
        this.headImg = headImg;
    }




}
