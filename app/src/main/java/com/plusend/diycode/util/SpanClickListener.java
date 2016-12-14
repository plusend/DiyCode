package com.plusend.diycode.util;

/**
 * 说明：html 中图片被点击后的回调
 * 用于自定义图片点击事件
 *
 * @author chenyou
 * @date 2016/04/05
 */
public interface SpanClickListener {
  void onClick(int type, String url);
}