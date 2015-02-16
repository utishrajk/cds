'use strict';

//angular.module("bham.activityModule", [
//        'ngResource',
//        //'bham.activityService',
//        'bham.security',
//        'bham.activityDirectives',
//        'bham.loggingModule'
//    ])
//
//    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
//        $routeProvider
//            .when('/activity/:id', {
//                templateUrl: "activity/activity.tpl.html",
//                access: {
//                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
//                },
//                controller: 'ActivityCtrl',
//                resolve: {
//                    loadedActivities: ['ActivityService', '$log', '$q','$route', '$location', 'ExceptionLoggingService', 'ErrorService', function(ActivityService, $log, $q, $route, $location, ExceptionLoggingService, ErrorService){
//                            var careManagerId = $route.current.params.id;
//
//                            if(!isNaN(careManagerId)){
//                                // Set up a promise to return
//                                var deferred = $q.defer();
//
//                                // Set up our resource calls
//                                var activityResource = ActivityService.getActivityResource();
//                                var activityData = activityResource.query({id:careManagerId},
//                                        function(response){ErrorService.success(response);},
//                                        function(response){ErrorService.error(response);});
//
//                                activityData.$promise.then(function(response) {
//                                    deferred.resolve(response);
//                                });
//                                return deferred.promise;
//                            }else{
//                                $location.path('error');
//                                ExceptionLoggingService.error();
//                            }
//                    }]
//                }
//            });
//    }])
//
//    .controller('ActivityCtrl', ['$scope', 'ActivityService', '$location', '$log', 'loadedActivities',  function ($scope, ActivityService, $location, $log, loadedActivities) {
//
//        var activityList = loadedActivities;
//
////      Initial table page size
//        $scope.pageSize = 10;
//
//        $scope.activities = activityList;
//        $scope.filteredActivities = activityList;
//        $scope.totalRecords = activityList.length;
//
//        //Calculating the the size of the shown page
//        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;
//
//        //Calcating the first record
//        $scope.startRecord = 1;
//
//        $scope.onSearch = function(){
//            //In case of search resetting the page number to the first page
//            $scope.pageNo = 1;
//
//            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
//                if($scope.searchBy === 'username'){
//                    $scope.composedCriteria = {username : $scope.criteria};
//                }else if($scope.searchBy === 'description'){
//                    $scope.composedCriteria = {description : $scope.criteria};
//                }
//            }else{
//                $scope.composedCriteria = $scope.criteria;
//            }
//        };
//
//
//        $scope.onPageSizeChange = function(){
//            if( $scope.pageSize > $scope.totalRecords) {
//                $scope.showPageSize = $scope.filteredActivities.length;
//            }else{
//                $scope.showPageSize = $scope.pageSize ;
//            }
//            //On changing page size reset to first page and set the page number to 0
//            $scope.startRecord = 1;
//            $scope.pageNo = 1;
//        };
//
//        //SORTING
//        $scope.sortField = undefined;
//        $scope.reverse = false;
//
//        $scope.sort = function (fieldName) {
//            if ($scope.sortField === fieldName) {
//                $scope.reverse = !$scope.reverse;
//            } else {
//                $scope.sortField = fieldName;
//                $scope.reverse = false;
//            }
//        };
//
//        $scope.isSortUp = function (fieldName) {
//            return $scope.sortField === fieldName && !$scope.reverse;
//        };
//
//        $scope.isSortDown = function (fieldName) {
//            return $scope.sortField === fieldName && $scope.reverse;
//        };
//
//        // PAGINATION
//        $scope.pages = [];
//        $scope.pageNo = 1;
//        $scope.firstPage = 1;
//        $scope.lastPage = 1;
//
//        var calculatePaginationPages = function(numberActivities, pageSize){
//            $scope.pages.length = 0;
//            var noOfPages = Math.ceil(numberActivities / pageSize);
//            $scope.lastPage = noOfPages;
//            for (var i=0; i< noOfPages; i++) {
//                $scope.pages.push(i);
//            }
//        };
//
//        $scope.$watch('filteredActivities.length', function(filteredSize){
//            calculatePaginationPages(filteredSize,$scope.pageSize );
//
//            //Update showPageSize when filteredActivity length changes
//            $scope.startRecord = parseInt($scope.pageSize) *( parseInt( $scope.pageNo) -1) + 1;
//            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
//            $scope.showPageSize = upperLimit > filteredSize? filteredSize  : upperLimit;
//
//        });
//
//        $scope.$watch('pageSize', function(newPageSize){
//            var totalActivity = 0;
//            if(angular.isDefined($scope.filteredActivities)){
//                totalActivity = $scope.filteredActivities.length;
//            }else if(angular.isDefined($scope.totalRecords)){
//                totalActivity = $scope.totalRecords.length;
//            }
//            calculatePaginationPages( totalActivity , newPageSize);
//            //Reset current to first page after user changes the page size.
//            $scope.pageNo = 1;
//        });
//
//        $scope.setActivePage = function (page_Num, increment) {
//            var pageNo = parseInt(page_Num) + parseInt(increment);
//            if (pageNo >=1 && pageNo <= $scope.pages.length) {
//                $scope.pageNo = pageNo;
//                // Updating showing pages
//                updateShowPageSize(pageNo);
//            }
//        };
//
//        $scope.$watch('pageNo', function(currentPageNo){
//            var newPageNo = parseInt(currentPageNo);
//            if(!isNaN(currentPageNo) && ( newPageNo > 0 && (newPageNo <= $scope.pages.length) ) ){
//                $scope.paginationIndexError = "";
//                updateShowPageSize(currentPageNo);
//            }else{
//                $scope.paginationIndexError = "Page number does not exist";
//            }
//        });
//
//        var updateShowPageSize = function(pageNo){
//            // Updating showing pages
//            $scope.startRecord = parseInt($scope.pageSize) * ( parseInt( pageNo) - 1) + 1;
//            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
//            var filteredactivitieslenght = $scope.filteredActivities.length;
//            $scope.showPageSize = upperLimit > filteredactivitieslenght ? filteredactivitieslenght  : upperLimit;
//        };
//
//    }]);
