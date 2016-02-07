package location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;

import weatherpackage.weatherService;

import com.owlike.genson.Genson;
import com.owlike.genson.internal.asm.Type;

public class Manager {
	private cache parkingsCacheDN = null;
	private cache electricChargerCacheDN = null;
	private ArrayList<eventDataSet> eventsCache = null;
	private double radius = 1d;

	private String parkingsFile = "/home/future/hackaton/location/src/main/resources/202625-0-aparcamientos-publicos.xml";
	private String parkings2File = "/home/future/hackaton/location/src/main/resources/202584-0-aparcamientos-residentes.xml";
	private String electricFile = "/home/future/hackaton/location/src/main/resources/electric.json";
	private String eventsFile = "/home/future/hackaton/location/src/main/resources/212504-0-agenda-actividades-deportes.xml";

	public Manager() {
		// Carga la caché de parkings

		try {
			parkingsLoad();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Carga de las estacioens eléctricas de recarga
		try {
			//electricChargerLoad();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Carga de los eventos
		try {
			eventsLoad();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Manager(String pPublicos, String pResidentes, String pElectricos, String deportes) {
		this.parkingsFile = pPublicos;
		this.parkings2File = pResidentes;
		this.electricFile = pElectricos;
		this.eventsFile = deportes;

		// Carga la caché de parkings

		try {
			parkingsLoad();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Carga de las estacioens eléctricas de recarga
		try {
			electricChargerLoad();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Carga de los eventos
		try {
			eventsLoad();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String dataManager(indataDN id2) {
		Genson genson = new Genson();

		karmapp_data kd = execute(id2);

		// Construido el objeto se pasa a formato json y se sevuelve
		String kdToString = genson.serialize(kd);
		return kdToString;

	}

	// Recibe la información pasada por la app, y genera la información a
	// devolver para el evento
	public String dataManager(String indata) {
		Genson genson = new Genson();
		// Recupero la info que me llega
		indataDN id2 = new indataDN();

		id2 = genson.deserialize(indata, indataDN.class);
		return dataManager(id2);
	}
	
	public karmapp_data execute(indataDN id2) {

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
		
		return kd;
	}

	private void eventsLoad() throws DocumentException {
		eventsCache = new ArrayList<eventDataSet>();

		File fXmlFile = new File(eventsFile);

		SAXReader reader = new SAXReader();
		// reader.read(fXmlFile)
		Document document = reader.read(fXmlFile);
		List nodeList = document.selectNodes("//Contenidos/contenido/atributos");
		for (Iterator iter = nodeList.iterator(); iter.hasNext();) {
			Node node = (Node) iter.next();
			//Recupero el evento
			eventData eventData = getEventsData(node);			
			//				workingCache.getCache().add((locationDN) parkingData);
			//Busco si por fecha ya hay eventos,
			Boolean isAppended = false;
			for (eventDataSet eventDataSet : eventsCache) {
				if(eventDataSet.equals(eventData.getEventTime())){
					eventDataSet.getEventsByDay().add(eventData.getEventLocation());
					isAppended = true;
				}
			}
			if(!isAppended){
				eventsCache.add(new eventDataSet(eventData.getEventTime(),eventData.getEventLocation()));
			}
		}

	}

	private eventData getEventsData(Node node) {
		List attributesList = node.selectNodes("./atributo/@nombre");
		eventData eventData = new eventData();
		for (Iterator iterAttributes = attributesList.iterator(); iterAttributes.hasNext();) {
			Node nodeid = (Node) iterAttributes.next();
			if (nodeid.getStringValue().equals("ID-EVENTO")) {
				int eventid = Integer.parseInt(nodeid.getParent().getData().toString());
				eventData.getEventLocation().setId(eventid);
			} else if (nodeid.getStringValue().equals("TITULO")) {
				eventData.getEventLocation().setDescription(nodeid.getParent().getData().toString());
			} else if (nodeid.getStringValue().equals("FECHA-EVENTO")) {
				eventData.setEventTime(new DateTime(nodeid.getParent().getData().toString().substring(0,10)));
			} else if (nodeid.getStringValue().equals("LOCALIZACION")) {
				List locationList = nodeid.getParent().selectNodes("./atributo/@nombre");
				for (Iterator iterlocation = locationList.iterator(); iterlocation.hasNext();) {
					Node nodelocation = (Node) iterlocation.next();
					if (nodelocation.getStringValue().equals("LATITUD")) {
						eventData.getEventLocation().setLat(Double.parseDouble(nodelocation.getParent().getData().toString()));
					} else if (nodelocation.getStringValue().equals("LONGITUD")) {
						eventData.getEventLocation().setLon(Double.parseDouble(nodelocation.getParent().getData().toString()));
					}
				}
			}

		}
		return eventData;
	}






	private void electricChargerLoad() throws IOException {

		Genson genson = new Genson();

		// Abro el expediente y comienzo la lectura
		InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;

		inputStream = new FileInputStream(electricFile);

		isr = new InputStreamReader(inputStream, "UTF-8");

		reader = new BufferedReader(isr);
		String line = reader.readLine();

		locationDN[] loca2 = genson.deserialize(line, locationDN[].class);

		electricChargerCacheDN = new cache();
		ArrayList<locationDN> ec = new ArrayList<locationDN>();
		for (locationDN locationDN : loca2) {
			ec.add(locationDN);
		}
		electricChargerCacheDN.setCache(ec);	
	}

	private void parkingsLoad() throws DocumentException {
		parkingsCacheDN = new cache();
		parkingsCacheDN.setCache(new ArrayList<locationDN>());

		processFile(parkingsCacheDN, parkingsFile);
		processFile(parkingsCacheDN, parkings2File);		
	}

	private void processFile(cache workingCache, String workingFile) throws DocumentException {
		File fXmlFile = new File(workingFile);

		SAXReader reader = new SAXReader();
		// reader.read(fXmlFile)
		Document document = reader.read(fXmlFile);
		List nodeList = document.selectNodes("//Contenidos/contenido/atributos");
		for (Iterator iter = nodeList.iterator(); iter.hasNext();) {
			Node node = (Node) iter.next();
			parkings parkingData = getParkingData(node);
			if (!isInCache(parkingData, workingCache)) {
				workingCache.getCache().add((locationDN) parkingData);
			}
		}
	}

	private Boolean isInCache(locationDN pd, cache localCache) {
		Boolean isIn = false;
		for (locationDN loc : localCache.getCache()) {
			if (loc.getId() == pd.getId()) {
				isIn = true;
			}
			if (isIn) {
				break;
			}
		}
		return isIn;
	}

	private parkings getParkingData(Node node) {
		List attributesList = node.selectNodes("./atributo/@nombre");
		parkings parkingData = new parkings();
		for (Iterator iterAttributes = attributesList.iterator(); iterAttributes.hasNext();) {
			Node nodeid = (Node) iterAttributes.next();
			if (nodeid.getStringValue().equals("ID-ENTIDAD")) {
				int parkingid = Integer.parseInt(nodeid.getParent().getData().toString());
				parkingData.setId(parkingid);
			} else if (nodeid.getStringValue().equals("NOMBRE")) {
				parkingData.setDescription(nodeid.getParent().getData().toString());
			} else if (nodeid.getStringValue().equals("ACCESIBILIDAD")) {
				parkingData.setAccesibilidad(Integer.parseInt(nodeid.getParent().getData().toString()));
			} else if (nodeid.getStringValue().equals("LOCALIZACION")) {
				List locationList = nodeid.getParent().selectNodes("./atributo/@nombre");
				for (Iterator iterlocation = locationList.iterator(); iterlocation.hasNext();) {
					Node nodelocation = (Node) iterlocation.next();
					if (nodelocation.getStringValue().equals("LATITUD")) {
						parkingData.setLat(Double.parseDouble(nodelocation.getParent().getData().toString()));
					} else if (nodelocation.getStringValue().equals("LONGITUD")) {
						parkingData.setLon(Double.parseDouble(nodelocation.getParent().getData().toString()));
					}
				}
			}

		}
		return parkingData;
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
						eventsList.add(new events(position, locationDN.getDescription(), locationDN.getLat(), locationDN.getLon()));
						position++;
					}
				}
				break;
			}
		}

		events[] eva = new events[eventsList.size()];
		eventsList.toArray(eva);
		return eva;

	}

	private ArrayList<locationDN> getPositions(double longitude, double latitude, ArrayList<locationDN> comparedLocation) {
		location_resolver lr = new location_resolver();
		locationDN searchedLocation = new locationDN();
		searchedLocation.setLon(longitude);
		searchedLocation.setLat(latitude);

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
				ecs.add(new electricCharger(position, locationDN.getDescription(), locationDN.getLat(), locationDN.getLon()));
				position++;
			}
		}

		electricCharger[] eca = new electricCharger[ecs.size()];
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
				parks.add(new parkings(position, locationDN.getDescription(), locationDN.getLat(), locationDN.getLon()));
				position++;
			}
		}
		parkings[] pka = new parkings[parks.size()];
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
