package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.topic.node.entity.Node;
import com.plusend.diycode.mvp.model.topic.node.presenter.NodesPresenter;
import com.plusend.diycode.mvp.model.topic.node.view.NodesView;
import com.plusend.diycode.mvp.model.topic.presenter.NewTopicPresenter;
import com.plusend.diycode.mvp.model.topic.view.NewTopicView;
import com.plusend.diycode.util.ToastUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateTopicActivity extends BaseActivity implements NewTopicView, NodesView {

  @BindView(R.id.title) EditText title;
  @BindView(R.id.content) EditText content;
  @BindView(R.id.section_name) Spinner sectionName;
  @BindView(R.id.node_name) Spinner nodeName;
  @BindView(R.id.toolbar) Toolbar toolbar;

  private List<Node> nodeList;
  private String[] sectionNames;
  private String[] nodeNames;

  private NewTopicPresenter newTopicPresenter;
  private NodesPresenter nodesPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_new_topic);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    nodesPresenter = new NodesPresenter(this);
    newTopicPresenter = new NewTopicPresenter(this);

    nodesPresenter.readNodes();
  }

  private void createTopic() {
    String section = sectionName.getDisplay().getName();
    int id = 45;
    for (Node node : nodeList) {
      if (node.getName().equals(section)) {
        id = node.getId();
      }
    }
    if (TextUtils.isEmpty(title.getText())) {
      ToastUtil.showText(this, "请输入标题");
      return;
    } else if (TextUtils.isEmpty(content.getText())) {
      ToastUtil.showText(this, "请输入发帖内容");
      return;
    }
    newTopicPresenter.newTopic(title.getText().toString(), content.getText().toString(), id);
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    List<Presenter> list = new ArrayList<>();
    list.add(nodesPresenter);
    list.add(newTopicPresenter);
    return list;
  }

  @Override public void getNewTopic(TopicDetail topicDetail) {
    if (topicDetail != null) {
      startActivity(new Intent(CreateTopicActivity.this, TopicActivity.class));
      finish();
    } else {
      ToastUtil.showText(this, "发布失败");
    }
  }

  @Override public void showNodes(final List<Node> nodeList) {
    if (nodeList == null || nodeList.isEmpty()) {
      return;
    }
    this.nodeList = nodeList;
    List<String> temp = getSectionNames(nodeList);
    sectionNames = temp.toArray(new String[temp.size()]);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionNames);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    sectionName.setAdapter(adapter);
    //nodeNames = nodeList.toArray(new String[nodeList.size()]);
    sectionName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String name = sectionNames[i];
        List<String> temp2 = getNodeNames(nodeList, name);
        nodeNames = temp2.toArray(new String[temp2.size()]);
        ArrayAdapter<String> adapter =
            new ArrayAdapter<>(CreateTopicActivity.this, android.R.layout.simple_spinner_item,
                nodeNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        nodeName.setAdapter(adapter);
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  private List<String> getSectionNames(List<Node> nodeList) {
    List<String> parents = new ArrayList<>();
    Set<String> set = new HashSet<>();
    for (Node node : nodeList) {
      String element = node.getSectionName();
      if (set.add(element)) parents.add(element);
    }
    return parents;
  }

  private List<String> getNodeNames(List<Node> nodeList, String sectionName) {
    List<String> nodeNameList = new ArrayList<>();
    for (Node node : nodeList) {
      String element = node.getSectionName();
      if (element.equals(sectionName)) {
        nodeNameList.add(node.getName());
      }
    }
    return nodeNameList;
  }

  @Override public Context getContext() {
    return this;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_send:
        createTopic();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_create_topic, menu);
    return super.onCreateOptionsMenu(menu);
  }
}
