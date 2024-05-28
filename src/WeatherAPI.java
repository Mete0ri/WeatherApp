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
    public int getDay(String city){
        try {
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            return (int) jsonObject.get("is_day");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return 3;
    }
    public String getWeatherCondition(String city){
        String weatherCondition = null;
        try {
            JSONObject jsonObject = getWeatherData(getLocationData(city));
            int weather = (int) jsonObject.get("weathercode");
            if(weather == 0){
                weatherCondition = "Clear";
            }else if(weather > 0 && weather <= 3){
                weatherCondition = "Partly cloudy";
            }else if(weather >= 45 && weather <= 48){
                weatherCondition = "Fog";
            }else if((weather >= 51 && weather <= 67) || (weather >= 80 && weather <= 82)){
                weatherCondition = "Rainy";
            }else if((weather >= 71 && weather <= 77) || (weather >= 85 && weather <= 86)){
                weatherCondition = "Snowy";
            }else if(weather >= 95 && weather <=99){
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

        if(locationData != null && !locationData.isEmpty()){
            return (JSONObject) locationData.get(0);
        }
        return null;
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
