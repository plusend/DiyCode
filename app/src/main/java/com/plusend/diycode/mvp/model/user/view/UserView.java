package com.plusend.diycode.mvp.model.user.view;

import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.base.BaseView;

/**
 * Created by plusend on 2016/11/28.
 */

public interface UserView extends BaseView {
  void getMe(UserDetailInfo userDetailInfo);

  void getUser(UserDetailInfo userDetailInfo);
}
