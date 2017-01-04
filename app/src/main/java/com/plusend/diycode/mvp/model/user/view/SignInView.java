package com.plusend.diycode.mvp.model.user.view;

import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.base.BaseView;

/**
 * Created by plusend on 2016/11/28.
 */

public interface SignInView extends BaseView {
  void getToken(Token token);
}
