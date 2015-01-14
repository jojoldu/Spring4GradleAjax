package zum.potal.dwlee.utils;


import java.sql.Timestamp;


public class Utils {
	
	public static Timestamp getNowTime() {
		return new Timestamp(System.currentTimeMillis());
	}
}
