package com.plusend.diycode.model.topic.event;

/**
 * Created by plusend on 2016/12/1.
 */

public class CreateTopicReplyEvent {
    private boolean isSuccessful;

    public CreateTopicReplyEvent(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
