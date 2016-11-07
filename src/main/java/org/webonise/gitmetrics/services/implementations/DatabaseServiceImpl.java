package org.webonise.gitmetrics.services.implementations;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Assignee;
import org.webonise.gitmetrics.documents.Branch;
import org.webonise.gitmetrics.documents.Collaborator;
import org.webonise.gitmetrics.documents.Comment;
import org.webonise.gitmetrics.documents.Label;
import org.webonise.gitmetrics.documents.PullRequest;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.documents.Review;
import org.webonise.gitmetrics.entities.GitRepository;
import org.webonise.gitmetrics.repositories.RepositoryCollection;
import org.webonise.gitmetrics.repositories.RepositoryList;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseServiceImpl implements DatabaseService {

    private final static Logger logger = Logger.getLogger(DatabaseServiceImpl.class);

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
    public List<Repository> findAllRepositories() {
        return repositoryCollection.findAll();
    }

    @Override
    public Repository findRepositoryDetailsByName(String name) {
        Repository repository = repositoryCollection.findByName(name);
        return repository;
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
        Repository repository = repositoryCollection.findByName(repositoryName);
        repository.getPullRequests().add(pullRequest);
        repositoryCollection.save(repository);
    }

    @Override
    public void closePullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void editPullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys) {
        closePullRequest(repositoryName, pullRequestNumber, updateKeys);
    }

    @Override
    public void addAssigneeInPullRequest(String repositoryName, int pullRequestNumber, Assignee assignee, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }

                pullRequest.getAssignees().add(assignee);
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void removeAssigneeFromPullRequest(String repositoryName, int pullRequestNumber, String assigneeLogin, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void addLabelToPullRequest(String repositoryName, int pullRequestNumber, Label label, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);
        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }

                pullRequest.getLabels().add(label);
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void removeLabelFromPullRequest(String repositoryName, int pullRequestNumber, String labelName, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void addCommentOnPullRequest(String repositoryName, int pullRequestNumber, Comment comment, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }

                pullRequest.getComments().add(comment);
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void removeCommentFromPullRequest(String repositoryName, int pullRequestNumber, long commentId, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void editCommentOnPullRequest(String repositoryName, int pullRequestNumber, long commentId, String updatedComment, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void addReviewToPullRequest(String repositoryName, int pullRequestNumber, Review review, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }

                boolean isAlreadyPresent = false;

                for (Review currentReview : pullRequest.getReviews()) {
                    if (currentReview.getId() == review.getId()) {
                        currentReview.setBody(review.getBody());
                        currentReview.setUser(review.getUser());
                        currentReview.setSubmittedAt(review.getSubmittedAt());
                        isAlreadyPresent = true;
                    }
                }

                if (!isAlreadyPresent) {
                    pullRequest.getReviews().add(review);
                }
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void addReviewCommentToReview(String repositoryName, int pullRequestNumber, long reviewId, Comment comment, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void editReviewCommentOnReview(String repositoryName, int pullRequestNumber, long reviewId, Comment updatedComment, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void deleteReviewCommentFromReview(String repositoryName, int pullRequestNumber, long reviewId, long commentId, Map<String, Object> updateKeys) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        List<PullRequest> pullRequests = repository.getPullRequests();

        for (PullRequest pullRequest : pullRequests) {
            if (pullRequest.getNumber() == pullRequestNumber) {
                for (Map.Entry<String, Object> entry : updateKeys.entrySet()) {
                    try {
                        pullRequest.set(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
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

        repositoryCollection.save(repository);
    }

    @Override
    public void addBranchToRepository(String repositoryName, Branch branch) {
        Repository repository = repositoryCollection.findByName(repositoryName);
        repository.getBranches().add(branch);
        repositoryCollection.save(repository);
    }

    @Override
    public void deleteBranchFromRepository(String repositoryName, String ref) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        Iterator<Branch> iterator = repository.getBranches().iterator();
        while (iterator.hasNext()) {
            Branch branch = iterator.next();
            if (branch.getRef().equalsIgnoreCase(ref)) {
                iterator.remove();
            }
        }
        repositoryCollection.save(repository);
    }

    @Override
    public void addCollaboratorToRepository(String repositoryName, Collaborator collaborator) {
        Repository repository = repositoryCollection.findByName(repositoryName);
        repository.getCollaborators().add(collaborator);
        repositoryCollection.save(repository);
    }

    @Override
    public void updateStaleStatus(String repositoryName, String branchName, Boolean isStale) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        for (Branch branch : repository.getBranches()) {
            if (branch.getRef().equalsIgnoreCase(branchName)) {
                branch.setStale(isStale);
            }
        }

        repositoryCollection.save(repository);
    }

    @Override
    public void updateMailSentStatus(String repositoryName, String branchName, Boolean isMailSent) {
        Repository repository = repositoryCollection.findByName(repositoryName);

        for (Branch branch : repository.getBranches()) {
            if (branch.getRef().equalsIgnoreCase(branchName)) {
                branch.setMailSent(isMailSent);
            }
        }

        repositoryCollection.save(repository);
    }
}
