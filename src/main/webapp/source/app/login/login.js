/**
 * Created by utish.rajkarnikar on 7/25/14.
 */

'use strict';

angular.module('bham.loginModule', [
        'ngResource',
        'bham.userService',
        'bham.security',
        'reCAPTCHA',
        'ngDialog',
        'ngCookies'
    ])
    .config([ 'reCAPTCHAProvider', 'ngDialogProvider', function (reCAPTCHAProvider, ngDialogProvider) {

        // required, please use your own key :)
        reCAPTCHAProvider.setPublicKey('6LcTI_oSAAAAAL-cvnZf0KOOp7H9rQQy9mx7Fuos');

        // optional
        reCAPTCHAProvider.setOptions({
            theme: 'clean'
        });

    }])
    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/login', {
                templateUrl: 'login/login.tpl.html',
                controller: 'LoginController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/logout', {
                templateUrl: 'login/login.tpl.html',
                controller: 'LogoutController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/register', {
                templateUrl: 'login/register.tpl.html',
                controller: 'RegisterController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordidentify', {
                templateUrl: 'login/forgot-password-identify.tpl.html',
                controller: 'ForgotPasswordIdentifyController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordquestions', {
                templateUrl: 'login/forgot-password-questions.tpl.html',
                controller: 'ForgotPasswordVerifyAnswersController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordverifiedmessage', {
                templateUrl: 'login/forgot-password-verified.tpl.html',
                controller: 'ForgotPasswordVerifiedController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpassword', {
                templateUrl: 'login/reset-password.tpl.html',
                controller: 'ResetPasswordController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpasswordsuccess', {
                templateUrl: 'login/reset-password-success.tpl.html',
                controller: 'ResetPasswordSuccessController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpasswordandsecurityquestions', {
                templateUrl: 'login/forgot-password-and-security-questions.tpl.html',
                controller: 'ForgotPasswordAndSecurityQuestionsController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });

    }])
    .controller('LoginController', ['$routeParams', '$scope', '$location', 'AuthenticationSharedService', '$rootScope', '$http', '$cookieStore', 'authService', 'Session', 'Account',
        function ($routeParams, $scope, $location, AuthenticationSharedService, $rootScope, $http, $cookieStore, authService, Session, Account) {

        var success = function (data, status, headers, config) {
            Account.get(function(data) {
                Session.create(data.id, data.userName, data.firstName, data.lastName, data.email, data.roles, data.showTrainingVideo);
                $cookieStore.put('account', JSON.stringify(Session));
                $rootScope.loginerror = false;
                $rootScope.checkEmailMessage = false;
                authService.loginConfirmed(data);
            });
        };

        var error = function (data, status, headers, config) {
            Session.destroy();
            $rootScope.loginerror = true;
            $scope.loginErrorMsg = data;
            $rootScope.checkEmailMessage = false;
        };

        $scope.login = function () {
            //If any patient has been selected, then set it to null;
            $scope.setSelectedPatient(null);

            AuthenticationSharedService.login({
                email: $scope.email,
                password: $scope.password
            }, success, error);
        };

        var successEnv = function (data) {
            $scope.environment = data.environment;
        };

        var errorEnv = function (error) {

        };

        var registrationMessage = $routeParams.registrationMessage;

        if(registrationMessage === 'tokenNotFound') {
           $rootScope.loginerror = true;
           $scope.loginErrorMsg =   "An error occurred in the system. Please contact the system administrator.";
        } else if (registrationMessage === 'tokenHasExpired') {
           $rootScope.loginerror = true;
           $scope.loginErrorMsg =   "The link you clicked on has expired. To re-start the registration process, please click on the Register button below.";
        } else if (registrationMessage === 'verified') {
            $rootScope.checkEmailMessage = true;
        }


        AuthenticationSharedService.getEnvironment(successEnv, errorEnv);
    }])
    .controller('LogoutController', ['$scope', '$location', 'AuthenticationSharedService', function ($scope, $location, AuthenticationSharedService) {
        $scope.stopIdle();
        AuthenticationSharedService.logout({
            success: function () {
                $location.path('');
            }
        });


    }])
    .controller('ForgotPasswordIdentifyController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

        $scope.resetPasswordIdentify = function () {

            var identity = $scope.forgotPasswordIdentity;

            var successCallback = function (data) {

                $scope.resetPassworderror = false;

                //since it's a success, make another call to retrieve the questions corresponding to the user
                var successRetrieveQuestions = function (questions) {

                    $rootScope.question1 = questions.question1;
                    $rootScope.question2 = questions.question2;
                    $location.path("/forgotpasswordquestions");
                };

                var errorRetrieveQuestions = function (error) {
                    $scope.resetPassworderror = true;
                    $scope.resetPasswordMsg = error.data.message;
                };

                UserService.retrieveQuestions(identity, successRetrieveQuestions, errorRetrieveQuestions);

                //set the identity in the scope of the AppCtrl
                $scope.forgotPasswordUser(identity);
            };

            var errorCallback = function (error) {
                $scope.resetPasswordMsg = "Username and/or date of birth is not valid";
                $scope.resetPassworderror = true;
            };

            UserService.identifyUser(identity, successCallback, errorCallback);
        };

    }])
    .controller('ForgotPasswordVerifyAnswersController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {
        $scope.verifyAnswers = function () {

            //retrieve the identity from the scope of the AppCtrl
            var identity = $scope.user;

            var securityAnswers = identity.securityAnswers = $scope.securityAnswers;

            var successVerifyAnswers = function (data) {

                $scope.verifyAnswerError = false;
                $location.path("/forgotpasswordverifiedmessage");
            };

            var errorVerifyAnswers = function (error) {

                $scope.verifyAnswersMsg = "One or more security answers are not valid";
                $scope.verifyAnswerError = true;
            };

            UserService.verifyAnswers(identity, successVerifyAnswers, errorVerifyAnswers);
        };

        $scope.forgotSecurityQuestions = function () {

            var identity = $scope.user;
            identity.securityAnswers = $scope.securityAnswers;

            var successSecurityQuestions = function (data) {
                $location.path("/forgotpasswordverifiedmessage");
                $scope.forgotQuestionsError = false;
            };

            var errorSecurityQuestions = function (error) {
                $scope.forgotQuestionsMsg = 'Unable to process the request';
                $scope.forgotQuestionsError = true;
            };

            UserService.forgotSecurityQuestions(identity, successSecurityQuestions, errorSecurityQuestions);
        };

    }])
    .controller('ForgotPasswordVerifiedController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

    }])
    .controller('ResetPasswordController', ['$routeParams', '$rootScope', '$scope', '$location', 'UserService', function ($routeParams, $rootScope, $scope, $location, UserService) {

        $scope.resetPassword = function () {

            var successResetPassword = function (data) {
                $location.path("/resetpasswordsuccess");
            };

            var errorResetPassword = function (error) {
                $scope.resetPasswordMsg = error.data.message;
                $scope.resetPasswordError = true;
            };

            var password = $scope.password;

            var data = {
                token: $routeParams.token,
                credential: password.password1,
                confirmPassword: password.password2

            };
            UserService.resetPassword(data, successResetPassword, errorResetPassword);
        };

        $scope.comparePasswords = function () {
            var user = $scope.password;
            $scope.passwordsMatch = user.password1 !== user.password2;
        };

    }])
    .controller('ResetPasswordSuccessController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

    }])
    .controller('ForgotPasswordAndSecurityQuestionsController', ['$routeParams', '$rootScope', '$scope', '$location', 'UserService', function ($routeParams, $rootScope, $scope, $location, UserService) {

        var successQuestions = function (data) {
            $scope.securityQuestions = data;
            $scope.tempSecurityQuestions1 = data;
            $scope.tempSecurityQuestions2 = data;
        };

        var errorQuestions = function (error) {
            $scope.resetPasswordAndQuestionsMsg = error.data.message;
            $scope.resetPasswordAndQuestionsError = true;
        };

        UserService.getSecurityQuestionsCodes(successQuestions, errorQuestions);

        $scope.resetPasswordAndSecurityQuestions = function () {

            var form = $scope.resetPasswordAndQuestions;
            var successResetPwdAndQuestions = function (data) {
                $location.path("/resetpasswordsuccess");
            };

            var errorResetPwdAndQuestions = function (error) {
                $scope.resetPasswordAndQuestionsError = true;
                $scope.resetPasswordAndQuestionsMsg = error.data.message;
            };

            var data = {
                token: $routeParams.token,
                credential: form.password1,
                confirmPassword: form.password2,
                securityQuestion1Code: form.securityQuestion1Code,
                securityAnswer1: form.securityAnswer1,
                securityQuestion2Code: form.securityQuestion2Code,
                securityAnswer2: form.securityAnswer2
            };

            UserService.resetPasswordAndSecurityQuestions(data, successResetPwdAndQuestions, errorResetPwdAndQuestions);
        };

        $scope.comparePasswords = function () {
            var form = $scope.resetPasswordAndQuestions;
            $scope.passwordsMatch = form.password1 !== form.password2;
        };

        $scope.compareSecurityQuestions = function () {
            var form = $scope.resetPasswordAndQuestions;
            $scope.questionsMatch = (form.securityQuestion1Code === form.securityQuestion2Code);
        };

        $scope.onSecurityQuestion1Change = function() {
            $scope.tempSecurityQuestions2 = $scope.composeSecurityQuestion($scope.securityQuestions , $scope.resetPasswordAndQuestions.securityQuestion1Code);
        };

        $scope.onSecurityQuestion2Change = function() {
            $scope.tempSecurityQuestions1 = $scope.composeSecurityQuestion($scope.securityQuestions , $scope.resetPasswordAndQuestions.securityQuestion2Code);
        };

    }])
    .controller('RegisterController', ['$rootScope', '$scope', '$location', 'UserService', 'reCAPTCHA','ngDialog', function ($rootScope, $scope, $location, UserService, reCAPTCHA, ngDialog) {

        $scope.registerUser = $scope.tempRegisterUser;
        $scope.registerUser.credential = null;
        $scope.registerUser.confirmPassword = null;
        $scope.registerUser.securityQuestion1Code = null;
        $scope.registerUser.securityAnswer1 = null;
        $scope.registerUser.securityQuestion2Code = null;
        $scope.registerUser.securityAnswer2 = null;
        $scope.registerUser.acceptTermsOfUse = null;

        if($scope.registerUser.dateOfBirth !== null) {
            $scope.isvaliddate = true;
        }

        var successCallback = function (data) {
            $scope.registererror = false;
            $location.path("/login");
            $rootScope.registrationSuccessMessage = true;
            
        };

        var errorCallback = function (error) {
            $scope.registerErrorMsg = error.data.message;
            $scope.registererror = true;
            $rootScope.registrationSuccessMessage = false;
        };

        var successQuestions = function (data) {
            $scope.securityQuestions = data;
            $scope.tempSecurityQuestions1 = data;
            $scope.tempSecurityQuestions2 = data;            
        };

        var errorQuestions = function (error) {
            $scope.registerErrorMsg = error.data.message;
        };

        UserService.getSecurityQuestionsCodes(successQuestions, errorQuestions);

        $scope.register = function () {
            $scope.registerUser.mailingAddressTelephoneNumber = $scope.registerUser.mailingAddressTelephoneNumber.replace(/[-()]/g, "");
            var registerUser = $scope.registerUser;
            $scope.tempRegisterUser = registerUser;
            UserService.create(registerUser, successCallback, errorCallback);
        };

        $scope.resetMessages = function () {
            
        };

        $scope.comparePasswords = function () {
            var user = $scope.registerUser;
            $scope.passwordsMatch = (user.credential !== user.confirmPassword);
        };

        $scope.compareSecurityQuestions = function () {
            var user = $scope.registerUser;
            $scope.questionsMatch = (user.securityQuestion1Code === user.securityQuestion2Code);
        };

        $scope.$watch('registerUser.mailingAddressTelephoneNumber', function (phoneNumber) {
            $scope.validatePhoneNumber(phoneNumber);
        });

        $scope.validatePhoneNumber = function(phoneNumber){
            if(angular.isDefined(phoneNumber)){
                var formattedPhoneNumber = phoneNumber.replace(/[-()]/g, "");
                var USPattern = new RegExp($scope.phonePattern);
                // Default Country
                $scope.registerUser.mailingAddressCountryCode = "US";
                $scope.isValidPhoneNumberLength =  $scope.isValidContactNumber(formattedPhoneNumber, $scope.registerUser.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
                if($scope.isValidPhoneNumberLength) {
                    $scope.isPhoneNumber = USPattern.test(phoneNumber);
                }
            }else{
                $scope.isPhoneNumber = true;
                $scope.isValidPhoneNumberLength = true;
            }
        };

        $scope.openTermOfUs = function(){
            ngDialog.open({ template: 'termofus' ,
                controller: ['$scope' , function($scope){}]
            });
        };

        $scope.openPrivacyPolicy = function(){
            ngDialog.open({ template: 'privacypolicy' ,
                controller: ['$scope' , function($scope){ }]
            });
        };

        $scope.onSecurityQuestion1Change = function() {
            $scope.tempSecurityQuestions2 = $scope.composeSecurityQuestion($scope.securityQuestions , $scope.registerUser.securityQuestion1Code);
        };

        $scope.onSecurityQuestion2Change = function() {
            $scope.tempSecurityQuestions1 = $scope.composeSecurityQuestion($scope.securityQuestions , $scope.registerUser.securityQuestion2Code);
        };

    }]);
