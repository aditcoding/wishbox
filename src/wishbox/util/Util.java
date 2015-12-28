package wishbox.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	public static Map<Object,Object> serviceProviderMap = new HashMap<Object, Object>();
	
	static {
		readJsonIntoMap();
	}
	
	public static void main(String[] args) {
		log("map::" +serviceProviderMap);
		
	}
	
	public static void readJsonIntoMap(){
		InputStream mapData;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			mapData = loadFileAsStream("/resturls.json");
			serviceProviderMap = objectMapper.readValue(mapData, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File loadFile(String name){
		try {
			URL url = Util.class.getResource(name);
			File file = new File(url.toURI());
			return file;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream loadFileAsStream(String fileName) {
		try{
			InputStream is = Util.class.getResourceAsStream(fileName);
			if(is != null){
				return is;
			} else {
				log("InputStream is null");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String loadFileAsString(String fileName){
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			String sCurrentLine;
			sb = new StringBuilder();
			br = new BufferedReader(new FileReader(fileName));
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static BufferedReader loadFileAsReader(String fileName){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return br;
	}
	
	public static void writeToFile(String str, String fileName){
		PrintWriter writer;
		try {
			log("Writing to file");
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void log(Object o) {
		System.out.println(o.toString());
	}
}
