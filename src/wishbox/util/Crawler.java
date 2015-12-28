package wishbox.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Crawler {
	
	private static ObjectMapper m = new ObjectMapper();
	private static int counter = 0;
	
	public static void main(String[] args) {

		try {
			ArrayNode rootNode = (ArrayNode)m.readTree(Util.loadFileAsStream("/rests.json")).get("all");
			for(int i=0; i<rootNode.size(); i++){
				ArrayNode merchantNode = (ArrayNode)rootNode.get(i).get("merchants");
				for(int j=0; j<merchantNode.size(); j++){
					JsonNode merchant =  merchantNode.get(j);
					String id = merchant.get("id").toString();
					id = id.replace("\"", "");
					String esUrl = "http://localhost:9200/delivery/merchants/" + id;
					try{
						putInES(esUrl, merchant.toString());
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {

		try {
			ArrayNode rootNode = (ArrayNode)m.readTree(Util.loadFileAsStream("/rests.json")).get("all");
			for(int i=0; i<rootNode.size(); i++){
				ArrayNode merchantNode = (ArrayNode)rootNode.get(i).get("merchants");
				for(int j=0; j<merchantNode.size(); j++){
					JsonNode merchant =  merchantNode.get(j);
					String id = merchant.get("id").toString();
					id = id.replace("\"", "");
					String name = merchant.get("summary").get("name").toString();
					String merchantType = merchant.get("summary").get("url").get("merchant_type").toString();
					merchantType = merchantType.replace("\"", "");
					String esUrl = "http://localhost:9200/delivery/" + merchantType + "/" + id;
					if(checkIfExists(esUrl)){
						continue;
					}
					String url = "https://api.delivery.com/merchant/" + id + "/menu?client_id=OTQ1OTVkZTY3YTdlZTQzNmZlNzAzZWFiZjM4ZmNlNTE5";
					JsonNode returnedNode = null;
					try{
						returnedNode = makeRequest(url);
						byte [] strByte = returnedNode.toString().getBytes("UTF-8");
						String retNodeStr = new String(strByte);
						String jsonMod = "{ \"id\" : " + id + ",\"name\" : " + name + ",\"data\":" + retNodeStr + "}";
						putInES(esUrl, jsonMod);
					} catch(Exception e){
						continue;
						if(e.getMessage().equalsIgnoreCase("premature")){
							log("prematured");
							try {
								Thread.sleep(5*60*1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							returnedNode = makeRequest(url);
						} else {
							log("genned");
							continue;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private static boolean checkIfExists(String esUrl) {
		try {
			URL urlObj = new URL(esUrl);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("HEAD");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				counter++;
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	private static JsonNode makeRequest(String url) {
		try {

			URL urlObj = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			InputStream is = conn.getInputStream();
			try{
				JsonNode node = m.readTree(is);
				return node;
			} catch(IOException e){
				e.printStackTrace();
			}
			
			//conn.disconnect();
			//return null;
			/*BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			log("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				log(output);
			}*/

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private static void putInES(String putUrl, String jsonStr){
		try {

			URL url = new URL(putUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(jsonStr);
			os.flush();
			os.close();
			
			/*File file1 = new File("C:\\Users\\Aditya\\workspace\\wishbox\\resources\\temp.json");
			PrintWriter writer = new PrintWriter(file1);
			writer.println(jsonStr);
			writer.flush();
			writer.close();*/
			if(conn.getResponseCode() == 200 || conn.getResponseCode() == 201){
				InputStream is = conn.getInputStream();
				is.close();
				counter++;
				log(counter);
			}
			/*BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			log("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				log(output);
			}*/
			//conn.disconnect();

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

