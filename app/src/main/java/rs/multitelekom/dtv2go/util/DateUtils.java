package rs.multitelekom.dtv2go.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String TAG = DateUtils.class.getSimpleName();

    public static Locale locale = Locale.getDefault();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", locale);

    public static String formatDate(Date date) {
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

}
