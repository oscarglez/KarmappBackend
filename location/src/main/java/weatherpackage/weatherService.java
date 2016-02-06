package weatherpackage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import location.weather;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class weatherService {
	// public weather GetDatos (Date fecha) throws IOException {
	public weather GetDatos(DateTime fecha) throws IOException {
		// YahooWeatherService service = new YahooWeatherService();
		// Channel channel = service.getForecast("766273", DegreeUnit.CELSIUS);
		// System.out.println(channel);
		// System.out.println(channel.getAtmosphere());
		// System.out.println(channel.getLink());

		weather datosTemperatura = new weather();

		String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
		String query = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='Madrid') and u='c'";
		String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json";

		URL fullUrl = new URL(fullUrlStr);
		InputStream is = fullUrl.openStream();

		JSONTokener tok = new JSONTokener(is);
		JSONObject result = new JSONObject(tok);

		Date fechaActual = new Date();
		SimpleDateFormat fechaFormat = new SimpleDateFormat("dd MMM yyyy");
		String txtFechaActual = fechaFormat.format(fechaActual);
		// System.out.println(txtFechaActual);

		JSONArray predicion = result.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
		for (int i = 0; i < predicion.length(); i++) {
			String baja = predicion.getJSONObject(i).getString("low");
			String alta = predicion.getJSONObject(i).getString("high");
			String estado = predicion.getJSONObject(i).getString("text");
			String txtFechaJson = predicion.getJSONObject(i).getString("date");
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

			try {

				Date FechaJson = formatter.parse(txtFechaJson);
				String txtFechaJsonFin = formatter.format(FechaJson);

				if (txtFechaActual.equals(txtFechaJsonFin)) {
					// System.out.println("Temperatura máxima: "+alta);
					// System.out.println("Temperatura mínima: "+baja);
					// System.out.println("Estado: "+estado);
					if (estado.toLowerCase().contains("rain"))
						datosTemperatura.setForecastid(0);
					else if (estado.toLowerCase().contains("cloudy"))
						datosTemperatura.setForecastid(1);
					else if (estado.toLowerCase().contains("sunny"))
						datosTemperatura.setForecastid(2);
					else
						datosTemperatura.setForecastid(3);
					System.out.println(datosTemperatura.getForecastid());
					datosTemperatura.setMax(Integer.parseInt(alta));
					datosTemperatura.setMin(Integer.parseInt(baja));
					datosTemperatura.setForecast(estado);
				}

				return datosTemperatura;

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return datosTemperatura;
	}
}
