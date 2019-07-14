package com.liyinan.signin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liyinan.signin.R;
import com.liyinan.signin.db.Departmant;
import com.liyinan.signin.db.Person;
import com.liyinan.signin.util.Utility;
import com.liyinan.signin.view.AQIView;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ALL_PERSON=2;
    public static final int WAIT_PERSON=3;
    public static final int LEAVE_PERSON=4;
    private FloatingActionButton floatingActionButton;
    private AQIView aqiView;
    private Toolbar toolbar;
    private TextView signedNumText;
    private TextView leaveNumText;
    private List<Person> personList=new ArrayList<>();
    private List<Departmant> departmantList=new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private ImageView titleImage;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView dateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=findViewById(R.id.fab);
        aqiView=findViewById(R.id.aqi_view);
        toolbar=findViewById(R.id.main_tool_bar);
        signedNumText=findViewById(R.id.signed_num);
        leaveNumText=findViewById(R.id.leave_num);
        coordinatorLayout=findViewById(R.id.coordinator_layout);
        //toolbar.setTitle("签到");
        titleImage=findViewById(R.id.title_image);
        navigationView=findViewById(R.id.main_nav_view);
        drawerLayout=findViewById(R.id.main_drawer);
        dateTextView=findViewById(R.id.main_date);

        //读数据库，若为空则创建
        personList=LitePal.findAll(Person.class);
        if(personList.size()==0){
            Utility.createPersonData();
        }
        departmantList=LitePal.findAll(Departmant.class);
        if(departmantList.size()==0){
            Utility.createDepartmentData();
        }

        //设置标题时间
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView.setText(month+"月"+day+"日");

        //设置透明状态栏
        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //设置菜单键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //设置侧边栏
        navigationView.setCheckedItem(R.id.nav_basic);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent=new Intent(MainActivity.this,ChoosePersonActivity.class);
                switch (menuItem.getItemId()){
                    case R.id.nav_all:
                        intent.putExtra("extra_nav",ALL_PERSON);
                        startActivity(intent);
                        break;
                    case R.id.nav_basic:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_leave:
                        intent.putExtra("extra_nav",LEAVE_PERSON);
                        startActivity(intent);
                        break;
                    case R.id.nav_wait:
                        intent.putExtra("extra_nav",WAIT_PERSON);
                        startActivity(intent);
                        break;
                    case R.id.nav_clear:
                        drawerLayout.closeDrawers();
                        clearAllData();
                        break;
                }
                return true;
            }
        });

        //设置悬浮按键
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChoosePersonActivity.class);
                startActivity(intent);
            }
        });

        setAqiView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAqiView();
    }

    private void setAqiView(){
        int num=0;
        personList= LitePal.findAll(Person.class);
        for (Person person:personList) {
            if(person.isSignIn()==true){
                num++;
            }
        }
        int progress=100*num/personList.size();
        aqiView.setProgress(progress);
        signedNumText.setText(String.valueOf(num));
        leaveNumText.setText(String.valueOf(0));
    }

    //按两次退出
    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        //第二次按返回键的时间戳
        long secondTime = System.currentTimeMillis();
        //如果第二次的时间戳减去第一次的时间戳大于2000毫秒，则提示再按一次退出，如果小于2000毫秒则直接退出。
        if (secondTime - firstTime > 2000) {
            //弹出是提示消息
            Snackbar sb = Snackbar.make(coordinatorLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
            sb.show();
            firstTime = secondTime;
        } else {
            super.onBackPressed();
        }
    }

    private void clearAllData(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("确认清空数据？");
        dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                personList= LitePal.findAll(Person.class);
                for (Person person:personList) {
                    person.setSignIn(false);
                    person.save();
                }
                setAqiView();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
