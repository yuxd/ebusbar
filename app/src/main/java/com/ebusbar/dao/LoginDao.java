package com.ebusbar.dao;

import java.io.Serializable;

/**
 * 登录数据
 * Created by Jelly on 2016/3/2.
 */
public class LoginDao implements Serializable{

    /**
     * uid : 1
     * token : token1
     * phone : 18617050557
     * nickName : AL菜菜
     * sex : 男
     * age : 15
     * card : true
     * usericon : http://192.168.0.118:8081/ebusbar/usericon/user01.png
     */

    private int uid;
    private String token;
    private String phone;
    private String nickName;
    private String sex;
    private int age;
    private boolean card;
    private String usericon;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCard(boolean card) {
        this.card = card;
    }



    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public int getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public boolean isCard() {
        return card;
    }

    public String getUsericon() {
        return usericon;
    }
}
