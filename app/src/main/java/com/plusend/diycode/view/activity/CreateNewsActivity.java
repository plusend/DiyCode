package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.news.entity.News;
import com.plusend.diycode.mvp.model.news.node.entity.NewsNode;
import com.plusend.diycode.mvp.model.news.node.presenter.NewsNodesPresenter;
import com.plusend.diycode.mvp.model.news.node.view.NewsNodesView;
import com.plusend.diycode.mvp.model.news.presenter.CreateNewsPresenter;
import com.plusend.diycode.mvp.model.news.view.CreateNewsView;
import com.plusend.diycode.mvp.model.topic.node.entity.Node;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.util.UrlUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.text.TextUtils.split;

public class CreateNewsActivity extends BaseActivity implements CreateNewsView, NewsNodesView {

  private static final String TAG = "CreateNewsActivity";

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.section_name) Spinner sectionName;
  @BindView(R.id.title) EditText title;
  @BindView(R.id.link) EditText link;
  @BindView(R.id.fab) FloatingActionButton fab;

  private Presenter mCreateNewsPresenter;
  private Presenter mNewsNodesPresenter;
  private String[] mSectionNames;
  private List<NewsNode> mNewsNodeList;

  @Override protected void onCreate(Bundle savedInstanceState) {

    setContentView(R.layout.activity_create_news);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    if (Intent.ACTION_SEND.equals(intent.getAction())) {
      String linkText = intent.getStringExtra(Intent.EXTRA_TEXT);
      String titleText = intent.getStringExtra(Intent.EXTRA_SUBJECT);
      Log.d(TAG, "getIntent: " + linkText + " " + titleText);

      if (!TextUtils.isEmpty(linkText) && TextUtils.isEmpty(titleText)) {
        titleText = linkText;
        linkText = UrlUtil.getUrl(linkText);
        if (TextUtils.isEmpty(linkText)) {
          linkText = titleText;
        } else {
          titleText = titleText.replace(linkText, "");
        }
      }

      link.setText(linkText);
      title.setText(titleText);
    }

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        createNews();
      }
    });

    mCreateNewsPresenter = new CreateNewsPresenter(this);
    mNewsNodesPresenter = new NewsNodesPresenter(this);

    ((NewsNodesPresenter) mNewsNodesPresenter).readNodes();
  }

  private void createNews() {
    if (isTextEmpty()) {
      return;
    }
    String section = sectionName.getDisplay().getName();
    int id = 11;
    for (NewsNode node : mNewsNodeList) {
      if (node.getName().equals(section)) {
        id = node.getId();
      }
    }
    ((CreateNewsPresenter) mCreateNewsPresenter).createNews(title.getText().toString(),
        link.getText().toString(), id);
  }

  private boolean isTextEmpty() {
    boolean result = false;
    if (TextUtils.isEmpty(title.getText().toString())) {
      ToastUtil.showText(this, "标题不能为空");
      result = true;
    } else if (TextUtils.isEmpty(link.getText().toString())) {
      ToastUtil.showText(this, "链接不能为空");
      result = true;
    } else if (TextUtils.isEmpty(sectionName.getDisplay().getName())) {
      ToastUtil.showText(this, "分类不能为空");
      result = true;
    }
    return result;
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    List<Presenter> list = new ArrayList<>();
    list.add(mCreateNewsPresenter);
    list.add(mNewsNodesPresenter);
    return list;
  }

  @Override public void showNews(News news) {
    if (news != null) {
      ToastUtil.showText(this, "分享成功");
      Intent intent = new Intent(this, WebActivity.class);
      intent.putExtra(WebActivity.URL, news.getAddress());
      startActivity(intent);
      finish();
    } else {
      ToastUtil.showText(this, "分享失败");
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override public void showNodes(final List<NewsNode> newsNodeList) {
    if (newsNodeList == null || newsNodeList.isEmpty()) {
      return;
    }
    List<String> temp = getSectionNames(newsNodeList);
    mSectionNames = temp.toArray(new String[temp.size()]);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mSectionNames);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    sectionName.setAdapter(adapter);
  }

  private List<String> getSectionNames(List<NewsNode> newsNodeList) {
    this.mNewsNodeList = newsNodeList;
    List<String> parents = new ArrayList<>();
    Set<String> set = new HashSet<>();
    for (NewsNode newsNode : newsNodeList) {
      String element = newsNode.getName();
      if (set.add(element)) parents.add(element);
    }
    return parents;
  }
}
