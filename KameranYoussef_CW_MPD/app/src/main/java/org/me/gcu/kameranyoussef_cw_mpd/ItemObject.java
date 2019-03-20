package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this is the template class for items of the xml data.
//this is to also prepare for parceling between activity.

import android.os.Parcel;
import android.os.Parcelable;

public class ItemObject implements Parcelable {
    //var
    String Title;
    String Magnitude;
    String Depth;
    String Date;
    String Time;
    String Category;
    String gLat ;
    String gLong;

    //constructor
    public ItemObject() { }

    // Getter and setter methods
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getMagnitude() {
        return Magnitude;
    }
    public void setMagnitude(String magnitude) {
        Magnitude = magnitude;
    }
    public String getDepth() {
        return Depth;
    }
    public void setDepth(String depth) {
        Depth = depth;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getTime() {
        return Time;
    }
    public void setTime(String time) {
        Time = time;
    }
    public String getCategory() {
        return Category;
    }
    public void setCategory(String category) {
        Category = category;
    }
    public String getgLat() {
        return gLat;
    }
    public void setgLat(String gLat) {
        this.gLat = gLat;
    }
    public String getgLong() {
        return gLong;
    }
    public void setgLong(String gLong) {
        this.gLong = gLong;
    }



    //set object to be in parcel
    protected ItemObject(Parcel in) {
         Title = in.readString();
         Magnitude = in.readString();
         Depth = in.readString();
         Date = in.readString();
         Time = in.readString();
         Category = in.readString();
         gLat = in.readString();
         gLong = in.readString();
    }


    //write the parcel and the destination of the parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Magnitude);
        dest.writeString(Depth);
        dest.writeString(Date);
        dest.writeString(Time);
        dest.writeString(Category);
        dest.writeString(gLat);
        dest.writeString(gLong);
        }

    @Override
    public int describeContents() {
        return 0;
    }

    //creating the parcel form the gaven data.
    public static final Creator<ItemObject> CREATOR = new Creator<ItemObject>() {
        @Override
        public ItemObject createFromParcel(Parcel in) {
            return new ItemObject(in);
        }

        @Override
        public ItemObject[] newArray(int size) {
            return new ItemObject[size];
        }
    };

}


