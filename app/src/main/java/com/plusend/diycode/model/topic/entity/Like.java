package com.plusend.diycode.model.topic.entity;

import com.google.gson.annotations.SerializedName;

public class Like {
    @SerializedName("obj_type") private String objType;
    @SerializedName("obj_id") private int objId;
    @SerializedName("count") private int count;

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override public String toString() {
        return "Like{"
            + "objType='"
            + objType
            + '\''
            + ", objId="
            + objId
            + ", count="
            + count
            + '}';
    }
}
