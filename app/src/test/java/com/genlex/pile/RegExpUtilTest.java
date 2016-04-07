package com.genlex.pile;

import com.ebusbar.utils.RegExpUtil;

import org.junit.Test;

/**
 * Created by Jelly on 2016/3/3.
 */
public class RegExpUtilTest {
    public static final String TAG="RegExpUtilTest";

    @Test
    public void testRegPhone(){
        boolean flag = RegExpUtil.regPhone("18617050557");
        System.out.print(flag);
    }
}
