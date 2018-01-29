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
            googleURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(googleURL.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            String result;
            char[] chars = new char[1024];
            try {
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                result = buffer.toString();
            }
            catch(IOException e) {
                //blah
            }


            //Gson gson = new Gson();
            //String geoResponse = gson.fromJson(br.readLine(), String.class);


            //JSONObject json = new JSONObject(jsonText);
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
