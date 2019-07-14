package com.liyinan.signin.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liyinan.signin.R;
import com.liyinan.signin.db.Person;
import com.liyinan.signin.view.AQIView;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private AQIView aqiView;
    private Toolbar toolbar;
    private TextView signedNumText;
    private TextView leaveNumText;
    private List<Person> personList=new ArrayList<>();
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=findViewById(R.id.fab);
        aqiView=findViewById(R.id.aqi_view);
        toolbar=findViewById(R.id.main_tool_bar);
        signedNumText=findViewById(R.id.signed_num);
        leaveNumText=findViewById(R.id.leave_num);
        //toolbar.setTitle("签到");
        button=findViewById(R.id.button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChoosePersonActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personList= LitePal.findAll(Person.class);
                for (Person person:personList) {
                    person.setSignIn(false);
                    person.save();
                }
                setAqiView();
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
}
