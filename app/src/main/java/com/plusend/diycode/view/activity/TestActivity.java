package com.plusend.diycode.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.util.KeyStoreHelper;
import java.io.IOException;
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

public class TestActivity extends BaseActivity {

  private static final String KEYSTORE_KEY_ALIAS = "DiyCode";
  @BindView(R.id.content) EditText content;
  @BindView(R.id.encrypt) Button encrypt;
  @BindView(R.id.decrypt) Button decrypt;
  @BindView(R.id.encrypted) TextView encrypted;
  @BindView(R.id.decrypted) TextView decrypted;
  @BindView(R.id.toolbar) Toolbar toolbar;

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

  @Override protected List<Presenter> getPresenter() {
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
}
