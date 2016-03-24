package com.biocome.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.exception.UncheckedException;

/**
 * 加解密工具类。
 */
public class CryptUtils {
	/**
	 * 原始数字加密摘要
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5Purity(byte[] plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText);
			byte b[] = md.digest();
			return toString(b);
		} catch (Exception e) {
			throw new BusinessException("加密失败", e);
		}
	}

	/**
	 * 多次循环加密
	 * 
	 * @param plainText
	 * @param n
	 * @return
	 */
	public static String MD5Times(byte[] plainText, int n) {
		for (int i = 0; i < n; i++) {
			plainText = MD5Purity(plainText).getBytes();
		}
		return new String(plainText);
	}

	/**
	 * 附加字符串加密
	 * 
	 * @param plainText
	 * @param append
	 * @return
	 */
	public static String MD5Append(String plainText, String append) {
		return MD5Purity((plainText + append).getBytes()).toUpperCase();
	}

	/**
	 * 乱序加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5Reverse(String plainText) {
		StringBuffer s = new StringBuffer(MD5Purity(plainText.getBytes()));
		s = s.reverse();
		plainText = s.toString();
		return MD5Purity(plainText.getBytes()).toUpperCase();
	}

	/**
	 * byte密文转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String toString(byte[] b) {
		StringBuffer buf = new StringBuffer("");
		int i = 0;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * 将密文分割加密后合并再次加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5X32(byte[] plainText) {
		StringBuilder sb = new StringBuilder();
		String s = MD5Purity(plainText);
		int len = s.length();
		for (int i = 0; i < len; i++) {
			sb.append(MD5Purity(s.substring(i, i + 1).getBytes()));
		}
		return MD5Purity(sb.toString().getBytes());
	}

	/**
	 * 文件摘要
	 * 
	 * @param file
	 * @return
	 */
	public static String MD5File(String file) {
		FileInputStream fis = null;
		byte[] bs = new byte[1024 * 4];
		MessageDigest digest;
		try {
			fis = new FileInputStream(file);
			digest = MessageDigest.getInstance("MD5");
			int len = 0;
			while ((len = fis.read(bs)) > 0) {
				digest.update(bs, 0, len);
			}
			return toString(digest.digest());
		} catch (Exception e) {
			throw new BusinessException("文件加密失败", e);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	/**
	 * Base64编码。
	 * 
	 * @param content
	 *            待编码内容
	 * @return 返回编码后的内容。
	 */
	public static String encodeBase64(String content) {
		return new String(encodeBase64(content.getBytes()));
	}

	/**
	 * Base64解码。
	 * 
	 * @param content
	 *            待解码内容
	 * @return 返回解码后的内容。
	 */
	public static String decodeBase64(String content) {
		return new String(decodeBase64(content.getBytes()));
	}

	/**
	 * Base64编码。
	 * 
	 * @param contentBytes
	 *            待编码内容字节数组
	 * @return 返回编码后的内容字节数组。
	 */
	public static byte[] encodeBase64(byte[] contentBytes) {
		return Base64.encodeBase64(contentBytes);
	}

	/**
	 * Base64解码。
	 * 
	 * @param contentBytes
	 *            待解码内容字节数组
	 * @return 返回解码后的内容字节数组。
	 */
	public static byte[] decodeBase64(byte[] contentBytes) {
		return Base64.decodeBase64(contentBytes);
	}

	/**
	 * 符合RFC 1321标准的MD5编码。
	 * 
	 * @param content
	 *            待编码的内容
	 * @return 返回编码后的内容。
	 */
	public static String md5(String content) {
		return DigestUtils.md5Hex(content);
	}

	/**
	 * 生成密钥对。
	 * 
	 * @return 返回生成的密钥对。
	 */
	public static KeyPair genKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			return keyGen.genKeyPair();
		} catch (Exception e) {
			throw new UncheckedException("生成密钥对时发生异常", e);
		}
	}

