import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
思路1：方法开头是都对参数的合法性判断
思路2：DataUtils里有的，不再重复
思路3：引用简单。只基于apache-common
思路4：为了调用简单，尽量不throws exception.用return null代替。对于int返回类型，只能抛出
思路5：弄个默认的日期格式yyyy-MM-dd
*/

/**
 * 日期工具类
 */
public class DateUtil {
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public DateUtil() {

    }

    /**
     * 对Date的加减时间操作的统一方法.
     * <pre>
     *     DateUtil.add(new Date(), Calendar.YEAR, 1);
     *     DateUtil.add(new Date(), Calendar.DATE, 5);
     * </pre>
     *
     * @param date          被操作的日期
     * @param calendarField 取值于Calendar定义的时间段，如 Calendar.YEAR , Calendar.DATE
     * @param amount        加减数量，可以是负数
     * @return 日期结果
     */
    public static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 对Date的加减时间操作的统一方法.
     * <pre>
     *     DateUtil.add("2017-09-13","yyyy-MM-dd", Calendar.YEAR, 1);
     *     DateUtil.add("2017-09-13","yyyy-MM-dd", Calendar.DATE, 5);
     * </pre>
     *
     * @param dateStr       被操作的日期字符串
     * @param pattern       dateStr对应的日期格式
     * @param calendarField 取值于Calendar定义的时间段，如 Calendar.YEAR , Calendar.DATE
     * @param amount        加减数量，可以是负数
     * @return 操作后的日期字符串
     */
    public static String add(final String dateStr, final String pattern, final int calendarField, final int amount) {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return df.format(c.getTime());
    }

    /**
     * 比较2个日期的大小，
     * case 1.日期1 > 日期2 ，返回1
     * case 2.日期1 = 日期2 ，返回0
     * case 3.日期1 < 日期2 ，返回-1
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果
     */
    public static int compare(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.compareTo(date2);
    }

    /**
     * 比较2个日期的大小，
     * case 1.日期1 > 日期2 ，返回1
     * case 2.日期1 = 日期2 ，返回0
     * case 3.日期1 < 日期2 ，返回-1
     *
     * @param dateStr1 日期1
     * @param dateStr2 日期2
     * @return 比较结果
     * @throws ParseException
     */
    public static int compare(final String dateStr1, final String dateStr2) throws ParseException {
        return compare(dateStr1, DEFAULT_DATE_FORMAT, dateStr2, DEFAULT_DATE_FORMAT);
    }

    /**
     * 比较2个日期的大小，
     * case 1.日期1 > 日期2 ，返回1
     * case 2.日期1 = 日期2 ，返回0
     * case 3.日期1 < 日期2 ，返回-1
     *
     * @param dateStr1 日期1
     * @param dateStr2 日期2
     * @param pattern  日期1、日期2的格式
     * @return 比较结果
     * @throws ParseException
     */
    public static int compare(final String dateStr1, final String dateStr2, final String pattern) throws ParseException {
        return compare(dateStr1, pattern, dateStr2, pattern);
    }

    /**
     * 比较2个日期的大小，
     * case 1.日期1 > 日期2 ，返回1
     * case 2.日期1 = 日期2 ，返回0
     * case 3.日期1 < 日期2 ，返回-1
     *
     * @param dateStr1 日期1
     * @param pattern1 dateStr1对应的格式
     * @param dateStr2 日期2
     * @param pattern2 dateStr2对应的格式
     * @return 比较结果
     * @throws ParseException
     */
    public static int compare(final String dateStr1, final String pattern1, final String dateStr2, final String pattern2) throws ParseException {
        if (dateStr1 == null || pattern1 == null || dateStr2 == null || pattern2 == null) {
            throw new IllegalArgumentException("The dateStrs and patterns must not be null");
        }
        DateFormat df1 = new SimpleDateFormat(pattern1);
        DateFormat df2 = new SimpleDateFormat(pattern2);
        return compare(df1.parse(dateStr1), df2.parse(dateStr2));
    }

