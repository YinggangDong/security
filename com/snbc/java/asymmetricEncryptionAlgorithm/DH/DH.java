package snbc.java.asymmetricEncryptionAlgorithm.DH;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * DH:密钥交换协议/算法(Diffie-Hellman Key Exchange/Agreement Algorithm)
 *
 * @author Lee Xiang
 * @date 2020/10/21 13:36
 * master分支
 **/
public class DH {
    private static String src = "jdk security dh";

    public static void main(String[] args) {
        jdkDH();
    }

    public static void jdkDH() {
        try {
            //1.初始化发送方密钥
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            //设置长度512位 这里会报异常，AES密钥所用的算法不被支持，这个是由于JDK8 update 161之后，DH的密钥长度至少为512位，
            //但AES算法密钥不能达到这样的长度，长度不一致所以导致报错。
            //解决的方法：将 -Djdk.crypto.KeyAgreement.legacyKDF=true 写入JVM系统变量中，可以在idea的run configurations里配置系统变量：
            //64的整数倍
            senderKeyPairGenerator.initialize(512);
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            //发送方公钥，发送给接收方（网络、文件。。。）
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

            //2.初始化接收方密钥
            KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
            //编码标准
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);

            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeypair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivateKey = receiverKeypair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeypair.getPublic().getEncoded();

            //3.密钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey, true);
            SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey, true);
            SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");

            if (Objects.equals(receiverDesKey, senderDesKey)) {
                System.out.println("双方密钥相同");
            }

            //4.加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk dh encrypt : " + Base64.encodeBase64String(result));

            //5.解密
            cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
            result = cipher.doFinal(result);
            System.out.println("jdk dh decrypt : " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
