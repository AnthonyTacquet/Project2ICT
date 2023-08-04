package com.empty.logica;

import com.empty.api.NMBSData;
import com.empty.global.Enum.DepartureOrArrival;
import com.empty.global.NMBS.*;

import org.json.JSONException;

import java.net.ConnectException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainLogic{

    private ArrayList<Station> stations = new ArrayList<Station>();
    private NMBSData request = new NMBSData();
    private String result = "";
    private ArrayList<Connection> connections;
    private Thread threadStops;

    public MainLogic() throws JSONException {
         threadStops = new Thread(new Runnable() {
            @Override
            public void run() {
                trustAllCertificates();
                try {
                    stations = request.GetStations();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Collections.sort(stations);
            }
        });
        threadStops.start();

    }

    public ArrayList<Station> GetStation(){
        return this.stations;
    }
    public ArrayList<Connection> GetConnections(Station depart, Station arrive, LocalDateTime date, DepartureOrArrival deporarr, int results) throws JSONException {
        return request.GetConnections(depart, arrive, date, deporarr, results);
    }

    public ArrayList<Departure> GetLiveBoardFromStationDeparture(Station station, LocalDateTime dateTime) throws JSONException {
        return request.GetLiveBoardDeparture(station, dateTime);
    }

    public ArrayList<Arrival> GetLiveBoardFromStationArrival(Station station, LocalDateTime dateTime) throws JSONException {
        return request.GetLiveBoardArrival(station, dateTime);
    }
    public ArrayList<Stop> GetStops(Connection stops){
        return stops.getDeparture().getStops();
    }

    public ArrayList<Connection> GetRoute(String origin, String destination) throws InterruptedException {

        Thread threadRoute = new Thread(() -> {
            Station departureStation = null;
            Station arrivalStation = null;
            for (Station s:stations) {
                if (origin.toLowerCase().contains(s.getName().toLowerCase()))
                {
                    departureStation = s;
                }
                if (destination.toLowerCase().contains(s.getName().toLowerCase()))
                {
                    arrivalStation = s;
                }
            }
            if (arrivalStation == null || departureStation == null){
                throw new RuntimeException("no station by these name(s) found check spelling");
            }
            else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        connections =  GetConnections(departureStation, arrivalStation, LocalDateTime.now(), DepartureOrArrival.DEPARTURE, 6);
                        result = "";
                        for (Connection c:connections) {
                            result = result + "\n" + c;
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        threadRoute.start();
        threadRoute.join();
        return connections;
    }
    public ArrayList<String> GetNamesStations() throws InterruptedException {
        ArrayList<String>names = new ArrayList<>();
        threadStops.join();
        for (Station name:stations) {
            names.add(name.getName());
        }
        return names;
    }
    private void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}
