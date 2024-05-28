import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {
    public double getTemperatur(String city){
        try{
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            return (double) jsonObject.get("temperature");
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
    public String getTime(String city){
        try {
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            return (String) jsonObject.get("time");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public char getDay(String city){
        char dayOrNight = 'a';
        try {
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            long code =  (long) jsonObject.get("is_day");
            if(code == 0){
                dayOrNight = 'N';
            }else if(code == 1){
                dayOrNight = 'D';
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return dayOrNight;
    }
    public String getWeatherCondition(String city){
        String weatherCondition = null;
        try {
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            long code = (long) jsonObject.get("weathercode");
            if(code == 0){
                weatherCondition = "Clear";
            }else if(code > 0 && code <= 3){
                weatherCondition = "Partly cloudy";
            }else if(code >= 45 && code <= 48){
                weatherCondition = "Fog";
            }else if((code >= 51 && code <= 67) || (code >= 80 && code <= 82)){
                weatherCondition = "Rainy";
            }else if((code >= 71 && code <= 77) || (code >= 85 && code <= 86)){
                weatherCondition = "Snowy";
            }else if(code >= 95 && code <=99){
                weatherCondition = "Storm";
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return weatherCondition;
    }
    private JSONObject getWeatherData(JSONObject locationData) throws IOException, ParseException {
        double latitude = (double) locationData.get("latitude");
        double longitude = (double) locationData.get("longitude");

        URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m,weather_code&current_weather=true");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() != 200){
            System.out.println("Can not connect to API");
            return null;
        }
        return new JSONObject((JSONObject) parser(readApi(connection)).get("current_weather"));
    }
    private JSONObject getLocationData(String city) throws IOException, ParseException {
        city = city.replaceAll(" ", "+");
        URL url = new URL("https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() != 200){
            System.out.println("Can not connect to API");
            return null;
        }
        JSONArray locationData = (JSONArray) parser(readApi(connection)).get("results");

        return (JSONObject) locationData.get(0);
    }
    private String readApi(HttpURLConnection connection) throws IOException{
        StringBuilder response = new StringBuilder();
        Scanner sc = new Scanner(connection.getInputStream());

        while(sc.hasNext()){
            response.append(sc.nextLine());
        }
        sc.close();
        return response.toString();
    }
    private JSONObject parser(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        return new JSONObject((JSONObject) parser.parse(response));
    }
}
