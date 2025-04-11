package unicorns.backend.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
public class DateTimeUtil {
    public static final String PATTERN_DMY = "dd-MM-yyyy";
    public static final String PATTERN_YMD_FULL = "yyyy-MM-dd";
    public static final String PATTERN_YMD_HIS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_MDY_24_OCS = "MM/dd/yyyy HH:mm:ss";
    public static final String PATTERN_MDY_12_OCS = "MM/dd/yyyy hh:mm:ss a";
    public static final String PATTERN_DMY_TIME = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_YMD_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_HM_DMY = "HH:mm dd-MM-yyyy";
    public static final String PATTERN_dd_MM_yyyy = "dd/MM/yyyy HH:mm";
    public static final String PATTERN_DD_MM_YYYY_HH_mm_ss = "dd/MM/yyyy_HH:mm:ss";

    public static String toYMDTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_YMD_TIME);
        return simpleDateFormat.format(date);
    }

    public static Date getCurentDateGMT() {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
        return time.getTime();
    }

    public static LocalDateTime convertStringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (date != null && date != "") {
            LocalDateTime time = LocalDateTime.parse(date, formatter);
            return time;
        }
        return null;
    }

    public static LocalDateTime convertStringToDateWithFormat(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        if (date != null && date != "") {
            LocalDateTime time = LocalDateTime.parse(date, formatter);
            return time;
        }
        return null;
    }

    public static LocalDateTime parseStringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            if (date != null && date != "") {
                LocalDateTime time = LocalDateTime.parse(date, formatter);
                return time;
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static String getDayAfter(String timestamp) {
        return getNextDays(timestamp, 1, PATTERN_YMD_FULL);
    }


    public static List<String> getDatesBetween(String from, String to, String format) {
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Date dateFrom = sdf.parse(from);
            Date dateTo = sdf.parse(to);

            return getDatesBetween(dateFrom, dateTo, format);
        } catch (Exception e) {
            log.error(e);
        }

        return dates;
    }

    public static List<String> getDatesBetween(Date dateFrom, Date dateTo, String format) {
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(dateFrom);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(dateTo);

        while (startCalendar.before(endCalendar)) {
            Date result = startCalendar.getTime();
            dates.add(sdf.format(result));
            startCalendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    public static String getYmdHis() {
        return getTimeNowFormat(PATTERN_YMD_HIS);
    }
    public static String getYmd() {
        return getTimeNowFormat(PATTERN_YMD_FULL);
    }
    public static String getTimeNowFormat(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }
    public static String transform(String srcTime, String srcFormat, String desFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
            Date srcDate = sdf.parse(srcTime);
            sdf = new SimpleDateFormat(desFormat);
            return sdf.format(srcDate);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String formatDateYmdFull(Date date) {
        return formatDate(date, PATTERN_YMD_FULL);
    }
    public static boolean isValidFormat(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date parse = sdf.parse(date);
            System.out.println("parse.toString() = " + parse.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getNextDays(String timestamp, int duration, String formatTime) {
        SimpleDateFormat format = new SimpleDateFormat(formatTime);
        try {
            Date date = format.parse(timestamp);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, duration);
            date = c.getTime();


            return format.format(date);
        } catch (ParseException e) {
            log.error(e);
        }

        return "";
    }

    public static String getNextDays(String timestamp, int duration) {
        return getNextDays(timestamp, duration, PATTERN_YMD_HIS);
    }

    public static Date createExpireTime(Date createDate, int expiredTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(createDate);
        cal.add(Calendar.MILLISECOND, expiredTime);
        return cal.getTime();
    }

    public static long getDiffDays(String from, String to, String format) {
        long diffInMil = getDiffMilliseconds(from, to, format);
        return TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS);
    }

    public static Date getFirstDayOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }

    public static long getDiffHours(String from, String to, String format) {
        return getDiffMilliseconds(from, to, format) / (60 * 60 * 1000);
    }

    public static long getDiffHours(String from, String format) {
        String to = getYmdHis();
        return getDiffHours(from, to, format);
    }

    public static long getDiffMilliseconds(String from, String to, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date dateFrom = sdf.parse(from);
            Date dateTo = sdf.parse(to);
            return Math.abs(dateTo.getTime() - dateFrom.getTime());
        } catch (ParseException e) {
            log.error(e);
        }

        return 0;
    }

    public static String convertStringToByte(String input) {
        String[] split = input.split(",");
        String ouput = split[split.length - 1];
        for (String i : split) {
            ouput += i;
        }
        ouput = ouput.substring(0, ouput.length() - 1);
        return ouput;
    }

    public static String convertByteToString(String input) {
        String temp = input + input.charAt(0);
        temp = temp.substring(1);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            output.append(temp.charAt(i)).append(",");
        }
        String s = output.toString();
        s = s.substring(0, s.length() - 1);
        return s;
    }

    public static String convertByteToHour(String input) {
        int begin = input.indexOf("1");
        int end = input.lastIndexOf("1");
        String startHour = "";
        String endHour = "";
        if (begin % 2 == 0) {
            startHour = begin / 2 + ":00";
        } else {
            startHour = begin / 2 + ":30";
        }
        if (end % 2 == 0) {
            endHour = end / 2 + ":00";
        } else {
            endHour = end / 2 + ":30";
        }
        return startHour + "-" + endHour;
    }

    public static String parseDateToString(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    public static String convertHourToByte(String input) {
        String[] hour = input.split("-");
        String fromHour = hour[0];
        String toHour = hour[1];
        int begin = getNumberFromString(fromHour);
        int end = getNumberFromString(toHour);
        StringBuilder byteOfHour = new StringBuilder();
        for (int i = 0; i < 48; i++) {
            if (i >= begin && i <= end) {
                byteOfHour.append("1");
            } else {
                byteOfHour.append("0");
            }
        }
        return byteOfHour.toString();
    }

    private static int getNumberFromString(String time) {
        String[] beginArr = time.split(":");
        int number = Integer.valueOf(beginArr[0]) * 2;
        if (!beginArr[1].equals("00")) {
            number += 1;
        }
        System.out.println(number);
        return number;
    }


    public static boolean validTime(String time) {
        String[] hour = time.split("-");
        String fromHour = hour[0];
        String toHour = hour[1];
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            Date from = parser.parse(fromHour);
            Date to = parser.parse(toHour);
            if (from.after(to) || from.compareTo(to) == 0) {
                return false;
            }
        } catch (ParseException e) {
            log.error(e);
        }
        return true;
    }


    public static LocalDateTime parseDateTime(String time, String format) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime parseLocalDateTime(String time, String format) {
        try {
            return parseDateTime(time, format);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public static LocalDateTime parseDateTimeV2(String time, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date parse = simpleDateFormat.parse(time);
        return parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate parseDate(String time, String format) {
        System.out.println(time);
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(format));
    }


    public static String parseDateTimeToString(LocalDate time, String format) {
        return ObjectUtils.isEmpty(time)
                ? null : parseDateTimeToString(time.atTime(LocalTime.MIN), format);
    }

    public static String parseDateTimeToString(LocalDateTime time, String format) {
        return DateTimeFormatter.ofPattern(format).format(time);
    }

    public static String parseLocalDate(LocalDate date, String format) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        } else {
            return DateTimeFormatter.ofPattern(format).format(date);
        }
    }

    public static LocalTime parseStringToLocalTime(String time, String format) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
    }




    public static Date convertSqlStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null && date != "") {
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static long getDiffDays(Date from, Date to) {
        long diffInMil = Math.abs(to.getTime() - from.getTime());
        return TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS);
    }

    public static Date addDay(Date dt, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        dt = c.getTime();
        return c.getTime();
    }

    public static Date getMinToday(LocalDate date) {
        Date fromDayDate = null;
        fromDayDate = Date.from(LocalDateTime.from(date.atStartOfDay()).withHour(0).withMinute(0).withSecond(0)
                .atZone(ZoneId.systemDefault()).toInstant());
        return fromDayDate;
    }

    public static Date getMaxToday(LocalDate date) {
        Date fromDayDate = null;
        fromDayDate = Date.from(LocalDateTime.from(date.atStartOfDay()).withHour(23).withMinute(59).withSecond(59)
                .atZone(ZoneId.systemDefault()).toInstant());
        return fromDayDate;
    }

    public static LocalDateTime getMaxToday8(LocalDate date) {
        return LocalDateTime.from(date.atStartOfDay()).withHour(23).withMinute(59).withSecond(59);
    }
    public static String formatDateV2(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = dateFormat.format(date);
        return strDate;
    }
    public static Date parseDateV2(String d, String format) throws ParseException {
        return (new SimpleDateFormat(format)).parse(d);
    }


    public static String convertToDatetimeString(String date) {
        return getNextDays(date, 0, PATTERN_DMY_TIME);
    }

    public static String getDayAfter2(String timestamp) {
        return getNextDays(timestamp, 1, PATTERN_DMY_TIME);
    }

    public static String convertStringToDateString(String dateTime, String format) throws ApplicationException {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            if (StringUtils.isEmpty(dateTime)) {
                return null;
            } else {
                dateTime = dateTime.trim();
                List<String> dateTimeList = new ArrayList<>();
                if (dateTime.contains("'T'")) {
                    dateTimeList = Arrays.asList(dateTime.split("'T'"));
                } else
                    dateTimeList = Arrays.asList(dateTime.split(" "));

                for (String dateTimeStr: dateTimeList ) {
                    if (dateTimeStr.length() == 10) {
                        if (dateTimeStr.contains("-")) {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("-"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                            }
                        } else {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("/"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            }
                        }
                    }
                }
            }
            return localDateTime.format(DateTimeFormatter.ofPattern(format));

        } catch (Exception e) {
            log.info(e);
            return dateTime;
        }
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTime) {
        try {
            if (StringUtils.isEmpty(dateTime)) {
                return null;
            } else {
                LocalDateTime localDateTime = null;
                dateTime = dateTime.trim();
                List<String> dateTimeList = new ArrayList<>();
                if (dateTime.contains("'T'")) {
                    dateTimeList = Arrays.asList(dateTime.split("'T'"));
                } else
                    dateTimeList = Arrays.asList(dateTime.split(" "));

                for (String dateTimeStr : dateTimeList) {
                    if (dateTimeStr.length() == 10) {
                        if (dateTimeStr.contains("-")) {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("-"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                            }
                        } else {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("/"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse(dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            }
                        }
                    } else if (dateTimeStr.length() == 4) {
                        localDateTime = LocalDateTime.parse(dateTimeStr + "-01-01" + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    } else if (dateTimeStr.length() == 7) {
                        if (dateTimeStr.contains("-")) {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("-"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + "-01" + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse("01-" + dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                            }
                        } else {
                            List<String> dayMonthYearList = Arrays.asList(dateTimeStr.split("/"));
                            if (dayMonthYearList.get(0).length() == 4) {
                                localDateTime = LocalDateTime.parse(dateTimeStr + "/01" + " 00:00:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                            } else {
                                localDateTime = LocalDateTime.parse("01/" + dateTimeStr + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                            }
                        }
                    }
                }
                return localDateTime;
            }
        } catch (Exception e) {
            log.info(e);
            return null;
        }
    }
}

