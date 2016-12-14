package com.plusend.diycode.view.adapter.site;

/**
 * Created by plusend on 2016/12/13.
 */
public class SiteName {
  private String name;
  private int id;

  public SiteName(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}