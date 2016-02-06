package location;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.joda.time.DateTime;

import weatherpackage.weatherService;

import com.owlike.genson.Genson;
import com.owlike.genson.internal.asm.Type;

public class Manager {
	private cache parkingsCacheDN =null;
	private cache electricChargerCacheDN = null;
	private ArrayList<eventDataSet> eventsCache = null;
	private double radius = 5d;

	public Manager() {
		// Carga la caché de parkings
		parkingsLoad();
		// Carga de las estacioens eléctricas de recarga
		electricChargerLoad();
		// Carga de los eventos
		eventsLoad();
	}

	// Recibe la información pasada por la app, y genera la información a
	// devolver para el evento
	public String dataManager(String indata) {
		Genson genson = new Genson();
		// Recupero la info que me llega
		indataDN id2 = new indataDN();

		id2 = genson.deserialize(indata, indataDN.class);

		// A partir de los datos recibidos se ejecutan las consultas cargando el
		// obj de resultados y se devuelve el json correspondiente
		karmapp_data kd = new karmapp_data();
		thermometer thermometer = new thermometer();

		// Recuperamos los datos del tiempo
		kd.setWeather(this.getWeather(id2.getEventDate()));

		// Recuperamos los datos de parkings
		parkings[] recoveredParkings = this.getParkings(id2.getLongitude(), id2.getLatitude());
		kd.setParkings(recoveredParkings);
		thermometer.setParkings(recoveredParkings.length);

		// Recuperamos las estaciones de recarga
		electricCharger[] recoverRecharger = this.getElectricCharger(id2.getLongitude(), id2.getLatitude());
		kd.setElectricCharger(recoverRecharger);
		thermometer.setElectricChargers(recoverRecharger.length);

		// Recupero el listado de eventos
		events[] events = this.getEvents(id2.getLongitude(), id2.getLatitude(), id2.getEventDate());
		kd.setEvents(events);
		thermometer.setEvents(events.length);

		thermometer.setAlergies(0);
		thermometer.setPollution(0);

		kd.setThermometer(thermometer);

		// Construido el objeto se pasa a formato json y se sevuelve
		String kdToString = genson.serialize(kd);
		return kdToString;

		// indataDN id = new indataDN();
		// id.setdate(DateTime.now().toString());
		// id.setLatitude(10d);
		// id.setLongitude(20d);
		// id.setUserID("ogonzalez@futurespace.es");

		// Recupero del listado de pares de fechas eventos el listado de eventos
		// que hay registrados para la fecha
		// Genson genson = new Genson();
		// String inputDataString = genson.serialize(id);

		// String inputdatagson =
		// "{\"date\":\"2016-02-06T07:12:23.304+01:00\",\"latitude\":10.0,\"longitude\":20.0,\"userID\":\"ogonzalez@futurespace.es\"}";

		// indataDN id2 = new indataDN();

		// id2 = genson.deserialize(indata, indataDN.class);
		// System.out.println(id.getLatitude());
		// System.out.println(id.getLongitude());
		// System.out.println(id.getdate());
		// System.out.println(id.getEventDate().toString());
		// System.out.println(id.getUserID());
		//
		// System.out.println(inputDataString);
		// return null;

	}

	private void eventsLoad() {
		eventsCache = new ArrayList<eventDataSet>();
	

	}

	private void electricChargerLoad() {
		electricChargerCacheDN = new cache();
		electricChargerCacheDN.setCache(new ArrayList<locationDN>());

	}

	private void parkingsLoad() {
		parkingsCacheDN = new cache();
		parkingsCacheDN.setCache(new ArrayList<locationDN>());
	}

	private events[] getEvents(double longitude, double latitude, DateTime eventDate) {
		// Busco en la caché de eventos el día correspondiente, y recupero la
		// localización de los eventos
		ArrayList<events> eventsList = new ArrayList<events>();
		// Recorro la caché de eventos buscando el día solicitado
		for (eventDataSet eventData : eventsCache) {
			if (eventData.getEventTime().toDate().equals(eventDate.toDate())) {
				ArrayList<locationDN> eventLocationList = this.getPositions(longitude, latitude, eventData.getEventsByDay());
				if (eventLocationList != null && eventLocationList.size() > 0) {
					int position = 0;
					for (locationDN locationDN : eventLocationList) {
						eventsList.add(new events(position, locationDN.getDescription(), locationDN.getLatitude(), locationDN.getLongitude()));
						position++;
					}
				}
				break;
			}
		}
		
		events[] eva= new events[eventsList.size()] ;
		eventsList.toArray(eva);
		return eva;
		
	}

	private ArrayList<locationDN> getPositions(double longitude, double latitude, ArrayList<locationDN> comparedLocation) {
		location_resolver lr = new location_resolver();
		locationDN searchedLocation = new locationDN();
		searchedLocation.setLongitude(longitude);
		searchedLocation.setLatitude(latitude);

		ArrayList<locationDN> locationList = lr.resolveLocations(searchedLocation, comparedLocation, radius);
		return locationList;
	}

	private electricCharger[] getElectricCharger(double longitude, double latitude) {
		ArrayList<electricCharger> ecs = new ArrayList<electricCharger>();

		// Utilizando la caché de estaciones de recarga, recupero el listado de
		// estaciones de recarga que
		// hay en la zona

		ArrayList<locationDN> electrigChargerLocationList = this.getPositions(longitude, latitude, electricChargerCacheDN.getCache());
		if (electrigChargerLocationList != null && electrigChargerLocationList.size() > 0) {
			int position = 0;
			for (locationDN locationDN : electrigChargerLocationList) {
				ecs.add(new electricCharger(position, locationDN.getDescription(), locationDN.getLatitude(), locationDN.getLongitude()));
				position++;
			}
		}

		electricCharger[] eca= new electricCharger[ecs.size()] ;
		ecs.toArray(eca);
		return eca;		

	}

	private parkings[] getParkings(double longitude, double latitude) {

		ArrayList<parkings> parks = new ArrayList<parkings>();
		// Utilizando la caché de parkings, recupero el listado de parkings que
		// hay en la zona

		ArrayList<locationDN> parkingsLocationList = this.getPositions(longitude, latitude, parkingsCacheDN.getCache());
		if (parkingsLocationList != null && parkingsLocationList.size() > 0) {
			int position = 0;
			for (locationDN locationDN : parkingsLocationList) {
				parks.add(new parkings(position, locationDN.getDescription(), locationDN.getLatitude(), locationDN.getLongitude()));
				position++;
			}
		}
		parkings[] pka= new parkings[parks.size()] ;
		parks.toArray(pka);
		return pka;
	}

	private weather getWeather(DateTime weatherDate) {
		weather weather = new weather();
		weatherService weatherService = new weatherService();
		try {
			weather = weatherService.GetDatos(weatherDate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weather;

	}

}
