package com.liyinan.signin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liyinan.signin.R;
import com.liyinan.signin.activity.ChoosePersonActivity;
import com.liyinan.signin.activity.MainActivity;
import com.liyinan.signin.db.Departmant;
import com.liyinan.signin.db.Person;
import com.liyinan.signin.util.Utility;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChoosePersonFragment extends Fragment {
    public static final int LEVEL_DEPARTMENT=0;
    public static final int LEVEL_PERSON=1;
    private List<Departmant> departmantList=new ArrayList<>();
    private List<Person> personList=new ArrayList<>();
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();
    private int currentLevel;
    private Departmant selectedDepartment;
    private Person selectedPerson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose_person,container,false);
        titleText=view.findViewById(R.id.title_text);
        backButton=view.findViewById(R.id.back_button);
        listView=view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_DEPARTMENT){
                    selectedDepartment=departmantList.get(position);
                    queryPerson();
                }else if (currentLevel==LEVEL_PERSON){
                    selectedPerson=personList.get(position);
                    AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
                    dialog.setTitle("确认签到？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("签到", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (selectedPerson.isSignIn()==true){
                                Toast.makeText(getContext(), "已签到", Toast.LENGTH_SHORT).show();
                            }else{
                                selectedPerson.setSignIn(true);
                                selectedPerson.save();
                                Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(), MainActivity.class);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_PERSON){
                    queryDepartment();
                }
            }
        });
        queryDepartment();
    }

    private void queryDepartment(){
        titleText.setText("部门");
        backButton.setVisibility(View.GONE);
        departmantList= LitePal.findAll(Departmant.class);
        if(departmantList.size()>0){
            dataList.clear();
            for (Departmant departmant:departmantList){
                dataList.add(departmant.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_DEPARTMENT;
        }else{
            Utility.createDepartmentData();
            queryDepartment();
        }
    }

    private void queryPerson(){
        titleText.setText("人员");
        backButton.setVisibility(View.VISIBLE);
        personList=LitePal.where("Department=?",selectedDepartment.getName()).find(Person.class);
        if(personList.size()>0){
            dataList.clear();
            for(Person person:personList){
                dataList.add(person.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PERSON;
        }else{
            Utility.createPersonData();
            queryPerson();
        }
    }
}
