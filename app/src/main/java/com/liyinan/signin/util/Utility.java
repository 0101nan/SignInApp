package com.liyinan.signin.util;

import com.liyinan.signin.db.Departmant;
import com.liyinan.signin.db.Person;

public class Utility {
    public static void createPersonData(){
        Person person1=new Person();
        person1.setName("李政");
        person1.setDepartment("通用组件技术组");
        person1.save();

        Person person2=new Person();
        person2.setName("朱泽琦");
        person2.setDepartment("通用组件技术组");
        person2.save();

        Person person3=new Person();
        person3.setName("曹春明");
        person3.setDepartment("运维产品组");
        person3.save();

        Person person4=new Person();
        person4.setName("查晓晶");
        person4.setDepartment("运维产品组");
        person4.save();

        Person person5=new Person();
        person5.setName("洪勇豪");
        person5.setDepartment("运维产品组");
        person5.save();

        Person person6=new Person();
        person6.setName("胡文涛");
        person6.setDepartment("运维产品组");
        person6.save();

        Person person7=new Person();
        person7.setName("陈钇东");
        person7.setDepartment("中间件");
        person7.save();

        Person person8=new Person();
        person8.setName("唐思源");
        person8.setDepartment("中间件");
        person8.save();

        Person person9=new Person();
        person9.setName("钱梦龙");
        person9.setDepartment("电信云产品组");
        person9.save();

        Person person10=new Person();
        person10.setName("王粤衡");
        person10.setDepartment("电信云产品组");
        person10.save();

        Person person11=new Person();
        person11.setName("吴磊");
        person11.setDepartment("电信云产品组");
        person11.save();

        Person person12=new Person();
        person12.setName("吴义凯");
        person12.setDepartment("电信云产品组");
        person12.save();

        Person person13=new Person();
        person13.setName("刘鑫");
        person13.setDepartment("网络与安全产品组");
        person13.save();

        Person person14=new Person();
        person14.setName("陆志成");
        person14.setDepartment("网络与安全产品组");
        person14.save();

        Person person15=new Person();
        person15.setName("孙旭东");
        person15.setDepartment("网络与安全产品组");
        person15.save();

        Person person16=new Person();
        person16.setName("章昕怡");
        person16.setDepartment("网络与安全产品组");
        person16.save();

        Person person17=new Person();
        person17.setName("程昊熠");
        person17.setDepartment("私有云产品组");
        person17.save();

        Person person18=new Person();
        person18.setName("李易南");
        person18.setDepartment("私有云产品组");
        person18.save();

        Person person19=new Person();
        person19.setName("邱程");
        person19.setDepartment("私有云产品组");
        person19.save();

        Person person20=new Person();
        person20.setName("王文强");
        person20.setDepartment("私有云产品组");
        person20.save();

        Person person21=new Person();
        person21.setName("钟英娇");
        person21.setDepartment("私有云产品组");
        person21.save();

        Person person22=new Person();
        person22.setName("唐菁荟");
        person22.setDepartment("Paas");
        person22.save();

        Person person23=new Person();
        person23.setName("李婉梅");
        person23.setDepartment("操作系统");
        person23.save();

        Person person24=new Person();
        person24.setName("罗子敬");
        person24.setDepartment("公有云产品组");
        person24.save();

        Person person25=new Person();
        person25.setName("汪洋");
        person25.setDepartment("公有云产品组");
        person25.save();

        Person person26=new Person();
        person26.setName("王浩");
        person26.setDepartment("公有云产品组");
        person26.save();

        Person person27=new Person();
        person27.setName("王磊");
        person27.setDepartment("公有云产品组");
        person27.save();

        Person person28=new Person();
        person28.setName("肖心园");
        person28.setDepartment("公有云产品组");
        person28.save();
    }

    public static void createDepartmentData(){
        Departmant departmant1=new Departmant();
        departmant1.setName("通用组件技术组");
        departmant1.save();

        Departmant departmant2=new Departmant();
        departmant2.setName("运维产品组");
        departmant2.save();

        Departmant departmant3=new Departmant();
        departmant3.setName("中间件");
        departmant3.save();

        Departmant departmant4=new Departmant();
        departmant4.setName("电信云产品组");
        departmant4.save();

        Departmant departmant5=new Departmant();
        departmant5.setName("网络与安全产品组");
        departmant5.save();

        Departmant departmant6=new Departmant();
        departmant6.setName("私有云产品组");
        departmant6.save();

        Departmant departmant7=new Departmant();
        departmant7.setName("Paas");
        departmant7.save();

        Departmant departmant8=new Departmant();
        departmant8.setName("操作系统");
        departmant8.save();

        Departmant departmant9=new Departmant();
        departmant9.setName("公有云产品组");
        departmant9.save();
    }
}
