package snbc.java.singleEncryptionAlgorithm.HMAC;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * MAC（Message Authentication Code，消息认证码算法）是含有密钥散列函数算法
 * 算法 摘要长度  备注
 * HmacMD5 128  BouncyCastle实现
 * HmacSHA1 160 BouncyCastle实现
 * HmacSHA256   256BouncyCastle实现
 * HmacSHA384   384BouncyCastle实现
 * HmacSHA512   512JAVA6实现
 * HmacMD2 128  BouncyCastle实现
 * HmacMD4 128  BouncyCastle实现
 * HmacSHA224 224BouncyCastle实现
 * @author Lee Xiang
 * @date 2020/10/27 19:29
 **/
public class HMAC {
    private static String str = "snbc.security.HMAC";

    public static void getHmacMd5() {
        try {
            //初始化keyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMd5");
            //产生秘钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得秘钥
            byte[] key = secretKey.getEncoded();

            //还原秘钥
            SecretKey restoreSecret = new SecretKeySpec(key, "HmacMd5");
            //实例化Mac
            Mac mac = Mac.getInstance(restoreSecret.getAlgorithm());
            //初始化mac
            mac.init(restoreSecret);
            //执行摘要
            byte[] hmacMD5Bytes = mac.doFinal(str.getBytes());
            //将字节数组编码为十六进制字符串 HexBin.encode(b)
            System.out.println(HexBin.encode(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getHmacMd5();
    }
}
