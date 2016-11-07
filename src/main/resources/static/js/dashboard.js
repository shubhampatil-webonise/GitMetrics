var app = angular.module("gitmetrics", []).controller("mainController", function($scope, $http){

    $scope.getRepoList = function(){
        $http({
            method : "GET",
            url : "/org/repos"
        }).then(function(response){
            $scope.repoList = response.data;
            return true;
        }, function(error){
            console.log(error);
            return false;
        });
    }
    
    $scope.fetchMembers = function(){
        
        $http({
            method : "GET",
            url : "/org/members/"
        }).then(function(response){
            $scope.members = response.data;
        }, function(error){
            console.log(error);
            return false;
        })
        
    }


    $scope.getRepoList();
    setInterval($scope.getRepoList, 2000);
})