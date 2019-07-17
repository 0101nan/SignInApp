package com.liyinan.signin.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liyinan.signin.R;
import com.liyinan.signin.db.Departmant;
import com.liyinan.signin.db.Person;
import com.liyinan.signin.util.Utility;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class ChoosePersonActivity extends AppCompatActivity {
    public static final int LEVEL_DEPARTMENT=0;
    public static final int LEVEL_PERSON=1;
    public static final int ALL_PERSON=2;
    public static final int WAIT_PERSON=3;
    public static final int LEAVE_PERSON=4;
    public static final int SET_LEAVE=5;
    private List<Departmant> departmantList=new ArrayList<>();
    private List<Person> personList=new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();
    private int currentLevel;
    private Departmant selectedDepartment;
    private Person selectedPerson;
    private int extra_nav;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_person);
        Intent intent=getIntent();
        extra_nav=intent.getIntExtra("extra_nav",0);
        listView=findViewById(R.id.list_view);
        toolbar=findViewById(R.id.person_toolbar);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_DEPARTMENT){
                    selectedDepartment=departmantList.get(position);
                    queryPerson();
                }else if (currentLevel==LEVEL_PERSON){
                    selectedPerson=personList.get(position);
                    AlertDialog.Builder dialog=new AlertDialog.Builder(ChoosePersonActivity.this);
                    dialog.setTitle(selectedPerson.getName());
                    dialog.setMessage("确认签到？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("签到", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (selectedPerson.isSignIn()==true){
                                Toast.makeText(ChoosePersonActivity.this, "已签到", Toast.LENGTH_SHORT).show();
                            }else{
                                selectedPerson.setSignIn(true);
                                selectedPerson.save();
                                Toast.makeText(ChoosePersonActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ChoosePersonActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }else if (currentLevel==ALL_PERSON){
                    selectedPerson=personList.get(position);
                    AlertDialog.Builder dialog=new AlertDialog.Builder(ChoosePersonActivity.this);
                    dialog.setTitle(selectedPerson.getName());
                    dialog.setMessage("确认请假 ？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (selectedPerson.isLeave()==true){
                                Toast.makeText(ChoosePersonActivity.this, "已请假", Toast.LENGTH_SHORT).show();
                            }else{
                                selectedPerson.setLeave(true);
                                selectedPerson.save();
                                Toast.makeText(ChoosePersonActivity.this, "请假成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ChoosePersonActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
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
        });

        if(extra_nav==ALL_PERSON){
            queryAllPerson();
        }else if(extra_nav==WAIT_PERSON){
            queryWaitPerson();
        }else if(extra_nav==LEAVE_PERSON){
            queryLeavePerson();
        }else if(extra_nav==SET_LEAVE){
            queryAllPerson();
        }else{
            queryDepartment();
        }

    }

    private void queryDepartment(){
        toolbar.setTitle("所有部门");
        departmantList= LitePal.findAll(Departmant.class);
        if(departmantList.size()>0){
            dataList.clear();
            for (Departmant departmant:departmantList){
                dataList.add(departmant.getName());
            }
            toolbar.setSubtitle(dataList.size()+"部门");
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_DEPARTMENT;
        }else{
            Utility.createDepartmentData();
            queryDepartment();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void queryPerson(){
        toolbar.setTitle("部门人员");
        personList=LitePal.where("Department=?",selectedDepartment.getName()).find(Person.class);
        if(personList.size()>0){
            dataList.clear();
            for(Person person:personList){
                if(person.isSignIn()==true){
                    dataList.add(person.getName()+"——已签到");
                }else if(person.isLeave()==true){
                    dataList.add(person.getName()+"——已请假");
                }else{
                    dataList.add(person.getName());
                }

            }
            toolbar.setSubtitle(dataList.size()+"人");
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PERSON;
        }else{
            Utility.createPersonData();
            queryPerson();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_PERSON){
                    queryDepartment();
                }
            }
        });
    }

    private void queryAllPerson(){
        toolbar.setTitle("所有人员");
        personList=LitePal.findAll(Person.class);
        if(personList.size()>0){
            dataList.clear();
            for(Person person:personList){
                if(person.isSignIn()==true){
                    dataList.add(person.getName()+"——已签到");
                }else if(person.isLeave()==true){
                    dataList.add(person.getName()+"——已请假");
                }else{
                    dataList.add(person.getName());
                }

            }
            toolbar.setSubtitle(dataList.size()+"人");
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=ALL_PERSON;
        }else{
            Utility.createPersonData();
            queryAllPerson();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void queryWaitPerson(){
        toolbar.setTitle("暂未签到");
        personList=LitePal.findAll(Person.class);
        if(personList.size()>0){
            dataList.clear();
            for(Person person:personList){
                if(person.isSignIn()==false && person.isLeave()==false){
                    dataList.add(person.getName());
                }
            }
            toolbar.setSubtitle(dataList.size()+"人");
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=WAIT_PERSON;
        }else{
            Utility.createPersonData();
            queryWaitPerson();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void queryLeavePerson(){
        toolbar.setTitle("请假人员");
        personList=LitePal.findAll(Person.class);
        if(personList.size()>0){
            dataList.clear();
            for(Person person:personList){
                if(person.isLeave()==true){
                    dataList.add(person.getName());
                }
            }
            toolbar.setSubtitle(dataList.size()+"人");
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEAVE_PERSON;
        }else{
            Utility.createPersonData();
            queryLeavePerson();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
