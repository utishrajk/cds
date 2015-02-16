'use strict';

xdescribe('bham.caremanagerModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.caremanagerModule");
    });

    xit("should be registered", function() {
        expect(module).not.toEqual(null);
    });

    xdescribe("Dependencies:", function() {

        var dependencies;
        var hasModule = function(m) {
            return dependencies.indexOf(m) >= 0;
        };
        beforeEach(function() {
            dependencies = module.value('bham.caremanagerModule').requires;
        });
        xit("should have bham.security as a dependency", function() {
            expect(hasModule('bham.security')).toEqual(true);
        });
        xit("should have bhma.caremanagerService as a dependency", function() {
            expect(hasModule('bham.caremanagerService')).toEqual(true);
        });
    });
});


xdescribe("bham.caremanagerModule CaremanagerCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.caremanagerModule'));
    beforeEach(module('angular-growl'));
    beforeEach(module('ui.bootstrap'));
    beforeEach(module('bham.loggingModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockCareManagerService, caremanager, loadedData, form , route, location, rootScope, httpBackend, growl, UserService, ErrorService, $modal, $compile;

    beforeEach(inject(function($rootScope, $controller, _$compile_, $route, $location, $httpBackend, _growl_, _$modal_){
//        caremanager = {lastName: "Ngassa",firstName: "Tomson", id:2, userName: "tmngassa", website: "http://bham.com"};
        $compile = _$compile_;
        caremanager = {lastName: "Ngassa",firstName: "Tomson", id:2, mailingAddressCountryCode: "US", securityQuestion1Code:"", securityQuestion2Code:""};
        scope = $rootScope.$new();
        rootScope = $rootScope;
        route = $route;
        location = $location;
        httpBackend = $httpBackend;
        growl = _growl_;
        $modal = _$modal_;

        var successCb = function(){console.log('Success');};
        var errorCb =  function (){ console.log('Error');};

        MockCareManagerService = {
            update : function(id, newcaremanager, successCb, errorCb) {
                caremanager = newcaremanager;
            },
            get : function(id, successCb, errorCb) {
                return caremanager ;
            }
        };

        UserService = {
            getSecurityQuestionsCodes: function(success, error){
            }
        };

        loadedData = [{}, {}, caremanager, {}, {}];

        scope.states = loadedData[0];
        scope.specialities = loadedData[1];
        scope.caremanager = loadedData[2];
        scope.countries = loadedData[3];

        scope.isValidNumber = function(zipcode){
            if(isNaN(zipcode)){
                return false;
            }else if(zipcode.length > 0){
                return true;
            }
        };

        scope.isValidContactNumber = function(zipcode, countryCode, US_Zipcode_Length){
            if((countryCode === "US") && ( zipcode.length !== US_Zipcode_Length ) ){
                return true;
            }else{
                return false;
            }
        };

        var element = angular.element(
            '<form name="caremanagerForm">' +
//                '<input ng-model="caremanager.mailingAddressPostalCode" name="zipCode" type="text" />' +
                '<input ng-model="caremanager.mailingAddressTelephoneNumber" name="phoneNumber"  type="text" />' +
                '</form>'
        );

        scope.isFutureDate = function (currentDate) {
            var result = false;
            if (currentDate) {
                var today = new Date().getTime();
                var newDate = new Date(currentDate).getTime();

                if (newDate > today) {
                    result = true;
                }
            }
            return result;

        };

        scope.caremanager = { mailingAddressTelephoneNumber: null};

        $compile(element)(scope);
        scope.$digest();
        form = scope.caremanagerForm;

        scope.isUnitedState = function(countryCode){
            return countryCode === "US";
        };

        scope.setActiveMenuItem = function(menuItem){
        };

        scope.user = {};

        ErrorService = {};

        scope.securityQuestions = [
            {code:1, codeSystem:0,codeSystemName:1,displayName: "What was the first album or cd you ever bought?", originalText: null},
            {code:1, codeSystem:0,codeSystemName:1,displayName: "What was the first album or cd you ever bought?", originalText: null}
        ];

        $controller('CaremanagerCtrl', {
            $scope: scope,
            CareManagerService: MockCareManagerService,
            loadedData: loadedData,
            UserService: UserService,
            growl: growl,
            ErrorService: ErrorService,
            $modal: $modal,
            $route: route
        });
    }));

    xit("should have default values", function() {

    });

    xit("should switch tab", function() {
        expect(scope.activeTab).toEqual('basic');
        scope.switchTabTo('tab1');
        expect(scope.activeTab).toEqual('tab1');
    });

    xit("should update caremanager", function() {
        caremanager.lastName = "Ngassa1";
        scope.save(caremanager.id, caremanager, function(){console.log('Success');},  function (){ console.log('Error');});
        var updatedCarmanager = MockCareManagerService.get(2);
        expect(caremanager.lastName).toEqual(updatedCarmanager.lastName);
    });

//    xit("shoule validate zipcode", function(){
////        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
////        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();
//
//        //Invalid zip code
//        scope.$apply();
//
//        //US zipcode
//        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
////        scope.caremanager.mailingAddressCountryCode = "US";
////        scope.caremanager.mailingAddressPostalCode = "21045"; // For valid US zip code
//        scope.$apply();
//
////        scope.caremanager.mailingAddressPostalCode = "2104533e"; // Invalid US zip code
//        scope.$apply();
//    });

    xit("shoule validate phone number", function(){
        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
//        expect(scope.caremanager.mailingAddressCountryCode ).toBeDefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        //Invalid phone number
        scope.$apply();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();

        //US phone number
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
//        scope.caremanager.mailingAddressCountryCode = "US";
        scope.caremanager.mailingAddressTelephoneNumber = "1111111111"; // For valid US phone unmber
        scope.$apply();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();

        scope.caremanager.mailingAddressTelephoneNumber = "2104533e"; // Invalid US phone number
        scope.$apply();
        expect(scope.isPhoneNumber ).toBeFalsy();
    });

    xit("should validate state, zip code and phone number on country change", function(){

//        expect(scope.caremanager.mailingAddressCountryCode ).toBeDefined();
//        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
//        expect(scope.caremanager.mailingAddressCountryCode ).toBeDefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
//        scope.caremanager.mailingAddressCountryCode = "US";
//        scope.caremanager.mailingAddressPostalCode = "21045";
        scope.caremanager.mailingAddressTelephoneNumber = "1111111111";

        scope.onCountrySelected();

        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();
    });

    xit("Should enable save button if validation pass", function(){
        expect(scope.isValidPhoneNumberLength).toBeUndefined();
//        expect(scope.caremanager.mailingAddressCountryCode ).toBeDefined();
//        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
//        expect(scope.caremanager.mailingAddressCountryCode ).toBeDefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";

        expect(form ).toBeDefined();

//        form.zipCode.$setViewValue("21045");
        form.phoneNumber.$setViewValue("1111111111");

        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    xit("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('individualproviders/1').respond(200);
        httpBackend.expectGET('statecodes').respond(200);
        httpBackend.expectGET('countrycodes').respond(200);
        httpBackend.expectGET('prefixes').respond(200);

        httpBackend.expectGET('caremanager/caremanager-profile.tpl.html').respond(200);

        location.path('/myaccount/1');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('caremanager/caremanager-profile.tpl.html');
        expect(route.current.params.id).toEqual('1');
        expect(route.current.controller).toEqual('CaremanagerCtrl');
        expect(route.current.resolve.loadedData).toBeDefined();

        location.path('/myaccount');
        rootScope.$digest();
        expect(route.current).toBeUndefined();
    });


    xit("should refresh page", function() {
        location.path("/myaccount/2");
        scope.reset();
        expect(location.path()).toEqual("/myaccount/2");

    });


    xit("should test security question validation", function() {
        scope.caremanager.securityQuestion1Code = "1";
        scope.onSecurityQuestion1Change();
        expect(scope.isSecurityQuestion1Required ).toBeFalsy();
        expect(scope.enableSecurityAnswer1IsRequired  ).toBeTruthy();

        scope.caremanager.securityQuestion2Code = "1";
        scope.onSecurityQuestion2Change();
        expect(scope.isSecurityQuestion2Required ).toBeFalsy();
        expect(scope.enableSecurityAnswer2IsRequired  ).toBeTruthy();
    });


});

