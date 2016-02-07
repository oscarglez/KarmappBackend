package location;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class eventData {
	private DateTime eventTime;
	private locationDN locationDN;

	public eventData() {

		locationDN = new locationDN();
	}

	public DateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(DateTime eventTime) {
		this.eventTime = eventTime;
	}

	public locationDN getEventLocation() {
		return locationDN;
	}

	public void setEventsLocation(locationDN locationDN) {
		this.locationDN = locationDN;
	}

}
