package com.empty.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.empty.global.Enum.DepartureOrArrival;
import com.empty.global.Enum.Occupancy;
import com.empty.global.Enum.Orientation;
import com.empty.global.Main.Helper;
import com.empty.global.Main.TimeOnly;
import com.empty.global.NMBS.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class NMBSData {
    private static HttpURLConnection connection;

    private String GetJsonString(String query) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                throw new Exception(responseContent.toString());
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return responseContent.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String PostOccupancy(String query, String stationId, String connectionId, String vehicleId, Occupancy occupancy, LocalDateTime dateTime) throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            URL url = new URL(query);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setRequestProperty ("connection", connectionId);
            connection.setRequestProperty ("from", stationId);
            connection.setRequestProperty ("date", dateTime.format(dateTimeFormatter));
            connection.setRequestProperty ("Ocp-Apim-Subscription-Key", vehicleId);
            connection.setRequestProperty ("Ocp-Apim-Subscription-Key", "http://api.irail.be/terms/" + occupancy.toString().toLowerCase());

            connection.setDoOutput(true);
            //connection.setRequestProperty ("Ocp-Apim-Subscription-Key", subscriptionKey);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                throw new Exception(responseContent.toString());
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return responseContent.toString();
    }


    public ArrayList<Station> GetStations() throws JSONException {
        String responseBody = GetJsonString("https://api.irail.be/stations/?format=json&lang=nl");

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray array = jsonObject.getJSONArray("station");

        return GetStationFromJsonArray(array);
    }

    private ArrayList<Station> GetStationFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Station> stations = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            int spacesToIndentEachLevel = 2;
            System.out.println(object.toString(spacesToIndentEachLevel));

            double locationY = object.getDouble("locationY");
            double locationX = object.getDouble("locationX");
            String standardName = object.getString("standardname");
            String name = object.getString("name");

            String id = object.getString("id");
            String apiId = object.getString("@id");

            Station station = new Station(locationY, locationX, standardName, name, id, apiId);
            stations.add(station);

        }
        return stations;
    }

    private Station GetStationFromJsonObject(JSONObject object) throws JSONException {
        int spacesToIndentEachLevel = 2;
        System.out.println(object.toString(spacesToIndentEachLevel));

        double locationY = object.getDouble("locationY");
        double locationX = object.getDouble("locationX");
        String standardName = object.getString("standardname");
        String name = object.getString("name");

        String id = object.getString("id");
        String apiId = object.getString("@id");

        return new Station(locationY, locationX, standardName, name, id, apiId);
    }

    private Vehicle GetVehicleFromJsonObject(JSONObject object) throws JSONException {
        String name = object.getString("name");
        String shortName = object.getString("shortname");
        String id = object.getString("@id");

        Vehicle vehicle = new Vehicle(name, shortName, 0, 0, id);

        if (object.has("locationX") && object.has("locationY")){
            double locationX = object.getDouble("locationX");
            double locationY = object.getDouble("locationY");
            vehicle.setLocationX(locationX);
            vehicle.setLocationY(locationY);
        }

        if (object.has("stops")){
            ArrayList<Stop> stops = GetStopsFromJsonArray(object.getJSONObject("stops").getJSONArray("stop"));
            vehicle.setStops(stops);
        }

        return vehicle;
    }

    private Platform GetPlatformFromJsonObject(JSONObject object) throws JSONException {
        String name = object.getString("name");
        String normal = object.getString("normal");
        return new Platform(name, normal);
    }

    private Departure GetDepartureFromJsonObject(JSONObject object) throws JSONException {
        int delay = Helper.FromSecondsToMinutes(object.getInt("delay"));
        String stationName = object.getString("station");
        Station station = GetStationFromJsonObject(object.getJSONObject("stationinfo"));
        Date time = new Date(object.getLong("time") * 1000);
        String vehicleName = object.getString("vehicle");
        Vehicle vehicle = GetVehicleFromJsonObject(object.getJSONObject("vehicleinfo"));
        int plarformNumber = object.getInt("platform");
        Platform platform = GetPlatformFromJsonObject(object.getJSONObject("platforminfo"));
        boolean left = Helper.IntToBool(object.getInt("left"));
        boolean canceled = Helper.IntToBool(object.getInt("canceled"));
        String direction = object.getJSONObject("direction").getString("name");
        ArrayList<Stop> stops = new ArrayList<>();
        if (object.has("stops"))
            stops = GetStopsFromJsonArray(object.getJSONObject("stops").getJSONArray("stop"));
        ArrayList<Alert> alters = new ArrayList<>();
        if (object.has("alerts"))
            alters = GetAlertsFromJsonArray(object.getJSONObject("alerts").getJSONArray("alert"));
        int waling = object.getInt("walking");
        String departureConnection = object.getString("departureConnection");

        return new Departure(delay, stationName, station, time, vehicleName, vehicle, plarformNumber, platform, left, canceled, direction, stops.size(), stops, alters, waling, departureConnection);
    }

    private ArrayList<Departure> GetDepartureFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Departure> departures = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            int delay = Helper.FromSecondsToMinutes(object.getInt("delay"));
            String stationName = object.getString("station");
            Station station = GetStationFromJsonObject(object.getJSONObject("stationinfo"));
            Date time = new Date(object.getLong("time") * 1000);
            String vehicleName = object.getString("vehicle");
            Vehicle vehicle = GetVehicleFromJsonObject(object.getJSONObject("vehicleinfo"));
            int plarformNumber = object.getInt("platform");
            Platform platform = GetPlatformFromJsonObject(object.getJSONObject("platforminfo"));
            boolean left = Helper.IntToBool(object.getInt("left"));
            boolean canceled = Helper.IntToBool(object.getInt("canceled"));
            String direction = "";
            if (object.has("direction"))
                direction = object.getJSONObject("direction").getString("name");
            ArrayList<Stop> stops = new ArrayList<>();
            if (object.has("stops"))
                stops = GetStopsFromJsonArray(object.getJSONObject("stops").getJSONArray("stop"));
            ArrayList<Alert> alters = new ArrayList<>();
            if (object.has("alerts"))
                alters = GetAlertsFromJsonArray(object.getJSONObject("alerts").getJSONArray("alert"));
            int walking = 0;
            if (object.has("walking"))
                walking = object.getInt("walking");
            String departureConnection = object.getString("departureConnection");

            Departure departure = new Departure(delay, stationName, station, time, vehicleName, vehicle, plarformNumber, platform, left, canceled, direction, stops.size(), stops, alters, walking, departureConnection);
            departures.add(departure);
        }

        return departures;
    }

    private Arrival GetArrivalFromJsonObject(JSONObject object) throws JSONException {
        int delay = Helper.FromSecondsToMinutes(object.getInt("delay"));;
        String stationName = object.getString("station");
        Station station = GetStationFromJsonObject(object.getJSONObject("stationinfo"));
        Date time = new Date(object.getLong("time") * 1000);
        String vehicleName = object.getString("vehicle");
        Vehicle vehicle = GetVehicleFromJsonObject(object.getJSONObject("vehicleinfo"));
        int plarformNumber = object.getInt("platform");
        Platform platform = GetPlatformFromJsonObject(object.getJSONObject("platforminfo"));
        boolean arrived = Helper.IntToBool(object.getInt("arrived"));
        boolean canceled = Helper.IntToBool(object.getInt("canceled"));
        int walking = object.getInt("walking");
        String direction = object.getJSONObject("direction").getString("name");

        return new Arrival(delay, stationName, station, time, vehicleName, vehicle, plarformNumber, platform, arrived, canceled, walking, direction);
    }

    private ArrayList<Arrival> GetArrivalFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Arrival> arrivals = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            int delay = Helper.FromSecondsToMinutes(object.getInt("delay"));;
            String stationName = object.getString("station");
            Station station = GetStationFromJsonObject(object.getJSONObject("stationinfo"));
            Date time = new Date(object.getLong("time") * 1000);
            String vehicleName = object.getString("vehicle");
            Vehicle vehicle = GetVehicleFromJsonObject(object.getJSONObject("vehicleinfo"));
            int plarformNumber = object.getInt("platform");
            Platform platform = GetPlatformFromJsonObject(object.getJSONObject("platforminfo"));
            boolean arrived = Helper.IntToBool(object.getInt("arrived"));
            boolean canceled = Helper.IntToBool(object.getInt("canceled"));
            int walking = object.getInt("walking");
            String direction = object.getJSONObject("direction").getString("name");

            Arrival arrival = new Arrival(delay, stationName, station, time, vehicleName, vehicle, plarformNumber, platform, arrived, canceled, walking, direction);
            arrivals.add(arrival);
        }
        return arrivals;
    }


    private ArrayList<Stop> GetStopsFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Stop> stops = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            int id = object.getInt("id");
            String stationName = object.getString("station");
            Station station = GetStationFromJsonObject(object.getJSONObject("stationinfo"));
            Date time = new Date();
            if (object.has("time"))
                time = new Date(object.getLong("time") * 1000);
            int delay = 0;
            if (object.has("delay"))
                delay = Helper.FromSecondsToMinutes(object.getInt("delay"));;
            boolean canceled = false;
            if (object.has("canceled"))
                canceled = Helper.IntToBool(object.getInt("canceled"));
            int departureDelay = Helper.FromSecondsToMinutes(object.getInt("departureDelay"));;
            boolean departureCanceled = Helper.IntToBool(object.getInt("departureCanceled"));
            Date scheduledDepartureTime = new Date(object.getLong("scheduledDepartureTime"));
            int arrivalDelay = Helper.FromSecondsToMinutes(object.getInt("arrivalDelay"));;
            boolean arrivalCanceled = Helper.IntToBool(object.getInt("arrivalCanceled"));
            boolean isExtraStop = Helper.IntToBool(object.getInt("isExtraStop"));
            Date scheduleArrivalTime = new Date(object.getLong("scheduledArrivalTime"));
            String departureConnection = "";
            if (object.has("departureConnection"))
                departureConnection = object.getString("departureConnection");

            Stop stop = new Stop(id, station, time, delay, null, canceled, departureDelay, departureCanceled, scheduledDepartureTime, arrivalDelay, 0, arrivalCanceled, isExtraStop, false, scheduleArrivalTime, departureConnection, "");
            stops.add(stop);
        }
        return stops;
    }

    private ArrayList<Alert> GetAlertsFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Alert> alerts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            int spacesToIndentEachLevel = 2;
            System.out.println(object.toString(spacesToIndentEachLevel));

            int id = object.getInt("id");
            String header = object.getString("header");
            String lead = object.getString("lead");
            String link = "";
            if (object.has("link"))
                link = object.getString("link");
            Date startTime = new Date(object.getLong("startTime"));
            Date endTime = new Date(object.getLong("endTime"));

            Alert alert = new Alert(id, header, lead, link, startTime, endTime);
            alerts.add(alert);

        }
        return alerts;
    }

    private ArrayList<Via> GetViasFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Via> vias = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            Arrival arrival = GetArrivalFromJsonObject(object.getJSONObject("arrival"));
            Departure departure = GetDepartureFromJsonObject(object.getJSONObject("departure"));
            TimeOnly timeBetween = TimeOnly.FromSecondsToTime(object.getInt("timeBetween"));
            String stationName = object.getString("station");
            Station station = GetStationFromJsonObject(object.getJSONObject("stationInfo"));
            String vehicle = object.getString("vehicle");
            String direction = object.getJSONObject("direction").getString("name");

            Via via = new Via(arrival, departure, timeBetween, stationName, station, vehicle, direction);
            vias.add(via);

        }

        return vias;
    }

    private ArrayList<Connection> GetConnectionFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Connection> connections = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            int spacesToIndentEachLevel = 2;
            System.out.println(object.toString(spacesToIndentEachLevel));

            int id = object.getInt("id");
            Departure departure = GetDepartureFromJsonObject(object.getJSONObject("departure"));
            Arrival arrival = GetArrivalFromJsonObject(object.getJSONObject("arrival"));
            TimeOnly duration = TimeOnly.FromSecondsToTime(object.getInt("duration"));
            ArrayList<Alert> alerts = GetAlertsFromJsonArray(object.getJSONObject("alerts").getJSONArray("alert"));

            Connection connection = new Connection(id, departure, arrival, duration, alerts);
            connections.add(connection);

        }
        return connections;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Disturbance> GetDisturbanceFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Disturbance> disturbances = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            int id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("description");
            String link = object.getString("link");
            String type = object.getString("type");
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(object.getLong("timestamp"), 0, ZoneOffset.ofHours(1));

            String attachment = "";
            if (object.has("attachment"))
                attachment = object.getString("attachment");

            Disturbance disturbance = new Disturbance(id, title, description, link, type, dateTime, attachment);
            disturbances.add(disturbance);
        }

        return disturbances;
    }

    public Material GetMaterialFromJsonObject(JSONObject object) throws JSONException {
        String parentType = object.getString("parent_type");
        String subType = object.getString("sub_type");
        Orientation orientation = Orientation.valueOf(object.getString("orientation"));

        return new Material(parentType, subType, orientation);
    }

    public ArrayList<Unit> GetUnitsFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Unit> units = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            int id = object.getInt("id");
            Material material = GetMaterialFromJsonObject(object.getJSONObject("materialType"));
            boolean hasToilets = Helper.StringToBool(object.getString("hasToilets"));
            boolean hasTables = Helper.StringToBool(object.getString("hasTables"));
            boolean hasSecondClassOutlets = Helper.StringToBool(object.getString("hasSecondClassOutlets"));
            boolean hasFirstClassOutlets = Helper.StringToBool(object.getString("hasFirstClassOutlets"));
            boolean hasHeating = Helper.StringToBool(object.getString("hasHeating"));
            boolean hasAirco = Helper.StringToBool(object.getString("hasAirco"));
            String materialNumber = object.getString("materialNumber");
            String tractionType = object.getString("tractionType");
            boolean canPassToNextUnit = Helper.StringToBool(object.getString("canPassToNextUnit"));
            int standingPlacesSecondClass = Integer.parseInt(object.getString("standingPlacesSecondClass"));
            int standingPlacesFirstClass = Integer.parseInt(object.getString("standingPlacesFirstClass"));
            int seatsCoupeSecondClass = Integer.parseInt(object.getString("seatsCoupeSecondClass"));
            int seatsCoupeFirstClass = Integer.parseInt(object.getString("seatsCoupeFirstClass"));
            int seatsSecondClass = Integer.parseInt(object.getString("seatsSecondClass"));
            int seatsFirstClass = Integer.parseInt(object.getString("seatsFirstClass"));
            int lengthInMeter = Integer.parseInt(object.getString("lengthInMeter"));
            boolean hasSemiAutomaticInteriorDoors = Helper.StringToBool(object.getString("hasSemiAutomaticInteriorDoors"));
            boolean hasLuggageSection = Helper.StringToBool(object.getString("hasLuggageSection"));
            String materialSubTypeName = object.getString("materialSubTypeName");
            int tractionPosition = Integer.parseInt(object.getString("tractionPosition"));
            boolean hasPrmSection = Helper.StringToBool(object.getString("hasPrmSection"));
            boolean hasPriorityPlaces = Helper.StringToBool(object.getString("hasPriorityPlaces"));
            boolean hasBikeSection = Helper.StringToBool(object.getString("hasBikeSection"));

            Unit unit = new Unit(id, material, hasToilets, hasTables, hasSecondClassOutlets, hasFirstClassOutlets, hasHeating, hasAirco, materialNumber, tractionType, canPassToNextUnit,
                    standingPlacesSecondClass, standingPlacesFirstClass, seatsCoupeSecondClass, seatsCoupeFirstClass, seatsSecondClass, seatsFirstClass, lengthInMeter,
                    hasSemiAutomaticInteriorDoors, hasLuggageSection, materialSubTypeName, tractionPosition, hasPrmSection, hasPriorityPlaces, hasBikeSection);
            units.add(unit);
        }

        return units;
    }

    public ArrayList<Segment> GetSegmentFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            int id = object.getInt("id");
            Station origin = GetStationFromJsonObject(object.getJSONObject("origin"));
            Station destination = GetStationFromJsonObject(object.getJSONObject("destination"));
            String source = object.getJSONObject("composition").getString("source");
            ArrayList<Unit> units = GetUnitsFromJsonArray(object.getJSONObject("composition").getJSONObject("units").getJSONArray("unit"));

            Segment segment = new Segment(id, origin, destination, source, units);
            segments.add(segment);
        }

        return segments;
    }

    public Composition GetCompositionFromJsonObject(JSONObject object) throws JSONException {
        ArrayList<Segment> segments = GetSegmentFromJsonArray(object.getJSONObject("composition").getJSONObject("segments").getJSONArray("segment"));
        return new Composition(segments);
    }

    public ArrayList<Connection> GetConnections(Station departure, Station arrival, LocalDateTime dateTime, DepartureOrArrival deporarr, int results) throws JSONException {
        String query = "https://api.irail.be/connections/?from=" + departure.getName() + "&to=" + arrival.getName() + "&date="
                + Helper.GetDate(dateTime) + "&time=" + Helper.GetTime(dateTime) +
                "&timesel=" + deporarr.toString().toLowerCase() + "&format=json&lang=nl&typeOfTransport=automatic&alerts=true&results=" + results;
        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray array = jsonObject.getJSONArray("connection");

        return GetConnectionFromJsonArray(array);
    }

    public ArrayList<Departure> GetLiveBoardDeparture(Station station, LocalDateTime dateTime) throws JSONException {
        String query = "https://api.irail.be/liveboard/?station=" + station.getName() + "&date=" + Helper.GetDate(dateTime) + "&time=" + Helper.GetTime(dateTime) +
                "&arrdep=departure&lang=nl&format=json&alerts=false";

        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray array = jsonObject.getJSONObject("departures").getJSONArray("departure");

        return GetDepartureFromJsonArray(array);
    }

    public ArrayList<Arrival> GetLiveBoardArrival(Station station, LocalDateTime dateTime) throws JSONException {
        String query = "https://api.irail.be/liveboard/?station=" + station.getName() + "&date=" + Helper.GetDate(dateTime) + "&time=" + Helper.GetTime(dateTime) +
                "&arrdep=arrival&lang=nl&format=json&alerts=false";

        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray array = jsonObject.getJSONObject("arrivals").getJSONArray("arrival");

        return GetArrivalFromJsonArray(array);
    }

    public Vehicle GetVehicle(String id, LocalDateTime dateTime) throws JSONException {
        String query = "https://api.irail.be/vehicle/?id=" + id + "&date=" + Helper.GetDate(dateTime) + "&format=json&lang=nl&alerts=false";
        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);

        return GetVehicleFromJsonObject(jsonObject);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Disturbance> GetDisturbance() throws JSONException {
        String query = "https://api.irail.be/disturbances/?format=json&lang=nl";
        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray array = jsonObject.getJSONArray("disturbance");

        return GetDisturbanceFromJsonArray(array);
    }

    public Composition GetComposition(String trainId) throws JSONException {
        String query = "https://api.irail.be/composition/?format=json&id='" + trainId + "'&data=''&lang=nl";

        String responseBody = GetJsonString(query);

        JSONObject jsonObject = new JSONObject(responseBody);

        return GetCompositionFromJsonObject(jsonObject);
    }

}