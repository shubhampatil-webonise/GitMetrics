package org.webonise.gitmetrics.ServiceImplementations;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.Documents.Assignee;
import org.webonise.gitmetrics.Documents.Branch;
import org.webonise.gitmetrics.Documents.Comment;
import org.webonise.gitmetrics.Documents.Label;
import org.webonise.gitmetrics.Documents.PullRequest;
import org.webonise.gitmetrics.Documents.Repository;
import org.webonise.gitmetrics.Documents.Review;
import org.webonise.gitmetrics.Entities.GitRepository;
import org.webonise.gitmetrics.Repositories.RepositoryCollection;
import org.webonise.gitmetrics.Repositories.RepositoryList;
import org.webonise.gitmetrics.Services.DatabaseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseServiceImpl implements DatabaseService {

    private final static Logger logger = Logger.getLogger(DatabaseServiceImpl.class.getName());

    @Autowired
    private RepositoryList repositoryList;

    @Autowired
    private RepositoryCollection repositoryCollection;

    @Autowired
    private Gson gson;

    @Override
    public List<GitRepository> findListOfRepositories() {
        return repositoryList.findAll();
    }

    @Override
    public List<Repository> findRepositoryDetailsByName(String name) {
        List<Repository> repositories = repositoryCollection.findByName(name);
        return repositories;
    }

    @Override
    public String saveRepository(Repository repository) {
        Repository repo = repositoryCollection.save(repository);
        return repo.getId();
    }

    @Override
    public void saveGitRepository(GitRepository gitRepository) {
        repositoryList.save(gitRepository);
    }

    @Override
    public void deleteRepository(String name) {
        repositoryCollection.deleteRepositoryByName(name);
        repositoryList.deleteGitRepositoryByName(name);
    }

    @Override
    public void savePullRequestInRepository(String repositoryName, PullRequest pullRequest) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            repository.getPullRequests().add(pullRequest);
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void closePullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void editPullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys) {
        closePullRequest(repositoryName, pullRequestNumber, updateKeys);
    }

    @Override
    public void addAssigneeInPullRequest(String repositoryName, int pullRequestNumber, Assignee assignee, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    pullRequest.getAssignees().add(assignee);
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void removeAssigneeFromPullRequest(String repositoryName, int pullRequestNumber, String assigneeLogin, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    Iterator iterator = pullRequest.getAssignees().iterator();

                    while (iterator.hasNext()) {
                        Assignee assignee = (Assignee) iterator.next();
                        if (assignee.getLogin().equals(assigneeLogin)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void addLabelToPullRequest(String repositoryName, int pullRequestNumber, Label label, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    pullRequest.getLabels().add(label);
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void removeLabelFromPullRequest(String repositoryName, int pullRequestNumber, String labelName, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    Iterator iterator = pullRequest.getLabels().iterator();

                    while (iterator.hasNext()) {
                        Label label = (Label) iterator.next();
                        if (label.getName().equals(labelName)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void addCommentOnPullRequest(String repositoryName, int pullRequestNumber, Comment comment, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    pullRequest.getComments().add(comment);
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void removeCommentFromPullRequest(String repositoryName, int pullRequestNumber, long commentId, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    Iterator iterator = pullRequest.getComments().iterator();

                    while (iterator.hasNext()) {
                        Comment comment = (Comment) iterator.next();
                        if (comment.getId() == commentId) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void editCommentOnPullRequest(String repositoryName, int pullRequestNumber, long commentId, String updatedComment, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    Iterator iterator = pullRequest.getComments().iterator();

                    while (iterator.hasNext()) {
                        Comment comment = (Comment) iterator.next();
                        if (comment.getId() == commentId) {
                            comment.setBody(updatedComment);
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void addReviewToPullRequest(String repositoryName, int pullRequestNumber, Review review, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    boolean isAlreadyPresent = false;

                    for (Review currentReview : pullRequest.getReviews()) {
                        if (currentReview.getId() == review.getId()) {
                            currentReview.setBody(review.getBody());
                            currentReview.setUser(review.getUser());
                            currentReview.setSubmittedAt(review.getSubmittedAt());
//                            currentReview.setComments(review.getComments());
                            isAlreadyPresent = true;
                        }
                    }

                    if (!isAlreadyPresent) {
                        pullRequest.getReviews().add(review);
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void addReviewCommentToReview(String repositoryName, int pullRequestNumber, long reviewId, Comment comment, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    boolean isAlreadyPresent = false;

                    for (Review currentReview : pullRequest.getReviews()) {
                        if (currentReview.getId() == reviewId) {
                            currentReview.getComments().add(comment);
                            isAlreadyPresent = true;
                        }
                    }

                    if (!isAlreadyPresent) {
                        Review review = new Review();
                        review.setId(reviewId);
                        review.setComments(new ArrayList());
                        review.getComments().add(comment);
                        pullRequest.getReviews().add(review);
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void editReviewCommentOnReview(String repositoryName, int pullRequestNumber, long reviewId, Comment updatedComment, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    for (Review currentReview : pullRequest.getReviews()) {
                        if (currentReview.getId() == reviewId) {
                            for (Comment comment : currentReview.getComments()) {
                                if (comment.getId() == updatedComment.getId()) {
                                    comment.copy(updatedComment);
                                }
                            }
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void deleteReviewCommentFromReview(String repositoryName, int pullRequestNumber, long reviewId, long commentId, Map<String, Object> updateKeys) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            List<PullRequest> pullRequests = repository.getPullRequests();

            for (PullRequest pullRequest : pullRequests) {
                if (pullRequest.getNumber() == pullRequestNumber) {
                    for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                        try {
                            pullRequest.set(entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }

                    for (Review currentReview : pullRequest.getReviews()) {
                        if (currentReview.getId() == reviewId) {

                            Iterator iterator = currentReview.getComments().iterator();

                            while (iterator.hasNext()) {
                                Comment comment = (Comment) iterator.next();

                                if (comment.getId() == commentId) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            }
        }

        repositoryCollection.save(repositories);
    }

    @Override
    public void addBranchToRepository(String repositoryName, Branch branch) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);

        for (Repository repository : repositories) {
            repository.getBranches().add(branch);
        }
        repositoryCollection.save(repositories);
    }

    @Override
    public void deleteBranchFromRepository(String repositoryName, String ref) {
        List<Repository> repositories = repositoryCollection.findByName(repositoryName);
        
        for (Repository repository : repositories) {
            Iterator<Branch> iterator = repository.getBranches().iterator();
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                if (branch.getRef().equalsIgnoreCase(ref)) {
                    iterator.remove();
                }
            }
        }
        repositoryCollection.save(repositories);
    }
}
