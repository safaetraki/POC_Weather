package com.poc.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
	// Key generated on my openweathermap account after registration
        String key="xxxxxxxxxxxxxxxxxxxxxxx";
        // I've chosen to search by city name but it can be done using other parameters such as longitude and latitude
        String cityName="Paris";
        // I'm building the url that I'll be using to get data from the api.
        StringBuilder url=new StringBuilder("");
        url.append("http://api.openweathermap.org/data/2.5/weather?q=");
        url.append(cityName);
        // When units=metric is specified the api gives us temperature in Celsius.
        url.append("&units=metric");
        // The api doesn't reply if no key is given
        url.append("&appid=");
        url.append(key);
        String answer="";
        try {
            // The getUrlContent method is the same that the teacher wrote on our Java class : I reused it :).
            answer=getUrlContent(url.toString());
            System.out.println("This is what the api answered :"+answer);
        } catch (IOException e) {
            System.out.println("A problem occurred while reading data : "+e.getMessage());
        }

    }

    /**** Download the content of a URL and return it as a string.
     * This version only makes use of builtin stream-manipulation classes.
     * @throws IOException if anything goes wrong with the download process.
     ***/
    public static String getUrlContent(String spec) throws IOException {

        // This constructor may throw MalformedUrlException if the "spec" parameter is not a valid URL.
        // We don't catch the error here, instead it propagates to the calling method since our signature declares it.
        URL url = new URL(spec);
        // 1. Open a connection to the URL.
        // 2. Get an InputStream from which we can read incoming data (low level function, byte per byte).
        // 3. Create an InputStreamReader that is able to consume the InputStream data an process it.
        // 4. Wrap it into a BufferedReader that adds the ability to read the content line by line thanks to a buffer.
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        url.openConnection().getInputStream()
                )
        );
        // It is more efficient to use a StringBuilder rather than String concatenation (+) in a loop.
        StringBuilder builder = new StringBuilder();

        // At this point, no data has been transferred yet.

        // Declare a variable that will hold the content of a line.
        String line;
        // In the same statement, get the next line from the builder, assign it to "line" and check if it is not null.
        // The readLine() call is the one that actually makes the data transfer to happen.
        // This "while" loop will break when the readLine() method will return null (content is all read).
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        // The transfer is done, always close() the readers, streams, etc. that we have used.
        reader.close();
        // Finally, return the content of the StringBuilder.
        return builder.toString();
    }
}
