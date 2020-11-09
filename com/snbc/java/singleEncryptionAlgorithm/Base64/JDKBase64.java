package snbc.java.singleEncryptionAlgorithm.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * BASE64算法使用：1.jdk包下的Base64的使用(这里只演示jdk包下的Base64的使用)
 * 2.Commons codec包的Base64的使用
 * 3.Bouncy Castle包的Base64的使用
 *
 * @author Lee Xiang
 * @date 2020/10/21 19:06
 **/
public class JDKBase64 {
    private static String str = "snbc.security.base64";

    public static void jdkBase64() {
        try {
            //编码
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(str.getBytes());
            System.out.println(encode);

            //解码
            BASE64Decoder decoder = new BASE64Decoder();
            System.out.println(new String(decoder.decodeBuffer(encode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkBase64();
    }

}
