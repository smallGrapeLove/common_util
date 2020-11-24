package com.xuhuan.demo;

import com.xuhuan.utils.common.TimeUtil;

import java.util.Date;
import java.util.Random;

/**
 * @author huan.xu
 * @Date 2020-11-10
 */
public class Demo {

    public static void main(String[] args){
//        for(int i=0;i<3000;i++){
////            System.out.println(TimeUtil.formatDate(new Date(),"yyyyMMddHHmmss"));
////            System.out.println(Math.round((Math.random()+1) * 1000));
//            System.out.println(Math.random());
//        }
        for(int i=0;i<3000;i++){
            Random ne=new Random();
            System.out.println(ne.nextInt(9000)+1000);


        }

    }
}
