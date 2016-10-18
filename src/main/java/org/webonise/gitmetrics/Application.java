package org.webonise.gitmetrics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.webonise.githubmetrics.webhooktest.ServiceImpl.JsonParserImpl;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        JsonObject a = new JsonObject();
        a.addProperty("b", "c");
        a.addProperty("f", "g");

//        JSONObject a = new JSONObject();
//        a.put("b", "c");
//        a.put("d", "e");

        JsonObject obj = new JsonObject();
        obj.add("a", a);
        obj.addProperty("d", true);

//        JSONObject obj = new JSONObject();
//        obj.put("a", a);
//        obj.put("b", true);

        JsonParserImpl jsonParser = new JsonParserImpl();
        String[] keys = {"a", "d"};

        JsonElement res = jsonParser.getJsonValue(keys, 0, obj);
//        System.out.println(res.getClass().getName());
    }
}
