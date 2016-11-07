# What is GitMetrics
A web application for managing organization and repository metrics using GitHub APIs and Spring Boot
### WebHooks
* Webhooks are used to record any change and store it in the database.
* Webhooks have been used to record these events:-
* When a new repository is created or deleted
* When a pull request is opened, closed, reopened, edited, assigned or unassigned
* When a pull request review is submitted
* When a pull request review is created, edited or deleted
* When a pull request comment is created,edited or deleted
* When a collaborator is added
* When a branch is created or deleted.
### Database
* Uses MongoDB to store Repository Details which includes PR,Collaborators and branches.
* Uses SQL to store Repository name and description.


### Environment Variable 
* ClientId and Client Secret obtained after registering the app are stored in environment variables


### Run GitMetrics
* Download the source code and open in Intellij/Eclipse
* Configure a maven project using pom.xml
* Use ngrok to tunnel localhost to internet
* Run Main.java
* Open browser and go to `<ngrok-url>`
* Login with your Github account.

    



