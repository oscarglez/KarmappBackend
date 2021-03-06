package location;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.owlike.genson.Genson;

public class eventDataSet {
	public DateTime eventTime;
	ArrayList<locationDN> eventsByDay;

	public eventDataSet(DateTime eventTime, locationDN eventLocation) {
		this.eventTime = eventTime;
		this.eventsByDay = new ArrayList<locationDN>();
		this.eventsByDay.add(eventLocation);
	}

	public eventDataSet(DateTime eventTime, ArrayList<locationDN> eventLocation) {
		this.eventTime = eventTime;
		this.eventsByDay = eventLocation;
	}

	public DateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(DateTime eventTime) {
		this.eventTime = eventTime;
	}

	public ArrayList<locationDN> getEventsByDay() {
		return eventsByDay;
	}

	public void setEventsByDay(ArrayList<locationDN> eventsByDay) {
		this.eventsByDay = eventsByDay;
	}
}
