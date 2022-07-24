/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * <p>
 * Title: CustomizeUtils
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public class CommonUtils {

	public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

	/**
	 * MD5 encode.
	 */
	public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
	}

	public static boolean isMobile(String mobileNumber) {
		Pattern p = Pattern.compile("^1(3[0-9]|4[0-9]|5[0-35-9]|8[0-9]|7[0-9]|6[0-9]|9[0-9])\\d{8}$");
		Matcher m = p.matcher(mobileNumber);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	public static boolean isName(String name) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public static boolean isPrimitive(Class<?> clazz) {
		try {
			if (clazz.isPrimitive()) {
				return true;
			}
			return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

}
