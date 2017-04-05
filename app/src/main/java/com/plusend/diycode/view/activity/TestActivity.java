package com.plusend.diycode.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.entity.Topic;
import com.plusend.diycode.util.KeyStoreHelper;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";
    private static final String KEYSTORE_KEY_ALIAS = "DiyCode";
    @BindView(R.id.content) EditText content;
    @BindView(R.id.encrypt) Button encrypt;
    @BindView(R.id.decrypt) Button decrypt;
    @BindView(R.id.encrypted) TextView encrypted;
    @BindView(R.id.decrypted) TextView decrypted;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.jsoup_get) Button jsoupGet;
    @BindView(R.id.jsoup_result) TextView jsoupResult;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        try {
            KeyStoreHelper.createKeys(TestActivity.this, KEYSTORE_KEY_ALIAS);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override protected List<BasePresenter> getPresenter() {
        return null;
    }

    @OnClick(R.id.encrypt) public void encrypt() {
        String stringToEncrypt = content.getText().toString();
        if (TextUtils.isEmpty(stringToEncrypt)) {
            return;
        }
        String resultString = null;
        try {
            resultString = KeyStoreHelper.encrypt(KEYSTORE_KEY_ALIAS, stringToEncrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | CertificateException | UnrecoverableEntryException | KeyStoreException | IOException | InvalidKeyException e) {
            e.printStackTrace();
        }

        encrypted.setText(resultString);
    }

    @OnClick(R.id.decrypt) public void decrypt() {
        String stringToDecrypt = encrypted.getText().toString();
        if (TextUtils.isEmpty(stringToDecrypt)) {
            return;
        }
        String resultString = null;
        try {
            resultString = KeyStoreHelper.decrypt(KEYSTORE_KEY_ALIAS, stringToDecrypt);
        } catch (InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | CertificateException | UnrecoverableEntryException | KeyStoreException | IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        decrypted.setText(resultString);
    }

    private static class MyHandler extends Handler {
        private WeakReference<TestActivity> activityWeakReference;

        public MyHandler(TestActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override public void handleMessage(Message msg) {
            TestActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == 1) {
                    Topic topic = (Topic) msg.obj;
                    activity.jsoupResult.append("======\n");
                    activity.jsoupResult.append(topic.getId() + "\n");
                    activity.jsoupResult.append(topic.getTitle() + "\n");
                    activity.jsoupResult.append(topic.getUser().getAvatarUrl() + "\n");
                    activity.jsoupResult.append(topic.getUser().getLogin() + "\n");
                    activity.jsoupResult.append(topic.getNodeName() + "\n");
                    activity.jsoupResult.append(topic.getRepliedAt() + "\n");
                }
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    @OnClick(R.id.jsoup_get) public void jsoupGet() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.diycode.cc/").get();
                    Log.d(TAG, "Document: " + doc.title());
                    Log.d(TAG, "Document: " + doc.head());
                    Log.d(TAG, "Document: " + doc.body());
                    int size = doc.getElementsByClass("fa fa-thumb-tack").size();
                    Log.d(TAG, "top size: " + size);
                    Elements elements = doc.getElementsByClass("panel-body");
                    Elements topics = elements.get(0).children();
                    Log.d(TAG, "size: " + topics.size());
                    for (int i = 0; i < size; i++) {
                        Element topic = topics.get(i);
                        Log.d(TAG, "src: " + topic.getElementsByTag("img").get(0).attr("src"));
                        Log.d(TAG, "node: " + topic.getElementsByClass("node").get(0).text());
                        Log.d(TAG,
                            "author: " + topic.getElementsByClass("hacknews_clear").get(1).text());
                        String time = topic.getElementsByClass("timeago").get(0).attr("title");
                        StringBuilder sb = new StringBuilder(time);
                        sb.insert(19, ".000");
                        time = sb.toString();
                        Log.d(TAG, "time: " + time);
                        Log.d(TAG, "title: " + topic.getElementsByClass("title media-heading")
                            .get(0)
                            .text());
                        String href = topic.getElementsByClass("title media-heading")
                            .get(0)
                            .getElementsByTag("a")
                            .attr("href");
                        Log.d(TAG, "topicId: " + href.substring(href.lastIndexOf("/") + 1));
                        Topic temp = new Topic();
                        temp.setId(Integer.valueOf(href.substring(href.lastIndexOf("/") + 1)));
                        Log.d(TAG, "id: " + temp.getId());
                        temp.setTitle(
                            topic.getElementsByClass("title media-heading").get(0).text());
                        temp.setNodeName(topic.getElementsByClass("node").get(0).text());
                        temp.setRepliedAt(time);
                        Topic.User user = new Topic.User();
                        user.setAvatarUrl(topic.getElementsByTag("img").get(0).attr("src"));
                        user.setLogin(topic.getElementsByClass("hacknews_clear").get(1).text());
                        temp.setUser(user);
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = temp;
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
