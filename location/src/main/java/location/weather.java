package location;

public class weather {
	private String forecast ="soleado";
	private int forecastid = 0;
	private int min = 0;
	private int max = 0;
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	public int getForecastid() {
		return forecastid;
	}
	public void setForecastid(int forecastid) {
		this.forecastid = forecastid;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	   
}
