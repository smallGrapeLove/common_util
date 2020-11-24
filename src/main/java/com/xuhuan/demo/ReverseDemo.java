package com.xuhuan.demo;

import org.junit.Test;

/**
 * 字符串反转demo
 *
 * @author huan.xu
 * @Time 2019-04-25 21:32
 */
public class ReverseDemo {

    /**
     * 通过String的substring方法实现字符串反转
     */
    @Test
    public void test1() {
        String str = "abcdefghijklmnopqrstuvwxyz";

        StringBuilder builder = new StringBuilder("");
        for (int i = str.length(); i > 0; i--) {
            String s = str.substring(i - 1, i);
            builder.append(s);
        }
        System.out.println(builder.toString());

    }

    /**
     * 通过String的charAt方法实现字符串反转
     */
    @Test
    public void test2() {
        String str = "abcdefghijklmnopqrstuvwxyz";

        StringBuilder builder = new StringBuilder("");
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            builder.append(String.valueOf(c));
        }
        System.out.println(builder.toString());
    }

    /**
     * 通过StringBuilder的reverse方法来实现字符串反转
     */
    @Test
    public void test3() {
        String str = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder(str);
        StringBuilder reverseBuilder = builder.reverse();
        System.out.println(reverseBuilder.toString());

    }
}
