package snbc.java.singleEncryptionAlgorithm.SHA;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全散列算法（英语：Secure Hash Algorithm，缩写为SHA）
 * SHA-1 SHA-224、SHA-256、SHA-384，和SHA-512
 *
 * @author Lee Xiang
 * @date 2020/10/27 19:27
 **/
public class SHA {
    public static void getMd5(String str) {
        try {
            //默认 SHA-1
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] b = messageDigest.digest(str.getBytes());
            //将字节数组编码为十六进制字符串 HexBin.encode(b)
            System.out.println(HexBin.encode(b));
            System.out.println(HexBin.encode(b).length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String str = "snbc.security.sha";
        String str1 = "snbc.security.sha1";
        getMd5(str);
        getMd5(str1);
    }
}
