package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this class is to get xml data and process the data.

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;



public class HandleXML {
    //var
    public ArrayList<ItemObject> itemObjects;
    public ArrayList<ListItemObject> listitem;
    public String text;
    private String Title= null;
    private String Magnitude= null;
    private double lMagnitude= 0;
    private String Depth= null;
    private int lDepth= 0;
    private String Time= null;
    private String Date= null;
    private String Category= null;
    private String gLat= null;
    private String gLong = null;


    public void ProcessData(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
    {
        //setting up array
        itemObjects = new ArrayList<>();
        listitem = new ArrayList<>();

        //getting xmlPullParser event
        int eventType = xmlPullParser.getEventType();

        //check if we reach to the end of the document
        while (eventType != XmlPullParser.END_DOCUMENT) {
            //initialise array
            ItemObject itemObject = new ItemObject();
            ListItemObject item = new ListItemObject();

            //getting tag name in XmlPullParser
            String tagName = xmlPullParser.getName();

            //check if eventType is true
            switch (eventType) {
                case XmlPullParser.START_TAG:
                        //nothing for start tag of each element in the document
                    break;

                    case XmlPullParser.TEXT:
                        //get the text from every element in the XmlPullParser
                        text = xmlPullParser.getText();
                    break;

                    case XmlPullParser.END_TAG:
                        //check if getting the correct title
                        // processing the text for each wanted element in the XmlPullParser
                        if (!text.equals("Recent UK earthquakes") && !text.equals("BGS Logo")) {
                            if (tagName.equalsIgnoreCase("title")) {
                                text = text.split(", ")[0];
                                text = text.replace("UK Earthquake alert : M", "");
                                text = text.replaceAll("\\d", "");
                                text = text.replace("-", "");
                                text = text.replace(".", "");
                                text = text.replace(":", "");
                                text = text.replace(",", " ");
                                text = text.replace("    ", "");
                                text = text.replace("   ", "");
                                text = text.replace("  ", "");
                                text = text.toLowerCase();
                                CaseConvert convert = new CaseConvert();
                                text = convert.camelCase(text);
                                Title = text;


                            }
                        }
                        if (!text.equals("Recent UK seismic events recorded by the BGS Seismology team")){
                            if (tagName.equalsIgnoreCase("description")) {
                                text = text.replaceAll(".*De", "De");
                                text = text.replace(" ;", ",");
                                text = text.replace("smic events reco", "");
                                Depth = text;

                                if (text.length() > 27)
                                {
                                    if (text.length() == 28){
                                        text = text.substring(13, 28);
                                    }else if(text.length() == 29) {
                                        text = text.substring(13, 29);
                                    }else {
                                        text = text.substring(13, 29);
                                    }
                                }

                                text =text.replace(" M", "M");
                                Magnitude = text;



                                text = text.replace("Magnitude: ", "");
                                lMagnitude = Double.parseDouble(text);



                                Depth = Depth.split(", ")[0];


                                Depth = Depth.replace("Depth: ", "");
                                Depth = Depth.replace(" km", "");
                                lDepth = Integer.parseInt(Depth);


                            }

                        }
                        if (tagName.equalsIgnoreCase("pubDate")) {
                            Time = text;


                            if (text.length() > 24) {
                                text = text.substring(0, 16);
                            }

                            if (Time.length() > 24) {
                                Time = Time.substring(16, 25);
                            }
                            Time = Time.replace(" ", "");
                            Date = text;


                        }

                        if (tagName.equalsIgnoreCase("category")) {
                            Category = text;
                        }

                        if (tagName.equalsIgnoreCase("geo:lat")) {
                            gLat = text;
                        }

                        if (tagName.equalsIgnoreCase("geo:long")) {
                            gLong = text;
                        }

                    break;

                default:
                    break;
            }


            //check if all elements are is set and ready amd adding the data to array
            if (Title != null && Magnitude != null  && Date != null && Category != null && gLat != null && gLong != null){

                itemObject.setTitle(Title);
                itemObject.setDate(Date);
                itemObject.setMagnitude(Magnitude);
                itemObject.setDepth(Depth);
                itemObject.setTime(Time);
                itemObject.setCategory(Category);
                itemObject.setgLat(gLat);
                itemObject.setgLong(gLong);


                item.setTitle(Title);
                item.setDate(Date);
                item.setMagnitude(lMagnitude);
                item.setDepth(lDepth);

                itemObjects.add(itemObject);
                listitem.add(item);

                Title = null;
                Magnitude = null;
                Date = null;
                Category = null;
                gLat = null;
                gLong = null;

            }
            eventType = xmlPullParser.next();
        }
    }


}
