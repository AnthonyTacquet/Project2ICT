package be.ikdoeict.aion2.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import be.ikdoeict.aion2.global.DeLijn.Api.*;
import be.ikdoeict.aion2.global.DeLijn.Normal.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import be.ikdoeict.aion2.global.DeLijn.Api.Entity;
import be.ikdoeict.aion2.global.DeLijn.Api.StopTimeApi;
import be.ikdoeict.aion2.global.DeLijn.Api.TripApi;
import be.ikdoeict.aion2.global.DeLijn.Normal.Agency;
import be.ikdoeict.aion2.global.DeLijn.Normal.CalendarDate;
import be.ikdoeict.aion2.global.DeLijn.Normal.FeedInfo;
import be.ikdoeict.aion2.global.DeLijn.Normal.Route;
import be.ikdoeict.aion2.global.DeLijn.Normal.Shape;
import be.ikdoeict.aion2.global.DeLijn.Normal.Stop;
import be.ikdoeict.aion2.global.DeLijn.Normal.StopTime;
import be.ikdoeict.aion2.global.DeLijn.Normal.Trip;

public class DeLijnData {
    private HttpURLConnection connection;
    private String dbConnectionString = "jdbc:mysql://con001.tacquet.be:60001/delijn?autoReconnect=true&useSSL=false&allowLoadLocalInfile=true";
    private String dbName = "delijn";
    private String user = "readonly"; //user_r
    private String pass = "qLSPwt3CpQFQ6346BTfc"; // User_rw2023*$

    // API

    private String GetJsonString(String query) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String subscriptionKey = "8b8887dd53fa4863910b7ac256c98838";

        try {
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
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

    private ArrayList<StopTimeApi> GetStopTimeFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<StopTimeApi> stopTimes = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            int stopCode = 0;
            if (object.has("stopCode"))
                stopCode = Integer.parseInt(object.getString("stopCode"));
            int stopId = 0;
            if (object.has("stopId"))
                stopId = Integer.parseInt(object.getString("stopId"));
            int departureDelay = object.getJSONObject("departure").getInt("delay");

            StopTimeApi stopTime = new StopTimeApi(stopCode, stopId, departureDelay);
            stopTimes.add(stopTime);
        }
        return stopTimes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private TripApi GetTripFromJsonObject(JSONObject object) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ArrayList<StopTimeApi> stopTime = new ArrayList<>();
        if (object.has("stopTimeUpdate"))
            stopTime = GetStopTimeFromJsonArray(object.getJSONArray("stopTimeUpdate"));

        int scheduleRelationShip;
        if (object.has("scheduleRelationship"))
            scheduleRelationShip = object.getInt("scheduleRelationship");

        LocalDateTime startDate;
        if (object.has("scheduleRelationship"))
            startDate = LocalDateTime.parse(object.getString("startDate"),formatter);

        int tripId = Integer.parseInt(object.getJSONObject("trip").getString("tripId"));

        int vehicle = 0;
        if (object.has("vehicle"))
            vehicle = Integer.parseInt(object.getJSONObject("vehicle").getString("id"));
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(object.getLong("timestamp"), 0, ZoneOffset.ofHours(1));

        return new TripApi(stopTime, tripId, vehicle, dateTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Entity> GetEntityFromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            TripApi trip = GetTripFromJsonObject(object.getJSONObject("tripUpdate"));
            long id = Long.parseLong(object.getString("id"));

            Entity entity = new Entity(trip, id);
            entities.add(entity);
        }
        return entities;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Entity> GetAllData() throws JSONException {
        String query = "https://api.delijn.be/gtfs/v2/realtime?json=true&delay=true&canceled=true&gtfsversion=2.0";
        String responseBody = GetJsonString(query);
        JSONObject jsonObject = new JSONObject(responseBody);
        //System.out.println(jsonObject.toString(2));

        return GetEntityFromJsonArray(jsonObject.getJSONArray("entity"));
    }

    public Entity GetDataEntity(long id) throws JSONException {
        String query = "https://api.delijn.be/gtfs/v2/realtime?json=true&delay=true&canceled=true&source=" + id + "&gtfsversion=2.0";
        String responseBody = GetJsonString(query);
        JSONObject jsonObject = new JSONObject(responseBody);
        //System.out.println(jsonObject.toString(2));
        return null;
    }

    // Read Database

