package net.zxl.rxjava1test;

import android.content.Context;

import java.util.List;

/**
 * autour: Zxl
 * date: 2017/10/16 16:20
 * update: 2017/10/16
 * email: zhuxinglong1992@qq.com
*/

public class Student {

    public Student(String name,List<Course> course)
    {
        this.coures=course;
        this.name=name;
    }

    private String name;

    public List<Course> getCoures() {
        return coures;
    }

    public void setCoures(List<Course> coures) {
        this.coures = coures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Course> coures;
}
