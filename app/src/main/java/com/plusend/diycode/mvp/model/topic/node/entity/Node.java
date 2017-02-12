package com.plusend.diycode.mvp.model.topic.node.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by plusend on 2016/12/5.
 */

public class Node {
  @SerializedName("id") private int id;
  @SerializedName("name") private String name;
  @SerializedName("topics_count") private int topicsCount;
  @SerializedName("summary") private String summary;
  @SerializedName("section_id") private int sectionId;
  @SerializedName("sort") private int sort;
  @SerializedName("section_name") private String sectionName;
  @SerializedName("updated_at") private String updatedAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTopicsCount() {
    return topicsCount;
  }

  public void setTopicsCount(int topicsCount) {
    this.topicsCount = topicsCount;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public int getSectionId() {
    return sectionId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
