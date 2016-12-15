package com.plusend.diycode.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;

public class SearchActivity extends AppCompatActivity {
  private static final String TAG = "SearchActivity";
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.result) TextView result;
  SearchView searchView;
  private String fromQuery;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    initActionBar(toolbar);

    handleIntent(getIntent());
  }

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {

    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      Log.d(TAG, "query: " + query);
      fromQuery = query;
      result.setText(query);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_search, menu);
    MenuItem search = menu.findItem(R.id.action_search);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView) search.getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    // 更改输入法中的搜索
    searchView.setImeOptions(3);
    // 展开
    search.expandActionView();
    // 设置初始值
    searchView.setQuery(fromQuery, false);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
