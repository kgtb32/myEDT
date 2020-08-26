package com.kgtb32.icallib;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class IcalParser {
    private Scanner fileReader;

    public IcalParser(Scanner icalData) {
        this.fileReader = icalData;
    }

    public Event[] getDateEvent(Date date) {
        this.fileReader.useDelimiter("BEGIN:VEVENT");
        this.fileReader.next();
        ArrayList<Event> events = new ArrayList<Event>();
        while (this.fileReader.hasNext()) {
            String str = this.fileReader.next();
            //Log.e("delem",str+"\n\n");
            ParserObject obj = new ParserObject(str);
            if (obj.isSameDate(date)) {
                events.add(new Event(obj.getDTSTART(), obj.getDTEND(), obj.getSummary(), obj.getEventsDescs().toArray(new EventDescription[obj.getEventsDescs().size()])));
                obj.removeDouble();
                Log.e("i","found");
            }
        }
        Log.e("IcalParser", "Parsing OK");
        return events.toArray(new Event[events.size()]);
    }

}