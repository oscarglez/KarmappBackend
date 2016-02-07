package location.reverse_geolocation;

/**
 * Created by user on 07/02/2016.
 */
public class caca {



    Map<String, Double> coords;
    coords = OpenStreetMapUtils.getInstance().getCoordinates("madrid");
    System.out.println("latitude"+ coords.get("lat"));
    System.out.println("longitude"+ coords.get("lon"));




    <!--FOR REVERSE GEOLOCATION-->
    <dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.15</version>
    </dependency>
    <dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1.1</version>
    </dependency>




}
