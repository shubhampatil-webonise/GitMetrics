package org.webonise.gitmetrics.services.implementations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.webonise.gitmetrics.documents.Comment;
import org.webonise.gitmetrics.documents.PullRequest;
import org.webonise.gitmetrics.documents.Repository;
import org.webonise.gitmetrics.documents.Review;
import org.webonise.gitmetrics.entities.GitRepository;
import org.webonise.gitmetrics.services.interfaces.DatabaseService;
import org.webonise.gitmetrics.services.interfaces.HttpRequestResponseService;
import org.webonise.gitmetrics.services.interfaces.JsonParser;
import org.webonise.gitmetrics.services.interfaces.MemberService;
import org.webonise.gitmetrics.services.interfaces.SessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    private HttpRequestResponseService httpRequestResponseService;

    @Autowired
    private DatabaseService databaseService;

//    @Value("${gitmetrics.client.personal-access-token}")
//    private String TOKEN;

    @Autowired
    private SessionService sessionService;

    @Value("${gitmetrics.org.name}")
    private String ORGANIZATION;

    @Autowired
    JsonParser jsonParser;

    @Override
    public String getMembers() throws IOException {
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", "token " + sessionService.get("accessToken"));

        String url = "https://api.github.com/orgs/" + ORGANIZATION + "/members";
        String members = httpRequestResponseService.get(url, headers);
        members = getMembersDetail(members);

        return members;
    }

    private String getMembersDetail(String members) {
        JSONArray jsonArray = new JSONArray(members);
        JSONArray memberArray = new JSONArray();

        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            jsonObject = getMemberDetail(jsonObject);
            memberArray.put(jsonObject);
        }

        return memberArray.toString();
    }

    private JSONObject getMemberDetail(JSONObject jsonObject) {
        List<String> keyList = new ArrayList();
        keyList.add("login");
        keyList.add("type");

        String member = jsonParser.parse(jsonObject.toString(), keyList);
        String comments = getCommentsByMemberLogin(jsonObject.getString("login"));
        String reviews = getReviewsByMemberLogin(jsonObject.getString("login"));
        member = jsonParser.addToJson(member, "comments", new JSONArray(comments));
        member = jsonParser.addToJson(member, "reviews", new JSONArray(reviews));


        return new JSONObject(member);
    }

    private String getReviewsByMemberLogin(String login) {
        List<GitRepository> repositoryList = databaseService.findListOfRepositories();
        JSONArray reviewArray = new JSONArray();

        for (int i = 0; i < repositoryList.size(); i++) {
            Repository repository = databaseService.findRepositoryDetailsByName(repositoryList.get(i).getName());

            List<PullRequest> pullRequests = repository.getPullRequests();
            Iterator pullRequestIterator = pullRequests.iterator();
            while (pullRequestIterator.hasNext()) {
                PullRequest pullRequest = (PullRequest) pullRequestIterator.next();

                List<Review> reviews = pullRequest.getReviews();
                Iterator reviewIterator = reviews.iterator();
                while (reviewIterator.hasNext()) {
                    Review review = (Review) reviewIterator.next();

                    if (review.getUser().getLogin().equals(login)) {
                        String currentReview = jsonParser.addToJson(new JSONObject(review).toString(), "repositoryName", repository.getName());
                        currentReview = jsonParser.addToJson(currentReview, "pullRequestNumber", pullRequest.getNumber());
                        reviewArray.put(new JSONObject(currentReview));
                    }
                }
            }
        }
        return reviewArray.toString();
    }

    private String getCommentsByMemberLogin(String login) {
        List<GitRepository> repositoryList = databaseService.findListOfRepositories();
        JSONArray commentArray = new JSONArray();

        for (int i = 0; i < repositoryList.size(); i++) {
            Repository repository = databaseService.findRepositoryDetailsByName(repositoryList.get(i).getName());

            List<PullRequest> pullRequests = repository.getPullRequests();
            Iterator pullRequestIterator = pullRequests.iterator();
            while (pullRequestIterator.hasNext()) {
                PullRequest pullRequest = (PullRequest) pullRequestIterator.next();

                List<Comment> comments = pullRequest.getComments();
                Iterator commentIterator = comments.iterator();
                while (commentIterator.hasNext()) {
                    Comment comment = (Comment) commentIterator.next();

                    if (comment.getUser().getLogin().equals(login)) {
                        String currentComment = jsonParser.addToJson(new JSONObject(comment).toString(), "repositoryName", repository.getName());
                        currentComment = jsonParser.addToJson(currentComment, "pullRequestNumber", pullRequest.getNumber());
                        commentArray.put(new JSONObject(currentComment));
                    }
                }
            }
        }
        return commentArray.toString();
    }
}
