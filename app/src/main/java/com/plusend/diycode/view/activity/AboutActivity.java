package com.plusend.diycode.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.plusend.diycode.BuildConfig;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.BasePresenter;
import com.plusend.diycode.view.adapter.about.Card;
import com.plusend.diycode.view.adapter.about.CardViewProvider;
import com.plusend.diycode.view.adapter.about.Category;
import com.plusend.diycode.view.adapter.about.CategoryViewProvider;
import com.plusend.diycode.view.adapter.about.Contributor;
import com.plusend.diycode.view.adapter.about.ContributorViewProvider;
import com.plusend.diycode.view.adapter.about.License;
import com.plusend.diycode.view.adapter.about.LicenseViewProvider;
import com.plusend.diycode.view.adapter.about.Line;
import com.plusend.diycode.view.adapter.about.LineViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author drakeet
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

  protected Toolbar toolbar;
  protected CollapsingToolbarLayout collapsingToolbar;

  protected Items items;
  protected MultiTypeAdapter adapter;
  protected TextView slogan, version;

  protected void onCreateHeader(ImageView icon, TextView slogan, TextView version) {
    setHeaderContentColor(Color.parseColor("#000000"));
    setNavigationIcon(R.mipmap.sign_close);
    icon.setImageResource(R.mipmap.ic_launcher);
    slogan.setText("A third-party Android client of Diycode.");
    version.setText("version " + BuildConfig.VERSION_NAME);
  }

  protected void onItemsCreated(@NonNull Items items) {
    items.add(new Category("介绍"));
    items.add(new Card(getString(R.string.card_content), "Diycode"));

    items.add(new Line());

    items.add(new Category("Developers"));
    items.add(new Contributor(R.mipmap.avatar_plusend, "plusend", "Android Developer",
        "https://github.com/plusend"));

    items.add(new Line());

    items.add(new Category("Open Source Licenses"));
    items.add(new License("MultiType", "drakeet", License.APACHE_2,
        "https://github.com/drakeet/MultiType"));
    items.add(new License("about-page", "drakeet", License.APACHE_2,
        "https://github.com/drakeet/about-page"));
    items.add(new License("butterknife", "JakeWharton", License.APACHE_2,
        "https://github.com/JakeWharton/butterknife"));
    items.add(new License("gson", "google", License.APACHE_2, "https://github.com/google/gson"));
    items.add(
        new License("glide", "bumptech", License.APACHE_2, "https://github.com/bumptech/glide"));
    items.add(
        new License("retrofit", "square", License.APACHE_2, "https://github.com/square/retrofit"));
    items.add(new License("eventbus", "greenrobot", License.APACHE_2,
        "https://github.com/greenrobot/EventBus"));
    items.add(new License("PhotoView", "chrisbanes", License.APACHE_2,
        "https://github.com/chrisbanes/PhotoView"));
    items.add(new License("BGASwipeBackLayout-Android", "bingoogolapple", License.APACHE_2,
        "https://github.com/bingoogolapple/BGASwipeBackLayout-Android"));
    items.add(new License("StatusBarUtil", "laobie", License.APACHE_2,
        "https://github.com/laobie/StatusBarUtil"));
  }

  @Nullable protected CharSequence onCreateTitle() {
    return null;
  }

  protected void onActionClick(View action) {
  }

  @SuppressWarnings("ConstantConditions") @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about_page_main_activity);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    ImageView icon = (ImageView) findViewById(R.id.icon);
    slogan = (TextView) findViewById(R.id.slogan);
    version = (TextView) findViewById(R.id.version);
    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    onCreateHeader(icon, slogan, version);

    final CharSequence title = onCreateTitle();
    if (title != null) {
      collapsingToolbar.setTitle(title);
    }

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
    onSetupRecyclerView(recyclerView);
  }

  @Override protected Toolbar getToolbar() {
    return null;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return null;
  }

  private void onSetupRecyclerView(RecyclerView recyclerView) {
    items = new Items();
    onItemsCreated(items);
    adapter = new MultiTypeAdapter(items);
    adapter.register(Category.class, new CategoryViewProvider());
    adapter.register(Card.class, new CardViewProvider(this));
    adapter.register(Line.class, new LineViewProvider());
    adapter.register(Contributor.class, new ContributorViewProvider());
    adapter.register(License.class, new LicenseViewProvider());
    recyclerView.setAdapter(adapter);
  }

  /**
   * Set the header view background to a given resource and replace the default value
   * ?attr/colorPrimary.
   * The resource should refer to a Drawable object or 0 to remove the background.
   *
   * @param resId The identifier of the resource.
   */
  public void setHeaderBackgroundResource(@DrawableRes int resId) {
    if (collapsingToolbar == null) {
      collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }
    collapsingToolbar.setContentScrimResource(resId);
    collapsingToolbar.setBackgroundResource(resId);
  }

  public void setHeaderContentColor(@ColorInt int color) {
    collapsingToolbar.setCollapsedTitleTextColor(color);
    slogan.setTextColor(color);
    version.setTextColor(color);
  }

  /**
   * Set the icon to use for the toolbar's navigation button.
   *
   * @param resId Resource ID of a drawable to set
   */
  public void setNavigationIcon(@DrawableRes int resId) {
    toolbar.setNavigationIcon(resId);
  }

  @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
    if (menuItem.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(menuItem);
  }

  @Override public void setTitle(CharSequence title) {
    collapsingToolbar.setTitle(title);
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.action) {
      onActionClick(v);
    }
  }
}
