'use strict';

angular.module("bham.dataElementsModule",
	[
     'bham.security', 
	 'ngDialog',
	 'bham.dataElementsService',
     'bham.loggingModule',
     'ngProgress',
     'ui.bootstrap',
     'bham.organizationService'
	])

    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/dataelements', {
                controller: 'DataElementsCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.user, USER_ROLES.admin]
                },
                templateUrl: "dataelements/dataElements.tpl.html",
                resolve: {
                    loadedData: ['OrganizationService',  '$log', '$q', '$location', 'ErrorService', function(OrganizationService,  $log, $q, $location, ErrorService){
                            // Set up a promise to return
                            var deferred = $q.defer();

                            var assessmentResource = OrganizationService.getAssessmentResource();
                            var assessmentData = assessmentResource.get(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);});

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([assessmentData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                    }]
                }

            }).when('/dataelements/:requestId', {
                controller: 'DataElementsCtrl2',
                access: {
                    authorizedRoles: [USER_ROLES.user, USER_ROLES.admin]
                },
                templateUrl: "dataelements/dataElements.tpl.html",
                resolve: {
                    loadedData: ['DataElementsService',  '$log', '$q','$route', '$location', 'ErrorService', function(DataElementsService,  $log, $q, $route, $location, ErrorService){

                        var requestId = $route.current.params.requestId;

                        if(requestId !== null){
                            // Set up a promise to return
                            var deferred = $q.defer();

                            var dataElementsResource = DataElementsService.retrieve();
                            var dataelementsData = dataElementsResource.get(
                                {requestId: requestId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);});

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([dataelementsData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            $log.error("Data elements: invalid request Id.");
                            $location.path('/error');
                        }


                    }]
                }
            });
    }])

    .config(['ngDialogProvider', function (ngDialogProvider) {
        ngDialogProvider.setDefaults({
            showClose: false,
            closeByDocument: true,
            closeByEscape: true
        });
    }])
    .controller('DataElementsCtrl', ['$scope','$rootScope', 'DataElementsService', '$log', 'ErrorService', '$location','ngProgress','$routeParams','$modal','loadedData', function($scope, $rootScope, DataElementsService, $log, ErrorService, $location, ngProgress, $routeParams, $modal, loadedData){

       //Select default menu.
        $scope.setActiveMenuItem('patientdataelements');

        $scope.showDataelementForm = true;
        $scope.drugUse = {};
        $scope.mentalPhysicalProblem = {};

        $scope.validateNumberOrX = function(field){
            if(field === 'm6_days_medical_problem'){
                $scope.m6_days_medical_problem =  !$scope.isANumberBetweenZeroAndThirtyOrX($scope.patient.m6_days_medical_problem);
            }else if(field === 'e19_days_employment_problem'){
                $scope.e19_days_employment_problem =  !$scope.isANumberBetweenZeroAndThirtyOrX($scope.patient.e19_days_employment_problem);
            }else if(field === 'l27_days_illegal'){
                $scope.l27_days_illegal =  !$scope.isANumberBetweenZeroAndThirtyOrX($scope.patient.l27_days_illegal);
            }else if(field === 'f30_days_conflict_family'){
                $scope.f30_days_conflict_family =  !$scope.isANumberBetweenZeroAndThirtyOrX($scope.patient.f30_days_conflict_family);
            }
        };

        $scope.isANumberBetweenZeroAndThirtyOrX = function(value){

            if(isNaN(value) && ( value === "X" || value === "x")){
                return true;
            }else if( !isNaN(value)&&  $scope.isInt(value) &&  (( parseInt(value) >= 0) && ( parseInt(value) <= 30)) ){
                return true;
            }else{
                return false;
            }
        };

        $scope.isInt = function(value){
            if((parseFloat(value) === parseInt(value)) && !isNaN(value) && ((value.toString()).indexOf('.') === -1) ){
                return true;
            } else {
                return false;
            }
        };

        $scope.validateGender = function(){
            if($scope.patient.genderCode === 'MA' || $scope.patient.genderCode === 'FE' || $scope.patient.genderCode === 'TG' || parseInt($scope.patient.genderCode) === -7 ){
                 $scope.disableOtherGender = true;
                $scope.patient.otherDescription = null;
                $scope.isOtherGenderInvalid = false;
            }else{

                if( $scope.patient.genderCode !== null){
                    $scope.disableOtherGender = false;
                    $scope.isOtherGenderInvalid = $scope.validateOtherGender();
                }

            }
        };

        $scope.ValidateOther = function(){
            $scope.isOtherGenderInvalid = $scope.validateOtherGender();
        };

        $scope.validateOtherGender = function(){
            return !( angular.isDefined($scope.patient.otherDescription) && ($scope.patient.otherDescription !== null) && $scope.patient.otherDescription.length > 0) ;
        };


        $scope.onNumberOfDaysChange = function( field ){
            switch (field) {
                case 'heroinDayCount':
                     {
                            $scope.drugUse.heroinDayCount= "";
                            $scope.isValidHeroinDayCount = (!$scope.isValidNumberOfDays($scope.patient.heroinDayCount))  ;
                     }
                     break;
                case 'morphineCount':
                     {
                         $scope.drugUse.morphineCount= "";
                         $scope.isValidMorphineCount = (!$scope.isValidNumberOfDays($scope.patient.morphineCount))  ;
                     }
                    break;
                case 'diluadidCount':
                    {
                        $scope.drugUse.diluadidCount= "";
                        $scope.isValidDiluadidCount = (!$scope.isValidNumberOfDays($scope.patient.diluadidCount)) ;
                    }
                        break;
                case 'demerolCount':
                    {
                        $scope.drugUse.demerolCount= "";
                        $scope.isValidDemerolCount = (!$scope.isValidNumberOfDays($scope.patient.demerolCount))  ;
                    }
                    break;
                case 'percocetCount':
                    {
                        $scope.drugUse.percocetCount= "";
                        $scope.isValidPercocetCount = (!$scope.isValidNumberOfDays($scope.patient.percocetCount));
                    }
                    break;
                case 'darvonCount':
                    {
                        $scope.drugUse.darvonCount= "";
                        $scope.isValidDarvonCount = (!$scope.isValidNumberOfDays($scope.patient.darvonCount))  ;
                    }
                    break;
                case 'codeineCount':
                    {
                        $scope.drugUse.codeineCount= "";
                        $scope.isValidCodeineCount = (!$scope.isValidNumberOfDays($scope.patient.codeineCount)) ;
                    }
                    break;
                case 'tylenolCount':
                    {
                        $scope.drugUse.tylenolCount= "";
                        $scope.isValidTylenolCount = (!$scope.isValidNumberOfDays($scope.patient.tylenolCount));
                    }
                    break;
                case 'oxycontinCount':
                    {
                        $scope.drugUse.oxycontinCount= "";
                        $scope.isValidOxycontinCount = (!$scope.isValidNumberOfDays($scope.patient.oxycontinCount) ) ;
                    }
                    break;
                case 'methadoneCount':
                    {
                        $scope.drugUse.methadoneCount= "";
                        $scope.isValidMethadoneCount = (!$scope.isValidNumberOfDays($scope.patient.methadoneCount) );
                    }
                    break;
                case 'depressionCount':
                {
                    $scope.mentalPhysicalProblem.depressionCount= "";
                    $scope.isInValidDepressionCount = (!$scope.isValidNumberOfDays($scope.patient.depressionCount) ) ;
                }
                    break;

                case 'anxietyCount':
                {
                    $scope.mentalPhysicalProblem.anxietyCount= "";
                    $scope.isInValidAnxietyCount = (!$scope.isValidNumberOfDays($scope.patient.anxietyCount) );
                }
                    break;

                case 'violentCount':
                {
                    $scope.mentalPhysicalProblem.violentCount= "";
                    $scope.isInValidViolentCount = (!$scope.isValidNumberOfDays($scope.patient.violentCount) );
                }
                    break;

                case 'psychMedicationCount':
                {
                    $scope.mentalPhysicalProblem.psychMedicationCount= "";
                    $scope.isInValidPsychMedicationCount = (!$scope.isValidNumberOfDays($scope.patient.psychMedicationCount) ) ;
                }
                    break;
                case 'd2a_intoxication_30':
                {
                    $scope.isInvalidAlcoholToxicationNumberOfDays = (!$scope.isValidNumberOfDays($scope.patient.d2a_intoxication_30)) ;
                }
                    break;
                case 'd3a_heroin_30':
                {
                    $scope.isInvalidHeroinNumberOfDays = (!$scope.isValidNumberOfDays($scope.patient.d3a_heroin_30))  ;
                }
                    break;
                case 'd4a_methadone_30':
                {
                    $scope.isInvalidMethadoneNumberOfDays = (!$scope.isValidNumberOfDays($scope.patient.d4a_methadone_30));
                }
                    break;
                case 'd5a_opiates_30':
                {
                    $scope.isInvalidOpiatesAnalgesicsNumberOfDays = (!$scope.isValidNumberOfDays($scope.patient.d5a_opiates_30)) ;
                }
                    break;
            }
        };

        /*
         * Check if it is an integer between 0 and 99999
         */

        $scope.validateIllegalSourceOfIncome = function(){
            $scope.illigalSource =   !($scope.isInt($scope.patient.e17_income_illegal) && ( parseInt($scope.patient.e17_income_illegal) >= 0 && parseInt($scope.patient.e17_income_illegal) <= 99999 ));
        };

        $scope.onRouteChange = function( field ){
            switch (field) {
                case 'heroinRouteCode':
                {
                    $scope.isInvalidHeroinRoute =  (!$scope.isValidRouteValue ($scope.patient.heroinRouteCode, $scope.drugUse.heroinRouteCode)) ;
                    $scope.drugUse.heroinRouteCode= "";
                }
                    break;
                case 'morphineRouteCode':
                {
                    $scope.isInvalidMorphineRoute =  (!$scope.isValidRouteValue ($scope.patient.morphineRouteCode, $scope.drugUse.morphineRouteCode)) ;
                    $scope.drugUse.morphineRouteCode= "";
                }
                    break;
                case 'diluadidRouteCode':
                {
                    $scope.isInvalidDiluadidRoute =  (!$scope.isValidRouteValue ($scope.patient.diluadidRouteCode, $scope.drugUse.diluadidRouteCode))  ;
                    $scope.drugUse.diluadidRouteCode= "";
                }
                    break;
                case 'demerolRouteCode':
                {
                    $scope.isInvalidDemerolRoute =  (!$scope.isValidRouteValue ($scope.patient.demerolRouteCode, $scope.drugUse.demerolRouteCode)) ;
                    $scope.drugUse.demerolRouteCode= "";
                }
                    break;
                case 'percocetRouteCode':
                {
                    $scope.isInvalidPercocetRoute =  (!$scope.isValidRouteValue ($scope.patient.percocetRouteCode, $scope.drugUse.percocetRouteCode)) ;
                    $scope.drugUse.percocetRouteCode= "";
                }
                    break;
                case 'darvonRouteCode':
                {
                    $scope.isInvalidDarvonRoute =  (!$scope.isValidRouteValue ($scope.patient.darvonRouteCode, $scope.drugUse.darvonRouteCode)) ;
                    $scope.drugUse.darvonRouteCode= "";
                }
                    break;
                case 'codeineRouteCode':
                {
                    $scope.isInvalidCodeineRoute =  (!$scope.isValidRouteValue ($scope.patient.codeineRouteCode, $scope.drugUse.codeineRouteCode)) ;
                    $scope.drugUse.codeineRouteCode= "";
                }
                    break;
                case 'tylenolRouteCode':
                {
                    $scope.isInvalidTylenolRoute =  (!$scope.isValidRouteValue ($scope.patient.tylenolRouteCode, $scope.drugUse.tylenolRouteCode)) ;
                    $scope.drugUse.tylenolRouteCode= "";
                }
                    break;
                case 'oxycontinRouteCode':
                {
                    $scope.isInvalidOxycontinRoute =  (!$scope.isValidRouteValue ($scope.patient.oxycontinRouteCode, $scope.drugUse.oxycontinRouteCode))  ;
                    $scope.drugUse.oxycontinRouteCode= "";
                }
                    break;
                case 'methadoneRouteCode':
                {
                    $scope.isInvalidMethadoneRoute =  (!$scope.isValidRouteValue ($scope.patient.methadoneRouteCode, $scope.drugUse.methadoneRouteCode)) ;
                    $scope.drugUse.methadoneRouteCode= "";
                }
                    break;

            }

        };

        /*
        *  Check if number of days an integer between 0 and 30.
        * */
        $scope.isValidNumberOfDays = function(value){
           return  $scope.isInt(value) && ( parseInt(value) >= 0 && parseInt(value) <= 30 );
        };

        $scope.isValidAge = function(){
            var isInteger = angular.isDefined($scope.patient.age) && $scope.isInt($scope.patient.age);
            $scope.isAgeOk =  isInteger&& ( (parseInt($scope.patient.age) > 0) && (parseInt($scope.patient.age) <= 89));
            $scope.isOverAge = isInteger && (parseInt($scope.patient.age) > 89);

        };


        $scope.isValidRouteValue = function(selectValue, radioValue){

            if(( (parseInt(selectValue) >= 1) && (parseInt(selectValue) <= 5)) && ( (parseInt(radioValue) === -7) || (parseInt(radioValue) === -8) || !angular.isDefined(radioValue) ||  radioValue === "" || radioValue === null )){
                return true;
            }else {
                return false;
            }
        };

        $scope.onRadioButtonChange = function(field){

            switch (field) {
                case 'heroinDayCount':
                     {
                        $scope.patient.heroinDayCount= "";
                        $scope.isValidHeroinDayCount = false;
                     }
                    break;
                case 'morphineCount':
                     {
                         $scope.patient.morphineCount= "";
                         $scope.isValidMorphineCount= false;
                     }
                    break;
                case 'diluadidCount':
                    {
                        $scope.patient.diluadidCount= "";
                        $scope.isValidDiluadidCount = false;
                    }
                    break;
                case 'demerolCount':
                    {
                        $scope.patient.demerolCount= "";
                        $scope.isValidDemerolCount= false;
                    }
                    break;
                case 'percocetCount':
                    {
                        $scope.patient.percocetCount= "";
                        $scope.isValidPercocetCount = false;
                    }
                    break;
                case 'darvonCount':
                    {
                        $scope.patient.darvonCount= "";
                        $scope.isValidDarvonCount= false;
                    }
                break;
                case 'codeineCount':
                {
                    $scope.patient.codeineCount= "";
                    $scope.isValidCodeineCount= false;
                }
                    break;
                case 'tylenolCount':
                {
                    $scope.patient.tylenolCount= "";
                    $scope.isValidTylenolCount= false;
                }
                    break;
                case 'oxycontinCount':
                {
                    $scope.patient.oxycontinCount= "";
                    $scope.isValidOxycontinCount= false;
                }
                    break;
                case 'methadoneCount':
                {
                    $scope.patient.methadoneCount= "";
                    $scope.isValidMethadoneCount = false;
                }
                    break;
                case 'heroinRouteCode':
                {
                    $scope.patient.heroinRouteCode= "";
                    $scope.isInvalidHeroinRoute = false;
                }
                    break;
                case 'morphineRouteCode':
                {
                    $scope.patient.morphineRouteCode= "";
                    $scope.isInvalidMorphineRoute = false;
                }
                    break;
                case 'diluadidRouteCode':
                {
                    $scope.patient.diluadidRouteCode= "";
                    $scope.isInvalidDiluadidRoute = false;
                }
                    break;
                case 'demerolRouteCode':
                {
                    $scope.patient.demerolRouteCode= "";
                    $scope.isInvalidDemerolRoute = false;
                }
                    break;
                case 'percocetRouteCode':
                {
                    $scope.patient.percocetRouteCode= "";
                    $scope.isInvalidPercocetRoute = false;
                }
                    break;
                case 'darvonRouteCode':
                {
                    $scope.patient.darvonRouteCode= "";
                    $scope.isInvalidDarvonRoute = false;
                }
                    break;
                case 'codeineRouteCode':
                {
                    $scope.patient.codeineRouteCode= "";
                    $scope.isInvalidCodeineRoute = false;
                }
                    break;
                case 'tylenolRouteCode':
                {
                    $scope.patient.tylenolRouteCode= "";
                    $scope.isInvalidTylenolRoute = false;
                }
                    break;
                case 'oxycontinRouteCode':
                {
                    $scope.patient.oxycontinRouteCode= "";
                    $scope.isInvalidOxycontinRoute = false;
                }
                    break;
                case 'methadoneRouteCode':
                {
                    $scope.patient.methadoneRouteCode= "";
                    $scope.isInvalidMethadoneRoute = false;
                }
                    break;

                case 'depressionCount':
                {
                    $scope.patient.depressionCount= "";
                    $scope.isInValidDepressionCount = false;
                }
                    break;

                case 'anxietyCount':
                {
                    $scope.patient.anxietyCount= "";
                    $scope.isInValidAnxietyCount = false;
                }
                    break;

                case 'violentCount':
                {
                    $scope.patient.violentCount= "";
                    $scope.isInValidViolentCount = false;
                }
                    break;

                case 'psychMedicationCount':
                {
                    $scope.patient.psychMedicationCount= "";
                    $scope.isInValidPsychMedicationCount = false;
                }
                    break;

            }
        };



        $scope.getComplexField = function(number, refusedOrDonnotknow){
            if(angular.isDefined(refusedOrDonnotknow) && ((parseInt(refusedOrDonnotknow) === -7) ||(parseInt(refusedOrDonnotknow) === -8) )){
                return refusedOrDonnotknow;
            }else if(angular.isDefined(number) && (!isNaN(number))){
                return number;
            }
        };

        $scope.updatePatientWithComplexFields = function(){
            $scope.dataelements = {};

            angular.copy($scope.patient, $scope.dataelements);

            //Gender Code
            if(!angular.isDefined($scope.patient.genderCode) || $scope.patient.genderCode === null){
                $scope.dataelements.genderCode = "UN";
            }

            //3 . Drug Use
            $scope.dataelements.heroinDayCount = $scope.getComplexField($scope.patient.heroinDayCount,$scope.drugUse.heroinDayCount );
            $scope.dataelements.morphineCount = $scope.getComplexField($scope.patient.morphineCount,$scope.drugUse.morphineCount );
            $scope.dataelements.diluadidCount = $scope.getComplexField($scope.patient.diluadidCount,$scope.drugUse.diluadidCount );
            $scope.dataelements.demerolCount = $scope.getComplexField($scope.patient.demerolCount,$scope.drugUse.demerolCount );
            $scope.dataelements.percocetCount = $scope.getComplexField($scope.patient.percocetCount,$scope.drugUse.percocetCount );
            $scope.dataelements.darvonCount = $scope.getComplexField($scope.patient.darvonCount,$scope.drugUse.darvonCount );
            $scope.dataelements.codeineCount = $scope.getComplexField($scope.patient.codeineCount,$scope.drugUse.codeineCount );
            $scope.dataelements.tylenolCount = $scope.getComplexField($scope.patient.tylenolCount,$scope.drugUse.tylenolCount );
            $scope.dataelements.oxycontinCount = $scope.getComplexField($scope.patient.oxycontinCount,$scope.drugUse.oxycontinCount );
            $scope.dataelements.methadoneCount = $scope.getComplexField($scope.patient.methadoneCount,$scope.drugUse.methadoneCount );

            //Route
            $scope.dataelements.heroinRouteCode = $scope.getComplexField($scope.patient.heroinRouteCode,$scope.drugUse.heroinRouteCode );
            $scope.dataelements.morphineRouteCode = $scope.getComplexField($scope.patient.morphineRouteCode,$scope.drugUse.morphineRouteCode );
            $scope.dataelements.diluadidRouteCode = $scope.getComplexField($scope.patient.diluadidRouteCode,$scope.drugUse.diluadidRouteCode );
            $scope.dataelements.demerolRouteCode = $scope.getComplexField($scope.patient.demerolRouteCode,$scope.drugUse.demerolRouteCode );
            $scope.dataelements.percocetRouteCode = $scope.getComplexField($scope.patient.percocetRouteCode,$scope.drugUse.percocetRouteCode );
            $scope.dataelements.darvonRouteCode = $scope.getComplexField($scope.patient.darvonRouteCode,$scope.drugUse.darvonRouteCode );
            $scope.dataelements.codeineRouteCode = $scope.getComplexField($scope.patient.codeineRouteCode,$scope.drugUse.codeineRouteCode );
            $scope.dataelements.tylenolRouteCode = $scope.getComplexField($scope.patient.tylenolRouteCode,$scope.drugUse.tylenolRouteCode );
            $scope.dataelements.oxycontinRouteCode = $scope.getComplexField($scope.patient.oxycontinRouteCode,$scope.drugUse.oxycontinRouteCode );
            $scope.dataelements.methadoneRouteCode = $scope.getComplexField($scope.patient.methadoneRouteCode,$scope.drugUse.methadoneRouteCode );

            // 5.
            $scope.dataelements.depressionCount = $scope.getComplexField($scope.patient.depressionCount,$scope.mentalPhysicalProblem.depressionCount );
            $scope.dataelements.anxietyCount = $scope.getComplexField($scope.patient.anxietyCount,$scope.mentalPhysicalProblem.anxietyCount );
            $scope.dataelements.violentCount = $scope.getComplexField($scope.patient.violentCount,$scope.mentalPhysicalProblem.violentCount );
            $scope.dataelements.psychMedicationCount = $scope.getComplexField($scope.patient.psychMedicationCount,$scope.mentalPhysicalProblem.psychMedicationCount );

        };


        $scope.createComplexFields = function(dataElement){

            $scope.isInValidAge = $scope.isValidAge();

            //Gender Code
            if(dataElement.genderCode === 'MA' || dataElement.genderCode === 'FE' || dataElement.genderCode === 'TG' || parseInt(dataElement.genderCode) === -7 ){
                $scope.patient.genderCode = dataElement.genderCode;
                $scope.disableOtherGender = true;
            }else{
                $scope.disableOtherGender = false;
                $scope.patient.otherDescription = dataElement.genderCode;
            }

            //For GPRA
            if(dataElement.asi === false && dataElement.gpra === true){
                //3 . Drug Use

                $scope.drugUse.heroinDayCount = $scope.setCountOrRouteRadioValue(dataElement.heroinDayCount,'heroinDayCount' ) ;
                $scope.drugUse.morphineCount = $scope.setCountOrRouteRadioValue(dataElement.morphineCount,'morphineCount' );
                $scope.drugUse.diluadidCount = $scope.setCountOrRouteRadioValue(dataElement.diluadidCount,'diluadidCount' );
                $scope.drugUse.demerolCount = $scope.setCountOrRouteRadioValue(dataElement.demerolCount,'demerolCount' );
                $scope.drugUse.percocetCount = $scope.setCountOrRouteRadioValue(dataElement.percocetCount,'percocetCount' ) ;
                $scope.drugUse.darvonCount = $scope.setCountOrRouteRadioValue(dataElement.darvonCount,'darvonCount' );
                $scope.drugUse.codeineCount = $scope.setCountOrRouteRadioValue(dataElement.codeineCount,'codeineCount' );
                $scope.drugUse.tylenolCount = $scope.setCountOrRouteRadioValue(dataElement.tylenolCount,'tylenolCount' ) ;
                $scope.drugUse.oxycontinCount = $scope.setCountOrRouteRadioValue(dataElement.oxycontinCount,'oxycontinCount' );
                $scope.drugUse.methadoneCount = $scope.setCountOrRouteRadioValue(dataElement.methadoneCount,'methadoneCount' );

                //Route
                $scope.drugUse.heroinRouteCode =  $scope.setCountOrRouteRadioValue(dataElement.heroinRouteCode,'heroinRouteCode' ) ;
                $scope.drugUse.morphineRouteCode = $scope.setCountOrRouteRadioValue(dataElement.morphineRouteCode,'morphineRouteCode' ) ;
                $scope.drugUse.diluadidRouteCode =  $scope.setCountOrRouteRadioValue(dataElement.diluadidRouteCode,'diluadidRouteCode' ) ;
                $scope.drugUse.demerolRouteCode = $scope.setCountOrRouteRadioValue(dataElement.demerolRouteCode,'demerolRouteCode' ) ;
                $scope.drugUse.percocetRouteCode = $scope.setCountOrRouteRadioValue(dataElement.percocetRouteCode,'percocetRouteCode' ) ;
                $scope.drugUse.darvonRouteCode = $scope.setCountOrRouteRadioValue(dataElement.darvonRouteCode,'darvonRouteCode' );
                $scope.drugUse.codeineRouteCode = $scope.setCountOrRouteRadioValue(dataElement.codeineRouteCode,'codeineRouteCode' );
                $scope.drugUse.tylenolRouteCode =  $scope.setCountOrRouteRadioValue(dataElement.tylenolRouteCode,'tylenolRouteCode' );
                $scope.drugUse.oxycontinRouteCode =  $scope.setCountOrRouteRadioValue(dataElement.oxycontinRouteCode,'oxycontinRouteCode' );
                $scope.drugUse.methadoneRouteCode =  $scope.setCountOrRouteRadioValue(dataElement.methadoneRouteCode,'methadoneRouteCode' );

                // 5.
                $scope.mentalPhysicalProblem.depressionCount =  $scope.setCountOrRouteRadioValue(dataElement.depressionCount,'depressionCount' ) ;
                $scope.mentalPhysicalProblem.anxietyCount =  $scope.setCountOrRouteRadioValue(dataElement.anxietyCount,'anxietyCount' ) ;
                $scope.mentalPhysicalProblem.violentCount =  $scope.setCountOrRouteRadioValue(dataElement.violentCount,'violentCount' ) ;
                $scope.mentalPhysicalProblem.psychMedicationCount = $scope.setCountOrRouteRadioValue(dataElement.psychMedicationCount,'psychMedicationCount' )  ;

                $scope.triggerGPRAValidation();
            }else if(dataElement.asi === true && dataElement.gpra === false){ //ASI
                $scope.triggerASIValidation();
            }

        };

        $scope.triggerGPRAValidation = function(){
            //Validate route on loading data
            $scope.validateAllRoute();

            //Validate count on loading data
            $scope.ValidateAllDrugUseCount();

            //Validate MentalPhysical Problem fields on loading data.
            $scope.validateMentalPhysical();
        };

        $scope.triggerASIValidation = function(){
            //3.
            $scope.validateNumberOrX('m6_days_medical_problem');

            //6.
            $scope.validateIllegalSourceOfIncome();

            // 7.
            $scope.validateNumberOrX('e19_days_employment_problem');

            //8.
            $scope.onNumberOfDaysChange('d2a_intoxication_30');
            $scope.onNumberOfDaysChange('d3a_heroin_30');
            $scope.onNumberOfDaysChange('d4a_methadone_30');
            $scope.onNumberOfDaysChange('d5a_opiates_30');

            //10.
            $scope.validateNumberOrX('l27_days_illegal');

            //16.
            $scope.validateNumberOrX('f30_days_conflict_family');
        };

        $scope.setCountOrRouteRadioValue = function(value, field){
            if( (parseInt(value) === -7) || (parseInt(value) === -8)) {
                $scope.onRadioButtonChange(field);
                return value;
            }else{
                return "";
            }
        };

        $scope.getASILookup = function(){
            DataElementsService.getASIRouteCodes(
                function(response){
                    $scope.asiRoute = response;
                },
                function(error){ErrorService.error(error);}
            );

            DataElementsService.getEpisodeCodes(
                function(response){
                    $scope.priorTreatment = response;
                },
                function(error){ErrorService.error(error);}
            );

            DataElementsService.getStatusCodes(
                function(response){
                    $scope.medicalStatus = response;
                },
                function(error){ErrorService.error(error);}
            );

            DataElementsService.getYesNoCodes(
                function(response){
                    $scope.yesNoNA = response;
                },
                function(error){ErrorService.error(error);}
            );
        };

        $scope.getGPRALookup = function(){
            DataElementsService.getRouteCodes(
                function(response){
                    $scope.routeOfDrugUse = response;
                },
                function(error){ErrorService.error(error);}
            );
        };

        $scope.getPatientDataElements = function(){

            DataElementsService.get(
                $scope.requestId,
                function (response) {
                    $scope.isDataNotElementAvailable = false;

                    //build the data element object for the UI
                    $scope.setUpDataElement(response);

                    $scope.showDataelementForm = true;
                },
                function (response) {
                    $scope.showDataelementForm = false;
                    $scope.isDataNotElementAvailable = true;
                }
            );
        };

        $scope.setUpDataElement = function(patient){
            $scope.patient = patient;

            if($scope.isASI($scope.patient)){
                $scope.getASILookup();
                $scope.assesstment = false;
            }else if( $scope.isGPRA($scope.patient)){
                $scope.getGPRALookup();
                $scope.assesstment = true;
            }
            $scope.createComplexFields(patient);
        };

        $scope.isDefinedAndValid = function(value){
            return (angular.isDefined(value) || value);
        };


        $scope. validateRoute =  function(selectValue, radioValue){
            if(selectValue === null ||radioValue === null ){
                return false;
            }else if(( (parseInt(selectValue) >= 1) && (parseInt(selectValue) <= 5)) || ( (parseInt(radioValue) === -7) || (parseInt(radioValue) === -8) || !angular.isDefined(radioValue) ||  radioValue === "")){
                return true;
            }else {
                return false;
            }
        };

        $scope.isValidDayCount = function(dayCount, radioValue){
            if(dayCount === null ||radioValue === null ){
                return false;
            }else if(( $scope.isValidNumberOfDays(dayCount)) || ( (parseInt(radioValue) === -7) || (parseInt(radioValue) === -8) || !angular.isDefined(radioValue) ||  radioValue === "")){
                return true;
            }else {
                return false;
            }
        };

        $scope.validateAllRoute = function(){
            $scope.isInvalidHeroinRoute =  (!$scope.validateRoute ($scope.patient.heroinRouteCode, $scope.drugUse.heroinRouteCode)) ;
            $scope.isInvalidMorphineRoute =  (!$scope.validateRoute ($scope.patient.morphineRouteCode, $scope.drugUse.morphineRouteCode)) ;
            $scope.isInvalidDiluadidRoute =  (!$scope.validateRoute ($scope.patient.diluadidRouteCode, $scope.drugUse.diluadidRouteCode))  ;
            $scope.isInvalidDemerolRoute =  (!$scope.validateRoute ($scope.patient.demerolRouteCode, $scope.drugUse.demerolRouteCode)) ;
            $scope.isInvalidPercocetRoute =  (!$scope.validateRoute ($scope.patient.percocetRouteCode, $scope.drugUse.percocetRouteCode)) ;
            $scope.isInvalidDarvonRoute =  (!$scope.validateRoute ($scope.patient.darvonRouteCode, $scope.drugUse.darvonRouteCode)) ;
            $scope.isInvalidCodeineRoute =  (!$scope.validateRoute ($scope.patient.codeineRouteCode, $scope.drugUse.codeineRouteCode)) ;
            $scope.isInvalidTylenolRoute =  (!$scope.validateRoute ($scope.patient.tylenolRouteCode, $scope.drugUse.tylenolRouteCode)) ;
            $scope.isInvalidOxycontinRoute =  (!$scope.validateRoute ($scope.patient.oxycontinRouteCode, $scope.drugUse.oxycontinRouteCode))  ;
            $scope.isInvalidMethadoneRoute =  (!$scope.validateRoute ($scope.patient.methadoneRouteCode, $scope.drugUse.methadoneRouteCode)) ;
        };

        $scope.ValidateAllDrugUseCount = function(){

            $scope.isValidHeroinDayCount = (!$scope.isValidDayCount($scope.patient.heroinDayCount, $scope.drugUse.heroinDayCount))  ;
            $scope.isValidMorphineCount = (!$scope.isValidDayCount($scope.patient.morphineCount, $scope.drugUse.morphineCount))  ;
            $scope.isValidDiluadidCount = (!$scope.isValidDayCount($scope.patient.diluadidCount, $scope.drugUse.diluadidCount)) ;
            $scope.isValidDemerolCount = (!$scope.isValidDayCount($scope.patient.demerolCount, $scope.drugUse.demerolCount))  ;
            $scope.isValidPercocetCount = (!$scope.isValidDayCount($scope.patient.percocetCount, $scope.drugUse.percocetCount));
            $scope.isValidDarvonCount = (!$scope.isValidDayCount($scope.patient.darvonCount, $scope.drugUse.darvonCount))  ;
            $scope.isValidCodeineCount = (!$scope.isValidDayCount($scope.patient.codeineCount, $scope.drugUse.codeineCount)) ;
            $scope.isValidTylenolCount = (!$scope.isValidDayCount($scope.patient.tylenolCount, $scope.drugUse.tylenolCount));
            $scope.isValidOxycontinCount = (!$scope.isValidDayCount($scope.patient.oxycontinCount, $scope.drugUse.oxycontinCount) ) ;
            $scope.isValidMethadoneCount = (!$scope.isValidDayCount($scope.patient.methadoneCount, $scope.drugUse.methadoneCount) );

        };

        $scope.validateMentalPhysical = function(){
            $scope.isInValidDepressionCount = (!$scope.isValidDayCount($scope.patient.depressionCount, $scope.drugUse.depressionCount) ) ;
            $scope.isInValidAnxietyCount = (!$scope.isValidDayCount($scope.patient.anxietyCount, $scope.drugUse.anxietyCount) );
            $scope.isInValidViolentCount = (!$scope.isValidDayCount($scope.patient.violentCount, $scope.drugUse.violentCount) );
            $scope.isInValidPsychMedicationCount = (!$scope.isValidDayCount($scope.patient.psychMedicationCount, $scope.drugUse.psychMedicationCount) ) ;
        };

        $scope.canSaveGPRA = function() {

            if(!angular.isDefined($scope.patient)){
                return true;
            }

            var alcoholDrugUseCount = $scope.isInValidDepressionCount ||  $scope.isInValidAnxietyCount || $scope.isInValidViolentCount || $scope.isInValidPsychMedicationCount ;

            var drugUseCount = $scope.isValidHeroinDayCount || $scope.isValidMorphineCount || $scope.isValidDiluadidCount || $scope.isValidDemerolCount || $scope.isValidPercocetCount || $scope.isValidDarvonCount || $scope.isValidCodeineCount || $scope.isValidTylenolCount ||$scope.isValidOxycontinCount || $scope.isValidMethadoneCount;

            var drugUseRoute = $scope.isInvalidHeroinRoute || $scope.isInvalidMorphineRoute || $scope.isInvalidDiluadidRoute || $scope.isInvalidDemerolRoute || $scope.isInvalidPercocetRoute || $scope.isInvalidDarvonRoute || $scope.isInvalidCodeineRoute || $scope.isInvalidTylenolRoute || $scope.isInvalidOxycontinRoute || $scope.isInvalidMethadoneRoute;

            var psychoEmotional = !angular.isDefined($scope.patient.emotionalProblemsCode) || (angular.isDefined($scope.patient.emotionalProblemsCode) && $scope.patient.emotionalProblemsCode === null);

            return  alcoholDrugUseCount || drugUseCount || drugUseRoute || !$scope.isAgeOk || psychoEmotional || $scope.isValidOtherGender() || $scope.isOverAge;
        };


        $scope.isValidOtherGender =  function(){
            if(!angular.isDefined($scope.patient) || !angular.isDefined($scope.patient.genderCode)){
                return false;
            }else if(!($scope.patient.genderCode === 'MA' || $scope.patient.genderCode === 'FE' || $scope.patient.genderCode === 'TG' || parseInt($scope.patient.genderCode) === -7) ){
                if( !angular.isDefined($scope.patient.otherDescription) || $scope.patient.otherDescription === null ||( angular.isDefined($scope.patient.otherDescription) && $scope.patient.otherDescription.length ===0)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        };

        $scope.canSaveASI = function() {
            var genderCode = $scope.isValidOtherGender() ;
            var drugAndAlcoholNumberOfDays = $scope.isInvalidAlcoholToxicationNumberOfDays || $scope.isInvalidHeroinNumberOfDays ||  $scope.isInvalidMethadoneNumberOfDays ||  $scope.isInvalidOpiatesAnalgesicsNumberOfDays;
            var numberbetweenZeroAndThirtyOrX = $scope.m6_days_medical_problem || $scope.e19_days_employment_problem ||  $scope.l27_days_illegal || $scope.f30_days_conflict_family;

            return genderCode || $scope.patientForm.$invalid  || numberbetweenZeroAndThirtyOrX || drugAndAlcoholNumberOfDays || !$scope.isAgeOk || $scope.illigalSource || $scope.isOverAge;
        };

        $scope.getGPRARecommendations = function () {
            //Start the progress bar
            ngProgress.start();

            $scope.updatePatientWithComplexFields();

            DataElementsService.create(
                $scope.dataelements.id,
                $scope.dataelements,
                function (response) {
                    //stop the progress bar
                    ngProgress.complete();
                    $scope.isRecommendationNotAvailable = false;
                    $location.path("/recommendations/" + $scope.patient.requestId);
                },
                function (response) {
                    //stop the progress bar
                    ngProgress.complete();
                    $scope.isRecommendationNotAvailable = true;
                }
            );
        };

        $scope.getASIRecommendations = function () {
            //Start the progress bar
            ngProgress.start();
            DataElementsService.createAsi(
                $scope.patient.id,
                $scope.patient,
                function (response) {
                    //stop the progress bar
                    ngProgress.complete();
                    $scope.isRecommendationNotAvailable = false;
                    $location.path("/recommendations/" + $scope.patient.requestId);
                },
                function (response) {
                    //stop the progress bar
                    ngProgress.complete();
                    $scope.isRecommendationNotAvailable = true;
                }
            );
        };

        // Data element logic from Recommendation page

        if(angular.isDefined($routeParams.requestId)){
            $scope.requestId = $routeParams.requestId;
            $scope.getPatientDataElements();

            //var patient = loadedData[0];
            //$scope.setUpDataElement(patient);
            //$scope.showDataelementForm = true;
        }

        // Set all field of the json object null
        $scope.clearAllFields = function(data){
            for (var key in data) {
                if (data.hasOwnProperty(key)) {

                    if(key === 'id' || key === 'staffId' || key === 'requestId' || key === 'asi' || key === 'gpra' || key === 'uniqueClientNumber' || key === 'timeStamp' || key === 'state'){
                        //ignore these fields
                    }
                    else {
                        data[key] = null;
                    }

                }
            }
            return data;
        };

        $scope.isGPRA = function(patient){
            return (patient.asi === false) && (patient.gpra === true) ;
        };

        $scope.isASI = function(patient){
            return (patient.asi === true) && (patient.gpra === false) ;
        };

        var resetAll = function(){
            //Reset all fields
            $scope.patient = $scope.clearAllFields($scope.patient);

            //Trigger validate of custom fields
            $scope.isValidAge();
            $scope.validateGender();

            if ($scope.isGPRA($scope.patient)) {
                $scope.drugUse = $scope.clearAllFields($scope.drugUse);
                $scope.mentalPhysicalProblem = $scope.clearAllFields($scope.mentalPhysicalProblem);
                $scope.triggerGPRAValidation();
            }else if($scope.isASI($scope.patient)){
                $scope.validateNumberOrX('m6_days_medical_problem');
                $scope.validateIllegalSourceOfIncome();
                $scope.validateNumberOrX('e19_days_employment_problem');
                $scope.validateNumberOrX('l27_days_illegal');
                $scope.validateNumberOrX('f30_days_conflict_family');
                $scope.onNumberOfDaysChange('d2a_intoxication_30');
                $scope.onNumberOfDaysChange('d3a_heroin_30');
                $scope.onNumberOfDaysChange('d4a_methadone_30');
                $scope.onNumberOfDaysChange('d5a_opiates_30');
            }
        };

        $scope.reset = function () {
            $modal.open({
                templateUrl: 'pdePrompt',
                backdrop: true,
                windowClass: 'modal',
                controller:  function ($scope, $modalInstance) {
                    $scope.clearAll = function () {
                        //Resetting all values to null.
                        resetAll();

                        $modalInstance.dismiss('cancel');
                    };
                    $scope.cancel = function () {
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

        $scope.calculateAssessment = function(assessmentStr){
            if(assessmentStr === 'gpra'){
                return true;
            }else if(assessmentStr === 'asi'){
                return false;
            }
        };

        // In case of a blank pde page

        // Calculate assessment: GPRA (true), ASI (false)
        $scope.assesstment = $scope.calculateAssessment(loadedData[0].assessment);

        if($scope.assessment){ //GRPA
            $scope.getGPRALookup();
            $scope.patient = {
                age: null,anxietyCount: null, asi: null, codeineCount: null, codeineRouteCode: null, codeineRouteDisplayName: null, darvonCount: null, darvonRouteCode: null, darvonRouteDisplayName: null,
                demerolCount: null, demerolRouteCode: null, demerolRouteDisplayName: null,depressionCount: null,diluadidCount: null, diluadidRouteCode: null, diluadidRouteDisplayName: null, emotionalProblemsCode: null,
                emotionalProblemsDisplayName: null, genderCode: null, genderDisplayName: null, gpra: null, healthIndicatorCode: null, healthIndicatorCodeDisplayName:null, heroinDayCount:null, heroinRouteCode: null,
                heroinRouteDisplayName: null, id: "", methadoneCount: null, methadoneRouteCode: null, methadoneRouteDisplayName: null, morphineCount: null, morphineRouteCode: null, morphineRouteDisplayName: null, note1: null,
                note2: null, otherDescription: null, oxycontinCount: null, oxycontinRouteCode: null, oxycontinRouteDisplayName: null, percocetCount: null, percocetRouteCode: null, percocetRouteDisplayName: null, psychMedicationCount: null,
                recommendationXml: null, requestId: null, staffId: null, timeStamp: null, tylenolCount: null, tylenolRouteCode: null, tylenolRouteDisplayName: null, uniqueClientNumber: null, violentCount: null
            };
        }else { //ASI
            $scope.getASILookup();
            //Creating default ASI PDE object
            $scope.patient = {
                age:null,asi:null, d2a_intoxication_30:null, d3a_heroin_30:null, d3c_route_of_intake_type_Code:null, d3c_route_of_intake_type_DisplayName:null, d4a_methadone_30:null,
                d4c_route_of_intake_type_Code:null, d4c_route_of_intake_type_DisplayName:null, d5a_opiates_30:null, d5c_route_of_intake_type_Code:null, d5c_route_of_intake_type_DisplayName:null,
                e17_income_illegal:null, e19_days_employment_problem:null, episodeCode:null, episodeDisplayName:null, f3_is_satisfied_marital_Code:null, f3_is_satisfied_marital_DisplayName:null,
                f18a_is_mother_30_Code:null, f18a_is_mother_30_DisplayName:null, f19a_is_father_30_Code:null, f19a_is_father_30_DisplayName:null, f20a_is_sibling_30_Code:null, f20a_is_sibling_30_DisplayName:null,
                f21a_is_spouse_30_Code:null, f21a_is_spouse_30_DisplayName:null, f22a_is_children_30_Code:null, f22a_is_children_30_DisplayName:null, f23a_is_other_30_Code: null, f23a_is_other_30_DisplayName:null,
                f24a_is_friends_30_Code:null, f24a_is_friends_30_DisplayName:null, f25a_is_neighbor_30_Code:null, f25a_is_neighbor_30_DisplayName:null, f26a_is_co_worker_30_Code:null, f26a_is_co_worker_30_DisplayName:null,
                f28b_is_abused_p_lifetime_Code:null, f28b_is_abused_p_lifetime_DisplayName:null, f29b_is_abused_s_lifetime_Code:null, f29b_is_abused_s_lifetime_DisplayName:null, f30_days_conflict_family:null, f32_patient_rating_Code:null,
                f32_patient_rating_DisplayName:null, f34_patient_rating_Code:null, f34_patient_rating_DisplayName:null, family_score:null, genderCode:null, genderDisplayName:null, gpra:null, id: "", l24_is_waiting_trial_Code:null, l24_is_waiting_trial_DisplayName:null,
                l27_days_illegal:null, l28_patient_rating_Code:null, l28_patient_rating_DisplayName:null, l29_patient_rating_Code:null, l29_patient_rating_DisplayName:null, legal_score:null, m6_days_medical_problem:null, m7_patient_rating_Code:null,
                m7_patient_rating_DisplayName:null, m8_patient_rating_Code:null, m8_patient_rating_DisplayName:null, medical_score:null, note1:null, note2:null, otherDescription:null, p4a_is_depression_30_Code:null,
                p4a_is_depression_30_DisplayName:null, p5a_is_anxiety_30_Code:null, p5a_is_anxiety_30_DisplayName:null, p8a_is_violent_30_Code:null, p8a_is_violent_30_DisplayName:null, p9a_is_suicidal_30_Code:null, p9a_is_suicidal_30_DisplayName:null,
                p11a_is_prescribed_30_Code:null, p11a_is_prescribed_30_DisplayName:null, recommendationXml:null, requestId:null, staffId:null, state:null, timeStamp:null, uniqueClientNumber:null
            };
        }
    }])
    .controller('DataElementsCtrl2', ['$scope','loadedData','$routeParams', '$controller','$window',  function($scope, loadedData, $routeParams, $controller, $window){
        // instantiate base controller
        $controller('DataElementsCtrl', { $scope: $scope });

        //Scroll to the top when page starts.
        $window.scrollTo(0, 0);

        if(angular.isDefined($routeParams.requestId)){
            $scope.requestId = $routeParams.requestId;
        }

        if(angular.isDefined(loadedData[0])){
            $scope.isDataNotElementAvailable = false;
            //build the data element object for the UI
            $scope.setUpDataElement(loadedData[0]);
            $scope.showDataelementForm = true;
        }else{
            $scope.showDataelementForm = false;
            $scope.isDataNotElementAvailable = true;
        }

    }]);
