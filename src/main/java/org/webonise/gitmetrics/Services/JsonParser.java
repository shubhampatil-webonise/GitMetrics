package org.webonise.gitmetrics.Services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface JsonParser {
    Map<String, Object> parse(JSONObject jsonObject, List<String> keys);
}
