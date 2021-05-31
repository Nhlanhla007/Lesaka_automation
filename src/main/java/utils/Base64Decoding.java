package utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Decoding {

	public String decode(String toDecode) throws Exception {
		byte[] base64decodedBytes = Base64.getDecoder().decode(toDecode);
		toDecode =new String(base64decodedBytes, "utf-8");
		return toDecode;
	}
	
}
