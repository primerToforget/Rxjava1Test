package net.zxl.rxjava1test;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/** 
 * autour: Zxl
 * date: 2017/10/16 16:20
 * update: 2017/10/16
 * email: zhuxinglong1992@qq.com
*/
 
public class MainActivity extends AppCompatActivity {

    private String tag="Rxjava1";
    private Button test4;
    Course course1=new Course("语文");
    Course course2=new Course("数学");
    Course course3=new Course("英语");
    Course course4=new Course("物理化");
    List<Course> courseList=new ArrayList<>();
    List<Course> courseList1=new ArrayList<>();
    Student student=new Student("小明",courseList);
    Student student2=new Student("小黄",courseList1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test4=(Button)findViewById(R.id.test4);
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList1.add(course1);
        courseList1.add(course3);
        courseList1.add(course4);
        //注释去掉就可用
        //test1();
        //test2();
        //test3();
        test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test4();
            }
        });
       // test5();
        //test6();
        //test7();
        test8();
    }
    public void test1()
    {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {//创建 Observable
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
//        Observable observable = Observable.just("Hello", "Hi", "Aloha");
//        String[] words = {"Hello", "Hi", "Aloha"};
//        Observable observable = Observable.from(words);
//        observable.subscribe(observer);
        observable.subscribe(subscriber);//Subscribe (订阅)
    }


    public void test2()
    {
        Observable observable = null;
        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(tag, s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(tag, "completed");
            }
        };
        observable = Observable.just("Hello", "Hi", "Aloha");
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }
    public void test3()
    {
        String[] names={"rxjava1","rxandroid1","rxjava2","rxandroid2"};
        Observable.from(names)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(tag,s);
            }
        });
    }
    public void test4()
    {
        final int drawableRes=R.drawable.spider;
        final ImageView imageview=(ImageView) findViewById(R.id.image1);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
            @Override
            public void onNext(Drawable drawable) {
                imageview.setImageDrawable(drawable);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  void test5()
    {
        Student[] students = {student,student2};
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String name) {
                Log.d(tag, name);
            }
        };
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(subscriber);
    }
    private void test6()
    {
        Student[] students = {student,student2};
        Subscriber<Student> subscriber = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCoures();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    Log.d(tag, course.getCourseName());
                }
            }
        };
        Observable.from(students)
                .subscribe(subscriber);
    }
    public void test7()
    {
        Student[] students = {student,student2};
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.d(tag, course.getCourseName());
            }
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCoures());
                    }
                })
                .subscribe(subscriber);
    }
    public void test8() {

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };
        Integer[] n = {1, 2, 3};
        Observable.from(n).lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                // 将事件序列中的 Integer 对象转换为 String 对象
                return new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext("" + integer);
                    }

                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }
                };
            }
        }).subscribe(subscriber);
    }

}
