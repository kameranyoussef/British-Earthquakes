package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this class has only one method. its used to convert the title of item in the listView

public class CaseConvert {
    String camelCase(String str)
    {
        StringBuilder builder = new StringBuilder(str);

        boolean isLastSpace = true;

        for(int i = 0; i < builder.length(); i++)
        {
            char ch = builder.charAt(i);
            if(isLastSpace && ch >= 'a' && ch <='z')
            {
                builder.setCharAt(i, (char)(ch + ('A' - 'a') ));
                isLastSpace = false;
            }
            else if (ch != ' ')
                isLastSpace = false;
            else
                isLastSpace = true;
        }
        return builder.toString();
    }
}
