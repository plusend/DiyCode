package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.UserBlock;

public class UserBlockEvent {
    private UserBlock userBlock;

    public UserBlockEvent(UserBlock userBlock) {
        this.userBlock = userBlock;
    }

    public UserBlock getUserBlock() {
        return userBlock;
    }
}
