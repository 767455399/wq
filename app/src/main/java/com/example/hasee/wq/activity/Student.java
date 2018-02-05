package com.example.hasee.wq.activity;

/**
 * Created by wangqing on 2017/12/13.
 * 单例模式
 */

public class Student {
    private int age;

    private Student() {
    }

    private  static Student s = new Student();

    public static Student getStudent() {
        return s;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
