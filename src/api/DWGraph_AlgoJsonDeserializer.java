package api;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * this class do JsonDeserializer for load 
 * the graph
 * 
 * @author yuval
 *
 */
public class DWGraph_AlgoJsonDeserializer implements JsonDeserializer<DWGraph_DS>
{

	@Override
	public DWGraph_DS deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		JsonObject jsonObject = json.getAsJsonObject();
		DWGraph_DS graph = new DWGraph_DS(); //

		JsonObject carsJsonObj = jsonObject.get("algo").getAsJsonObject().get("Edges").getAsJsonObject();
		int k= 0;
		int i=0;
		for (Entry<String, JsonElement> set : carsJsonObj.entrySet()) {
			String hashKey = set.getKey(); //the key of the hashmap 
			JsonElement jsonValueElement = set.getValue(); //the value of the hashmap as json element

			JsonArray edge = jsonValueElement.getAsJsonArray();
			k = Integer.parseInt(hashKey);
			if(i+1==k || k==0) {
				if(k==0) {
					graph.addNode(new Node());
					k++;
				}
				else if(i+1==k){
					graph.addNode(new Node());
					k++;
					i++;
				}
			}
			else if(i+1<k) {
				while(i+1<k) {
					node_data nd = new Node();
					graph.addNode(nd);
					graph.removeNode(nd.getKey());
					i++;
				}
				i++;
				graph.addNode(new Node());
			}

		}
		for (Entry<String, JsonElement> set : carsJsonObj.entrySet()) {
			String hashKey = set.getKey(); //the key of the hashmap 
			JsonElement jsonValueElement = set.getValue(); //the value of the hashmap as json element
			int l= Integer.parseInt(hashKey);
			JsonArray edge = jsonValueElement.getAsJsonArray();
			Iterator<JsonElement> it = edge.iterator(); 
			while(it.hasNext()) {
				JsonElement je= it.next();
				if(!(je instanceof  JsonNull)) {
					int h = je.getAsJsonObject().get("dest").getAsJsonObject().get("key").getAsInt();//.getAsJsonObject().get("dest").getAsInt(); 
					double w =je.getAsJsonObject().get("weight").getAsDouble(); 
					graph.connect(l, h, w);
				}
			}
		}
		return graph;
	}

}

