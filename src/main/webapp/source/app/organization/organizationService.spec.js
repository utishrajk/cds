'use strict';


describe('bham.organizationService', function () {
    var module;

    beforeEach(function () {
        module = angular.module("bham.organizationService");
    });

    it("should be registered", function () {
        expect(module).not.toEqual(null);
    });

    describe("Dependencies:", function () {
        var dependencies;
        var hasModule = function (m) {
            return dependencies.indexOf(m) >= 0;
        };

        beforeEach(function () {
            dependencies = module.value('bham.organizationService').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });
    });
});

describe('OrganizationService', function(){
    var mockOrganizationService, $httpBackend, organization;

    beforeEach(module('bham.organizationService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockOrganizationService = $injector.get('OrganizationService');
        });
    });

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    /*it('should get organization by id', inject(function () {
        organization = {orgName: "Bham",mailingAddressCountryCode: "US", id:2, mailingAddressPostalCode: "21030", website: "http://bham.com", mailingAddressTelephoneNumber: "1231231234"};
        $httpBackend.whenGET("organizationalproviders").respond(organization);
        var result = mockOrganizationService.getOrganizationResource().get({id:2});
        $httpBackend.flush();
        expect(result).toEqualData(organization);
    }));*/

    it('should query country Resource ', inject(function () {
        var states = [{code:"AL", displayName:"ALABAMA"}, {code:"AK", displayName:"ALASKA"}];
        $httpBackend.whenGET("statecodes").respond(states);
        var result = mockOrganizationService.getStateResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(states);
    }));

    it('should query state Resource ', inject(function () {
        var countries = [{code: "US", displayName: "United States"}, {code: "KZ", displayName: "Kazakhstan"}];
        $httpBackend.whenGET("countrycodes").respond(countries);
        var result = mockOrganizationService.getCountryResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(countries);
    }));


    it('should query services Resource ', inject(function () {
        var services = [{code:"H0018", displayName:"Behavioral health; short-term residential"}, {code:"H2034", displayName:"Alcohol and/or drug abuse halfway house services, per diem"}];
        $httpBackend.whenGET("servicecodes").respond(services);
        var result = mockOrganizationService.getServiceResource().query();
        $httpBackend.flush();
    }));


    /*it('should update organization ', inject(function () {
        organization = {orgName: "Bham",mailingAddressCountryCode: "US", id:2, mailingAddressPostalCode: "21030", website: "http://bham.com", mailingAddressTelephoneNumber: "1231231234"};
        $httpBackend.expectPUT('organizationalproviders/2').respond({status: 200});
        var status = mockOrganizationService.update(organization,
            function(data ){
                status = data.status;
            },
            function(error){});
        $httpBackend.flush();
        expect(status).toEqual(200);
    }));*/

});
