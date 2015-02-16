'use strict';


describe('bham.dataElementsService', function () {
    var module;

    beforeEach(function () {
        module = angular.module("bham.dataElementsService");
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
            dependencies = module.value('bham.dataElementsService').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });

        it("should have bham.loggingModule as a dependency", function () {
            expect(hasModule('bham.loggingModule')).toEqual(true);
        });
    });
});

describe('DataElementsService', function(){
    var mockDataElementsService, $httpBackend, dataElements;

    beforeEach(module('bham.dataElementsService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockDataElementsService = $injector.get('DataElementsService');
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

    it('should get data elements ', inject(function () {
        dataElements = [{raceCode: " ",genderCode: "M", id:2, zipCode: "211", yearOfBirth: '1980'}];
        $httpBackend.whenGET("dataelements").respond(dataElements);
        var result = mockDataElementsService.retrieve().query();
        $httpBackend.flush();
        expect(result).toEqualData(dataElements);
    }));

    it('should get Route Codes ', inject(function () {
        var routeCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("routecodes").respond(routeCodes);
        var result = mockDataElementsService.getRouteCodeResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(routeCodes);
    }));

    it('should get ASI Route Codes ', inject(function () {
        var asiRouteCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("asiroutecodes").respond(asiRouteCodes);
        var result = mockDataElementsService.getASIRouteResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(asiRouteCodes);
    }));

    it('should get Episode Codes ', inject(function () {
        var episodeCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("episodecodes").respond(episodeCodes);
        var result = mockDataElementsService.getEpisodeCodeResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(episodeCodes);
    }));


    it('should get Status Codes ', inject(function () {
        var statusCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("statuscodes").respond(statusCodes);
        var result = mockDataElementsService.getStatusCodeResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(statusCodes);
    }));

    it('should get Yes/No Codes ', inject(function () {
        var yesNoCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("yesornocodes").respond(yesNoCodes);
        var result = mockDataElementsService.getYesOrNoCodeResource().query();
        $httpBackend.flush();
        expect(result).toEqualData(yesNoCodes);
    }));


    it('should query for Yes/No Codes ', inject(function () {
        var yesNoCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("yesornocodes").respond(yesNoCodes);
        var result = mockDataElementsService.getYesNoCodes(
            function(data){
                result=data;
            }, function(error){});
        $httpBackend.flush();
        expect(result).toEqualData(yesNoCodes);
    }));


    it('should query for Status Codes ', inject(function () {
        var statusCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("statuscodes").respond(statusCodes);
        var result = mockDataElementsService.getStatusCodes(
            function(data){
                result=data;
            }, function(error){

            });
        $httpBackend.flush();
        expect(result).toEqualData(statusCodes);
    }));

    it('should query for Episode Codes ', inject(function () {
        var episodeCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("episodecodes").respond(episodeCodes);
        var result = mockDataElementsService.getEpisodeCodes(
            function(data){
                result=data;
            }, function(error){

            });
        $httpBackend.flush();
        expect(result).toEqualData(episodeCodes);
    }));

    it('should query for ASI route Codes ', inject(function () {
        var asiRouteCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("asiroutecodes").respond(asiRouteCodes);
        var result = mockDataElementsService.getASIRouteCodes(
            function(data){
                result=data;
            }, function(error){

            });
        $httpBackend.flush();
        expect(result).toEqualData(asiRouteCodes);
    }));

    it('should Route Codes ', inject(function () {
        var routeCodes = [{code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"}];
        $httpBackend.whenGET("routecodes").respond(routeCodes);
        var result = mockDataElementsService.getRouteCodes(
            function(data){
                result=data;
            }, function(error){

            });
        $httpBackend.flush();
        expect(result).toEqualData(routeCodes);
    }));

    it('should get data elements ', inject(function () {
        var dataElements = {requestId:1000, code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"};
        $httpBackend.whenGET("dataelements/1000").respond(dataElements);
        var result = mockDataElementsService.get(1000,
            function(data){
                result=data;
            }, function(error){});
        $httpBackend.flush();
        expect(result).toEqualData(dataElements);
    }));

    it('should create ASI data elements ', inject(function () {
        var dataElements = {requestId:1000, code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"};
        $httpBackend.whenPUT("dataelements/asi/1000").respond({status: 200});
        var status = mockDataElementsService.createAsi(1000, dataElements,
            function(data){
                status = data.status;
            }, function(error){});
        $httpBackend.flush();
        expect(status).toEqualData(200);
    }));

    it('should create GPRA data elements ', inject(function () {
        var dataElements = {requestId:1000, code: "1002-5",codeSystem: "2.16.840.1.113883.6.238", codeSystemName: "Race and Ethnicity - CDC", displayName: "American Indian or Alaska Native"};
        $httpBackend.whenPUT("dataelements/gpra/1000").respond({status: 200});
        var status = mockDataElementsService.create(1000, dataElements,
            function(data){
                status = data.status;
            }, function(error){});
        $httpBackend.flush();
        expect(status).toEqualData(200);
    }));
});
