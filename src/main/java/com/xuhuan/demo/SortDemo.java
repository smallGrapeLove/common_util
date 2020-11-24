package com.xuhuan.demo;

import org.junit.Test;

import java.util.Arrays;

/**
 * 排序
 *
 * @author huan.xu
 * @Time 2019-04-22 15:16
 */
public class SortDemo {

    /**
     * 冒泡排序：通俗的意思就是讲，在一组数据中，相邻元素依次比较大小，最大的放后面，最小的冒上来
     */
    @Test
    public void mpSort() {
        int[] nums = {19, 8, 25, 93, 14, 5, 76};
        System.out.println("原始数组：" + Arrays.toString(nums));
        //第一个for循环是程序需要执行要走多少趟
        for (int i = 0; i < nums.length - 1; i++) {
            //第二个for循环是每趟需要比较多少次
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
            System.out.println("第" + (i + 1) + "次排序：" + Arrays.toString(nums));
        }
        System.out.println("最终数组：" + Arrays.toString(nums));

    }

    /**
     * 二分查找
     */
    @Test
    public void efFind() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8,};
        int value = binary(nums, 2);
        System.out.println(value);

    }

    public int binary(int[] nums, int value) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int moddle = (low + high) / 2;
            if (value == nums[moddle]) {
                return moddle;
            }
            if (value > nums[moddle]) {
                low=moddle+1;
            }
            if (value < nums[moddle]) {
                high=moddle-1;
            }
        }
        return -1;
    }

}
