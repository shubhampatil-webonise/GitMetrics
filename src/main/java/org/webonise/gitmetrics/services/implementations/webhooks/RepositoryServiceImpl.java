package org.webonise.gitmetrics.services.implementations.webhooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.entities.GitRepository;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.webhooks.RepositoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private Gson gson;

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private DatabaseService databaseService;

    private String payload;

    @Override
    public void actionOn(String payload) {
        this.payload = payload;
        String action = gson.fromJson(payload, JsonObject.class).get("action").getAsString();

        if (action.equals("created")) {
            actionOnCreate();
        } else if (action.equals("deleted")) {
            actionOnDelete();
        }
    }

    private void actionOnCreate() {
        String objectId = saveRepositoryToMongo();
        saveRepositoryToSql(objectId);
    }

    private void actionOnDelete() {
        String repoName = gson.fromJson(payload, JsonObject.class).getAsJsonObject("repository").get("name").getAsString();
        databaseService.deleteRepository(repoName);
    }

    private String saveRepositoryToMongo() {
        List<String> arrayList = new ArrayList<String>();

        arrayList.add("repository.name");
        arrayList.add("repository.owner.login");
        arrayList.add("repository.owner.url");
        arrayList.add("repository.owner.type");
        arrayList.add("repository.url");
        arrayList.add("repository.description");

        String result = jsonParser.parse(payload, arrayList);
        result = jsonParser.parse(result, "repository");
        result = jsonParser.addToJson(result, "collaborators", new JSONArray());
        result = jsonParser.addToJson(result, "pullRequests", new JSONArray());
        result = jsonParser.addToJson(result, "branches", new JSONArray());

        String senderLogin = jsonParser.parse(payload, "sender.login");
        result = jsonParser.addToJson(result, "sender", senderLogin);

        String privateRepo = jsonParser.parse(payload, "repository.private");
        result = jsonParser.addToJson(result, "privateRepo", privateRepo);

        String createdAt = jsonParser.parse(payload, "repository.created_at");
        result = jsonParser.addToJson(result, "createdAt", createdAt);

        String updatedAt = jsonParser.parse(payload, "repository.updated_at");
        result = jsonParser.addToJson(result, "updatedAt", updatedAt);

        Repository repository = gson.fromJson(result, Repository.class);
        return databaseService.saveRepository(repository);
    }

    private void saveRepositoryToSql(String objectId) {
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("repository.name");
        arrayList.add("repository.description");

        String result = jsonParser.parse(payload, arrayList);
        result = jsonParser.parse(result, "repository");
        result = jsonParser.addToJson(result, "objectId", objectId);

        GitRepository gitRepository = gson.fromJson(result, GitRepository.class);
        databaseService.saveGitRepository(gitRepository);
    }
}

