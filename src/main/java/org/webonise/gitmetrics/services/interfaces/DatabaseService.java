package org.webonise.gitmetrics.services.interfaces;

import org.springframework.stereotype.Service;
import org.webonise.gitmetrics.documents.Assignee;
import org.webonise.gitmetrics.documents.Branch;
import org.webonise.gitmetrics.documents.Collaborator;
import org.webonise.gitmetrics.documents.Comment;
import org.webonise.gitmetrics.documents.Label;
import org.webonise.gitmetrics.documents.PullRequest;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.documents.Review;
import org.webonise.gitmetrics.entities.GitRepository;

import java.util.List;
import java.util.Map;

@Service
public interface DatabaseService {
    List<GitRepository> findListOfRepositories();

    Repository findRepositoryDetailsByName(String name);

    String saveRepository(Repository repository);

    void saveGitRepository(GitRepository gitRepository);

    void deleteRepository(String name);

    void savePullRequestInRepository(String repositoryName, PullRequest pullRequest);

    void closePullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys);

    void editPullRequest(String repositoryName, int pullRequestNumber, Map<String, Object> updateKeys);

    void addAssigneeInPullRequest(String repositoryName, int pullRequestNumber, Assignee assignee, Map<String, Object> updateKeys);

    void removeAssigneeFromPullRequest(String repositoryName, int pullRequestNumber, String assigneeLogin, Map<String, Object> updateKeys);

    void addLabelToPullRequest(String repositoryName, int pullRequestNumber, Label label, Map<String, Object> updateKeys);

    void removeLabelFromPullRequest(String repositoryName, int pullRequestNumber, String labelName, Map<String, Object> updateKeys);

    void addCommentOnPullRequest(String repositoryName, int pullRequestNumber, Comment comment, Map<String, Object> updateKeys);

    void removeCommentFromPullRequest(String repositoryName, int pullRequestNumber, long commentId, Map<String, Object> updateKeys);

    void editCommentOnPullRequest(String repositoryName, int pullRequestNumber, long commentId, String updatedComment, Map<String, Object> updateKeys);

    void addReviewToPullRequest(String repositoryName, int pullRequestNumber, Review review, Map<String, Object> updateKeys);

    void addReviewCommentToReview(String repositoryName, int pullRequestNumber, long reviewId, Comment comment, Map<String, Object> updateKeys);

    void editReviewCommentOnReview(String repositoryName, int pullRequestNumber, long reviewId, Comment updatedComment, Map<String, Object> updateKeys);

    void deleteReviewCommentFromReview(String repositoryName, int pullRequestNumber, long reviewId, long commentId, Map<String, Object> updateKeys);

    void addBranchToRepository(String repositoryName, Branch branch);

    void deleteBranchFromRepository(String repositoryName, String ref);

    void addCollaboratorToRepository(String repositoryName, Collaborator collaborator);

    boolean getMailSent(String repositoryName, String branchName);

    void updateStale(String repositoryName, String branchName);
    
    void updateMailSent(String repositoryName, String branchName);
}
