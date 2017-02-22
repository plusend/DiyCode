package com.plusend.diycode.model.user.event;

import com.plusend.diycode.model.user.entity.Token;

public class RefreshTokenEvent {
  private Token token;

  public RefreshTokenEvent(Token token) {
    this.token = token;
  }

  public Token getToken() {
    return token;
  }
}
