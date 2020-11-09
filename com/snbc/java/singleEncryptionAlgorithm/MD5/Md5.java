package snbc.java.singleEncryptionAlgorithm.MD5;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Lee Xiang
 * @date 2020/10/21 19:47
 **/
public class Md5 {

    public static void getMd5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("Md5");
            byte[] b = messageDigest.digest(str.getBytes());
            //将字节数组编码为十六进制字符串 HexBin.encode(b)
            System.out.println(HexBin.encode(b));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String str = "snbc.security.md";
        String str1 = "snbc.security.md1";
        getMd5(str);
        getMd5(str1);
    }
}
