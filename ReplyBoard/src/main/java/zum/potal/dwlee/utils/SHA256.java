package zum.potal.dwlee.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.RandomStringUtils;

import com.coremedia.iso.Hex;

public class SHA256 {

	public static String encode(String str){
		String SHA = ""; 
		String salt ="";
		try{
			
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			salt = RandomStringUtils.randomAscii(20);
			
			sh.update((str+salt).getBytes()); 
			byte byteData[] = sh.digest();
			
			SHA = Hex.encodeHex(byteData);
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
}
