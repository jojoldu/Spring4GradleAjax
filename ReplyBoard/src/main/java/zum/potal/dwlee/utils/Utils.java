package zum.potal.dwlee.utils;


import java.sql.Timestamp;

import zum.potal.dwlee.vo.User;


public class Utils {
	
	public static Timestamp getNowTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	
	
//	public static boolean setSecurityPassword(User user) {
//		String password = SHA256.encode(user.getPassword());
//		if("".equals(password)){
//			return false;
//		}
//		user.setPassword(password);
//		return true;
//	}

}
