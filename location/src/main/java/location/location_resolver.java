package location;

import java.util.ArrayList;

public class location_resolver {

	// Devuelve un objeto localización con latitud, longitud y nombre de evento
	public ArrayList<locationDN> resolveLocations(locationDN searchedLocationDN, ArrayList<locationDN> locationList,double radiusMaximun) {
		ArrayList<locationDN> returnedLocations = new ArrayList<locationDN>();
		for (locationDN locationDN : locationList) {
			Boolean hasToAppend = isInRange(searchedLocationDN, locationDN, radiusMaximun);
			if(hasToAppend){
				returnedLocations.add(locationDN);				
			}
		}
		
		return returnedLocations;
	}

	//Recupera si la distancia es menor que un rango dado
	private Boolean isInRange(locationDN searched, locationDN origin, double radiusMaximun) {
		double searchedLatitude = 0d;
		double searchedLongitude = 0d;
		double originLatitude = 0d;
		double originLongitude = 0d;

		double deg2radMultiplier = Math.PI / 180;

		searchedLatitude = searched.getLatitude() * deg2radMultiplier;
		searchedLongitude = searched.getLongitude() * deg2radMultiplier;

		originLatitude = origin.getLatitude() * deg2radMultiplier;
		originLongitude = origin.getLongitude() * deg2radMultiplier;

		double radius = 6378.137; // earth mean radius defined by WGS84
		double dlon = originLatitude - originLongitude;
		double distance = Math.acos(Math.sin(searchedLatitude) * Math.sin(originLatitude) + Math.cos(searchedLatitude) * Math.cos(originLatitude) * Math.cos(dlon)) * radius;

		return distance < radiusMaximun;
	}
	
	//Recupera la localización geográfica a partir de una coordenada.
	
	
	

}
