package org.me.gcu.kameranyoussef_cw_mpd;
// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this is the template class for Spinner Items .

public class SpinnerItems {
    //var
    private String mSpinnerItemsTitle;
    private int mSpinnerItemsIcon;

    //constructor
    public SpinnerItems(String mSpinnerItems, int mFlagImage) {
        this.mSpinnerItemsTitle = mSpinnerItems;
        this.mSpinnerItemsIcon = mFlagImage;
    }

    // Getter and setter methods
    public String getmSpinnerItemsTitle() {
        return mSpinnerItemsTitle;
    }

    public int getmSpinnerItemsIcon() {
        return mSpinnerItemsIcon;
    }

}
