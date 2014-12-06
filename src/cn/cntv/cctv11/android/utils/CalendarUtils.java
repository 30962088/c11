package cn.cntv.cctv11.android.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtils {

	public static class CalendarDate {
		private Date date;
		private boolean enable;
		private int total;
		private boolean selected;

		public CalendarDate(Date date, boolean enable) {
			super();
			this.date = date;
			this.enable = enable;
		}

		public Date getDate() {
			return date;
		}

		public boolean isEnable() {
			return enable;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getTotal() {
			return total;
		}
		
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		public boolean isSelected() {
			return selected;
		}
		
		public int getDay(){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.DATE);
		}

	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public static List<CalendarDate> getDay(int year, int month) {

		Date startDate = getStartWeekOfMonth(year, month);

		Date endDate = getEndWeekOfMonth(year, month);

		int daysApart = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24l));

		List<CalendarDate> rows = new ArrayList<CalendarDate>();

		for (int i = 0; i <= daysApart; i++) {

			Date date = getDistanceDate(startDate, i);

			boolean enable = false;

			if (getMonth(date) + 1 == month) {
				enable = true;
			}
			rows.add(new CalendarDate(date, enable));

		}
		return rows;

	}

	private static Date getEndWeekOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);
		c.add(Calendar.DATE, -1);
		int week = c.get(Calendar.DAY_OF_WEEK);
		return getDistanceDate(c.getTime(), 7 - week);
	}

	private static Date getStartWeekOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, 1);
		int week = c.get(Calendar.DAY_OF_WEEK);
		return getDistanceDate(c.getTime(), 1 - week);
	}

	private static Date getDistanceDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

}
