package com.plusend.diycode.model.topic.event;

import com.plusend.diycode.model.topic.entity.Like;

public class UnLikeEvent {
    private Like like;

    public UnLikeEvent(Like like) {
        this.like = like;
    }

    public Like getLike() {
        return like;
    }
}
