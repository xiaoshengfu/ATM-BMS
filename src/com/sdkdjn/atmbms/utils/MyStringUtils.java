package com.sdkdjn.atmbms.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public class MyStringUtils {

	/**
	 * 获得md5加密后的数据
	 * 
	 * @param value
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String getMD5Value(String value) {

		try {
			// 获得jdk提供消息摘要算法工具类
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 加密的结果10进制
			byte[] md5ValueByteArray = messageDigest.digest(value.getBytes());
			// 将10进制 转换16进制
			BigInteger bigInteger = new BigInteger(1, md5ValueByteArray);

			return bigInteger.toString(16);
		} catch (Exception e) {
			throw new RuntimeException("密码转化错误！");
		}
	}

	/**
	 * 获得15位的随机数
	 * 
	 * @return
	 */
	public static String getNumberID() {
		UUID uuid = UUID.randomUUID();
		String[] s = uuid.toString().split("-");
		String numberID = Long.parseLong(s[0] + s[1], 16) + "";
		if (numberID.length() == 15) {
			return numberID;
		} else {
			Random r = new Random(System.currentTimeMillis());
			numberID += r.nextInt(9);
			return numberID;
		}
	}

	/**
	 * 获得不带“-”的32位UUID值
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");

	}
}
