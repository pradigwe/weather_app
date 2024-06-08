//retrieve weather data from API - backend logic fetches latest weather data
//from the external API and returns it
//GUI displays data to user

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherApp {
    //fetches weather data for given location
    public static JSONObject getWeatherData(String locationName){
        //get location coordinates using geolocation API
        JSONArray locationData = getLocationData(locationName);

        // extract latitude and longitude
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        doublt longitude = (double) location.get("longitude");

        //build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" + 
                "latitude" + latitude + "&longitude" + longitude + 
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=America%FLos_Angles";
        
        try{
            // call api response
            HttpURLConnection conn = fetchApiResponse(urlString);

            //check for response status
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }
            //store resulting json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                //read and store into string builder
                resultJson.append(scanner.nextLine());
            }

            //close scanner
            scanner.close();

            //close url connection
            conn.disconnect();

            //parse through data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            //retrieve hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            // get current hour's data, so index of current hour is found
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);
        }

        return null;
    }

    public static JSONArray getLocationData(String locationName){
        //repalces any whitespace in location name to + for API ofrmat
        locationName = locationName.replaceAll(" ", "+");

        //build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + 
                locationName + "&count=10&language=en&format=json";
        try{
            // call api and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            //check response data (200 = successful connection)
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }
            else{
                //store API results
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                
                //read and store resulting data into strin builder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }

                //close scanner
                scanner.close();
                //close url connection
                conn.disconnect();

                //parse JSON string into JSON obj
                JSONParser parser = new JSONParser();
                JSONObject resulJsonObject = (JSONObject) parser.parse(String valueOf(resultJson));

                //get list of location data the API generated from location name
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //couldn't find location'
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //set request method to get
            conn.setRequestMethod("GET");

            //connect to our API
            conn.connect();
        }
        catch(IDException e){
            e.printStackTrace();
        }
    }

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();
    }

    private static String getCurrentTime(){
        //get current data nd time
        LocalDateTime currentDateTime = LocalDateTime.now();

        //format date to be year-day-month
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        //format and print current date and time
        String formattedDateTime = //TIME STAMP AT 31:20
    }
}
