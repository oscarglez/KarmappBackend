package location;

public class karmapp_data {
	private int alert = 0;
	private thermometer thermometer = null;
	private weather weather = null;
	private events[] events = null;
	private parkings[] parkings = null;
	private electricCharger[] electricCharger = null;
	public int getAlert() {
		return alert;
	}
	public void setAlert(int alert) {
		this.alert = alert;
	}
	public thermometer getThermometer() {
		return thermometer;
	}
	public void setThermometer(thermometer thermometer) {
		this.thermometer = thermometer;
	}
	public weather getWeather() {
		return weather;
	}
	public void setWeather(weather weather) {
		this.weather = weather;
	}
	public events[] getEvents() {
		return events;
	}
	public void setEvents(events[] events) {
		this.events = events;
	}
	public parkings[] getParkings() {
		return parkings;
	}
	public void setParkings(parkings[] parkings) {
		this.parkings = parkings;
	}
	public electricCharger[] getElectricCharger() {
		return electricCharger;
	}
	public void setElectricCharger(electricCharger[] electricCharger) {
		this.electricCharger =electricCharger;
	}
	
	

}
