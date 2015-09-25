package skypebot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on May 17, 2015
 */
public class Weather {
    
    public static String getWeather(String location, String api) {
        try {
            String query;
            String tname;
            String tcountry = "";
            if (location.contains(",")) {
                tname = location.substring(0, location.indexOf(","));
                tcountry = location.substring(location.indexOf(","), location.length());
            } else {
                tname = location;
            }
            
            try {
                query = "zip=" + Integer.parseInt(tname) + tcountry;
            } catch (NumberFormatException e) {
                query = "q=" + location;
            }
            
            System.out.println("Grabbing weather @ " + query);
            
            // create the connection
            HttpURLConnection con = (HttpURLConnection) new URL("http://api.openweathermap.org/data/2.5/weather?" + query
                    + "&APIID=" + api + "&mode=json&units=imperial").openConnection();
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            
            // read input
            StringBuilder re = new StringBuilder();
            int i;
            while ((i = in.read()) != -1) {
                re.append((char) i);
            }
            
            // parse input
            Gson gson = new Gson();
            JsonObject o = gson.fromJson(re.toString(), JsonObject.class);
            JsonObject cord = o.getAsJsonObject("coord");
            JsonObject sys = o.getAsJsonObject("sys");
            JsonObject weather = o.getAsJsonArray("weather").get(0).getAsJsonObject();
            JsonObject main = o.getAsJsonObject("main");
            
            // extract data
            double lat = cord.get("lat").getAsDouble();
            double lon = cord.get("lon").getAsDouble();
            
            String country = sys.get("country").getAsString();
            
            Date sunrise = new Date(1000 * sys.get("sunrise").getAsLong());
            Date sunset = new Date(1000 * sys.get("sunset").getAsLong());
            SimpleDateFormat time = new SimpleDateFormat("h:mm a");
            
            String weatherDescription = weather.get("description").getAsString();
            
            double temp = main.get("temp").getAsDouble();
            int humidity = main.get("humidity").getAsInt();
            
            //String mapsUrl = "http://maps.google.com/maps?z=12&t=m&q=loc:" + lat + "+" + lon;
            
            String name = o.get("name").getAsString();
            
            // build report
            StringBuilder report = new StringBuilder();
            report.append("Weather for " + name + ", " + country).append("\n");
            report.append("Temp: ").append(temp).append("°F\n");
            report.append("Humidity: ").append(humidity).append("%\n");
            report.append("Sunrise: ").append(time.format(sunrise)).append("\n");
            report.append("Sunset: ").append(time.format(sunset)).append("\n");
            report.append("Description: ").append(weatherDescription).append("\n");
            report.append("Geo data:\n");
            //report.append("  Maps url: " + Chat.link(mapsUrl) + "\n");
            report.append("  Cords: lat=").append(lat).append(" lon=").append(lon).append("\n");
            report.append("  Country: ").append(country).append("\n");
            return report.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error has occurred while getting weather data for " + location + " " + e.getMessage();
        }
    }
}
