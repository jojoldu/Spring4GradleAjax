package zum.potal.dwlee.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}
