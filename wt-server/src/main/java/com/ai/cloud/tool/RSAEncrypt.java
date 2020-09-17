package com.ai.cloud.tool;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
	public static void main(String[] args) throws Exception {
		//生成公钥和私钥
		//genKeyPair();
		//加密字符串
		String message = "13200000000";
		keyMap.put(0, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZwyTxQjvFy/y5a/jy26eDUl9lWepJU4KSntqDTAShfVeqk+9doBkcAoOoh0FHYopR0Dw7u11bxQbjsnpXHyZgqODYuHunMWzruPoxstb7A9j+2odva4DRx0zi2Oef7Se57ZVMfTliQEP0SVT2BdPvXshko/hA2GntI1h2MBI18QIDAQAB");
		
String sss = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJnDJPFCO8XL/Llr+PLbp4NSX2VZ6klTgpKe2oNMBKF9V6qT712gGRwCg6iHQUdiilHQPDu7XVvFBuOyelcfJmCo4Ni4e6cxbOu4+jGy1vsD2P7ah29rgNHHTOLY55/tJ7ntlUx9OWJAQ/RJVPYF0+9eyGSj+EDYae0jWHYwEjXxAgMBAAECgYBcrUc99yrpzEqWRCL31jcAKwz+nJRe/+uqjf6stovv/glx8dqLduzLeiR61pMdAKLYLwevBP5/SqEdarzceBT7rHf6zNTZgT+DOiHPwfhRxSTwlkYqyzV5bH6+nc7026DLtw0F2f5wdI5OAD0IvgAQ33eCTAazF2MnKQcgCLhWAQJBAMpEQ/9XTbtFXaiwg5VOoBngxLXprjTJxLHJO3kU3/rGwPz0HkEXsR2eQCO+R1noRFIllkmgWxKmi8KhOB2bKOECQQDCnDJj+RMcfepnsdxJTcUbKupjaSfeMP9EXqW+eOoYLWbvFRdZ1NoiGrzcLF7fZjIm88Qv/bPr19SUh0BauV8RAkBkncrZhTtksh/iH1SFfGRGGzqfZ4xCNDCFZB4SoGC+5gnwixtVtAxmOGedoDTsM49w8cOuAnfrci/J9NruV1XhAkAgEf173B/6IgXu8LCMo/RHddsKZtnbE9f9PFuz99kcAt9jpuYiN6F63U0iO22nAwBgyusZvYVY6Ehv8Zn5WXMBAkEAkzgS/CBTT1h0UQ50PS/G2l/CJrjK9KQ/ZbMs957eJKAjNjE5mwzjOn7tFAYOnALr+XoEzGGpwjHXZdJzN+WQng==";
		keyMap.put(1, sss);
		System.out.println("随机生成的公钥为:" + keyMap.get(0));		System.out.println("随机生成的私钥为:" + keyMap.get(1));
		String messageEn = encrypt(message,keyMap.get(0));
		System.out.print(messageEn);
		String messageDe = decrypt(messageEn,keyMap.get(1));
		System.out.print("0----"+messageDe);
		
		System.out.println("还原后的字符串为:" + messageDe);
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("ip", messageDe);
		String sendHttpPostRequest = HttpClientUtil.sendHttpPostRequest("https://localhost/lua", map);
		System.out.println(sendHttpPostRequest);*/
		
		
		
	}
	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static void genKeyPair() throws NoSuchAlgorithmException {  
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
		// 初始化密钥对生成器，密钥大小为96-1024位  
		keyPairGen.initialize(1024,new SecureRandom());  
		// 生成一个密钥对，保存在keyPair中  
		KeyPair keyPair = keyPairGen.generateKeyPair();  
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));  
		// 得到私钥字符串  
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
		// 将公钥和私钥保存到Map
		keyMap.put(0,publicKeyString);  //0表示公钥
		keyMap.put(1,privateKeyString);  //1表示私钥
	}  
	/** 
	 * RSA公钥加密 
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	/** 
	 * RSA私钥解密
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

}

