package org.webonise.gitmetrics.services.implementations.webhooks;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.documents.Collaborator;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.webhooks.CollaboratorService;

import java.util.ArrayList;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
    @Autowired
    JsonParser jsonParser;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private Gson gson;

    private static final String REPOSITORY = "repository.name";
    private static final String COLLABORATOR = "member.login";
    private static final String TYPE = "member.type";
    private static final String MEMBER = "member";

    @Override
    public void actionOnCreate(String payload) {
        ArrayList<String> keysList = new ArrayList<String>();
        keysList.add(COLLABORATOR);
        keysList.add(TYPE);
        String collaborator = jsonParser.parse(payload, keysList);
        collaborator = jsonParser.parse(collaborator, MEMBER);
        String repositoryName = jsonParser.parse(payload, REPOSITORY);
        Collaborator newCollaborator = gson.fromJson(collaborator, Collaborator.class);
        databaseService.addCollaboratorToRepository(repositoryName, newCollaborator);
    }
}
