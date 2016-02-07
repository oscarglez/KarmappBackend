package location;

public class parkings extends locationDN {
	public parkings() {
		super();
	};

	public parkings(int id, String description, double lat, double lon) {
		super(id, description, lat, lon);

	}
	
	private int accesibilidad =0;

	public int getAccesibilidad() {
		return accesibilidad;
	}

	public void setAccesibilidad(int accesibilidad) {
		this.accesibilidad = accesibilidad;
	}
	
}
