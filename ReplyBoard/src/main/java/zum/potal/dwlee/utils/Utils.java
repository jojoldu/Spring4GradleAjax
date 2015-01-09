package zum.potal.dwlee.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zum.potal.dwlee.vo.User;

public class Utils {
	
	public static String getNowTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return dayTime.format(new Date(time));
	}

	public static boolean setSecurityPassword(User user) {
		String password = SHA256.getPbCipher(user.getPassword());
		if(password==null){
			return false;
		}
		user.setPassword(password);
		return true;
	}
	
	public static boolean checkValidUser(User user){
		Pattern pId = Pattern.compile("^[0-9a-z]$");
		Matcher mId = pId.matcher(user.getId());
		
		Pattern pEmail = Pattern.compile("^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$");
		Matcher mEmail = pEmail.matcher(user.getEmail());
		
		if(mId.matches() && mEmail.matches()){
			return true;
		}
		return false;
	}

}
