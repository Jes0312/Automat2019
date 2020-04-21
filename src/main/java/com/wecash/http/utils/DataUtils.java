package com.wecash.http.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/25  5:17 PM
 * @ContentUse :
 */
public class DataUtils {

    /**
     *  随机生成渠道订单号
     * @return
     */
    public static String channelOrderNoCreate(){

        String channelOrderNo = "ZJPT_Test_" + timeInfo("yyyyMMdd") + "_" + String.valueOf(randomInfo());

        return channelOrderNo;
    }


    /**
     *  生成指定格式的时间
     * @param pattern
     * @return
     */
    public static String timeInfo(String pattern){

        String formatDate = "";
        Date date = new Date();
        DateFormat dFormat = new SimpleDateFormat(pattern); //HH表示24小时制；
        formatDate = dFormat.format(date);

        return formatDate;
    }

    public static int randomInfo(){
        Random rand = new Random();
        int dataInfo = rand.nextInt(1000000) + 1000000;
        return dataInfo;
    }



    public static void main(String[] args){
        channelOrderNoCreate();
    }

}
