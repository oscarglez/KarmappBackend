package location;

public class baseDN {
	private int id=0;	
	private String description = null;
	private double lat=0d;;
	private double lon= 0d;
	
	public baseDN(){}
	public baseDN(int id,String description,double lat,double lon){
		this.id = id;
		this.description=description;
		this.lat=lat;
		this.lon=lon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
}
