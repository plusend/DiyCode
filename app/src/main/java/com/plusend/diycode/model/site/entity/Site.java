package com.plusend.diycode.model.site.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by plusend on 2016/11/27.
 */

public class Site {
    @SerializedName("name") private String name;
    @SerializedName("id") private int id;
    @SerializedName("sites") private List<Sites> sites;

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

    public List<Sites> getSites() {
        return sites;
    }

    public void setSites(List<Sites> sites) {
        this.sites = sites;
    }

    public static class Sites {
        @SerializedName("name") private String name;
        @SerializedName("url") private String url;
        @SerializedName("avatar_url") private String avatarUrl;

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
}
