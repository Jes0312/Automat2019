package com.wecash.http.cases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class lalal {
    public static void main(String[] args) {
//        int radom = (int)(Math.random()*5);
//        System.out.println(radom);
        List list = new ArrayList();
        Map rowData = new HashMap();

        for (int i = 1; i <= 1050; i++) {
            Random ran=new Random();

            int radom =ran.nextInt(10000);


//            System.out.println(radom);
            list.add("o"+radom+"o"+',');
            System.out.println(list);

        }


    }








}
