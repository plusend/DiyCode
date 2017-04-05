package com.plusend.diycode.model.user.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.user.entity.Token;

/**
 * Created by plusend on 2016/11/28.
 */

public interface SignInView extends BaseView {
    void getToken(Token token);
}
