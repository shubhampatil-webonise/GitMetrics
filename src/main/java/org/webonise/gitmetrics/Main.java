package org.webonise.gitmetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.webonise.gitmetrics.Entities.GitRepository;
import org.webonise.gitmetrics.Repositories.RepositoryList;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private RepositoryList repositoryList;

    @Override
    public void run(String... strings) throws Exception {
        repositoryList.save(new GitRepository("Shubham", "Shubham's Repo"));
        repositoryList.save(new GitRepository("Nitish", "Nitish's Repo"));
        repositoryList.save(new GitRepository("Shirish", "Shirish's Repo"));
        repositoryList.save(new GitRepository("Deep", "Deep's Repo"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
