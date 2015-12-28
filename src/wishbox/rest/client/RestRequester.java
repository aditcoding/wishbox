package wishbox.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RestRequester {

	private static String searchUrl = "http://localhost:8080/VidyaMap/search";

	public static void main(String[] args) {

		try {

			URL url = new URL(searchUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			// String input = Util.loadFileAsString("tmp/input.json");
			String input = "plants temperature";
			log(input);

			OutputStreamWriter os = new OutputStreamWriter(
					conn.getOutputStream());
			os.write(input);
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode() + " "
						+ conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			log("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				log(output);
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void makeGetRequest(Map<String, String> params) {
		try {
			String restUrl = params.get("url");
			String query = params.get("query");
			query = URLEncoder.encode(query, "UTF-8");
			URL url = new URL(restUrl + "?q=" + query);
			HttpURLConnection conn = null;
			if(restUrl.contains("https")){
				conn = (HttpsURLConnection) url.openConnection();
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setRequestMethod("GET");
			for(String str : params.keySet()){
				if(str.contains("header")){
					String key = str.split(".")[1];
					String value = params.get(str);
					conn.setRequestProperty(key, value);
				}
			}

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode() + " "
						+ conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			log("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				log(output);
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void log(Object o) {
		System.out.println(o.toString());
	}
}
