package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.User;

/**
 * Created by plusend on 2016/11/28.
 */

public interface UserView extends BaseView{
  void getMe(User user);
}
