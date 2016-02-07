package location;

import java.util.ArrayList;

public class program_location {

	public static void main(String[] args) {

		Manager manager = new Manager();
		String inputdatagson = "{\"date\":\"2016-02-07T07:12:23.304+01:00\",\"latitude\":40.4521419,\"longitude\":-3.6903855000000476,\"userID\":\"ogonzalez@futurespace.es\"}";
		String dataManagerEvent = manager.dataManager(inputdatagson);
		
		System.out.println(dataManagerEvent);



//"latitude\":40.4521419,\"longitude\":-3.6903855000000476
//
//
//
//40.4522
//-3.6813300000000027
		
//		locationDN searchedLocationDN = null;
//		ArrayList<locationDN> locationList = null;
//		double radiusMaximun = 5d;
//
//		searchedLocationDN = new locationDN();
//		searchedLocationDN.setLat(40.4521419);
//		searchedLocationDN.setLon(-3.6903855000000476);
//		searchedLocationDN.setDescription("comida en esquina del bernabeu");
//
//		locationList = new ArrayList<locationDN>();
//		locationDN loc = new locationDN();
//		loc.setLat(40.4522);
//		loc.setLon(-3.6813300000000027);
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
