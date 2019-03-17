package com.message.demo.test.component.common;

import java.util.Random;

public class RandomNames {
    public static String randomize(String s) {
        return s + "-" + new Random().nextInt(Integer.MAX_VALUE);
    }
}
