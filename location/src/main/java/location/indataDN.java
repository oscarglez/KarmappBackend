package location;

import org.joda.time.DateTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class indataDN {
	@SerializedName("lon")
	private double longitude = 0d;;
	@SerializedName("lat")
	private double latitude = 0d;	
	@SerializedName("id")
	private String userID = "";
	@SerializedName("date")
	private String date;
	public String direccion;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getdate() {
		return date;
	}

	public void setdate(String date) {
		this.date = date;		
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public DateTime getEventDate() {
		return new DateTime(date);
	}

	// public void setEventDate(DateTime eventDate) {
	// this.eventDate = eventDate;
	// }
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
