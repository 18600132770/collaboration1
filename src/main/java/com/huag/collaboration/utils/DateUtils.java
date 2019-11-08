package com.huag.collaboration.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huag
 * @date 2019/10/18 11:32
 */
public class DateUtils {

    /**
     * 获取两个时间差多少天
     * 数据输入格式yyyy-MM-dd
     * @param stopDate
     * @param startDate
     * @return
     */
    public static Long getDateDifferenceByDay(String stopDate, String startDate){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            long stopTimeLong = df.parse(stopDate).getTime();
            long startTimeLong =  df.parse(startDate).getTime();
            long timeLength = stopTimeLong - startTimeLong;
            return timeLength/1000/60/60/24;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateIn_yyyyMMddHHmmss(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

}
