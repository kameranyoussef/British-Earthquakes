package org.me.gcu.kameranyoussef_cw_mpd;
// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CaseConvertTest {

    private String Text1;
    private String Text2;

    @Before
    public void setUp() throws Exception {
        Text1 = "Test Case";
    }

    @Test
    public void camelCase() {
        CaseConvert Test = new CaseConvert();
        Text2 = Test.camelCase("Test Case");

        Assert.assertTrue(Text2.equals(Text1));
    }

    @After
    public void tearDown() throws Exception {
        Text1 = null;
        Text2 = null;


    }
}