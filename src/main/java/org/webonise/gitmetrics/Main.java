package org.webonise.gitmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.webonise.gitmetrics.Documents.Branch;
import org.webonise.gitmetrics.Documents.Collaborator;
import org.webonise.gitmetrics.Documents.Owner;
import org.webonise.gitmetrics.Documents.PullRequest;
import org.webonise.gitmetrics.Documents.Repository;
import org.webonise.gitmetrics.Repositories.RepositoryCollection;
import org.webonise.gitmetrics.Repositories.RepositoryList;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private RepositoryList repositoryList;

    @Autowired
    private RepositoryCollection repositoryCollection;

    @Override
    public void run(String... strings) throws Exception {
        Collaborator collaborator = new Collaborator();
        List<Collaborator> collaborators = new ArrayList<Collaborator>();
        collaborators.add(collaborator);

        Branch branch = new Branch();
        List<Branch> branches = new ArrayList<Branch>();
        branches.add(branch);

        PullRequest pullRequest = new PullRequest();
        List<PullRequest> pullRequests = new ArrayList<PullRequest>();
        pullRequests.add(pullRequest);

        Owner owner = new Owner();

        repositoryCollection.save(new Repository("myrepo", "my desc", "me", "today", "right now", "google.com", false, owner, collaborators, branches, pullRequests));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
