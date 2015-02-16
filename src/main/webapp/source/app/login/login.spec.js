///**
// * Created by tomson.ngassa on 3/10/14.
// */

'use strict';


describe('bham.loginModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.loginModule");
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
            dependencies = module.value('bham.loginModule').requires;
        });

        it("should have bham.security as a dependency", function() {
            expect(hasModule('bham.security')).toEqual(true);
        });

        it("should have bhma.userService as a dependency", function() {
            expect(hasModule('bham.userService')).toEqual(true);
        });

        it("should have reCAPTCHA as a dependency", function() {
            expect(hasModule('reCAPTCHA')).toEqual(true);
        });

        it("should have ngDialog as a dependency", function() {
            expect(hasModule('ngDialog')).toEqual(true);
        });

    });
});


describe("bham.loginModule LoginController", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.loginModule'));
    beforeEach(module('http-auth-interceptor'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockOrganizationService, organizationProfile, loadedData, form, route, location, rootScope, httpBackend, cookieStore, AuthenticationSharedService, authService, routeParams;

    beforeEach(inject(function($rootScope, $controller, $compile, $route, $location, $httpBackend, _$cookieStore_, _AuthenticationSharedService_, _authService_, _$routeParams_){
        rootScope = $rootScope;
        scope = $rootScope.$new();
        route = $route;
        location = $location;
        httpBackend = $httpBackend;
        cookieStore = _$cookieStore_;
        AuthenticationSharedService = _AuthenticationSharedService_;
        authService = _authService_;
        routeParams = _$routeParams_;

        $controller('LoginController', {
            $scope: scope,
            $cookieStore: cookieStore,
            AuthenticationSharedService: AuthenticationSharedService
        });
    }));

    xit("should have correct registration message", function() {
        routeParams.registrationMessage = 'tokenNotFound';
        expect(rootScope.loginerror).toBeTruthy();


    });


    //it("should update organization", function() {
    //    organizationProfile.orgName = "bham1";
    //    scope.save( organizationProfile, function(){console.log('Success');},  function (){ console.log('Error');});
    //    var updatedCarmanager = MockOrganizationService.get(1);
    //    expect(organizationProfile.orgName).toEqual(updatedCarmanager.orgName);
    //});

    //xit("should validate zipcode", function(){
    //    expect(scope.validZipCodeLength).toBeUndefined();
    //    expect(scope.validZipcode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressPostalCode).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressTelephoneNumber).toBeUndefined();
    //
    //    //Invalid zip code
    //    scope.$apply();
    //    expect(scope.isNotValidZipCodeLength).toBeFalsy();
    //    expect(scope.zipcodeNAN ).toBeFalsy();
    //
    //    //US zipcode
    //    scope.organizationProfile.mailingAddressCountryCode = "US";
    //    scope.organizationProfile.mailingAddressPostalCode = "21045"; // For valid US zip code
    //    scope.$apply();
    //    expect(scope.validZipCodeLength).toBeTruthy();
    //    expect(scope.zipcodeIsANumber ).toBeTruthy();
    //
    //    scope.organizationProfile.mailingAddressPostalCode = "2104533e"; // Invalid US zip code
    //    scope.$apply();
    //    expect(scope.isNotValidZipCodeLength).toBeFalsy();
    //    expect(scope.zipcodeNAN ).toBeFalsy();
    //});

    //xit("should validate phone number", function(){
    //    expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
    //    expect(scope.phoneNumberNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();
    //
    //    //Invalid phone number
    //    scope.$apply();
    //    expect(scope.isNotValidPhoneNumberLength).toBeFalsy();
    //    expect(scope.phoneNumberNAN ).toBeFalsy();
    //
    //    //US phone number
    //    scope.US_TELEPHONE_DIGIT_LENGTH = "10";
    //    scope.organizationProfile.mailingAddressCountryCode = "US";
    //    scope.organizationProfile.mailingAddressTelephoneNumber = "1111111111"; // For valid US phone unmber
    //    scope.$apply();
    //    expect(scope.isValidPhoneNumberLength).toBeTruthy();
    //    expect(scope.isPhoneNumber ).toBeTruthy();
    //
    //    scope.organizationProfile.mailingAddressTelephoneNumber = "2104533e"; // Invalid US phone number
    //    scope.$apply();
    //    expect(scope.isNotValidPhoneNumberLength).toBeFalsy();
    //    expect(scope.phoneNumberNAN ).toBeFalsy();
    //});

    //xit("should validate state, zip code, fax and phone number on country change", function(){
    //
    //    expect(scope.isNotValidZipCodeLength).toBeUndefined();
    //    expect(scope.zipcodeNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressPostalCode ).toBeUndefined();
    //
    //    expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
    //    expect(scope.phoneNumberNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();
    //
    //    scope.US_ZIPCODE_DIGIT_LENGTH = "5";
    //    scope.US_TELEPHONE_DIGIT_LENGTH = "10";
    //    scope.organizationProfile.mailingAddressCountryCode = "US";
    //    scope.organizationProfile.mailingAddressPostalCode = "21045";
    //    scope.organizationProfile.mailingAddressTelephoneNumber = "1111111111";
    //
    //    scope.validateStateZipcodePhoneFax();
    //
    //    expect(scope.validZipCodeLength).toBeTruthy();
    //    expect(scope.zipcodeIsANumber ).toBeTruthy();
    //    expect(scope.isValidPhoneNumberLength).toBeTruthy();
    //    expect(scope.isPhoneNumber ).toBeTruthy();
    //});

    //xit("Should enable save button if validation pass", function(){
    //    expect(scope.isNotValidZipCodeLength).toBeUndefined();
    //    expect(scope.zipcodeNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressPostalCode ).toBeUndefined();
    //
    //    expect(scope.isNotValidPhoneNumberLength ).toBeUndefined();
    //    expect(scope.phoneNumberNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressTelephoneNumber ).toBeUndefined();
    //
    //    scope.US_ZIPCODE_DIGIT_LENGTH = "5";
    //    scope.US_TELEPHONE_DIGIT_LENGTH = "10";
    //
    //    expect(form ).toBeDefined();
    //
    //    form.organizationZipCode.$setViewValue("21045");
    //    form.organizationPhoneNumber.$setViewValue("1111111111");
    //
    //    scope.$digest();
    //    expect(scope.canSave()).toBeTruthy();
    //});

    //xit("should load resolve data", function(){
    //    expect(route.current).toBeUndefined();
    //    httpBackend.expectGET('organizationalproviders').respond(200);
    //    httpBackend.expectGET('statecodes').respond(200);
    //    httpBackend.expectGET('countrycodes').respond(200);
    //    httpBackend.expectGET('servicecodes').respond(200);
    //    httpBackend.expectGET('prefixes').respond(200);
    //
    //    httpBackend.expectGET('organization/organization.tpl.html').respond(200);
    //
    //    location.path('/organization/1');
    //    rootScope.$digest();
    //
    //    expect(route.current.templateUrl).toBe('organization/organization.tpl.html');
    //    expect(route.current.params.id).toEqual('1');
    //    expect(route.current.controller).toEqual('OrganizationProfileCtrl');
    //    expect(route.current.resolve.loadedData).toBeDefined();
    //
    //    location.path('/organization');
    //    rootScope.$digest();
    //    expect(route.current).toBeUndefined();
    //
    //});

    //xit("should select a service", function(){
    //    var code = "H0018";
    //    var serviceCode = scope.isSelectedService(code);
    //    expect(serviceCode).toBeTruthy();
    //});
    //
    //xit("should validate NPI", function(){
    //    scope.organizationProfile.npi = "1233";
    //    scope.$apply();
    //    expect(scope.isNotANumber).toBeFalsy(); //Should be a number
    //});
    //
    //xit("should validate Authorize phone number", function(){
    //    expect(scope.isNotValidAutorizePhoneNumberLength  ).toBeUndefined();
    //    expect(scope.autorizePhoneNumberNAN).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.authorizedOfficialTelephoneNumber ).toBeUndefined();
    //
    //    //US phone number
    //    scope.US_TELEPHONE_DIGIT_LENGTH = "10";
    //    scope.organizationProfile.mailingAddressCountryCode = "US";
    //    scope.organizationProfile.authorizedOfficialTelephoneNumber = "1111111111"; // For valid US phone unmber
    //    scope.$apply();
    //    expect(scope.isValidAuthorizedPhoneNumberLength ).toBeTruthy();
    //    expect(scope.isAuthorizedPhoneNumber  ).toBeTruthy();
    //
    //    scope.organizationProfile.authorizedOfficialTelephoneNumber = "2104533e"; // Invalid US phone number
    //    scope.$apply();
    //    expect(scope.isAuthorizedPhoneNumber  ).toBeFalsy();
    //});
    //
    //xit("should validate fax number", function(){
    //    expect(scope.isNotValidFaxNumberLength ).toBeUndefined();
    //    expect(scope.faxNumberNAN ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressCountryCode ).toBeUndefined();
    //    expect(scope.organizationProfile.mailingAddressFaxNumber).toBeUndefined();
    //
    //    //Invalid fax number
    //    scope.$apply();
    //    expect(scope.isNotValidFaxNumberLength ).toBeFalsy();
    //    expect(scope.faxNumberNAN  ).toBeFalsy();
    //
    //    //US fax number
    //    scope.US_TELEPHONE_DIGIT_LENGTH = "10";
    //    scope.organizationProfile.mailingAddressCountryCode = "US";
    //    scope.organizationProfile.mailingAddressFaxNumber = "1111111111"; // For valid US fax unmber
    //    scope.$apply();
    //    expect(scope.isValidFaxNumberLength).toBeTruthy();
    //    expect(scope.isFaxNumber ).toBeTruthy();
    //
    //    scope.organizationProfile.mailingAddressFaxNumber = "2104533e"; // Invalid US fax number
    //    scope.$apply();
    //    expect(scope.isFaxNumber ).toBeFalsy();
    //});
    //
    //xit("should switch tab", function() {
    //    expect(scope.activeTab).toEqual('organizationProfile');
    //    scope.switchTabTo('contact');
    //    expect(scope.activeTab).toEqual('contact');
    //});
    //
    //xit("should refresh page", function() {
    //    location.path("/orgnanization/1");
    //    scope.reset();
    //    expect(location.path()).toEqual("/orgnanization/1");
    //
    //});
});

