package com.xuhuan.utils.print;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 答应工具类
 *
 * @author huan.xu
 * @Time 2019-04-11 11:15
 */
public class PrintUtil {

    /**
     * 打印set
     *
     * @param set
     */
    public static void printlnSet(Set<String> set) {
        if (CollectionUtils.isNotEmpty(set)) {
            for (String str : set) {
                System.out.println(str);
            }
        }
    }

    /**
     * 打印list<String>
     * @param list
     */
    public static void printlnList(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }
}
