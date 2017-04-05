package com.plusend.diycode.view.adapter.site;

/**
 * Created by plusend on 2016/12/13.
 */
public class SitesName {
    private String name;
    private String url;
    private String avatarUrl;

    public SitesName(String name, String url, String avatarUrl) {
        this.name = name;
        this.url = url;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}