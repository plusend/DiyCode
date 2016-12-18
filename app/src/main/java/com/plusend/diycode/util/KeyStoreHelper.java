/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plusend.diycode.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import static java.security.spec.RSAKeyGenParameterSpec.F4;

public class KeyStoreHelper {

  public static final String TAG = "KeyStoreHelper";

  /**
   * Creates a public and private key and stores it using the Android Key
   * Store, so that only this application will be able to access the keys.
   */
  public static void createKeys(Context context, String alias)
      throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
    if (!isSigningKey(alias)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        createKeysM(alias, false);
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        createKeysJBMR2(context, alias);
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  private static void createKeysJBMR2(Context context, String alias)
      throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

    Calendar start = new GregorianCalendar();
    Calendar end = new GregorianCalendar();
    end.add(Calendar.YEAR, 30);

    KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
        // You'll use the alias later to retrieve the key. It's a key
        // for the key!
        .setAlias(alias)
        .setSubject(new X500Principal("CN=" + alias))
        .setSerialNumber(BigInteger.valueOf(Math.abs(alias.hashCode())))
        // Date range of validity for the generated pair.
        .setStartDate(start.getTime())
        .setEndDate(end.getTime())
        .build();

    KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(SecurityConstants.TYPE_RSA,
        SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
    kpGenerator.initialize(spec);
    KeyPair kp = kpGenerator.generateKeyPair();
    Log.d(TAG, "Public Key is: " + kp.getPublic().toString());
  }

  @TargetApi(Build.VERSION_CODES.M)
  private static void createKeysM(String alias, boolean requireAuth)
      throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,
            SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
    keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(alias,
        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setAlgorithmParameterSpec(
        new RSAKeyGenParameterSpec(1024, F4))
        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA384,
            KeyProperties.DIGEST_SHA512)
        // Only permit the private key to be used if the user authenticated
        // within the last five minutes.
        .setUserAuthenticationRequired(requireAuth)
        .build());
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    Log.d(TAG, "Public Key is: " + keyPair.getPublic().toString());
  }

  /**
   * JBMR2+ If Key with the default alias exists, returns true, else false.
   * on pre-JBMR2 returns true always.
   */
  private static boolean isSigningKey(String alias) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      try {
        KeyStore keyStore =
            KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
        keyStore.load(null);
        return keyStore.containsAlias(alias);
      } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
        Log.e(TAG, e.getMessage(), e);
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * Returns the private key signature on JBMR2+ or else null.
   */
  public static String getSigningKey(String alias)
      throws CertificateException, UnrecoverableEntryException, NoSuchAlgorithmException,
      KeyStoreException, IOException {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      KeyStore.PrivateKeyEntry entry = getPrivateKeyEntry(alias);
      if (entry == null) {
        return null;
      }
      Certificate cert = entry.getCertificate();
      if (cert == null) {
        return null;
      }
      byte[] input = cert.getEncoded();
      return Base64.encodeToString(input, Base64.NO_WRAP);
    } else {
      return null;
    }
  }

  private static KeyStore.PrivateKeyEntry getPrivateKeyEntry(String alias)
      throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException,
      UnrecoverableEntryException {
    KeyStore ks = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
    ks.load(null);
    KeyStore.Entry entry = ks.getEntry(alias, null);

    if (entry == null) {
      Log.w(TAG, "No key found under alias: " + alias);
      return null;
    }

    if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
      Log.w(TAG, "Not an instance of a PrivateKeyEntry");
      return null;
    }
    return (KeyStore.PrivateKeyEntry) entry;
  }

  /**
   * 加密
   */
  public static String encrypt(String alias, String plainText)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      BadPaddingException, IllegalBlockSizeException, CertificateException,
      UnrecoverableEntryException, KeyStoreException, IOException {
    if (TextUtils.isEmpty(plainText)) {
      return null;
    }
    KeyStore.PrivateKeyEntry entry = getPrivateKeyEntry(alias);
    if (entry == null) {
      return null;
    }
    PublicKey publicKey = entry.getCertificate().getPublicKey();
    Cipher cipher = getCipher();
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    return Base64.encodeToString(cipher.doFinal(plainText.getBytes()), Base64.NO_WRAP);
  }

  /**
   * 解密
   */
  public static String decrypt(String alias, String cipherText)
      throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
      NoSuchAlgorithmException, NoSuchPaddingException, CertificateException,
      UnrecoverableEntryException, KeyStoreException, IOException {
    if (TextUtils.isEmpty(cipherText)) {
      return null;
    }
    KeyStore.PrivateKeyEntry entry = getPrivateKeyEntry(alias);
    if (entry == null) {
      return null;
    }
    PrivateKey privateKey = entry.getPrivateKey();
    Cipher cipher = getCipher();
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    return new String(cipher.doFinal(Base64.decode(cipherText, Base64.NO_WRAP)));
  }

  private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    return Cipher.getInstance(
        String.format("%s/%s/%s", SecurityConstants.TYPE_RSA, SecurityConstants.BLOCKING_MODE,
            SecurityConstants.PADDING_TYPE));
  }

  interface SecurityConstants {
    String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
    String TYPE_RSA = "RSA";
    String PADDING_TYPE = "PKCS1Padding";
    String BLOCKING_MODE = "NONE";

    String SIGNATURE_SHA256withRSA = "SHA256withRSA";
    String SIGNATURE_SHA512withRSA = "SHA512withRSA";
  }
}