    public ArrayList<Stop> GetStops(){
        ArrayList<Stop> stops = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".stops;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int stopId = resultSet.getInt("stop_id");
                int stopCode = resultSet.getInt("stop_code");
                String stopName = resultSet.getString("stop_name");
                String stopDesc = resultSet.getString("stop_desc");
                double stopLat = resultSet.getDouble("stop_lat");
                double stopLong = resultSet.getDouble("stop_long");
                int stopZoneId = resultSet.getInt("stop_zone_id");
                String stopUrl = resultSet.getString("stop_url");
                String locationType = resultSet.getString("location_type");
                String parentStation = resultSet.getString("parent_station");
                boolean wheelchair = resultSet.getBoolean("wheelchair_boarding");

                stops.add(new Stop(stopId, stopCode, stopName, stopDesc, stopLat, stopLong, stopZoneId, stopUrl, locationType, parentStation, wheelchair));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stops;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int GetDepartureDelay(int tripId, int stopId) throws JSONException {
        int delay = 0;
        List<Entity> entitys = GetAllData().stream().filter(e -> e.getTrip().getTripId() == tripId).collect(Collectors.toList());
        Optional<Entity> entityOpt = entitys.stream().filter(e -> e.getTrip().getStopTime().stream().filter(d -> d.getStopId() == stopId) != null).findFirst();
        Entity entity = new Entity();
        if (entityOpt.isPresent()){
            entity = entityOpt.get();
            Optional<StopTimeApi> stopTimeOpt = entity.getTrip().getStopTime().stream().filter(e -> e.getStopId() == stopId).findFirst();
            if (stopTimeOpt.isPresent()){
                StopTimeApi stopTimeApi = stopTimeOpt.get();
                delay = stopTimeApi.getDepartureDelay();
            }
        }
        return delay;
    }

    public Stop GetStop(int stopId){
        Stop stop = new Stop();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".stops WHERE stop_id = " + stopId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int stopCode = resultSet.getInt("stop_code");
            String stopName = resultSet.getString("stop_name");
            String stopDesc = resultSet.getString("stop_desc");
            double stopLat = resultSet.getDouble("stop_lat");
            double stopLong = resultSet.getDouble("stop_long");
            int stopZoneId = resultSet.getInt("stop_zone_id");
            String stopUrl = resultSet.getString("stop_url");
            String locationType = resultSet.getString("location_type");
            String parentStation = resultSet.getString("parent_station");
            boolean wheelchair = resultSet.getBoolean("wheelchair_boarding");

            stop = new Stop(stopId, stopCode, stopName, stopDesc, stopLat, stopLong, stopZoneId, stopUrl, locationType, parentStation, wheelchair);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stop;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<StopTime> GetStopTimes(LocalDate date){
        ArrayList<StopTime> stopTimes = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".stop_times;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int tripId = resultSet.getInt("trip_id");
                LocalTime arrivalTime = LocalTime.parse(resultSet.getTime("arrival_time").toString());
                LocalTime departureTime = LocalTime.parse(resultSet.getTime("departure_time").toString());
                int stopId = resultSet.getInt("stop_id");
                int stopSequence = resultSet.getInt("stop_sequence");
                int stopHeadsign = resultSet.getInt("stop_headsign");
                int pickUpType = resultSet.getInt("pick_up_type");
                int dropOffType = resultSet.getInt("drop_off_type");
                String shapeDistTravelled = resultSet.getString("shape_dist_travelled");
                int delay = GetDepartureDelay(tripId, stopId);

                Stop stop = GetStop(stopId);
                Trip trip = GetTrip(tripId, date);

                StopTime newStopTime = new StopTime(tripId, stopId, delay, arrivalTime, departureTime, stopSequence, stopHeadsign, pickUpType, dropOffType, shapeDistTravelled);
                newStopTime.setStop(stop);
                newStopTime.setTrip(trip);
                //stopTimes.add();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stopTimes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StopTime GetStopTime(int tripId, int stopId, LocalDate date){
        StopTime stopTime = new StopTime();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".stop_times WHERE stop_id = " + stopId + " AND trip_id = " + tripId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            LocalTime arrivalTime = LocalTime.parse(resultSet.getTime("arrival_time").toString());
            LocalTime departureTime = LocalTime.parse(resultSet.getTime("departure_time").toString());
            int stopSequence = resultSet.getInt("stop_sequence");
            int stopHeadsign = resultSet.getInt("stop_headsign");
            int pickUpType = resultSet.getInt("pick_up_type");
            int dropOffType = resultSet.getInt("drop_off_type");
            String shapeDistTravelled = resultSet.getString("shape_dist_travelled");
            int delay = GetDepartureDelay(tripId, stopId);

            Stop stop = GetStop(stopId);
            Trip trip = GetTrip(tripId, date);

            StopTime newStopTime = new StopTime(tripId, stopId, delay, arrivalTime, departureTime, stopSequence, stopHeadsign, pickUpType, dropOffType, shapeDistTravelled);
            newStopTime.setStop(stop);
            newStopTime.setTrip(trip);
            //stopTimes.add();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stopTime;
    }

    public ArrayList<Trip> GetTrips(){
        ArrayList<Trip> trips = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".trips;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int tripId = resultSet.getInt("trip_id");
                int routeId = resultSet.getInt("route_id");
                int serviceId = resultSet.getInt("service_id");
                String tripHeadsign = resultSet.getString("trip_headsign");
                String tripShortName = resultSet.getString("trip_short_name");
                int directionId = resultSet.getInt("direction_id");
                int blockId = resultSet.getInt("block_id");
                int shape_id = resultSet.getInt("shape_id");

                trips.add(new Trip(tripId, routeId, serviceId, tripHeadsign, tripShortName, directionId, blockId, shape_id));

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return trips;
    }

    public Trip GetTrip(int tripId, LocalDate date){
        Trip trip = new Trip();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".trips WHERE trip_id = " + tripId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int routeId = resultSet.getInt("route_id");
            int serviceId = resultSet.getInt("service_id");
            String tripHeadsign = resultSet.getString("trip_headsign");
            String tripShortName = resultSet.getString("trip_short_name");
            int directionId = resultSet.getInt("direction_id");
            int blockId = resultSet.getInt("block_id");
            int shape_id = resultSet.getInt("shape_id");

            Route route = GetRoute(routeId);
            Shape shape = GetShape(shape_id);
            CalendarDate calendarDate = GetCalendarDate(serviceId, date);

            trip = new Trip(tripId, routeId, serviceId, tripHeadsign, tripShortName, directionId, blockId, shape_id);
            trip.setShape(shape);
            trip.setRoute(route);
            trip.setCalendarDate(calendarDate);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return trip;
    }

    public ArrayList<Shape> GetShapes(){
        ArrayList<Shape> shapes = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".shapes;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int shapeId = resultSet.getInt("shape_id");
                double shapeLat = resultSet.getDouble("shape_pt_lat");
                double shapeLong = resultSet.getDouble("shape_pt_long");
                int shapeSequence = resultSet.getInt("shape_pt_sequence");
                double shapeDistTravelled = resultSet.getDouble("shape_dist_travelled");

                shapes.add(new Shape(shapeId, shapeLat, shapeLong, shapeSequence, shapeDistTravelled));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return shapes;
    }

    public Shape GetShape(int shapeId){
        Shape shape = new Shape();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".shapes WHERE shape_id = " + shapeId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            double shapeLat = resultSet.getDouble("shape_pt_lat");
            double shapeLong = resultSet.getDouble("shape_pt_long");
            int shapeSequence = resultSet.getInt("shape_pt_sequence");
            double shapeDistTravelled = resultSet.getDouble("shape_dist_travelled");

            shape = new Shape(shapeId, shapeLat, shapeLong, shapeSequence, shapeDistTravelled);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return shape;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<CalendarDate> GetCalendarDates(){
        ArrayList<CalendarDate> calendarDates = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".calendar_dates;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int serviceId = resultSet.getInt("service_id");
                LocalDate date = LocalDate.parse(resultSet.getDate("date").toString());
                int exceptionType = resultSet.getInt("exception_type");

                calendarDates.add(new CalendarDate(serviceId, date, exceptionType));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return calendarDates;
    }

    public CalendarDate GetCalendarDate(int serviceId, LocalDate date){
        CalendarDate calendarDate = new CalendarDate();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".calendar_dates WHERE service_id = " + serviceId + " AND date = \'" + date.toString() +"\';";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            int exceptionType = resultSet.getInt("exception_type");

            calendarDate = new CalendarDate(serviceId, date, exceptionType);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return calendarDate;
    }

    public ArrayList<Route> GetRoutes(){
        ArrayList<Route> routes = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".routes;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int routeId = resultSet.getInt("route_id");
                int agencyId = resultSet.getInt("agency_id");
                String routeShortName = resultSet.getString("route_short_name");
                String routeLongName = resultSet.getString("route_long_name");
                String routeDesc = resultSet.getString("route_desc");
                int routeType = resultSet.getInt("route_type");
                String routeUrl = resultSet.getString("route_url");
                String routeColor = resultSet.getString("route_color");
                String routeTextColor = resultSet.getString("route_text_color");

                Route route = new Route(routeId, agencyId, routeShortName, routeLongName, routeDesc, routeType, routeUrl, routeColor, routeTextColor);
                route.setAgency(GetAgency(agencyId));
                routes.add(route);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return routes;
    }

    public Route GetRoute(int routeId){
        Route route = new Route();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".routes WHERE route_id = " + routeId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            int agencyId = resultSet.getInt("agency_id");
            String routeShortName = resultSet.getString("route_short_name");
            String routeLongName = resultSet.getString("route_long_name");
            String routeDesc = resultSet.getString("route_desc");
            int routeType = resultSet.getInt("route_type");
            String routeUrl = resultSet.getString("route_url");
            String routeColor = resultSet.getString("route_color");
            String routeTextColor = resultSet.getString("route_text_color");

            route = new Route(routeId, agencyId, routeShortName, routeLongName, routeDesc, routeType, routeUrl, routeColor, routeTextColor);
            route.setAgency(GetAgency(agencyId));

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return route;
    }

    public ArrayList<Agency> GetAgencies(){
        ArrayList<Agency> agencies = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".agencies;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int agencyId = resultSet.getInt("agency_id");
                String agencyName = resultSet.getString("agency_name");
                String agencyUrl = resultSet.getString("agency_url");
                String agencyTimezone = resultSet.getString("agency_timezone");
                String agencyLang = resultSet.getString("agency_lang");
                String agencyPhone = resultSet.getString("agency_phone");

                agencies.add(new Agency(agencyId, agencyName, agencyUrl, agencyLang, agencyTimezone, agencyPhone));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return agencies;
    }

    public Agency GetAgency(int agencyId){
        Agency agency = new Agency();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".agencies WHERE agency_id = " + agencyId + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            String agencyName = resultSet.getString("agency_name");
            String agencyUrl = resultSet.getString("agency_url");
            String agencyTimezone = resultSet.getString("agency_timezone");
            String agencyLang = resultSet.getString("agency_lang");
            String agencyPhone = resultSet.getString("agency_phone");

            agency = new Agency(agencyId, agencyName, agencyUrl, agencyLang, agencyTimezone, agencyPhone);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return agency;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<FeedInfo> GetAllFeedInfo(){
        ArrayList<FeedInfo> feedInfos = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".feed_info;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String feedName = resultSet.getString("feed_publisher_name");
                String feedUrl = resultSet.getString("feed_publisher_url");
                String feedLang = resultSet.getString("feed_lang");
                LocalDate startDate = LocalDate.parse(resultSet.getDate("feed_start_date").toString());
                LocalDate endDate = LocalDate.parse(resultSet.getDate("feed_end_date").toString());
                String feedVersion = resultSet.getString("feed_version");
                String feedMail = resultSet.getString("feed_contact_mail");

                feedInfos.add(new FeedInfo(feedName, feedUrl, feedLang, startDate, endDate, feedVersion, feedMail));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return feedInfos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedInfo GetFeedInfo(String name){
        FeedInfo feedInfo = new FeedInfo();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbConnectionString,user,pass);

            String query = "SELECT * FROM " + dbName + ".feed_info WHERE feed_publisher_name = " + name + ";";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            String feedUrl = resultSet.getString("feed_publisher_url");
            String feedLang = resultSet.getString("feed_lang");
            LocalDate startDate = LocalDate.parse(resultSet.getDate("feed_start_date").toString());
            LocalDate endDate = LocalDate.parse(resultSet.getDate("feed_end_date").toString());
            String feedVersion = resultSet.getString("feed_version");
            String feedMail = resultSet.getString("feed_contact_mail");

            feedInfo = new FeedInfo(name, feedUrl, feedLang, startDate, endDate, feedVersion, feedMail);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return feedInfo;
    }

}
