package location;

import java.util.ArrayList;

public class location_resolver {

	// Devuelve un objeto localización con latitud, longitud y nombre de evento
	public ArrayList<locationDN> resolveLocations(locationDN searchedLocationDN, ArrayList<locationDN> locationList, double radiusMaximun) {
		ArrayList<locationDN> returnedLocations = new ArrayList<locationDN>();
		for (locationDN locationDN : locationList) {
			Boolean hasToAppend = isInRange(searchedLocationDN, locationDN, radiusMaximun);
			if (hasToAppend) {
				returnedLocations.add(locationDN);
			}
		}

		return returnedLocations;
	}

	// Recupera si la distancia es menor que un rango dado
	private Boolean isInRange(locationDN searched, locationDN origin, double radiusMaximun) {

		double lat1 = searched.getLat();// 41.3879169;
		double lon1 = searched.getLon();// 2.1699187;
		double lat2 = origin.getLat();// 40.4167413;
		double lon2 = origin.getLon();// -3.7032498;

		double deg2radMultiplier = Math.PI / 180;
		
		
		double R = 6378.137; // Radio de la tierra en km
		double dLat = (lat2 - lat1) * deg2radMultiplier;
		double dLong = (lon2 - lon1) * deg2radMultiplier;

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1*deg2radMultiplier) * Math.cos(lat2*deg2radMultiplier) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;

		return d<radiusMaximun;
		//

//		double searchedLatitude = 0d;
//		double searchedLongitude = 0d;
//		double originLatitude = 0d;
//		double originLongitude = 0d;
//
//		// double deg2radMultiplier = Math.PI / 180;
//		double deg2radMultiplier = 1;
//
//		searchedLatitude = searched.getLat() * deg2radMultiplier;
//		searchedLongitude = searched.getLon() * deg2radMultiplier;
//
//		originLatitude = origin.getLat() * deg2radMultiplier;
//		originLongitude = origin.getLon() * deg2radMultiplier;
//
//		double radius = 6378.137; // earth mean radius defined by WGS84
//		double dlon = originLatitude - originLongitude;
//		double distance = Math.acos(Math.sin(searchedLatitude) * Math.sin(originLatitude) + Math.cos(searchedLatitude) * Math.cos(originLatitude) * Math.cos(dlon)) * radius;
//
//		return distance < radiusMaximun;
	}

	// Recupera la localización geográfica a partir de una coordenada.

}
