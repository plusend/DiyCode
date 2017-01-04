package com.plusend.diycode.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.topic.view.CreateTopicReplyView;
import com.plusend.diycode.util.Constant;

/**
 * Created by plusend on 2016/12/1.
 */

public class CreateTopicReplyFragment extends Fragment implements CreateTopicReplyView{
  private static final String TAG = "CreateTopicReplyFragmen";
  @BindView(R.id.title) TextView title;
  @BindView(R.id.body) EditText body;

  public static CreateTopicReplyFragment newInstance(int topicId) {
    CreateTopicReplyFragment createTopicReplyFragment = new CreateTopicReplyFragment();
    Bundle b = new Bundle();
    b.putInt(Constant.TOPIC_ID, topicId);
    createTopicReplyFragment.setArguments(b);
    return createTopicReplyFragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View rootView = inflater.inflate(R.layout.activity_create_topic_reply, container, false);
    ButterKnife.bind(this, rootView);

    return rootView;
  }

  @Override public void getResult(boolean isSuccessful) {
    if(isSuccessful){

    }
  }
}
