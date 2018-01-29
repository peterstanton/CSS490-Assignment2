package hiForm;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.cert.Certificate;
import java.util.stream.Collectors;

public class thisForm {
    private static final String baseGeocode ="https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String googleAPI ="AIzaSyAXOUJZME49BZfMWw3XGCqdrZ0-2MvQj6U";
    private static final String baseWeather = "https://api.darksky.net/forecast/";
    private static final String weatherKey ="6695e2cfcb5944be3b28b2bcbe4cce57";
    private JPanel mainPanel;
    private JButton exitButton;
    private JTextArea inputArea;
    private JButton runButton;
    private JLabel weatherimage;
    public static void main(String[] args) {
        JFrame frame = new JFrame("thisForm");
        frame.setContentPane(new thisForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private thisForm() {
        ImageIcon icon = new ImageIcon( getClass().getResource("poweredby.png") );

        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(200, 75,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        weatherimage.setIcon(icon);
        exitButton.addActionListener(exiter -> System.exit(0));
        runButton.addActionListener(runner -> process(inputArea.getText()));
        weatherimage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://darksky.net/poweredby/"));
                    }
                    catch(Exception x) {
                        //blah
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void process(String input) {
        if(input.isEmpty()) {
            return;
        }
        String[] parsed = input.split("\\W+");
        URL googleURL = processGeoURL(parsed);
        String geoResult = processResponse(googleURL);
        double geoLat = Double.parseDouble(geoResult.split("\n")[63].split(":")[1].replaceAll(",", ""));
        double geoLong = Double.parseDouble((geoResult.split("\n")[64].split(":")[1]).replaceAll(",", ""));
        URL darkSkyURL = processWeatherURL(geoLat, geoLong);
        String weatherResult = processResponse(darkSkyURL);
    }

    private URL processGeoURL(String[] parsed) {
        String joined = String.join("+", parsed);
        joined += "&key=" + googleAPI;
        try {
            return new URL(baseGeocode + joined);
        }
        catch (MalformedURLException e) {
            //blah
        }
        return null;
    }

    private String processResponse(URL inURL) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inURL.openStream()));
            String outResult;
            StringBuilder builder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            try {
                while ((read = reader.read(chars)) != -1)
                    builder.append(chars, 0, read);

                outResult = builder.toString();
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

}