	/**
	 * 从密钥对中获取Base64编码的公钥字符串。
	 * 
	 * @param keyPair
	 *            密钥对
	 * @return 返回Base64编码后的公钥字符串。
	 */
	public static String getPublicKey(KeyPair keyPair) {
		return new String(encodeBase64(keyPair.getPublic().getEncoded()));
	}

	/**
	 * 从密钥对中获取Base64编码的私钥字符串。
	 * 
	 * @param keyPair
	 *            密钥对
	 * @return 返回Base64编码的私钥字符串。
	 */
	public static String getPrivateKey(KeyPair keyPair) {
		return new String(encodeBase64(keyPair.getPrivate().getEncoded()));
	}

	/**
	 * 从Base64编码的公钥字符串中获取公钥。
	 * 
	 * @param publicKey
	 *            Base64编码的公钥字符串
	 * @return 返回公钥。
	 */
	public static PublicKey getPublicKey(String publicKey) {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(
					decodeBase64(publicKey.getBytes()));
			return factory.generatePublic(spec);
		} catch (Exception e) {
			throw new UncheckedException("将字符串转换为公钥时发生异常", e);
		}
	}

	/**
	 * 从Base64编码的私钥字符串中获取私钥。
	 * 
	 * @param privateKey
	 *            Base64编码的私钥字符串
	 * @return 返回私钥。
	 */
	public static PrivateKey getPrivateKey(String privateKey) {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
					decodeBase64(privateKey.getBytes()));
			return factory.generatePrivate(spec);
		} catch (Exception e) {
			throw new UncheckedException("将字符串转换为私钥时发生异常", e);
		}
	}

	/**
	 * 对字符串进行签名。
	 * 
	 * @param srcString
	 *            待签名的字符串
	 * @param privateKey
	 *            私钥
	 * @return 返回Base64编码格式的签名。
	 */
	public static String sign(String srcString, PrivateKey privateKey) {
		try {
			Signature rsa = Signature.getInstance("MD5withRSA");
			rsa.initSign(privateKey);
			rsa.update(srcString.getBytes());
			byte[] sig = rsa.sign();
			return new String(encodeBase64(sig));
		} catch (Exception e) {
			throw new UncheckedException("对字符串进行签名时发生异常", e);
		}
	}

	/**
	 * 验证签名。
	 * 
	 * @param srcString
	 *            原文字符串
	 * @param publicKey
	 *            公钥
	 * @param signature
	 *            签名
	 * @return 验证签名成功返回true，否则返回false。
	 */
	public static Boolean verify(String srcString, PublicKey publicKey,
			String signature) {
		try {
			Signature rsa = Signature.getInstance("MD5withRSA");
			rsa.initVerify(publicKey);
			rsa.update(srcString.getBytes());
			return rsa.verify(decodeBase64(signature.getBytes()));
		} catch (Exception e) {
			throw new UncheckedException("验证签名时发生异常", e);
		}
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 获取密钥
	 */
	public static SecretKeySpec getKeySpec(int length, String name,
			String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(name);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes("UTF-8"));
			kgen.init(length, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			return new SecretKeySpec(enCodeFormat, name);
		} catch (Exception e) {
			throw new BusinessException("获取密钥失败");
		}
	}

	/**
	 * AES加密。
	 * 
	 * @param content
	 *            待加密的内容
	 * @param password
	 *            密码
	 * @return 返回加密后的内容。
	 */
	public static String aesEncrypt(String content, String password) {
		try {
			SecretKeySpec key = getKeySpec(128, "AES", password);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
			return parseByte2HexStr(encrypted);
		} catch (Exception e) {
			throw new BusinessException("AES加密时发生异常。", e);
		}
	}

	/**
	 * AES解密。
	 * 
	 * @param content
	 *            待解密的内容
	 * @param password
	 *            密码
	 * @return 返回解密后的内容。
	 */
	public static String aesDecrypt(String content, String password) {
		try {
			SecretKeySpec key = getKeySpec(128, "AES", password);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(parseHexStr2Byte(content));
			return new String(result, "UTF-8");
		} catch (Exception e) {
			throw new BusinessException("AES解密时发生异常。", e);
		}
	}

	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	public static String getFileMD5(byte[] data) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
}
