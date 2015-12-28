package wishbox.nlp;

public class QueryParser {
	
	public static String parseQuery(String query){
		//TODO Implement Logic
		String ret = "";
		if(query.contains("ticket")){
			ret = "stubhub";
		}
		
		return ret;
	}

	static void log(Object o) {
		System.out.println(o.toString());
	}
}
