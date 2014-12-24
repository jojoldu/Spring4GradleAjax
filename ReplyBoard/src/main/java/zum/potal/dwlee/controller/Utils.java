package zum.potal.dwlee.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String getNowTime(){
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		return dayTime.format(new Date(time));
	}
}
