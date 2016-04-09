package com.ebusbar.utils;

import junit.framework.TestCase;

/**
 * Created by Jelly on 2016/4/7.
 */
public class RegExpUtilTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testRegPhone() throws Exception {
        boolean flag = RegExpUtil.regPhone("18617050557");
        assertEquals(flag,false);
    }
}