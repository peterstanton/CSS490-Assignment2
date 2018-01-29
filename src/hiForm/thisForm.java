package hiForm;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.stream.Collectors;

import com.google.gson.*;

public class thisForm {
    private static final String baseGeocode ="https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String googleAPI ="AIzaSyAXOUJZME49BZfMWw3XGCqdrZ0-2MvQj6U";
    private static final String baseWeather = "https://api.darksky.net/forecast/";
    private static final String weatherKey ="6695e2cfcb5944be3b28b2bcbe4cce57";
    public static void main(String[] args) {
        JFrame frame = new JFrame("thisForm");
        frame.setContentPane(new thisForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel mainPanel;

    public thisForm() {
        exitButton.addActionListener(exiter -> System.exit(0));
        runButton.addActionListener(runner -> process(inputArea.getText()));
    }

    private void process(String input) {
        String[] parsed = input.split("\\W+");
        URL googleURL = processGeoURL(parsed);
        String geoResult = processResponse(googleURL);
        String[] geoParsed = geoResult.split("\n");
        double geoLat = Double.parseDouble(geoResult.split("\n")[63].split(":")[1].replaceAll(",", ""));
        double geoLong = Double.parseDouble((geoResult.split("\n")[64].split(":")[1]).replaceAll(",", ""));
        URL darkSkyURL = processWeatherURL(geoLat, geoLong);
        String weatherResult = processResponse(darkSkyURL);
        String temp = new String();
    }

    private URL processGeoURL(String[] parsed) {
        String joined = String.join("+", parsed);
        joined += "&key=" + googleAPI;
        try {
            URL googleURL = new URL(baseGeocode + joined);
            return googleURL;
        }
        catch (MalformedURLException e) {
            //blah
        }
        return null;
    }

    private String processResponse(URL inURL) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inURL.openStream()));
            String outResult = new String();
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            try {
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                outResult = buffer.toString();
                return outResult;
            }
            catch(IOException e) {
                //blah
            }
        }
        catch(IOException e) {
            //blah
        }
        return null;
    }

    private URL processWeatherURL(double geoLat, double geoLong) {
        String joined = baseWeather + weatherKey + '/' + geoLat + ',' + geoLong;
        try {
            return new URL(joined);
        }
        catch(MalformedURLException e) {
            //blah
        }
        return null;
    }


    //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY


    private JButton exitButton;
    private JTextArea inputArea;
    private JButton runButton;
}
