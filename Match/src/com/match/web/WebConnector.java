package com.match.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Pushpak
 *
 */
public class WebConnector {
	
	private static final String SET_COOKIE = "Set-Cookie";
	/**
	 * Get httpUrlConnection {@link HttpURLConnection} for connecting to web. 
	 * @return
	 */
	public URLConnection getConnetion() {
		
		URLConnection connection = null;
		try {			
			URL url = new URL("http://www.accuweather.com/en/in/chennai/206671/weather-forecast/206671");
			connection = url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * Get current temperature from accuweather.com
	 * @param connection
	 * @return
	 */
	public boolean CheckForRainFall(URLConnection connection) {
		
		Map<String, List<String>> map = connection.getHeaderFields();
		if(map.size() != 0) {
			List<String> temperatureKeyVals = map.get(SET_COOKIE);
			String temperatureValString = temperatureKeyVals.get(2);
			if(temperatureValString.contains("rain") || temperatureValString.contains("shower")) {
				return true;
			}
		}
		return false;
	}
}
