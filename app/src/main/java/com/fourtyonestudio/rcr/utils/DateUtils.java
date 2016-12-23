package com.fourtyonestudio.rcr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String FORMAT_DATE = "dd MMMM yyyy";
//    public static String formatDateDefault(String date) {
//        String textNewDate = "";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        DateFormat dfNew = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
//        try {
//            Date newDate = df.parse(date);
//            textNewDate = dfNew.format(newDate);
//        } catch (ParseException e) {
//
//        }
//        return textNewDate;
//    }

//    public static String formatDateNow() {
//        DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
//        String currentDateTime = df.format(new Date());
//        return currentDateTime;
//    }

    public static String getDateNow() {
        return getDateNow(FORMAT_DATE);
    }

    public static String getDateNow(String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        String currentDateTime = df.format(new Date());
        return currentDateTime;
    }

//    public static Date fromString(String date) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        DateFormat dfNew = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
//        Date newDate = null;
//        try {
//            newDate = df.parse(date);
//        } catch (ParseException e) {
//
//        }
//        return newDate;
//    }

    public static boolean isPreviousDay(String date) {
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        try {
            Date inputDate = df.parse(date);
            Date dateNow = new Date();

//            Log.e("benar", df.format(inputDate) + " " + df.format(dateNow));
//            Log.e("benar", (inputDate.before(dateNow)&&!inputDate.equals(dateNow))+""+inputDate.before(dateNow));

            if (inputDate.compareTo(dateNow) < -1) {
                return true;
            } else {
                return false;
            }
//            if (inputDate.before(dateNow)&&!inputDate.equals(dateNow)) {
//                return true;
//            } else {
//                return false;
//            }

        } catch (ParseException e) {
            return false;
        }

//        String textNewDate = "";
////        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat dfNew = new SimpleDateFormat("dd MMMM yyyy");
//        try {
//            Date newDate = dfNew.parse(date);
//            textNewDate = dfNew.format(newDate);
//            Date date1 = dfNew.parse(textNewDate);
//            Date date2 = new Date();
//            Log.e("benar", dfNew.format(date1) + " " + dfNew.format(date2));
//            Log.e("benar", Boolean.toString(date2.compareTo(date1) < 0));
//            if (date2.compareTo(date1) >1) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (ParseException e) {
//            return false;
//        }
    }

}
