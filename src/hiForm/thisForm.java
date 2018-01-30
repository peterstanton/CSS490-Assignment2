package hiForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class thisForm {
    private static final String baseGeocode = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String googleAPI = "AIzaSyAXOUJZME49BZfMWw3XGCqdrZ0-2MvQj6U";
    private static final String baseWeather = "https://api.darksky.net/forecast/";
    private static final String weatherKey = "6695e2cfcb5944be3b28b2bcbe4cce57";
    private JPanel mainPanel;
    private JButton exitButton;
    private JTextArea inputArea;
    private JButton runButton;
    private JLabel weatherimage;
    private JLabel googleimage;
    private JOptionPane resultWindow;

    private thisForm() {
        ImageIcon weaIcon = new ImageIcon(getClass().getResource("poweredby.png"));
        Image weaImage = weaIcon.getImage();
        Image newWeaImage = weaImage.getScaledInstance(194, 76, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        weaIcon = new ImageIcon(newWeaImage);
        weatherimage.setIcon(weaIcon);

        ImageIcon gooIcon = new ImageIcon(getClass().getResource("powered_by_google_on_white_hdpi.png"));
        Image gooImage = gooIcon.getImage();
        Image newGooImage = gooImage.getScaledInstance(288, 36, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        gooIcon = new ImageIcon(newGooImage);
        googleimage.setIcon(gooIcon);

        exitButton.addActionListener(exiter -> System.exit(0));
        runButton.addActionListener(runner -> process(inputArea.getText()));
        weatherimage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://darksky.net/poweredby/"));
                    } catch (Exception x) {
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
        googleimage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://developers.google.com/maps/documentation/geocoding/intro"));
                    } catch (Exception x) {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("thisForm");
        frame.setContentPane(new thisForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void process(String input) {
        if (input.isEmpty()) {
            return;
        }
        String[] parsed = input.split("\\W+");
        URL googleURL = processGeoURL(parsed);
        String geoResult = processResponse(googleURL);
        ArrayList<String> geoSplit = new ArrayList<String>(Arrays.asList(geoResult.split("\n")));
        int geoLatIndex = -1;
        int geoLongIndex = -1;
        for (int i = 0; i < geoSplit.size(); i++) {
            if (geoSplit.get(i).contains("location")) {
                geoLatIndex = i + 1;
                geoLongIndex = i + 2;
                break;
            }
        }
        if (geoLatIndex == -1) {
            return;
        }
        double geoLat = Double.parseDouble(geoSplit.get(geoLatIndex).split(":")[1].replaceAll(",", ""));
        double geoLong = Double.parseDouble(geoSplit.get(geoLongIndex).split(":")[1].replaceAll(",", ""));
        URL darkSkyURL = processWeatherURL(geoLat, geoLong);
        String weatherResult = processResponse(darkSkyURL);
        printResults(weatherResult, input);
    }

    private void printResults(String weatherResult, String locale) {
        //stuff here.
        String[] results = weatherResult.split(",");
        JOptionPane.showMessageDialog(null, "Hi", "Results for " + locale, JOptionPane.PLAIN_MESSAGE);


        //should loop through with a switch tree and on switch, stuff values in a string builder, then
        //spit it out when I'm done.
    }

    private URL processGeoURL(String[] parsed) {
        String joined = String.join("+", parsed);
        joined += "&key=" + googleAPI;
        try {
            return new URL(baseGeocode + joined);
        } catch (MalformedURLException e) {
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
            } catch (IOException e) {
                //blah
            }
        } catch (IOException e) {
            //blah
        }
        return null;
    }

    private URL processWeatherURL(double geoLat, double geoLong) {
        String joined = baseWeather + weatherKey + '/' + geoLat + ',' + geoLong;
        try {
            return new URL(joined);
        } catch (MalformedURLException e) {
            //blah
        }
        return null;
    }

}
