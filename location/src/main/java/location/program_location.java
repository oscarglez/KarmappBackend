package location;

import java.util.ArrayList;

public class program_location {

	public static void main(String[] args) {

		Manager manager = new Manager();
		String inputdatagson = "{\"date\":\"2016-02-06T07:12:23.304+01:00\",\"latitude\":10.0,\"longitude\":20.0,\"userID\":\"ogonzalez@futurespace.es\"}";
		String dataManagerEvent = manager.dataManager(inputdatagson);
		
		System.out.println(dataManagerEvent);

//		locationDN searchedLocationDN = null;
//		ArrayList<locationDN> locationList = null;
//		double radiusMaximun = 5d;
//
//		searchedLocationDN = new locationDN();
//		searchedLocationDN.setLatitude(41.3879169);
//		searchedLocationDN.setLongitude(2.1699187);
//		searchedLocationDN.setDescription("roma");
//
//		locationList = new ArrayList<locationDN>();
//		locationDN loc = new locationDN();
//		loc.setLatitude(41.3879169);
//		loc.setLongitude(2.1699187);
//		loc.setDescription("madrid centro");
//		locationList.add(loc);
//
//		location_resolver locres = new location_resolver();
//		ArrayList<locationDN> positions = locres.resolveLocations(searchedLocationDN, locationList, radiusMaximun);
//
//		for (locationDN locationDN : positions) {
//			System.out.println(locationDN.getDescription());
//		}

	}

}
