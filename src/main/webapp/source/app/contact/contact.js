/**
 * Created by tomson.ngassa on 8/21/2014.
 */

'use strict';

angular.module("bham.contactModule", ['bham.security','bham.contactService'])

    .config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

        $routeProvider
        .when('/aboutus', {
                templateUrl: "contact/aboutus.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller:'ContactCtrl'
            })
        .when('/aboutuspublic', {
                templateUrl: "contact/aboutus-public.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.all]
                },
                controller:'ContactCtrl'
            })
        .when('/termofuse', {
            templateUrl: "contact/termofuse.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'ContactCtrl'
        })
        .when('/termofusepublic', {
            templateUrl: "contact/termofuse-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'ContactCtrl'
        })
        .when('/privacypolicy', {
            templateUrl: "contact/privacypolicy.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'ContactCtrl'
        })
        .when('/privacypolicypublic', {
            templateUrl: "contact/privacypolicy-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'ContactCtrl'
        })
        .when('/contactus', {
                templateUrl: "contact/contactus.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller:'ContactCtrl'
        })
        .when('/contactuspublic', {
                templateUrl: "contact/contactus-public.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.all]
                },
                controller:'ContactCtrl'
        })
        .when('/generalfeedback', {
            templateUrl: "contact/feedback.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'GeneralFeedbackCtrl'
        })

        .when('/generalfeedbackpublic', {
            templateUrl: "contact/feedback-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'GeneralFeedbackCtrl'
        })
        .when('/feedbacksuccess', {
            templateUrl: "contact/feedbacksuccess.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'ContactCtrl'
        })

        .when('/feedbacksuccesspublic', {
            templateUrl: "contact/feedbacksuccess-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'ContactCtrl'
        })

        .when('/faqs', {
            templateUrl: "contact/faqs.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'ContactCtrl'
        })

        .when('/faqspublic', {
            templateUrl: "contact/faqs-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'ContactCtrl'
        })
        .when('/generalfeedbackerror', {
            templateUrl: "contact/contact-error.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            controller:'ContactCtrl'
        })

        .when('/generalfeedbackerrorpublic', {
            templateUrl: "contact/contact-error-public.tpl.html",
            access: {
                authorizedRoles: [USER_ROLES.all]
            },
            controller:'ContactCtrl'
        });
    }])
    .controller('ContactCtrl', ['$scope', '$location',function($scope, $location){
        $scope.setActiveMenuItem('contacts');
        var currentPathWithSlash = $location.path();
        var currentPath = currentPathWithSlash.substring(1, currentPathWithSlash.length);
        $scope.setCurrentFooterPage(currentPath);
    }])
    .controller('GeneralFeedbackCtrl', ['$scope', 'ContactService', '$location',function($scope, ContactService, $location){

        $scope.setCurrentFooterPage('feedback');

        $scope.save = function(){
            var feedbackResource = ContactService.getFeedbackResource();
            feedbackResource.save($scope.feedback,
                function(response){
                    if($scope.isLogin()){
                        $location.path('/feedbacksuccess');
                    }else{
                        $location.path('/feedbacksuccesspublic');
                    }
                },
                function(response){
                    if($scope.isLogin()){
                        $location.path('/generalfeedbackerror');
                    }else{
                        $location.path('/generalfeedbackerrorpublic');
                    }
                }
            );
        };

        $scope.reset = function(){
            $scope.feedback = {};
        };

    }]);
