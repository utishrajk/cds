'use strict';
angular.module('bham', [
        'templates-app',
        'templates-common',
        'ngRoute',
        'bham.training',
        'bham.caremanagerModule',
        'bham.organizationModule',
        'bham.documentLibraryModule',
        'bham.directives',
        'bham.breadcrumbsModule',
        'bham.reminderModule',
        //'bham.activityModule',
        'bham.loginModule',
        'http-auth-interceptor',
        'spring-security-csrf-token-interceptor',
        'ngCookies',
        'bham.security',
        'bham.userService',
        'bham.loggingModule',
        'bham.errorModule',
        'bham.dataElementsModule',
        'bham.recommendationModule',
        'bham.contactModule',
        'ngDialog',
        'angular-growl',
        'bham.accessService',
        'bham.filters',
        'ngIdle',
        'ui.bootstrap'
    ])

    .config(['ngDialogProvider', 'growlProvider','$idleProvider', '$keepaliveProvider', function (ngDialogProvider, growlProvider, $idleProvider, $keepaliveProvider) {
        ngDialogProvider.setDefaults({
            showClose: true,
            closeByDocument: true,
            closeByEscape: true
        });

        growlProvider.globalPosition('top-center');
        growlProvider.globalDisableCountDown(true);

        //ngIdle Configuration
        $idleProvider.idleDuration(540);
        $idleProvider.warningDuration(5);
        $keepaliveProvider.interval(5);

    }])

    .run(['$rootScope', '$location', '$http', 'AuthenticationSharedService', 'Session', 'USER_ROLES', '$cookieStore', 'ngDialog', 'CareManagerService','$idle', '$keepalive', '$modal',
        function ($rootScope, $location, $http, AuthenticationSharedService, Session, USER_ROLES, $cookieStore, ngDialog, CareManagerService, $idle, $keepalive, $modal) {

            $rootScope.$on('$routeChangeStart', function (event, next) {

                $rootScope.authenticated = AuthenticationSharedService.isAuthenticated();
                $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
                $rootScope.userRoles = USER_ROLES;
                $rootScope.account = Session;

                var authorizedRoles = (angular.isDefined(next.access) && angular.isDefined(next.access.authorizedRoles) ) ? next.access.authorizedRoles : "";
                if (!AuthenticationSharedService.isAuthorized(authorizedRoles)) {
                    event.preventDefault();
                    if (AuthenticationSharedService.isAuthenticated()) {
                        // user is not allowed
                        $rootScope.$broadcast("event:auth-notAuthorized");
                    } else {
                        // user is not logged in
                        $rootScope.$broadcast("event:auth-loginRequired");
                    }
                } else {
                    // Check if the customer is still authenticated on the server
                    // Try to load a protected 1 pixel image.
                    if (!$rootScope.authenticated && $location.path() === "/register") {
                        $location.path('/register').replace();
                    }
                }
            });

            $rootScope.open = function(){
                ngDialog.open({ template: 'videopopup' ,
                    controller: ['$scope', '$rootScope' , function($scope, $rootScope){
                        $scope.toggleVideo = function(neverShow){
                            $rootScope.neverShowVideo = neverShow;
                        };
                    }]
                });
            };

            // Call when the the client is confirmed
            $rootScope.$on('event:auth-loginConfirmed', function (data) {
                if ($location.path() === "/login") {
                    if ( angular.isDefined( $cookieStore.get('account') )) {
                        var account = JSON.parse($cookieStore.get('account'));
                        if( !account.showTrainingVideo){
                            $rootScope.open();
                        }
                    }
                    //For ngIdle
                    $rootScope.startIdle();

                    $location.path('/dataelements').replace();
                }
            });

            // Call when the 401 response is returned by the server
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                Session.destroy();
                $rootScope.authenticated = false;
                if ($location.path() !== "/" && $location.path() !== "") {
                    $location.path('/login').replace();
                }
            });

            // Call when the 403 response is returned by the server
            $rootScope.$on('event:auth-notAuthorized', function (rejection) {
                $location.path('/error/403').replace();
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function () {
                $location.path('/login');
            });

            $rootScope.$on('ngDialog.closed', function (e, $dialog) {

                if ( angular.isDefined( $cookieStore.get('account') )) {
                    if($rootScope.neverShowVideo) {
                        var account = JSON.parse($cookieStore.get('account'));
                        var data = {
                            
                            showTrainingVideo : $rootScope.neverShowVideo
                        };
                        CareManagerService.updateTrainingVideoOption(data,
                            function(success){
                                console.log(success);
                            },
                            function(e){
                                console.log(e);
                            });
                    }
                }
            });

            //Logic fo ng-idle
            $rootScope.started = false;

            function closeModals() {
                if ($rootScope.warning) {
                    $rootScope.warning.close();
                    $rootScope.warning = null;
                }

                if ($rootScope.timedout) {
                    $rootScope.timedout.close();
                    $rootScope.timedout = null;
                }
            }

            $rootScope.$on('$idleStart', function() {
                closeModals();

                $rootScope.warning = $modal.open({
                    templateUrl: 'warning-dialog.html',
                    windowClass: 'modal-danger'
                });
            });

            $rootScope.$on('$idleEnd', function() {
                console.log("Logging out Current user...");
                $location.path('/logout');
                closeModals();
            });

            $rootScope.$on('$idleTimeout', function() {
                closeModals();
                $rootScope.timedout = $modal.open({
                    templateUrl: 'timedout-dialog.html',
                    windowClass: 'modal-danger'
                });
            });

            $rootScope.startIdle = function() {
                closeModals();
                $idle.watch();
                $rootScope.started = true;
            };

            $rootScope.stopIdle = function() {
                closeModals();
                $idle.unwatch();
                $rootScope.started = false;

            };

        }])

    .controller('AppCtrl', [ '$scope', '$location', 'BreadcrumbsService', '$route', '$cookieStore','$anchorScroll', '$timeout', 'Session','$http','AccessService', '$window',
        function ($scope, $location, BreadcrumbsService, $route, $cookieStore, $anchorScroll, $timeout, Session, $http, AccessService, $window ) {

            $scope.headnavbar = '../head-navbar.tpl.html';
            $scope.breadcrums = '../breadcrums.tpl.html';
            $scope.sidenavbar = '../side-navbar.tpl.html';
            $scope.footer = '../footer.tpl.html';
            $scope.contactnavbar = '../contact-head-navbar.tpl.html';

            $scope.gpraDataElementTemplate = 'dataelements/dataElementsGPRA.tpl.html';
            $scope.asiDataElementTemplate = 'dataelements/dataElementsASI.tpl.html';

            $scope.gpraRecDataElementsTableTemplate = 'recommendations/rec-gpra-dataelements-table.tpl.html';
            $scope.asiRecDataElementsTableTemplate = 'recommendations/rec-asi-dataelements-table.tpl.html';

            $scope.companyName = "Inflexxion, Inc.";
            $scope.projectName = "Behavioral Health: Clinical Decision Support";
            $scope.projectNameInitial = "Behavioral Health: Clinical Decision Support";
			$scope.USCountryCode= "US";
			
			$scope.US_TELEPHONE_DIGIT_LENGTH = 10;
            $scope.US_ZIPCODE_DIGIT_LENGTH = 5;
            $scope.appVersion = "(version 0.0.1)";
            $scope.neverShowTrainingVideo = false;
            $scope.US_COUNTRY_CODE = "US";

            $scope.notification = "";

            //Date pattern used by all the forms to check the format of the date
            $scope.datePattern = /(0[1-9]|1[012])[ \/.](0[1-9]|[12][0-9]|3[01])[ \/.](19|20)\d\d/;

            $scope.emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

            $scope.passwordPattern = /(?=^.{12,}$)(?=.*\d)(?=.*\W+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;

            $scope.phonePattern = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;

            $scope.breadcrumbs = function () {
                var breadcrumbs = BreadcrumbsService.getAll();

                //updateing the current page
                if (breadcrumbs.length >= 1) {
                    // Update the current page
                    $scope.currentPage = breadcrumbs[0].name;
                }

                return breadcrumbs;
            };

            $scope.redirect = function (path) {
                $location.path(path);
                $route.reload();
            };

            $scope.getEntityById = function (entityList, entityId) {
                for (var i = 0; i < entityList.length; i++) {
                    if (entityList[i].id === parseInt(entityId)) {
                        return entityList[i];
                    }
                }
            };

            $scope.getEntityByCode = function (entityList, code) {
                for (var i = 0; i <   entityList.length; i++) {
                    if (  entityList[i].code === code) {
                        return  entityList[i];
                    }
                }
            };

            $scope.isNullOrUndefined = function(value){
                return angular.isUndefined(value) || value === null;
            };

            $scope.deleteEntityById = function (entityList, entityId) {
                for (var i = 0; i < entityList.length; i++) {
                    if (entityList[i].id === parseInt(entityId)) {
                        entityList.splice(i, 1);
                        break;
                    }
                }
            };

            $scope.openCustomMenu = false;

            $scope.setSelectedPatient = function(patient){
                $scope.selectedPatient = patient;
            };

            // Display Admin tab based upon roles
            $scope.isAdmin = function () {
                for(var i=0; i < Session.userRoles.length; i++){
                    if(Session.userRoles[i]==="ROLE_ADMIN"){
                        return true;
                    }
                }
                return false;
            };


            $scope.setActiveMenuItem = function (menuItem) {
                $scope.activeMenuItem = menuItem;
                $scope.activeSubMenuItem = false;
                $scope.highlightMenu = false;
                $scope.highlightPatientMenu = false;
                $scope.activePatientSubMenuItem = false;
                $scope.enableDropDownMenu = false;
            };

            $scope.activeSubMenuItem = false;
            $scope.highlightMenu = false;
            $scope.highlightPatientMenu = false;
            $scope.activePatientSubMenuItem = false;

            $scope.toggleToolsMenuItem = function(menuItem){
                $scope.activeMenuItem = menuItem;
                $scope.highlightPatientMenu = false;
                $scope.activePatientSubMenuItem = false;

                $scope.highlightMenu = !$scope.highlightMenu ;
                $scope.activeSubMenuItem = !$scope.activeSubMenuItem;
            };

            $scope.selectToolsSubMenu = function(menuItem){
                $scope.activeMenuItem = menuItem;
                $scope.activeSubMenuItem = true;
                $scope.enableDropDownMenu = false;
            };

//            $scope.togglePatientMenuItem = function(menuItem){
//                $scope.activeMenuItem = menuItem;
//                $scope.highlightMenu = false;
//                $scope.activeSubMenuItem = false;
//
//                $scope.highlightPatientMenu = !$scope.highlightPatientMenu;
//                $scope.activePatientSubMenuItem = !$scope.activePatientSubMenuItem;
//            };
//
//            $scope.selectPatientSubMenu = function(menuItem){
//                $scope.activeMenuItem = menuItem;
//                $scope.activePatientSubMenuItem = true;
//                $scope.enableDropDownMenu = false;
//            };

            //// End Code


            $scope.populateCustomPatientMenu = function (selectedPatient) {
                $scope.selectedPatient = selectedPatient;
                $scope.selectedPatientId = selectedPatient.id;
                $scope.selectedPatientFullName = angular.isDefined(selectedPatient) ? selectedPatient.fullName:'';

                $scope.collapseDemographicsAccordion = "";
                $scope.toggleDemographicsAccordionClass = false;

                $scope.collapseConditionsAccordion = "";
                $scope.toggleConditionsAccordionClass = false;

                $scope.collapseSocialhistoryAccordion = "";
                $scope.toggleSocialhistoryAccordionClass = false;

                $scope.collapseProcedureAccordion = "";
                $scope.toggleProcedureAccordionClass = false;

                $scope.enableCustomPatientMenu();
                $scope.removeActiveClassInSideNavBar();
            };

            $scope.selectTreatmentPlanMenu = function () {
                $scope.treatmentPlanMenuitem = true;
            };

            $scope.selectSummaryRecordMenu = function () {
                $scope.summaryCareRecordMenuitem = true;
            };

            $scope.onToggledDemographicsAccordion = function () {
                $scope.collapseDemographicsAccordion = $scope.collapseDemographicsAccordion === "collapse" ? '' : 'collapse';
                $scope.toggleDemographicsAccordionClass = !$scope.toggleDemographicsAccordionClass;
            };

            $scope.onToggledConditionsAccordion = function () {
                $scope.collapseConditionsAccordion = $scope.collapseConditionsAccordion === "collapse" ? '' : 'collapse';
                $scope.toggleConditionsAccordionClass = !$scope.toggleConditionsAccordionClass;
            };

            $scope.onToggledSocialHistoryAccordion = function () {
                $scope.collapseSocialhistoryAccordion = $scope.collapseSocialhistoryAccordion === "collapse" ? '' : 'collapse';
                $scope.toggleSocialhistoryAccordionClass = !$scope.toggleSocialhistoryAccordionClass;
            };

            $scope.onToggledProcedureAccordion = function () {
                $scope.collapseProcedureAccordion = $scope.collapseProcedureAccordion === "collapse" ? '' : 'collapse';
                $scope.toggleProcedureAccordionClass = !$scope.toggleProcedureAccordionClass;
            };

            $scope.removeActiveClassInSideNavBar = function () {
                $scope.trainingMenuitem = false;
                $scope.myAccountMenuItem = false;
                $scope.organizationMenuitem = false;
                $scope.patientListMenuitem = false;
                $scope.reportsMenuitem = false;
                $scope.toolsResourceMenuitem = false;
                $scope.conditionsMenuitem = false;
                $scope.socialHistoryMenuitem = false;
                $scope.procedureMenuitem = false;
                $scope.treatmentPlanMenuitem = false;
                $scope.demographicsMenuitem = false;
                $scope.summaryCareRecordMenuitem = false;
                $scope.remindersMenuitem = false;
                $scope.feedbackMenuitem = false;
                $scope.activityMenuitem = false;
            };

            $scope.addActiveClassInSideNavBar = function (menuItem) {
                if (menuItem === 'training') {
                    $scope.trainingMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'myAccount') {
                    $scope.myAccountMenuItem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'organization') {
                    $scope.organizationMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'patientList') {
                    $scope.patientListMenuitem = true;
                    $scope.disableCustomPatientMenu();
                }else if (menuItem === 'reports') {
                    $scope.reportsMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'feedback') {
                    $scope.feedbackMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'toolsAndResource') {
                    $scope.toolsResourceMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'reminders') {
                    $scope.remindersMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'conditions') {
                    $scope.enableCustomPatientMenu();
                    $scope.conditionsMenuitem = true;
                } else if (menuItem === 'socialHistory') {
                    $scope.enableCustomPatientMenu();
                    $scope.socialHistoryMenuitem = true;
                } else if (menuItem === 'procedure') {
                    $scope.enableCustomPatientMenu();
                    $scope.procedureMenuitem = true;
                } else if (menuItem === 'treatmentPlan') {
                    $scope.enableCustomPatientMenu();
                    $scope.treatmentPlanMenuitem = true;
                } else if (menuItem === 'activity') {
                    $scope.activityMenuitem = true;
                    $scope.disableCustomPatientMenu();
                } else if (menuItem === 'demographics') {
                    $scope.enableCustomPatientMenu();
                    $scope.demographicsMenuitem = true;
                }
                else if (menuItem === 'summaryCareRecord') {
                    $scope.enableCustomPatientMenu();
                    $scope.summaryCareRecordMenuitem = true;
                }
            };

            $scope.onSelectmenu = function (menuItem) {
                $scope.enableDropDownMenu = false;
                $scope.removeActiveClassInSideNavBar();
                $scope.addActiveClassInSideNavBar(menuItem);
            };

            $scope.enableCustomPatientMenu = function () {
                $scope.openCustomMenu = true;
            };

            $scope.disableCustomPatientMenu = function () {
                $scope.openCustomMenu = false;
            };

            $scope.toJSON = function (obj) {
                return JSON.stringify(obj, null, 2);
            };

            //hide or show elements use in demographics table
            //Determine whether to route to demographics or patientlist page
            $scope.isDemographics = false;

            $scope.toggleDemographicMode = function (isDemographics) {
                $scope.isDemographics = isDemographics;
            };

            $scope.showPatientProfile = function () {
                $location.path("/patient/" + $scope.selectedPatientId + "/patientprofile");
            };

            $scope.isFutureDate = function (currentDate) {
                var result = false;
                if($scope.isNullOrUndefined(currentDate)){
                    return result;
                }else if (currentDate) {
                    var today = new Date().getTime();
                    var newDate = new Date(currentDate).getTime();

                    if (newDate > today) {
                        result = true;
                    }
                }
                return result;

            };

            $scope.isEndDateBeforeStartDate = function (startDate, endDate) {
                var result = false;
                if($scope.isNullOrUndefined(startDate) || $scope.isNullOrUndefined(endDate)){
                    return result;
                }else if (startDate && endDate) {
                    var start = new Date(startDate);
                    var end = new Date(endDate);
                    if (end < start) {
                        result = true;
                    }
                }
                return result;
            };
            
            $scope.getAge = function(dateString) {
                var today = new Date();
                var birthDate = new Date(dateString);
                var age = today.getFullYear() - birthDate.getFullYear();
                var m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }
                return age;	
            };

            $scope.enableDropDownMenu = false;

            $scope.toggleDropDownMenu = function () {
                $scope.enableDropDownMenu = !$scope.enableDropDownMenu;
            };

            $scope.gotoTop = function () {
                //Id of the element you want to scroll to
                $location.hash('top');
                // call $anchorScroll()
                $anchorScroll();
            };

            $scope.goToBookmark = function (bookmark) {
                //Id of the element you want to scroll to
                $location.hash(bookmark);
                // call $anchorScroll()
                $anchorScroll();
            };

            // Create patient
            $scope.showError = function (ngModelController, error) {
                return ngModelController.$error[error];
            };

            $scope.isDirty = function (ngModelController) {
                return ngModelController.$dirty;
            };

            $scope.isValidNumber = function (number) {
                var result = false;
                if($scope.isNullOrUndefined(number)){
                    return result;
                }else {
                    var trimmedNumber = number.trim();
                    if ( !isNaN(trimmedNumber) && (trimmedNumber.length > 0)) {
                        result = true;
                    }
                }
                return result;
            };

            $scope.isUnitedState = function(countryCode){
                return countryCode === "US";
            };

            $scope.isValidContactNumber = function (number, countryCode, requireLength) {
                var result = false;
                if( $scope.isNullOrUndefined(number) ){
                    result = false;
                }else {
                    result = $scope.isUnitedState(countryCode) && (  number.length === requireLength );
                }
                return result;
            };

            $scope.forgotPasswordUser = function (user) {
                $scope.user = user;
            };

            $scope.urlHistory = [];

            $scope.$on('$routeChangeSuccess', function () {
                if ($location.$$absUrl.split('#')[1] !== $scope.urlHistory[$scope.urlHistory.length - 1]) {
                    $scope.urlHistory.push($location.$$absUrl.split('#')[1]);
                }
                $scope.currentFooterPage = ""; //Refresh the CSS that is used to disable the links in the footer
            });

            var back = function(){
                $scope.urlHistory.pop();
                $location.path($scope.urlHistory[$scope.urlHistory.length - 1]);
            };

            //Back from back button
            $scope.goBack = function () {
                back();
            };

            //Back from browser back button
            $scope.$back = function () {
                back();
            };


            $scope.setCurrentFooterPage = function(currentFooterPage){
                $scope.currentFooterPage = currentFooterPage;
            };

            $scope.validatePhoneLength = function (phoneNumber) {
                return $scope.isValidNumber(phoneNumber) && $scope.isValidContactNumber(phoneNumber, $scope.US_COUNTRY_CODE, $scope.US_TELEPHONE_DIGIT_LENGTH);
            };
          
            $scope.tempRegisterUser = {};
			
			$scope.isLogin = function(){
                if ( angular.isDefined( $cookieStore.get('account') )) {
                    var account = JSON.parse($cookieStore.get('account'));
                    if( angular.isDefined(account)){
                        return true;
                    }
                }
                return false;
            };

            $scope.composeSecurityQuestion = function(securityQuestion, securityQuestionCode){
                var question = [];

                for(var i = 0 ; i < securityQuestion.length ; i++){
                    if(securityQuestion[i].code !== securityQuestionCode){
                        question.push(securityQuestion[i]);
                    }
                }

                return question;
            };

            $scope.tempRegisterUser = {};

            $scope.$on('$routeChangeStart', function () {

                if($scope.isLogin()){
                    var access = {
                        path: $location.path(),
                        description: "Accessing:" + $location.path(),
                        host: $window.location.host
                    };
                    AccessService.save(access);
                }
            });
        }]);
