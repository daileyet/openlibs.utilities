package com.openthinks.libs.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Test {
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String text = "123456123456";
		MessageDigest digest;
		digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes("UTF-8"));

		System.out.println(hash.length);
		for(byte b:hash) {
			String hex=String.format("%02x", Byte.toUnsignedInt(b));
			System.out.print(hex);
			System.out.print(" ");
		}
		System.out.println(hash.length);
		System.out.println(NIOByteUtils.toHexString(hash));
		// Integer.toHexString(i)
		text="hello world";
		hash = digest.digest(text.getBytes("UTF-8"));
		System.out.println(NIOByteUtils.toHexString(hash));
	}
}
