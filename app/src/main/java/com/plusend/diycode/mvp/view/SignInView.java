package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.Token;

/**
 * Created by plusend on 2016/11/28.
 */

public interface SignInView extends BaseView {
  void getToken(Token token);
}
