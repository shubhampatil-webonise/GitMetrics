package org.webonise.gitmetrics.Utilities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Services.DatabaseService;
import org.webonise.gitmetrics.Services.JsonParser;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class StaleBranchDetector {
    @Autowired
    private OkHttpClient client;

    @Autowired
    JsonParser parser;

    @Autowired
    private DatabaseService databaseService;

    public static final String TOKEN = "68d692d998c5c76a05b361e00b711e0d0b4c880b";
    public static final String ROOT_URL = "https://api.github.com/repos";
    public static final String BRANCH_URL = "/branches";
    public static final String PULL_REQUEST = "/pulls?state=all";
    public static final String COMMIT_DATE = "commit.committer.date";
    public static final String COMMITTTER_NAME = "commit.committer.name";
    public static final String EMAIL = "commit.author.email";
    public static final String MERGE_STATUS = "merged";
    public static final String STATE = "state";
    public static final String HEAD = "head.ref";
    public static final String COMMIT_URL = "commit.url";
    public static final String NAME = "name";
    public static final String PR_URL = "url";
    private int staleTime = 0;
    private String repositoryName;
    private JSONArray jsonBranchArray;
    private JSONArray jsonPRArray;

    public Set<StaleBranch> getMailingList() throws IOException {
        Set<StaleBranch> mailingSet;
        boolean mailSent;
        mailingSet = new HashSet<StaleBranch>();
        for (int i = 0; i < jsonBranchArray.length(); i++) {
            StaleBranch staleBranch = getBranch(jsonBranchArray.getJSONObject(i));
            mailSent = databaseService.getMailSentValue(repositoryName, staleBranch.getName());
            if (isBranchStale(staleBranch.getDate(), jsonPRArray, staleBranch.getName()) && (!mailSent)) {
                databaseService.updateStaleValue(repositoryName, staleBranch);
                mailingSet.add(staleBranch);
            }
        }
        return mailingSet;
    }

    public void fetchData(String owner, String repositoryName) throws IOException {
        String responseData;
        this.repositoryName = repositoryName;
        responseData = getResponse(ROOT_URL + "/" + owner + "/" + repositoryName + BRANCH_URL);
        jsonBranchArray = getJsonArray(responseData);
        responseData = getResponse(ROOT_URL + "/" + owner + "/" + repositoryName + PULL_REQUEST);
        jsonPRArray = getJsonArray(responseData);
    }

    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder().header("Authorization", " Token " + TOKEN).url(url).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code" + response);
        }
        return response.body().string();
    }

    private JSONArray getJsonArray(String data) {
        String responseObject = "{\"branches\":" + data + "}";
        JSONObject object = new JSONObject(responseObject);
        return object.getJSONArray("branches");
    }

    private long getDateDifference(String date) {
        Calendar date1 = DatatypeConverter.parseDateTime(date);
        Date date2 = new Date();
        long days = (date2.getTime() - date1.getTime().getTime());
        days = days / (24 * 60 * 60 * 1000);
        return days;
    }

    private StaleBranch getBranch(JSONObject object) throws IOException {
        StaleBranch staleBranch = new StaleBranch();
        staleBranch.setName(object.getString(NAME));
        String url = parser.parse(object.toString(), COMMIT_URL);
        String responseObject = getResponse(url);
        staleBranch.setDate(parser.parse(responseObject, COMMIT_DATE));
        staleBranch.setLast_committer(parser.parse(responseObject, COMMITTTER_NAME));
        staleBranch.setEmail(parser.parse(responseObject, EMAIL));
        return staleBranch;
    }

    private boolean isBranchStale(String date, JSONArray jsonPRArray, String branch) throws IOException {
        String mergeStatus;
        String state;
        String fromBranch;
        if (getDateDifference(date) >= staleTime) {
            for (int i = 0; i < jsonPRArray.length(); i++) {
                JSONObject object = jsonPRArray.getJSONObject(i);
                String prDetails = getResponse(parser.parse(object.toString(), PR_URL));
                mergeStatus = parser.parse(prDetails, MERGE_STATUS);
                state = object.getString(STATE);
                fromBranch = parser.parse(object.toString(), HEAD);
                if (branch.equalsIgnoreCase(fromBranch) && mergeStatus != null && state.equalsIgnoreCase("closed")) {
                    return true;
                }
            }
        }
        return false;
    }
}