    /**
     * 判断一个日期是否落在另2个日期之间
     *
     * @param compareDate 比较的日期
     * @param startDate   日期区间起
     * @param endDate     日期区间止
     * @return 是否落入
     */
    public static boolean isBetween(final Date compareDate, final Date startDate, final Date endDate) {
        if (compareDate == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return compareDate.compareTo(startDate) >= 0 && compareDate.compareTo(endDate) <= 0;
    }

    /**
     * 判断一个日期是否落在另2个日期之间，默认日期格式是yyyy-MM-dd
     *
     * @param compareDateStr 比较的日期
     * @param startDateStr   日期区间起
     * @param endDateStr     日期区间止
     * @return 是否落入
     */
    public static boolean isBetween(final String compareDateStr, final String startDateStr, final String endDateStr) throws ParseException {
        return isBetween(compareDateStr, startDateStr, endDateStr, DEFAULT_DATE_FORMAT);
    }

    /**
     * 判断一个日期是否落在另2个日期之间
     *
     * @param compareDateStr 比较的日期
     * @param startDateStr   日期区间起
     * @param endDateStr     日期区间止
     * @param pattern        日期格式
     * @return 是否落入
     */
    public static boolean isBetween(final String compareDateStr, final String startDateStr, final String endDateStr, final String pattern) throws ParseException {
        if (compareDateStr == null || startDateStr == null || endDateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStrs and patterns must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return isBetween(df.parse(compareDateStr), df.parse(startDateStr), df.parse(endDateStr));
    }

    /**
     * 格式化，默认日期格式是yyyy-MM-dd
     *
     * @param date 日期
     * @return 格式化的日期
     */
    public static String format(final Date date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 格式化的日期
     */
    public static String format(final Date date, final String pattern) {
        if (date == null || pattern == null) {
            throw new IllegalArgumentException("The date and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 对日期字符串格式化
     *<pre>
     *     DateUtil.format("2019-02-15","yyyy-MM-dd","dd/MM/yyyy")  = 15/02/2019
     *     DateUtil.format("2000/1/15","yyyy/M/dd","yyyy/MM/dd")  =  2000/01/15
     *</pre>
     * @param dateStr 日期字符串
     * @param inputPattern  dateStr对应的日期格式
     * @param outputPattern 返回的日期格式
     * @return
     */
    public static String format(final String dateStr, final String inputPattern, final String outputPattern) {
        if (dateStr == null || inputPattern == null || outputPattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat inputDF = new SimpleDateFormat(inputPattern);
        DateFormat outputDF = new SimpleDateFormat(outputPattern);
        try {
            return outputDF.format(inputDF.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验是否为合理的日期,默认格式是yyyy-MM-dd
     * @param dataStr 日期字符串
     * @return 是否合格
     */
    public static boolean isValid(final String dataStr) {
        return isValid(dataStr, DEFAULT_DATE_FORMAT);
    }

    /**
     * 根据日期的格式校验是否为合理的日期
     *
     * @param dataStr 日期字符串
     * @param pattern dataStr对应的格式
     * @return 是否合格
     */
    public static boolean isValid(final String dataStr, final String pattern) {
        if (dataStr == null) {
            return false;
        }
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern must not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        try {
            sdf.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 返回系统日期的周几
     *
     * @return 周几
     */
    public static int getDayOfWeek() {
        return getDayOfWeek(new Date());
    }

    /**
     * 返回指定日期的周几
     *
     * @param date 日期
     * @return 周几
     */
    public static int getDayOfWeek(final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : (calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    /**
     * 获取月的第一天，时分秒不置空。
     *
     * @param date 指定日期
     * @return 月的第一天
     */
    public static Date getFirstDayOfMonth(final Date date) {
        return getFirstDayOfNextMonth(date, 0, false);
    }

    /**
     * 获取月的第一天，可选时分秒是否置空。
     * @param date 日期
     * @param isTruncate 是否置空
     * @return 月的第一天
     */
    public static Date getFirstDayOfMonth(final Date date,final boolean isTruncate) {
        return getFirstDayOfNextMonth(date, 0, isTruncate);
    }

    /**
     * 获取月的第一天
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @return 月的第一天
     */
    public static String getFirstDayOfMonth(final String dateStr, final String pattern) {
        return getFirstDayOfNextMonth(dateStr, pattern, 0);
    }

    /**
     * 获取下个月的第一天，时分秒不置空。
     *
     * @param date 日期
     * @return 月的第一天
     */
    public static Date getFirstDayOfNextMonth(final Date date) {
        return getFirstDayOfNextMonth(date, 1, false);
    }

    /**
     * 获取若干个月后的第一天，时分秒不置空。当n是负数时，则是若干个月前。
     *
     * @param date 日期
     * @param n    移动几个月
     * @return 月的第一天
     */
    public static Date getFirstDayOfNextMonth(final Date date, final int n) {
        return getFirstDayOfNextMonth(date, n, false);
    }

    /**
     * 获取若干个月后的第一天。当n是负数时，则是若干个月前。
     *
     * @param date       日期
     * @param n          移动几个月
     * @param isTruncate 是否截断
     * @return 月的第一天
     */
    public static Date getFirstDayOfNextMonth(final Date date, final int n, final boolean isTruncate) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, n);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        if (isTruncate) {
            return DateUtils.truncate(ca, Calendar.DAY_OF_MONTH).getTime();
        }
        return ca.getTime();
    }

    /**
     * 获取若干个月后的第一天。当n是负数时，则是若干个月前。
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @param n       移动几个月
     * @return 月的第一天
     */
    public static String getFirstDayOfNextMonth(final String dateStr, final String pattern, final int n) {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, n);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return df.format(ca.getTime());
    }

    /**
     * 获取月的最后一天，时分秒不置空。如果置空，使用getLastDayOfMonthTruncate（Date）
     *
     * @param date 日期
     * @return 月的最后一天
     */
    public static Date getLastDayOfMonth(final Date date) {
        return getLastDayOfNextMonth(date, 0, false);
    }

    /**
     * 获取月的最后一天，时分秒置空。
     *
     * @param date 日期
     * @return 月的最后一天
     */
    public static Date getLastDayOfMonth(final Date date,final boolean isTruncate) {
        return getLastDayOfNextMonth(date, 0, isTruncate);
    }

    /**
     * 获取月的最后一天
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @return 月的最后一天
     */
    public static String getLastDayOfMonth(final String dateStr, final String pattern) {
        return getLastDayOfNextMonth(dateStr, pattern, 0);
    }

    /**
     * 获取下个月后的最后一天，时分秒不置空。
     * @param date 日期
     * @param n 移动几个月
     * @return 月的最后一天
     */
    public static Date getLastDayOfNextMonth(final Date date) {
        return getLastDayOfNextMonth(date, 1, false);
    }

    /**
     * 获取若干个月后的最后一天，时分秒不置空。当n是负数时，则是若干个月前。
     * @param date 日期
     * @param n 移动几个月
     * @return 月的最后一天
     */
    public static Date getLastDayOfNextMonth(final Date date,final int n) {
        return getLastDayOfNextMonth(date, n, false);
    }

    /**
     * 获取若干个月后的最后一天，时分秒置空。当n是负数时，则是若干个月前。
     *
     * @param date       日期
     * @param n          移动几个月
     * @param isTruncate 是否截断
     * @return 月的最后一天
     */
    public static Date getLastDayOfNextMonth(final Date date, final int n, final boolean isTruncate) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, n);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (isTruncate) {
            return DateUtils.truncate(ca, Calendar.DAY_OF_MONTH).getTime();
        }
        return ca.getTime();
    }

    /**
     * 获取若干个月后的最后一天。当n是负数时，则是若干个月前。
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @param n       移动几个月
     * @return 月的最后一天
     */
    public static String getLastDayOfNextMonth(final String dateStr, final String pattern, int n) {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, n);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return df.format(ca.getTime());
    }



    /**
     * 获取年的第一天
     *
     * @param year 年的字符串，4位
     * @return 年的第一天
     */
    public static String getFirstDayByYear(final String year) {
        if (year == null) {
            throw new IllegalArgumentException("The year must not be null");
        }
        return year + "-01-01";
    }

    /**
     * 获取年的第一天，时分秒不置空。
     *
     * @param date 日期
     * @return 年的第一天
     */
    public static Date getFirstDayOfYear(final Date date) {
        return getFirstDayOfNextYear(date, 0, false);
    }

    /**
     * 获取年的第一天，时分秒置空。如果不置空，使用getFirstDayOfYear（Date）
     *
     * @param date 日期
     * @return 年的第一天
     */
    public static Date getFirstDayOfYear(final Date date,final boolean isTruncate) {
        return getFirstDayOfNextYear(date, 0, isTruncate);
    }

    /**
     * 获取年的第一天
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @return 年的第一天
     */
    public static String getFirstDayOfYear(final String dateStr, final String pattern) {
        return getFirstDayOfNextYear(dateStr, pattern, 0);
    }

    /**
     * 获取明年的第一天。当n是负数时，则是若干年前。
     * @param date 日期
     * @return 明年的第一天
     */
    public static Date getFirstDayOfNextYear(final Date date){
        return getFirstDayOfNextYear(date,1,false);
    }

    /**
     * 获取若干年后的第一天。当n是负数时，则是若干年前。
     * @param date 日期
     * @param n 移动几年
     * @return 明年的第一天
     */
    public static Date getFirstDayOfNextYear(final Date date,final int n){
        return getFirstDayOfNextYear(date,n,false);
    }


    /**
     * 获取若干年后的第一天。当n是负数时，则是若干年前。
     *
     * @param date       日期
     * @param n          移动几年
     * @param isTruncate 是否截断
     * @return 年的第一天
     */
    public static Date getFirstDayOfNextYear(final Date date, final int n, final boolean isTruncate) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.YEAR, n);
        ca.set(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        if (isTruncate) {
            return DateUtils.truncate(ca, Calendar.DAY_OF_MONTH).getTime();
        }
        return ca.getTime();
    }

    /**
     * 获取若干年后的第一天。当n是负数时，则是若干年前。
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @param n       移动几年
     * @return 年的第一天
     */
    public static String getFirstDayOfNextYear(final String dateStr, final String pattern, final int n) {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.YEAR, n);
        ca.set(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return df.format(ca.getTime());
    }


    /**
     * 获取若干年后的最后一天。
     *
     * @param year 年的字符串
     * @return 年的最后一天
     */
    public static String getLastDayByYear(final String year) {
        if (year == null) {
            throw new IllegalArgumentException("The year must not be null");
        }
        return year + "-12-31";
    }

    /**
     * 获取若干年后的最后一天，时分秒不置空。如果置空，使用getLastDayOfYearTruncate（Date）
     *
     * @param date 日期
     * @return 年的最后一天
     */
    public static Date getLastDayOfYear(final Date date) {
        return getLastDayOfNextYear(date, 0, false);
    }

    /**
     * 获取若干年后的最后一天，时分秒置空。如果不置空，使用getLastDayOfYear（Date）
     *
     * @param date 日期
     * @return 年的最后一天
     */
    public static Date getLastDayOfYear(final Date date,final boolean isTruncate) {
        return getLastDayOfNextYear(date, 0, isTruncate);
    }

    /**
     * 获取若干年后的最后一天，时分秒不置空。如果置空，使用getLastDayOfYearTruncate（Date）
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的格式
     * @return 年的最后一天
     */
    public static String getLastDayOfYear(final String dateStr, final String pattern) {
        return getLastDayOfNextYear(dateStr, pattern, 0);
    }

    /**
     * 获取明年的最后一天，时分秒不置空。
     * @param date
     * @return
     */
    public static Date getLastDayOfNextYear(final Date date){
        return getLastDayOfNextYear(date, 1, false);
    }

    /**
     * 获取若干年后的最后一天。当n是负数时，则是若干年前。
     *
     * @param date       日期
     * @param n          移动几年
     * @param isTruncate 是否截断
     * @return 年后的最后一天
     */
    public static Date getLastDayOfNextYear(final Date date, final int n, final boolean isTruncate) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.YEAR, n);
        ca.set(Calendar.MONTH, 11);
        ca.set(Calendar.DAY_OF_MONTH, 31);
        if (isTruncate) {
            return DateUtils.truncate(ca, Calendar.DAY_OF_MONTH).getTime();
        }
        return ca.getTime();
    }

    /**
     * 获取若干年后的最后一天。当n是负数时，则是若干年前。
     *
     * @param dateStr 日期字符串
     * @param pattern dateStr对应的模式
     * @param n       移动几年
     * @return 年的最后一天
     */
    public static String getLastDayOfNextYear(final String dateStr, final String pattern, final int n) {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("The dateStr and pattern must not be null");
        }
        DateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.YEAR, n);
        ca.set(Calendar.MONTH, 11);
        ca.set(Calendar.DAY_OF_MONTH, 31);
        return df.format(ca.getTime());
    }

    /**
     * 获取2个日期的月份差。需要注意：如果2个日期相同，这个方法返回1。
     * 因为对于大部分需求，这个方法是用来求类似“月数×每月费用=总费用”中的月数。
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 月份差
     */
    public static int getMonthDiff(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int years = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int months = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(12 * years + months) + 1;
    }

    /**
     * 获取2个日期的月份差。需要注意：如果2个日期相同，这个方法返回1。
     * 因为对于大部分需求，这个方法是用来求类似“月数×每月费用=总费用”中的月数。
     *
     * @param dateStr1 日期1
     * @param dateStr2 日期2
     * @return 月份差
     * @throws ParseException
     */
    public static int getMonthDiff(final String dateStr1, final String dateStr2) throws ParseException {
        return getMonthDiff(dateStr1, dateStr2, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取2个日期的月份差。需要注意：如果2个日期相同，这个方法返回1。
     * 因为对于大部分需求，这个方法是用来求类似“月数×每月费用=总费用”中的月数。
     *
     * @param dateStr1 日期字符串1
     * @param dateStr2 日期字符串2
     * @param pattern  日期字符串1、2对应的格式
     * @return 月份差
     */
    public static int getMonthDiff(final String dateStr1, final String dateStr2, final String pattern) throws ParseException {
        if (dateStr1 == null || dateStr2 == null || pattern == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        DateFormat sdf = new SimpleDateFormat(pattern);
        return getMonthDiff(sdf.parse(dateStr1), sdf.parse(dateStr2));
    }

    /**
     * 获取系统当前日期，返回格式为yyyy-MM-dd
     *
     * @return 系统当前日期
     */
    public static String getSysDateStr() {
        return format(new Date(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取系统当前日期，返回格式为输入参数dataFormat
     *
     * @param dataFormat 返回格式
     * @return 系统当前日期
     */
    public static String getSysDateStr(String dataFormat) {
        return format(new Date(), dataFormat);
    }


}
