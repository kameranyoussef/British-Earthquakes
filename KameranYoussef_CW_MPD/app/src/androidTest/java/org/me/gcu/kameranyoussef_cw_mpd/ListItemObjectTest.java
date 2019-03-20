package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ListItemObjectTest {

    private String mText1;
    private String mText2;
    private ListItemObject items = new ListItemObject();

    @Before
    public void setUp() throws Exception {
        mText1 = "Test";
        mText2 = "Test";
    }


    @Test
    public void getTitle() {items.getTitle(); }

    @Test
    public void setTitle() { items.setTitle(mText1); }

    @Test
    public void getDate() { items.getDate(); }

    @Test
    public void setDate() {items.setTitle(mText2); }

    @After
    public void tearDown() throws Exception {
        mText1 = null;
        mText2 = null;
    }

}