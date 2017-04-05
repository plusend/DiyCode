package com.plusend.diycode.model.topic.event;

import com.plusend.diycode.model.topic.entity.Like;

public class LikeEvent {
    private Like like;

    public LikeEvent(Like like) {
        this.like = like;
    }

    public void setLike(Like like) {
        this.like = like;
    }
}
