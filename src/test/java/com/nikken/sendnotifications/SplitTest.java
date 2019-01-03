package com.nikken.sendnotifications;

import org.junit.Test;

public class SplitTest {

    @Test
    public void testSplitStringToArray() {
        String notSplitString = "prengifos@gmail.com, abc@gmail.com";
        String[] array = notSplitString.split("\\,", -1);
        System.out.println(array.length);
    }
}
