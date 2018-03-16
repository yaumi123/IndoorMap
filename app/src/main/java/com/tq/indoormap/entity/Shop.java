package com.tq.indoormap.entity;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by niantuo on 2017/4/2.
 */

@Entity
public class Shop implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String guid;
    private String name;
    private String type;
    private String introduction;
    private int level;
    private Long typeId;
    private String icon;

    private float pointX;
    private float pointY;

    private String remark;
    private String node;


    public Shop() {
        super();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }


    private Shop(Parcel in) {
        guid = in.readString();
        name = in.readString();
        introduction = in.readString();
        level = in.readInt();
        icon = in.readString();
        type = in.readString();
        pointX = in.readFloat();
        pointY = in.readFloat();

        node = in.readString();
        remark = in.readString();
    }

    @Generated(hash = 740435951)
    public Shop(Long id, String guid, String name, String type, String introduction,
                int level, Long typeId, String icon, float pointX, float pointY,
                String remark, String node) {
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.type = type;
        this.introduction = introduction;
        this.level = level;
        this.typeId = typeId;
        this.icon = icon;
        this.pointX = pointX;
        this.pointY = pointY;
        this.remark = remark;
        this.node = node;
    }


    public PointF getPoint() {
        return new PointF(pointX, pointY);
    }

    public void setPoint(PointF point) {
        this.pointX = point.x;
        this.pointY = point.y;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(guid);
        dest.writeString(name);
        dest.writeString(introduction);
        dest.writeInt(level);
        dest.writeString(icon);
        dest.writeString(type);
        dest.writeFloat(pointX);
        dest.writeFloat(pointY);

        dest.writeString(node);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    public float getPointX() {
        return this.pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return this.pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }


    public static Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
