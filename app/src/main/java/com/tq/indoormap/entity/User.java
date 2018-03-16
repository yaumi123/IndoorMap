package com.tq.indoormap.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by niantuo on 2017/4/2.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String guid;
    private String name;
    private String gender;
    private String avatar;
    private String password;

    private String birthday;

    private String phone;


    @Generated(hash = 586692638)
    public User() {
    }
    @Generated(hash = 194644134)
    public User(Long id, String guid, String name, String gender, String avatar,
            String password, String birthday, String phone) {
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.gender = gender;
        this.avatar = avatar;
        this.password = password;
        this.birthday = birthday;
        this.phone = phone;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGuid() {
        return this.guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
