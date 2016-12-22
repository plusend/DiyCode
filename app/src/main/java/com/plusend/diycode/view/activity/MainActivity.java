package com.plusend.diycode.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.entity.User;
import com.plusend.diycode.mvp.presenter.UserPresenter;
import com.plusend.diycode.mvp.view.UserView;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.view.adapter.MainPagerAdapter;
import com.plusend.diycode.view.fragment.TopicFragment;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, UserView {
  private static final String TAG = "MainActivity";

  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;
  private MenuItem search;
  ImageView avatar;
  TextView email;
  private User me;

  private UserPresenter userPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setLogo(R.mipmap.logo_actionbar);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, NewTopicActivity.class));
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    PagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);

    View header = navigationView.getHeaderView(0);
    email = (TextView) header.findViewById(R.id.email);
    avatar = (ImageView) header.findViewById(R.id.avatar);
    avatar.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (!TextUtils.isEmpty(Constant.VALUE_TOKEN)) {
          Intent intent = new Intent(MainActivity.this, UserActivity.class);
          intent.putExtra(UserActivity.LOGIN_NAME, PrefUtil.getMe(MainActivity.this).getLogin());
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, SignInActivity.class);
          startActivityForResult(intent, SignInActivity.REQUEST_CODE);
        }
      }
    });

    Token token = PrefUtil.getToken(this);
    if (!TextUtils.isEmpty(token.getAccessToken())) {
      Constant.VALUE_TOKEN = token.getAccessToken();
      me = PrefUtil.getMe(this);
      if (!TextUtils.isEmpty(me.getLogin())
          && !TextUtils.isEmpty(me.getAvatarUrl())
          && !TextUtils.isEmpty(me.getEmail())) {
        showMe(me);
      } else {
        Log.d(TAG, "user is null");
        userPresenter = new UserPresenter(this);
        userPresenter.getMe();
      }
    } else {
      Log.d(TAG, "token is null");
      userPresenter = new UserPresenter(this);
    }
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    search = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) search.getActionView();
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setImeOptions(3);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        break;
      case R.id.action_search:
        break;
      case R.id.action_notification:
        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_post) {
      Intent intent = new Intent(MainActivity.this, MyTopicsActivity.class);
      intent.putExtra(MyTopicsActivity.TITLE, "我的帖子");
      intent.putExtra(MyTopicsActivity.TYPE, TopicFragment.TYPE_CREATE);
      startActivity(intent);
    } else if (id == R.id.nav_collect) {
      Intent intent = new Intent(MainActivity.this, MyTopicsActivity.class);
      intent.putExtra(MyTopicsActivity.TITLE, "我的收藏");
      intent.putExtra(MyTopicsActivity.TYPE, TopicFragment.TYPE_FAVORITE);
      startActivity(intent);
    } else if (id == R.id.nav_comment) {
      Intent intent = new Intent(MainActivity.this, MyRepliesActivity.class);
      intent.putExtra(MyRepliesActivity.LOGIN_NAME, me.getLogin());
      startActivity(intent);
    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_about) {
    } else if (id == R.id.nav_setting) {
      startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void getMe(User user) {
    Log.d(TAG, "getMe: " + user);
    if (user == null) {
      return;
    }
    PrefUtil.saveMe(this, user);
    me = user;
    showMe(user);
  }

  @Override public void getUser(User user) {

  }

  private void showMe(User user) {
    email.setText(user.getEmail());
    Glide.with(this)
        .load(user.getAvatarUrl())
        .crossFade()
        .bitmapTransform(new CropCircleTransformation(this))
        .into(avatar);
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    if (userPresenter != null) {
      userPresenter.start();
    }
    // 从别的页面回来的时候，不保留之前的搜索状态
    if (search != null) {
      search.collapseActionView();
    }
  }

  @Override protected void onStop() {
    if (userPresenter != null) {
      userPresenter.stop();
    }
    super.onStop();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        Log.d(TAG, "resultCode: " + resultCode);
        if (resultCode == SignInActivity.RESULT_OK) {
          userPresenter.getMe();
        }
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
