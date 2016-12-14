package com.plusend.diycode.util;


import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * textview 显示内容点击处理
 * <p/>
 * 从中获取到需要处理的内容点击事件
 */
public class LinkMovementMethodExt extends LinkMovementMethod {


  public static int CLICKABLE_SPAN_URL = 1;
  public static int CLICKABLE_SPAN_IMAGE = 2;

  private SpanClickListener mSpanClickListener;

  int x1 = 0;
  int x2 = 0;
  int y1 = 0;
  int y2 = 0;

  public LinkMovementMethodExt(SpanClickListener listener) {
    mSpanClickListener = listener;
  }

  @Override
  public boolean onTouchEvent(TextView widget, Spannable buffer,
      MotionEvent event) {

    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      x1 = (int) event.getX();
      y1 = (int) event.getY();
    }

    if (event.getAction() == MotionEvent.ACTION_UP) {
      x2 = (int) event.getX();
      y2 = (int) event.getY();

      if (Math.abs(x1 - x2) < 10 && Math.abs(y1 - y2) < 10) {

        x2 -= widget.getTotalPaddingLeft();
        y2 -= widget.getTotalPaddingTop();

        x2 += widget.getScrollX();
        y2 += widget.getScrollY();

        Layout layout = widget.getLayout();
        int line = layout.getLineForVertical(y2);
        int off = layout.getOffsetForHorizontal(line, x2);

        ImageSpan[] imageSpans = buffer.getSpans(off, off, ImageSpan.class);
        if (imageSpans.length != 0) {
          Selection.setSelection(buffer,
              buffer.getSpanStart(imageSpans[0]),
              buffer.getSpanEnd(imageSpans[0]));
          if (mSpanClickListener != null) {
            mSpanClickListener.onClick(CLICKABLE_SPAN_IMAGE, imageSpans[0].getSource());
          }
          return true;
        }

        URLSpan[] urlSpans = buffer.getSpans(off, off, URLSpan.class);
        if (urlSpans.length != 0) {
          Selection.setSelection(buffer,
              buffer.getSpanStart(urlSpans[0]),
              buffer.getSpanEnd(urlSpans[0]));
          if (mSpanClickListener != null) {
            mSpanClickListener.onClick(CLICKABLE_SPAN_URL, urlSpans[0].getURL());
          }
          return true;
        }
      }
    }

    //return false;
    return false;


  }


  public boolean canSelectArbitrarily() {
    return true;
  }

  public boolean onKeyUp(TextView widget, Spannable buffer, int keyCode,
      KeyEvent event) {
    return false;
  }
}