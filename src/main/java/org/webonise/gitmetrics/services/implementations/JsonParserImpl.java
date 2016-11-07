package org.webonise.gitmetrics.services.implementations;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.services.interfaces.JsonParser;

import java.util.Arrays;
import java.util.List;

@Component
public class JsonParserImpl implements JsonParser {

    @Override
    public String parse(String jsonBody, String key) {
        JSONObject jsonObject = new JSONObject(jsonBody);
        List<String> innerKeys = Arrays.asList(key.split("\\."));
        Object value = getJsonValue(innerKeys, jsonObject);
        return value.toString();
    }

    @Override
    public String parse(String jsonBody, List<String> keys) {
        JSONObject resultJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject(jsonBody);

        for (String key : keys) {
            List<String> innerKeys = Arrays.asList(key.split("\\."));
            Object value = getJsonValue(innerKeys, jsonObject);

            if (value != null) {
                add(resultJsonObject, innerKeys, value);
            }
        }

        return resultJsonObject.toString();
    }

    @Override
    public String addToJson(String jsonBody, String key, Object value) {
        JSONObject jsonObject = new JSONObject(jsonBody);
        List<String> innerKeys = Arrays.asList(key.split("\\."));
        add(jsonObject, innerKeys, value);
        return jsonObject.toString();
    }

    private void add(JSONObject currentJsonObject, List<String> innerKeys, Object value) {
        int index = 0;
        String tempKey = innerKeys.get(index);

        while (currentJsonObject.optJSONObject(tempKey) != null) {
            currentJsonObject = currentJsonObject.getJSONObject(tempKey);
            index = index + 1;
            tempKey = innerKeys.get(index);
        }

        for (int i = innerKeys.size() - 1; i > index; i--) {
            String currentKey = innerKeys.get(i);
            JSONObject tempJsonObject = new JSONObject();
            tempJsonObject.put(currentKey, value);
            value = tempJsonObject;
        }

        currentJsonObject.put(innerKeys.get(index), value);
    }

    private Object getJsonValue(List<String> keyList, JSONObject jsonObject) {
        JSONObject currentJsonObject = jsonObject;

        for (String key : keyList) {

            if (currentJsonObject.optJSONObject(key) != null) {
                currentJsonObject = currentJsonObject.getJSONObject(key);
            } else if (currentJsonObject.opt(key) != null) {
                if (keyList.indexOf(key) == (keyList.size() - 1)) {
                    return currentJsonObject.get(key);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return currentJsonObject;
    }
}