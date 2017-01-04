package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;

/**
 * Created by plusend on 2016/11/28.
 */

public interface UserView extends BaseView {
  void getMe(UserDetailInfo userDetailInfo);

  void getUser(UserDetailInfo userDetailInfo);
}
