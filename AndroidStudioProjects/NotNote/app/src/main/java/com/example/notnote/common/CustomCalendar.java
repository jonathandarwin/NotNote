package com.example.notnote.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomCalendar {

    public static String ConvertDateToView(String rawDate)
    {
        DateFormat init = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat fin = new SimpleDateFormat("dd MMM yyyy");

        try{
            Date date = init.parse(rawDate);
            rawDate = fin.format(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return rawDate;
    }

    public static String ConvertMonthYearToView(String rawDate)
    {
        DateFormat init = new SimpleDateFormat("yyyy-MM");
        DateFormat fin = new SimpleDateFormat("MMM yyyy");

        try{
            Date date = init.parse(rawDate);
            rawDate = fin.format(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return rawDate;
    }

    public static String ConvertViewToMonthYear(String rawDate)
    {
        DateFormat init = new SimpleDateFormat("MMM yyyy");
        DateFormat fin = new SimpleDateFormat("yyyy-MM");

        try{
            Date date = init.parse(rawDate);
            rawDate = fin.format(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return rawDate;
    }


    public static String ConvertViewToDate(String rawDate)
    {
        DateFormat init = new SimpleDateFormat("dd MMM yyyy");
        DateFormat fin = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date date = init.parse(rawDate);
            rawDate = fin.format(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return rawDate;
    }

    public static String GetCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.getTime());

        DateFormat init = new SimpleDateFormat("MMM dd,yyyy");
        DateFormat fin = new SimpleDateFormat("yyyy-MM-dd");

        if(Locale.getDefault().getDisplayLanguage().equals("Bahasa Indonesia")){
            init = new SimpleDateFormat("dd MMM yyyy");
        }

        try {
            Date dt = init.parse(currentDate);
            currentDate = fin.format(dt);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return currentDate;
    }

    public static String GetCurrentTime()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        return currentDate;
    }

    public static boolean CompareDate(String datepicker, String datenow)
    {
        if(datepicker.compareTo(datenow) >= 0){
            return true;
        }

        return false;
    }

    public static String GetAuditedTime()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy, hh:mm:ss a");
        return format.format(date);
    }

    public static String GetYear(String rawDate){
        return rawDate.substring(0,4);
    }

    public static String GetMonth(String rawDate){
        return rawDate.substring(5,7);
    }

    public static String GetDay(String rawDate){return rawDate.substring(8,10); }
}
