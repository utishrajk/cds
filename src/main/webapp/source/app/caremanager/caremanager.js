'use strict';

angular.module("bham.caremanagerModule", [
        'bham.security',
        'bham.caremanagerService',
        'bham.userService',
        'ui.bootstrap',
        'bham.caremanagerDirectives',
        'bham.loggingModule',
        'angular-growl'
    ])

    .config(['$routeProvider', 'USER_ROLES','growlProvider', function($routeProvider, USER_ROLES, growlProvider) {

        growlProvider.globalPosition('top-center');
        growlProvider.globalDisableCountDown(true);

        $routeProvider
            .when('/myaccount', {
                templateUrl: "caremanager/caremanager-profile.tpl.html",
                controller: "CaremanagerCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['CareManagerService', 'ExceptionLoggingService',  '$log', '$q','$route', '$location', 'UserService', 'ErrorService', function(CareManagerService,ExceptionLoggingService,  $log, $q, $route, $location, UserService, ErrorService){

                            // Set up a promise to return
                            var deferred = $q.defer();

                            var careManagerResource = CareManagerService.getCareManagerResource();
                            var careManagerData = careManagerResource.get(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);});

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([careManagerData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;

                    }]
                }
            });
    }])
    .controller("CaremanagerCtrl",['$scope','$log', '$location', 'loadedData', 'CareManagerService', '$modal', 'UserService', 'ErrorService','$route',
        function($scope, $log, $location, loadedData, CareManagerService, $modal , UserService, ErrorService, $route) {

//            $http.defaults.headers.common.currentUrl = $location.path();

            //Select menu
            $scope.setActiveMenuItem('myAccount');
            $scope.caremanager = loadedData[0];
            $scope.validatePhoneNumber = function(phoneNumber){
                if(angular.isDefined(phoneNumber)){
                    var formattedPhoneNumber = phoneNumber.replace(/[-()]/g, "");

                    if( $scope.isUnitedState( $scope.caremanager.mailingAddressCountryCode)){
                        var USPattern = new RegExp($scope.phonePattern);
                        $scope.isValidPhoneNumberLength =  $scope.isValidContactNumber(formattedPhoneNumber, $scope.caremanager.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
                        if($scope.isValidPhoneNumberLength) {
                            $scope.isPhoneNumber = USPattern.test(phoneNumber);
                        }
                    }else{
                        $scope.isPhoneNumber = $scope.isValidNumber(formattedPhoneNumber);
                        $scope.isValidPhoneNumberLength = true;
                    }
                }else{
                    $scope.isPhoneNumber = true;
                    $scope.isValidPhoneNumberLength = true;
                }
            };

            $scope.$watch('caremanager.mailingAddressTelephoneNumber', function(phoneNumber){
                $scope.validatePhoneNumber(phoneNumber);
            });

            $scope.onCountrySelected = function(){
//            $scope.validateZipCode($scope.caremanager.mailingAddressPostalCode);
                $scope.validatePhoneNumber($scope.caremanager.mailingAddressTelephoneNumber);
            };

            $scope.save = function(caremanager){

                $scope.caremanager.mailingAddressTelephoneNumber = $scope.caremanager.mailingAddressTelephoneNumber.replace(/[-()]/g, "");
                if($scope.caremanager.mailingAddressCountryCode === $scope.USCountryCode){
                    caremanager.nonUSState = "";
                }else{
                    caremanager.mailingAddressStateName = "NON US STATE";
                }

                //this is required to be always true to submit the data (set during the registration)
                caremanager.acceptTermsOfUse = true;

                CareManagerService.update(caremanager,
                    function(data){$log.info("Success in processing the request..."); $scope.redirect('/caremanager/' + $scope.account.id);}, //account is being stored in rootscope
                    function(response){ErrorService.error(response);});
            };

            //By default should be valid
            $scope.isvaliddate = true;

            $scope.canSave = function(){
                var validPhoneNumber = ($scope.isPhoneNumber && $scope.isValidPhoneNumberLength);
                var futureDoB = !$scope.isFutureDate($scope.caremanager.dateOfBirth);
                return ($scope.caremanagerForm.$dirty && $scope.caremanagerForm.$valid && validPhoneNumber && futureDoB) && $scope.isvaliddate;
            };

            // Security Questions
            var successQuestions = function (data) {
                $scope.securityQuestions = data;
                $scope.tempSecurityQuestions1 = composeSecurityQuestion(data, $scope.caremanager.securityQuestion2Code) ;
                $scope.tempSecurityQuestions2 = composeSecurityQuestion(data, $scope.caremanager.securityQuestion1Code);

            };

            var errorQuestions = function (error) {
                $scope.registerErrorMsg = error.data.message;
            };

            UserService.getSecurityQuestionsCodes(successQuestions, errorQuestions);

            // Create and Confirm Password
//        $scope.pw1 = 'password';

            // Cancel
            $scope.reset = function() {
                $route.reload();
            };

            ///
            $scope.user = {
                username: '',
                credential: null
            };

            $scope.loginErrorMsg = "Invalid Data";
            $scope.loginerror = false;


            $scope.open = function (caremanager) {

                $modal.open({
                    templateUrl: 'myModalContent.html',
                    backdrop: true,
                    windowClass: 'modal',
                    controller:  function ($scope, $modalInstance, $log, user, growl) {

                        $scope.emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                        $scope.user = user;
                        $scope.verify = function () {
                            var config ={ttl: 2000};
                            var successVerifyIdentity = function () {

                                caremanager.acceptTermsOfUse = true;
                                CareManagerService.update(caremanager,
                                    function(){
                                        $log.info("Success in processing the request...");
                                    },
                                    function(error){
                                        $log.error(error.data.message );
                                        $location.search("message",error.data.message );
                                        $scope.redirect('/error');}
                                );
                                $scope.user.username = '';
                                $scope.user.credential = null;
                                growl.success("<b>My Account</b> has been updated successfully", config);

                                $modalInstance.dismiss('cancel');
                            };
                            var errorVerifyIdentity = function (error) {
                                $scope.loginerror = true;
                                $scope.loginErrorMsg = error.data.message;
                            };
                            UserService.verifyIdentity(user, successVerifyIdentity, errorVerifyIdentity);

                        };
                        $scope.cancel = function () {
                            $scope.user.username = '';
                            $scope.user.credential = null;
                            $modalInstance.dismiss('cancel');
                        };
                    },
                    size: 'sm',
                    resolve: {
                        user: function () {
                            return $scope.user;
                        }
                    }
                });
            };

            var composeSecurityQuestion = function(securityQuestion, securityQuestionCode){
                var question = [];

                for(var i = 0 ; i < securityQuestion.length ; i++){
                    if(securityQuestion[i].code !== securityQuestionCode){
                        question.push(securityQuestion[i]);
                    }
                }

                return question;
            };

            $scope.onSecurityQuestion1Change = function () {
                $scope.isSecurityQuestion1Required = ($scope.caremanager.securityQuestion1Code.length === 0) ;
                $scope.enableSecurityAnswer1IsRequired = !(isNaN($scope.caremanager.securityQuestion1Code)) ;

                if($scope.enableSecurityAnswer1IsRequired){
                    $scope.caremanager.securityAnswer1 = "";
                }

                //Compose security question dropdown
                $scope.tempSecurityQuestions2 = composeSecurityQuestion($scope.securityQuestions , $scope.caremanager.securityQuestion1Code);
            };

            $scope.enableRequiredForSecurityQuestion2 = false;

            $scope.onSecurityQuestion2Change = function () {
                $scope.isSecurityQuestion2Required = ($scope.caremanager.securityQuestion2Code.length === 0) ;
                $scope.enableSecurityAnswer2IsRequired = !(isNaN($scope.caremanager.securityQuestion2Code)) ;

                if($scope.enableSecurityAnswer2IsRequired){
                    $scope.caremanager.securityAnswer2 = "";
                }

                //Compose security question dropdown
                $scope.tempSecurityQuestions1 = composeSecurityQuestion($scope.securityQuestions , $scope.caremanager.securityQuestion2Code);
            };

        }]);
