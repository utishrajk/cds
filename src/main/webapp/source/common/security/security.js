'use strict';

/* Services */
angular.module('bham.security', [])

.factory('Account', ['$resource',
    function ($resource) {
        return $resource('app/rest/account', {}, {
        });
    }])

.factory('Password', ['$resource',
    function ($resource) {
        return $resource('app/rest/account/change_password', {}, {
        });
    }])

.factory('Sessions', ['$resource',
    function ($resource) {
        return $resource('app/rest/account/sessions/:series', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }])

.factory('ThreadDumpService', ['$http',
    function ($http) {
        return {
            dump: function() {
                var promise = $http.get('dump').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    }])

.factory('HealthCheckService', ['$rootScope', '$http',
    function ($rootScope, $http) {
        return {
            check: function() {
                var promise = $http.get('health').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    }])
.factory("UserService", ["$cookieStore", function($cookieStore){

        return {
            getUser: function(){
                var cookieStoreUser = $cookieStore.get('account');
                if(angular.isDefined(cookieStoreUser)) {
                    return JSON.parse(cookieStoreUser);
                }
            }
        };

    }])
.factory('LogsService', ['$resource',
    function ($resource) {
        return $resource('app/rest/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel':  { method: 'PUT'}
        });
    }])

.factory('Session', ['$cookieStore',
function ($cookieStore) {
    this.create = function (id, userName, firstName, lastName, email, userRoles, showTrainingVideo) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoles = userRoles;
        this.showTrainingVideo = showTrainingVideo;
    };
    this.destroy = function () {
        this.id = null;
        this.userName = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.roles = null;
        this.showTrainingVideo = null;
        $cookieStore.remove('account');
    };
    return this;
}])

.constant('USER_ROLES', {
        all: '*',
        admin: 'ROLE_ADMIN',
        user: 'ROLE_USER'
    })

.factory('AuthenticationSharedService', ['$rootScope', '$http', '$cookieStore', 'authService', 'Session', 'Account','$location', '$resource',
    function ($rootScope, $http, $cookieStore, authService, Session, Account, $location, $resource) {
        var Environment = $resource('app/public/environment');

        return {
            login: function (param, successCb, errorCb) {
                var data ="j_username=" + param.email +"&j_password=" + param.password;
                $http.post('app/authentication', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
                    },
                    ignoreAuthModule: 'ignoreAuthModule'
                })
                .success(successCb)
                .error(errorCb);
            },
            isAuthenticated: function () {
                if (!Session.userName) {
                    // check if the user has a cookie
                    if ( angular.isDefined( $cookieStore.get('account') )) {
                        var account = JSON.parse($cookieStore.get('account'));
                        Session.create(account.id, account.userName, account.firstName, account.lastName,
                            account.email, account.userRoles, account.showTrainingVideo);
                        $rootScope.account = Session;
                    }
                }
                return !!Session.userName;
            },
            isAuthorized: function (authorizedRoles) {
                if (!angular.isArray(authorizedRoles)) {
                    if (authorizedRoles === '*') {
                        return true;
                    }
                    authorizedRoles = [authorizedRoles];
                }

                var isAuthorized = false;

                angular.forEach(authorizedRoles, function(authorizedRole) {
                    var authorized = (!!Session.userName &&
                        Session.userRoles.indexOf(authorizedRole) !== -1);

                    if (authorized || authorizedRole === '*') {
                        isAuthorized = true;
                    }
                });

                return isAuthorized;
            },
            logout: function () {
                $rootScope.authenticationError = false;
                var cb = function (data, status, headers, config) {
                    Session.destroy();
                    authService.loginCancelled();
                };
                $http.post('app/logout').success(cb).error(cb);
            },
            getEnvironment: function(success, error) {
                Environment.get(success, error);
            }
        };
    }]);
