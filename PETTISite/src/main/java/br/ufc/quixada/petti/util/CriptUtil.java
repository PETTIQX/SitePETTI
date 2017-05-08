package br.ufc.quixada.petti.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptUtil {

	public static String hashSenha(String senha){
		MessageDigest md;
		String senhaHash = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(senha.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			senhaHash = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senhaHash;
	}
	
}
