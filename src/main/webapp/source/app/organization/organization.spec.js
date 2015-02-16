///**
// * Created by tomson.ngassa on 3/10/14.
// */

'use strict';


describe('bham.organizationModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.organizationModule");
    });

    it("should be registered", function() {
        expect(module).not.toEqual(null);
    });

    describe("Dependencies:", function() {

        var dependencies;

        var hasModule = function(m) {
            return dependencies.indexOf(m) >= 0;
        };

        beforeEach(function() {
            dependencies = module.value('bham.organizationModule').requires;
        });

//        it("should have ngResource as a dependency", function() {
//            expect(hasModule('ngResource')).toEqual(true);
//        });

        it("should have bham.security as a dependency", function() {
            expect(hasModule('bham.security')).toEqual(true);
        });

        it("should have bhma.organizationService as a dependency", function() {
            expect(hasModule('bham.organizationService')).toEqual(true);
        });

    });
});


describe("bham.organizationModule OrganizationCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.organizationModule'));
    beforeEach(module('angular-growl'));


    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockOrganizationService, organizationProfile, loadedData, form, route, location, rootScope, httpBackend, growl;

    beforeEach(inject(function($rootScope, $controller, $compile, $route, $location, $httpBackend, _growl_){
        organizationProfile = {orgName: "Bham",mailingAddressStateName: "Maryland", id:"1", website: "http://bham.com", services:["H0018","H2034", "H0004"]};
        rootScope = $rootScope;
        scope = $rootScope.$new();
        route = $route;
        location = $location;
        httpBackend = $httpBackend;
        growl = _growl_;

        var successCb = function(){console.log('Success');};
        var errorCb =  function (){ console.log('Error');};

        MockOrganizationService = {
            update : function(neworganizationProfile, successCb, errorCb) {
                organizationProfile = neworganizationProfile;
            },
            get : function(id, successCb, errorCb) {
                return organizationProfile ;
            }
        };

        loadedData = [organizationProfile, {}, {}, {}];
        scope.states = loadedData[1];
        scope.countries = loadedData[2];
        scope.services = loadedData[3];

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
            '<form name="organizationProfileForm">' +
                '<input ng-model="organizationProfile.mailingAddressPostalCode" name="organizationZipCode" type="text" />' +
                '<input ng-model="organizationProfile.mailingAddressTelephoneNumber" name="organizationPhoneNumber"  type="text" />' +
            '</form>'
        );
        scope.organizationProfile = { mailingAddressPostalCode:null, mailingAddressTelephoneNumber: null};

        $compile(element)(scope);
        scope.$digest();
        form = scope.organizationProfileForm;

        scope.isUnitedState = function(countryCode){
            return countryCode === "US";
        };

        scope.setActiveMenuItem = function(){

        };

        $controller('OrganizationProfileCtrl', {
            $scope: scope,
            OrganizationService: MockOrganizationService,
            loadedData: loadedData,
            growl: growl
        });
    }));

    it("should have default values", function() {
        expect(scope.activeTab).toEqual('organizationProfile');
        expect(scope.states).toEqual({});
        expect(scope.isANumber).toBeFalsy();
    });


    it("should update organization", function() {
        organizationProfile.orgName = "bham1";
        scope.save( organizationProfile, function(){console.log('Success');},  function (){ console.log('Error');});
        var updatedCarmanager = MockOrganizationService.get(1);
        expect(organizationProfile.orgName).toEqual(updatedCarmanager.orgName);
    });

    it("should validate zipcode", function(){
        expect(scope.validZipCodeLength).toBeUndefined();
        expect(scope.validZipcode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressPostalCode).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressTelephoneNumber).toBeUndefined();

        //Invalid zip code
        scope.$apply();
        expect(scope.isNotValidZipCodeLength).toBeFalsy();
        expect(scope.zipcodeNAN ).toBeFalsy();

        //US zipcode
        scope.organizationProfile.mailingAddressCountryCode = "US";
        scope.organizationProfile.mailingAddressPostalCode = "21045"; // For valid US zip code
        scope.$apply();
        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();

        scope.organizationProfile.mailingAddressPostalCode = "2104533e"; // Invalid US zip code
        scope.$apply();
        expect(scope.isNotValidZipCodeLength).toBeFalsy();
        expect(scope.zipcodeNAN ).toBeFalsy();
    });

    it("should validate phone number", function(){
        expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
        expect(scope.phoneNumberNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();

        //Invalid phone number
        scope.$apply();
        expect(scope.isNotValidPhoneNumberLength).toBeFalsy();
        expect(scope.phoneNumberNAN ).toBeFalsy();

        //US phone number
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.organizationProfile.mailingAddressCountryCode = "US";
        scope.organizationProfile.mailingAddressTelephoneNumber = "1111111111"; // For valid US phone unmber
        scope.$apply();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();

        scope.organizationProfile.mailingAddressTelephoneNumber = "2104533e"; // Invalid US phone number
        scope.$apply();
        expect(scope.isNotValidPhoneNumberLength).toBeFalsy();
        expect(scope.phoneNumberNAN ).toBeFalsy();
    });

    it("should validate state, zip code, fax and phone number on country change", function(){

        expect(scope.isNotValidZipCodeLength).toBeUndefined();
        expect(scope.zipcodeNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
        expect(scope.phoneNumberNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.organizationProfile.mailingAddressCountryCode = "US";
        scope.organizationProfile.mailingAddressPostalCode = "21045";
        scope.organizationProfile.mailingAddressTelephoneNumber = "1111111111";

        scope.validateStateZipcodePhoneFax();

        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();
    });

    it("Should enable save button if validation pass", function(){
        expect(scope.isNotValidZipCodeLength).toBeUndefined();
        expect(scope.zipcodeNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
        expect(scope.phoneNumberNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";

        expect(form ).toBeDefined();

        form.organizationZipCode.$setViewValue("21045");
        form.organizationPhoneNumber.$setViewValue("1111111111");

        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('organizationalproviders').respond(200);
        httpBackend.expectGET('statecodes').respond(200);
        httpBackend.expectGET('countrycodes').respond(200);
        httpBackend.expectGET('servicecodes').respond(200);
        httpBackend.expectGET('prefixes').respond(200);

        httpBackend.expectGET('organization/organization.tpl.html').respond(200);

        location.path('/organization');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('organization/organization.tpl.html');
        expect(route.current.controller).toEqual('OrganizationProfileCtrl');
        expect(route.current.resolve.loadedData).toBeDefined();

    });

    it("should select a service", function(){
        var code = "H0018";
        var serviceCode = scope.isSelectedService(code);
        expect(serviceCode).toBeTruthy();
    });

    it("should validate NPI", function(){
        scope.organizationProfile.npi = "1233";
        scope.$apply();
        expect(scope.isNotANumber).toBeFalsy(); //Should be a number
    });

    it("should validate Authorize phone number", function(){
        expect(scope.isNotValidAutorizePhoneNumberLength  ).toBeUndefined();
        expect(scope.autorizePhoneNumberNAN).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.authorizedOfficialTelephoneNumber ).toBeUndefined();

        //US phone number
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.organizationProfile.mailingAddressCountryCode = "US";
        scope.organizationProfile.authorizedOfficialTelephoneNumber = "1111111111"; // For valid US phone unmber
        scope.$apply();
        expect(scope.isValidAuthorizedPhoneNumberLength ).toBeTruthy();
        expect(scope.isAuthorizedPhoneNumber  ).toBeTruthy();

        scope.organizationProfile.authorizedOfficialTelephoneNumber = "2104533e"; // Invalid US phone number
        scope.$apply();
        expect(scope.isAuthorizedPhoneNumber  ).toBeFalsy();
    });

    it("should validate fax number", function(){
        expect(scope.isNotValidFaxNumberLength ).toBeUndefined();
        expect(scope.faxNumberNAN ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.organizationProfile.mailingAddressFaxNumber).toBeUndefined();

        //Invalid fax number
        scope.$apply();
        expect(scope.isNotValidFaxNumberLength ).toBeFalsy();
        expect(scope.faxNumberNAN  ).toBeFalsy();

        //US fax number
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.organizationProfile.mailingAddressCountryCode = "US";
        scope.organizationProfile.mailingAddressFaxNumber = "1111111111"; // For valid US fax unmber
        scope.$apply();
        expect(scope.isValidFaxNumberLength).toBeTruthy();
        expect(scope.isFaxNumber ).toBeTruthy();

        scope.organizationProfile.mailingAddressFaxNumber = "2104533e"; // Invalid US fax number
        scope.$apply();
        expect(scope.isFaxNumber ).toBeFalsy();
    });

    it("should switch tab", function() {
        expect(scope.activeTab).toEqual('organizationProfile');
        scope.switchTabTo('contact');
        expect(scope.activeTab).toEqual('contact');
    });

    it("should refresh page", function() {
        location.path("/orgnanization/1");
        scope.reset();
        expect(location.path()).toEqual("/orgnanization/1");

    });
});

