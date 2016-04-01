
package modal;

import android.text.format.Time;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Day {	
	// int startDay;
	int monthEndDay;
	int day;
	int year;
	int month;

	boolean event_status;
	boolean event_loaded = false;

	public Day(int day, int year, int month,
				  boolean _event_status) {
		
		this.day = day;
		this.year = year;
		this.month = month;
		this.event_status = _event_status;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, end);
		TimeZone tz = TimeZone.getDefault();
		monthEndDay = Time.getJulianDay(cal.getTimeInMillis(),
				TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal
						.getTimeInMillis())));
	}

	// public long getStartTime(){
	// return startTime;
	// }
	//
	// public long getEndTime(){
	// return endTime;
	// }
	public boolean isEvent_status() {
		return event_status;
	}

	public void setEvent_status(boolean event_status) {
		this.event_status = event_status;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}


	public int getDay() {
		return day;
	}





}
