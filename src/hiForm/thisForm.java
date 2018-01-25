package hiForm;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.Certificate;
import com.google.gson.*;


public class thisForm {
    private static final String baseGeocode ="https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String googleAPI ="AIzaSyAXOUJZME49BZfMWw3XGCqdrZ0-2MvQj6U";
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
        try {
            String joined = String.join("+", parsed);
            joined += "&key=" + googleAPI;
            URL googleURL = new URL(baseGeocode + joined);
            HttpsURLConnection toGoogle = new HttpsURLConnection(googleURL) {
                @Override
                public String getCipherSuite() {
                    return null;
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    return new Certificate[0];
                }

                @Override
                public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
                    return new Certificate[0];
                }

                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {

                }
            };
            googleURL.openConnection();
            //toGoogle.setRequestMethod("GET");
            //toGoogle.setRequestProperty("Accept", "application/json");
            //InputStreamReader myreturn = new InputStreamReader(toGoogle.getInputStream());
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) toGoogle.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            String latLong = rootobj.get("zip_code").getAsString(); //just grab the zipcode

        }
        catch(MalformedURLException e) {
            //blah
        }
        catch(ProtocolException e) {
            //blah
        }
        catch(IOException e) {

        }
    }

    //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY


    private JButton exitButton;
    private JTextArea inputArea;
    private JButton runButton;
}
