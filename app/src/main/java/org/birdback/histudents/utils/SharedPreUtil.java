package org.birdback.histudents.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


import static java.lang.String.valueOf;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 */
public class SharedPreUtil {

    private static SharedPreferences mSharePre = null;
    private static SharedPreferences.Editor mEditor = null;
    private static final String DEFAULT_SEED = "123456";

    public static synchronized void initialize(Context context) {
        if (mSharePre == null) {
            mSharePre = context.getSharedPreferences(BaseConfig.SP_DEFAULT, Context.MODE_PRIVATE);
            mEditor = mSharePre.edit();
        }
    }


    public static boolean putValue(String k, String v) {
        mEditor.putString(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, int v) {
        mEditor.putInt(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, boolean v) {
        mEditor.putBoolean(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, float v) {
        mEditor.putFloat(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, long v) {
        mEditor.putLong(k, v);
        return mEditor.commit();
    }

    public static String getValue(String key, String def) {
        return mSharePre.getString(key, def);
    }

    public static Integer getValue(String key, Integer def) {
        return mSharePre.getInt(key, def);
    }

    public static Boolean getValue(String key, Boolean def) {
        return mSharePre.getBoolean(key, def);
    }

    public static Float getValue(String key, Float def) {
        return mSharePre.getFloat(key, def);
    }

    public static Long getValue(String key, Long def) {
        return mSharePre.getLong(key, def);
    }
    //====================

    public static boolean putEncryptValue(String k, String v) {
        return putEncrypt(k, v, DEFAULT_SEED);
    }

    public static boolean putEncryptValue(String k, int v) {
        return putEncrypt(k, valueOf(v), DEFAULT_SEED);
    }

    public static boolean putEncryptValue(String k, boolean v) {
        return putEncrypt(k, valueOf(v), DEFAULT_SEED);
    }

    public static boolean putEncryptValue(String k, float v) {
        return putEncrypt(k, valueOf(v), DEFAULT_SEED);
    }

    public static boolean putEncryptValue(String k, long v) {
        return putEncrypt(k, valueOf(v), DEFAULT_SEED);
    }


    public static boolean putEncryptValue(String k, int v, String seed) {
        return putEncrypt(k, valueOf(v), seed);
    }

    public static boolean putEncryptValue(String k, boolean v, String seed) {
        return putEncrypt(k, valueOf(v), seed);
    }

    public static boolean putEncryptValue(String k, float v, String seed) {
        return putEncrypt(k, valueOf(v), seed);
    }

    public static boolean putEncryptValue(String k, long v, String seed) {
        return putEncrypt(k, valueOf(v), seed);
    }

    public static boolean putStringSet(String k, Set<String> v) {
        mEditor.putStringSet(k, v);
        return mEditor.commit();
    }

    public static boolean putEncrypt(String k, String v, String seed) {
        if (VerifyUtil.isEmpty(seed)) {
            seed = DEFAULT_SEED;
        }
        v = encrypt(v, seed);
        if (VerifyUtil.isEmpty(v)) {
            return false;
        }
        mEditor.putString(k, v);
        return mEditor.commit();
    }


    public static String getDecryptValue(String k, String def) {
        if (def == null) {
            def = "";
        }
        return getDecrypt(k, def, DEFAULT_SEED);
    }

    public static Integer getDecryptValue(String k, int def) {
        return Integer.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Boolean getDecryptValue(String k, boolean def) {
        return Boolean.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Float getDecryptValue(String k, float def) {
        return Float.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Long getDecryptValue(String k, long def) {
        return Long.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static String getDecryptValue(String k, String def, String seed) {
        if (def == null) {
            def = "";
        }
        return getDecrypt(k, def, DEFAULT_SEED);
    }

    public static Integer getDecryptValue(String k, int def, String seed) {
        return Integer.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Boolean getDecryptValue(String k, boolean def, String seed) {
        return Boolean.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Float getDecryptValue(String k, float def, String seed) {
        return Float.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }

    public static Long getDecryptValue(String k, long def, String seed) {
        return Long.valueOf(getDecrypt(k, def, DEFAULT_SEED));
    }


    public static String getDecrypt(String k, Object def, String seed) {
        if (VerifyUtil.isEmpty(seed)) {
            seed = DEFAULT_SEED;
        }
        String defStr = valueOf(def);
        String value = mSharePre.getString(k, defStr);

        if (value.equals(defStr)) {
            return value;
        }

        value = decrypt(value, seed);
        if (VerifyUtil.isEmpty(value)) {
            return defStr;
        }

        return value;
    }

    public static Map<String, ?> getAll() {
        return mSharePre.getAll();
    }

    public static Set<String> getStringSet(String key, Set<String> def) {
        return mSharePre.getStringSet(key, def);
    }


    /**
     * 加密
     *
     * @param source 明文
     * @param seed   种子
     * @return 密文(Base64字符串)
     */
    private static String encrypt(String source, String seed) {
        String encryptStr = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(seed), new IvParameterSpec(new byte[16]));
            encryptStr = new String(Base64.encode(cipher.doFinal(source.getBytes()), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    /**
     * 解密
     *
     * @param source 密文
     * @param seed   种子
     * @return 明文
     */
    private static String decrypt(String source, String seed) {
        String decryptStr = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKeySpec(seed), new IvParameterSpec(new byte[16]));
            decryptStr = new String(cipher.doFinal(Base64.decode(source.getBytes(), Base64.DEFAULT)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return decryptStr;
    }

    /**
     * 根据种子生成KeySpec
     *
     * @param seed 加密种子
     * @return keySpec
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    private static SecretKeySpec getKeySpec(String seed) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//added by lijun on 2016/8/5.因Android N 废弃了Crypto提供者所以N版本以上将采用以下方式生成SecretKeySpec
            byte[] salt = new byte[32];
            KeySpec keySpec = new PBEKeySpec(seed.toCharArray(), salt, 100, salt.length * 8);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES");
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN ? SecureRandom.getInstance("SHA1PRNG", "Crypto") : SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed.getBytes());
        keyGenerator.init(128, secureRandom);
        return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
    }

}  