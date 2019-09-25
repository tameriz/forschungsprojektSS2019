package util;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//JsonUtil which converts strings to json and json to string
public class JsonUtil{

    public JsonUtil(){

    }

    public static String convertValuesToJson(String user, String date, String stepcount) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("date", date);
        jsonObject.put("stepcount", stepcount);
        String jsonString = jsonObject.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    public static ArrayList<String> convertJsonToValues(String jsonString){
        JSONParser jsonParser = new JSONParser();
        ArrayList<String> values = new ArrayList<>();
        try{
            JSONObject object = (JSONObject) jsonParser.parse(jsonString);
            values.add((String) object.get("user"));
            values.add((String) object.get("date"));
            values.add((String) object.get("stepcount"));
        } catch (ParseException e){
            e.printStackTrace();
        }
        return values;
    }
}
