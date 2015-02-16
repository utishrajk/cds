'use strict';

angular.module("bham.training", ['bham.security'])

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

		$routeProvider
		.when('/training', {
				templateUrl: "training/training.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller:'TrainingCtrl'
			});
}])
.controller('TrainingCtrl', ['$scope',function($scope){
        //Select menu
        if(!$scope.activeSubMenuItem){
            $scope.toggleToolsMenuItem('toolsandresources');
            $scope.selectToolsSubMenu('training');
        }
}]);