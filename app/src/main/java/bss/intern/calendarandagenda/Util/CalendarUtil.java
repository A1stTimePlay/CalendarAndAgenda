package bss.intern.calendarandagenda.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {

    public static Calendar today(){
        Calendar calendar = Calendar.getInstance();
        return calendar;
    }

    public static String stringMonthInYear(Calendar calendar) {
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }

    public static String stringDayInWeek(Calendar calendar) {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
    }

    public static String stringDayInMonth(Calendar calendar) {
        return Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String stringHeader(Calendar calendar) {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH) + " " + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int dayOfWeek(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
