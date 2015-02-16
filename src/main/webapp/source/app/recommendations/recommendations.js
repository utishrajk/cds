/**
 * Created by tomson.ngassa on 8/20/2014.
 */

'use strict';

angular.module("bham.recommendationModule", ['bham.security', 'bham.recommendationsService', 'bham.dataElementsService'])

    .config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

        $routeProvider
            .when('/recommendations/:requestId', {
                templateUrl: "recommendations/recommendations.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller:'RecommendationsCtrl',
                resolve: {
                    loadedData: ['RecommendationsService', 'ExceptionLoggingService','DataElementsService', '$log', '$q', '$route', '$location', 'ErrorService',
                        function (RecommendationsService, ExceptionLoggingService,DataElementsService, $log, $q, $route, $location, ErrorService) {
                            // Set up a promise to return
                            var deferred = $q.defer();

                            if(angular.isDefined($route.current.params.requestId)){
                                var requestId = $route.current.params.requestId ;

                                var recommendationResource = RecommendationsService.retrieve();
                                var recommendationData = recommendationResource.get(
                                    {requestId: requestId},
                                    function (response) {
                                        ErrorService.success(response);
                                    },
                                    function (response) {
                                        ErrorService.error(response);
                                    }
                                );

                                var dataElementsResource = DataElementsService.retrieveWithFlags();
                                var dataElementsData = dataElementsResource.get(
                                    {requestId: requestId},
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                var routeCodeResource = DataElementsService.getRouteCodeResource();
                                var routeCodeData = routeCodeResource.query(
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                var statusCodeResource = DataElementsService.getStatusCodeResource();
                                var statusCodeData = statusCodeResource.query(
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                var yesnoCodeResource = DataElementsService.getYesOrNoCodeResource();
                                var yesnoCodeData = yesnoCodeResource.query(
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                var episodeCodeResource = DataElementsService.getEpisodeCodeResource();
                                var episodeCodeData = episodeCodeResource.query(
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                var asiRouteCodeResource = DataElementsService.getASIRouteResource();
                                var asiRouteCodeData = asiRouteCodeResource.query(
                                    function (response) {ErrorService.success(response);},
                                    function (response) {ErrorService.error(response);}
                                );

                                // Wait until both resources have resolved their promises, then resolve this promise
                                //$q.all([routeResourceData.$promise, patientResourceData.$promise]).then(function (response) {
                                $q.all([recommendationData.$promise,
                                        dataElementsData.$promise,
                                        routeCodeData.$promise,
                                        statusCodeData.$promise,
                                        yesnoCodeData.$promise,
                                        episodeCodeData.$promise,
                                        asiRouteCodeData.$promise]).then(function (response) {

                                    deferred.resolve(response);
                                });
                                return deferred.promise;
                            }else{
                                $log.error("Recommendation: invalid requestId" );
                                $location.path('/error');
                            }



                        }]
                }
            });
    }])
    .controller('RecommendationsCtrl', ['$scope','$rootScope','loadedData', 'RecommendationsService','DataElementsService', '$log', 'ErrorService', '$location','$window',
        function($scope, $rootScope, loadedData, RecommendationsService,DataElementsService, $log, ErrorService, $location, $window){

        //Always scroll to the begining of the page.
        $window.scrollTo(0, 0);

        $scope.today = new Date();
        //Select menu
        //Select default menu.
        $scope.setActiveMenuItem('patientdataelements');

        $scope.recommendationsObject = loadedData[0];

        $scope.dataElements = loadedData[1];

        $scope.routeCodes =  loadedData[2];

        $scope.statusCodes =  loadedData[3];

        $scope.yesnoCodes =  loadedData[4];

        $scope.episodeCodes =  loadedData[5];

        $scope.asiRouteCodes =  loadedData[6];


        $scope.toggleAssestment = function(){
            if( $scope.dataElements.asi === true && $scope.dataElements.gpra === false){
                $scope.assessment = true; //ASI
            }else if( $scope.dataElements.asi === false && $scope.dataElements.gpra === true ){
                $scope.assessment = false; //GPRA
            }
        };

        $scope.toggleAssestment();

        $scope.getGenderDisplayName = function(gender){
            if(gender === "MA"){
                return "Male";
            }else if(gender === "FE"){
                return "Female";
            }else if(gender === "TG"){
                return "Transgender  ";
            }else if(parseInt(gender) === -7){
                return "Refused";
            }else if(gender === "UN"){
                return $scope.dataElements.otherDescription;
            }
        };

        $scope.getDayCountDisplayName = function(numberOfDays){
            if(parseInt(numberOfDays) === -7){
                return "Refuse";
            }else  if(parseInt(numberOfDays) === -8){
                return "Don't Know";
            }else if( (parseInt(numberOfDays)>= 0) && (parseInt(numberOfDays)<= 30)){
                return numberOfDays ;
            }
        };

        $scope.getRouteDisplayName = function(value){
            if(parseInt(value) === -7){
                return "Refuse";
            }else  if(parseInt(value) === -8){
                return "Don't Know";
            }else if(parseInt(value) > 0){
               var entity =  $scope.getEntityByCode( $scope.routeCodes, value.toString());
                if(entity !== null){
                    return entity.displayName;
                }
            }
        };

    }]);
