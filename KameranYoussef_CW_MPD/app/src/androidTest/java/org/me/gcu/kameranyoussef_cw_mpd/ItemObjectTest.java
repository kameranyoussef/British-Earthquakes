package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ItemObjectTest {

    private String Test = "Test";
    private ItemObject ObjSet = new ItemObject();

    @Before
    public void setUp() throws Exception {
        Test = "Test";
        ObjSet.setTitle(Test);
        ObjSet.setMagnitude(Test);
        ObjSet.setDepth(Test);
        ObjSet.setDate(Test);
        ObjSet.setTime(Test);
        ObjSet.setCategory(Test);
        ObjSet.setgLat(Test);
        ObjSet.setgLong(Test);
    }


    @Test
    public void setTitle() { ObjSet.setTitle(Test); }
    @Test
    public void getTitle() { Assert.assertTrue(ObjSet.getTitle().equals(Test)); }


    @Test
    public void setMagnitude() { ObjSet.setMagnitude(Test);}
    @Test
    public void getMagnitude() { Assert.assertTrue(ObjSet.getTitle().equals(Test)); }


    @Test
    public void setDepth() { ObjSet.setDepth(Test); }
    @Test
    public void getDepth() { Assert.assertTrue(ObjSet.getDepth().equals(Test)); }

    @Test
    public void setDate() { ObjSet.setDate(Test); }
    @Test
    public void getDate() { Assert.assertTrue(ObjSet.getDate().equals(Test)); }



    @Test
    public void setTime() { ObjSet.setTime(Test); }
    @Test
    public void getTime() { Assert.assertTrue(ObjSet.getTime().equals(Test)); }



    @Test
    public void setCategory() { ObjSet.setCategory(Test); }
    @Test
    public void getCategory() { Assert.assertTrue(ObjSet.getCategory().equals(Test));}



    @Test
    public void setgLat() { ObjSet.setgLat(Test);}
    @Test
    public void getgLat() { Assert.assertTrue(ObjSet.getgLat().equals(Test)); }



    @Test
    public void setgLong() { ObjSet.setgLong(Test);}
    @Test
    public void getgLong() { Assert.assertTrue(ObjSet.getgLong().equals(Test)); }



    @After
    public void tearDown() throws Exception {
        Test = null;
        ObjSet.equals(false);
    }
}