package org.webonise.gitmetrics.ServiceImpl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.webonise.githubmetrics.webhooktest.Services.JsonParser;

import java.util.List;
import java.util.Map;

@Component
public class JsonParserImpl implements JsonParser {

    @Autowired
    @Qualifier("resultMap")
    private Map<String, Object> resultMap;

    @Override
    public Map<String, Object> parse(JSONObject jsonObject, List<String> keys) {

//        for (String key : keys) {
//            String[] innerKeys = key.split("\\.");
//            Object value = getJsonValue(innerKeys, 0, jsonObject);
//            resultMap.put(key, value);
//        }
        return resultMap;
    }

    public JsonElement getJsonValue(String[] keyList, int index, JsonObject jsonObject) {

//        Object currentValue = jsonObject;

//        JsonElement currentValue = jsonObject;
////        System.out.println(currentValue.getAsString());
//
//        for (String key : keyList) {
//            System.out.println(key);
//            try {
//                currentValue = currentValue.getAsJsonPrimitive(key);
//                System.out.println("adsad" + currentValue.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            System.out.println(key);
////            System.out.println(jsonObject.get(key));
//
////            try {
////                currentValue = jsonObject.get(key);
////                System.out.println(currentValue.getAsString());
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
//        }
//
//        return currentValue;
        if (index < keyList.length) {
            Iterator iterator = jsonObject.keys();
            String currentInnerKey = keyList[index];
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(currentInnerKey)) {
                    if (jsonObject.optJSONArray(key) == null && jsonObject.optJSONObject(key) == null) {
                        // if (index == keyList.length - 1) {
                        return jsonObject.get(key);
                        //}
                    } else if (jsonObject.optJSONObject(key) != null) {
                        return getJsonValue(keyList, ++index, jsonObject.getJSONObject(key));
                    } else if (jsonObject.optJSONArray(key) != null) {
                        JSONArray jsonArray = jsonObject.getJSONArray(key);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Object value = getJsonValue(keyList, ++index, jsonArray.getJSONObject(i));
                            if (value != null)
                                return value;
                        }
                    }
                }
            }
        }

    }
}
