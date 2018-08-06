package com.pigtom.study.introduction_algorithms.chapter12;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/6 14:52
 */
public enum  ColorEnum {
    RED("red", 0),
    BLACK("black", 1);
    private Integer value;
    private String des;
    ColorEnum(String des, Integer value) {
        this.value = value;
        this.des = des;
    }
}
