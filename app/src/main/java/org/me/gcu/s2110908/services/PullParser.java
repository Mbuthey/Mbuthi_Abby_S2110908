package org.me.gcu.s2110908.services;

import android.util.Log;
import android.util.Xml;

import org.me.gcu.s2110908.Helper;
import org.me.gcu.s2110908.models.Item;
import org.me.gcu.s2110908.models.WeatherForecastItem;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PullParser {
    public static List<WeatherForecastItem> parseThreeDaysWeatherData(String xml) {
        String xmlData = cleanXmlString(xml);
        ArrayList<WeatherForecastItem> weatherForecastItems = new ArrayList<>();

        XmlPullParserFactory factory = null;
        org.xmlpull.v1.XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        InputStream stream = null;

        try {
            // auto-detect the encoding from the stream
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();
            boolean done = false;
            WeatherForecastItem item = null;
            while (eventType != org.xmlpull.v1.XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case org.xmlpull.v1.XmlPullParser.START_DOCUMENT:
                        break;
                    case org.xmlpull.v1.XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            item = new WeatherForecastItem();
                        } else if (item != null) {
                            if (name.equalsIgnoreCase("description")) {
                                String description = parser.nextText().trim();
//                                item.setDescription(description);
                            } else if (name.equalsIgnoreCase("pubDate")) {
                                System.out.println("pubDate");
//                                item.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase("title")) {
                                String title = parser.nextText().trim();
                                String day = title.split(":")[0];
                                item.setDay(day);
                                String weatherCondition = title.split(":")[1].split(",")[0].trim();
                                item.setWeatherCondition(weatherCondition);
                                item.setWeatherIcon(Helper.getWeatherIcon(weatherCondition));
                                // Find the index of "Minimum Temperature" and extract the substring
                                int minTempIndex = title.indexOf("Minimum Temperature:");
                                if (minTempIndex != -1) {
                                    String minTempSubstring = title.substring(minTempIndex + "Minimum Temperature:".length(), title.indexOf("°C", minTempIndex));
                                    item.setLowTemp(minTempSubstring.trim() + "°C");
                                } else {
                                    // Handle case where "Minimum Temperature" is not present
                                    item.setLowTemp("N/A");
                                }

                                // Find the index of "Maximum Temperature" and extract the substring
                                int maxTempIndex = title.indexOf("Maximum Temperature:");
                                if (maxTempIndex != -1) {
                                    String maxTempSubstring = title.substring(maxTempIndex + "Maximum Temperature:".length(), title.indexOf("°C", maxTempIndex));
                                    item.setHighTemp(maxTempSubstring.trim() + "°C");
                                } else {
                                    // Handle case where "Maximum Temperature" is not present
                                    item.setHighTemp("N/A");
                                }
                            }
                        }
                        break;
                    case org.xmlpull.v1.XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase("item") && item != null) {
                            Log.i("Added", item.toString());
                            weatherForecastItems.add(item);
                        } else if (name.equalsIgnoreCase("channel")) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return weatherForecastItems;
    }

    public static Item parseItem(String xmlData) throws XmlPullParserException, IOException {
        Item item = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(xmlData));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("item")) {
                        item = new Item();
                    } else if (item != null) {
                        if (name.equalsIgnoreCase("description")) {
                            String description = parser.nextText().trim();
                            System.out.println("Description: " + description);
//                            item.setTemperature(description.substring(description.indexOf("Temperature:"), description.indexOf("°C")).trim() + "°C");
//                            item.setWindDirection(description.substring(description.indexOf("Wind Direction:"), description.indexOf(",")).trim());
//                            item.setWindSpeed(description.substring(description.indexOf("Wind Speed:"), description.indexOf(",")).trim());
//                            item.setHumidity(description.substring(description.indexOf("Humidity:"), description.indexOf(",")).trim());
//                            item.setPressure(description.substring(description.indexOf("Pressure:"), description.indexOf(",")).trim());
                            String temperatureCelsius = description.split("Temperature: ")[1].split("°C")[0].trim();
                            item.setTemperature(temperatureCelsius);

                            // Extract wind direction
                            String windDirection = description.split("Wind Direction: ")[1].split(",")[0].trim();
                            item.setWindDirection(windDirection);

                            // Extract wind speed
                            String windSpeed = description.split("Wind Speed: ")[1].split("mph")[0].trim();
                            item.setWindSpeed(windSpeed);

                            // Extract humidity
                            String humidity = description.split("Humidity: ")[1].split("%")[0].trim();
                            item.setHumidity(humidity);

                            // Extract pressure
                            String pressure = description.split("Pressure: ")[1].split("mb")[0].trim();
                            item.setPressure(pressure);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        return item;
    }


    private static String cleanXmlString(String xml) {
        //Get rid of the first tag <?xml version="1.0" encoding="utf-8"?>
        int i = xml.indexOf(">");
        xml = xml.substring(i+1);
        //Get rid of the 2nd tag <rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
        i = xml.indexOf(">");
        xml = xml.substring(i+1);
        // Get rid of </rss> tag
        i = xml.lastIndexOf("<");
        xml = xml.substring(0, i).trim();
        Log.e("MyTag - cleaned",xml);

        return xml;
    }


}
