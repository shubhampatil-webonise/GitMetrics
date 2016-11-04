var app = angular.module("gitmetrics", []).controller("mainController", function($scope, $http, $window){

    $scope.repository = null;
    
    $scope.getRepoData = function(){
        var repoName = $window.location.pathname.split("/")[2];

        $http({
            method : "GET",
            url : "/org/repos/" + repoName
        }).then(function(response){
            $scope.repository = response.data;  
            return true;
        }, function(error){
            console.log(error);
            return false;
        });        
    }

    $scope.getRepoData();
})