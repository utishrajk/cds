/**
 * Created by tomson.ngassa on 8/11/2014.
 */

'use strict';

angular.module("bham.errorModule", [
    'ngResource',
    'bham.security'
   ])
    .config(['$routeProvider', 'USER_ROLES', '$httpProvider',
        function ($routeProvider, USER_ROLES, $httpProvider) {

            $routeProvider
                .when('/error/403', {
                    templateUrl: 'error/error-403.tpl.html',
                    controller: 'ErrorCtrl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                })
                .when('/error/404', {
                    templateUrl: 'error/error-404.tpl.html',
                    controller: 'ErrorCtrl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                })
                .when('/error/500', {
                    templateUrl: 'error/error-500.tpl.html',
                    controller: 'ErrorCtrl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                })
                .when('/error', {
                    templateUrl: 'error/error.tpl.html',
                    controller: 'ErrorCtrl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                })
                .when('/invalidurl', {
                    templateUrl: 'error/error-invalidurl.tpl.html',
                    controller: 'InvalidUrlCtrl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                })
                .otherwise({
                    redirectTo: '/invalidurl',
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    }
                });
        }])
    .controller('ErrorCtrl', [ '$scope', '$location', function ($scope, $location) {
        $scope.setActiveMenuItem('error');
    }])
    .controller('InvalidUrlCtrl', [ '$scope', '$location', function ($scope, $location) {
        $scope.setActiveMenuItem('invalidurl');
    }]);

