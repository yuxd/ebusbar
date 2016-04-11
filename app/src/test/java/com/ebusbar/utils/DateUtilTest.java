package com.ebusbar.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jelly on 2016/4/9.
 */
public class DateUtilTest {

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void testDifferDate() throws Exception {
        String result = DateUtil.DifferDate("2016-4-9 18:50:39","2016-4-9 18:35:38");
        System.out.print(result);
        Assert.assertEquals(result,"15");
    }
}