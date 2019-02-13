package dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TransTypeSQL_GregorianCalendar{

	public static final String FORMAT_DATE = "dd/MM/yyyy à HH:mm:ss";
	//      22/08/2018 à 20:20:20

	
	public static String StringFromGregorianCalendar(GregorianCalendar gc) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(gc.getTime());
	}
	
	public static GregorianCalendar GregorianCalendarFromSQLTimeStamp(Timestamp ts) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(ts.getTime());
		return date;
	}
	public static Timestamp SQLTimeStampFromGregorianCalendar(GregorianCalendar gc) {
		return new Timestamp(gc.getTimeInMillis());
	}

	public static GregorianCalendar GregorianCalendarfromString(String date) throws ParseException {
		GregorianCalendar rep= new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		Date dateD = format.parse(date);
		rep.setTime(dateD);
		return rep;
	}
	
	
}
