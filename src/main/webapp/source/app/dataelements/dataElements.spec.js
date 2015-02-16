'use strict';

describe('bham.dataElementsModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.dataElementsModule");
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
            dependencies = module.value('bham.dataElementsModule').requires;
        });

        it("should have bham.security as a dependency", function() {
            expect(hasModule('bham.security')).toEqual(true);
        });

        it("should have bhma.dataElementsService as a dependency", function() {
            expect(hasModule('bham.dataElementsService')).toEqual(true);
        });

        it("should have ngDialog as a dependency", function() {
            expect(hasModule('ngDialog')).toEqual(true);
        });
    });
});


xdescribe("bham.dataElementsModule DataElementsCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.dataElementsModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockDataElementsService, dataElements, loadedData, form , route, location, rootScope, httpBackend, growl, UserService, ErrorService, $modal;

    beforeEach(inject(function($rootScope, $controller, $compile, $route, $location, $httpBackend){
       dataElements = {raceCode: " ",genderCode: "M", id:2, zipCode: "211", yearOfBirth: '1980'};

        scope = $rootScope.$new();

        rootScope = $rootScope;
        route = $route;
        location = $location;
        httpBackend = $httpBackend;

        MockDataElementsService = { };

        loadedData = [{},dataElements];

        scope.races = loadedData[0];

        var element = angular.element(
            '<form name="patientForm">' +
                '<input ng-model="patient.yearOfBirth" name="yearOfBirth" type="text" />' +
                '<input ng-model="patient.zipCode" name="zipCode"  type="text" />' +
                '</form>'
        );

        $compile(element)(scope);
        scope.$digest();
        form = scope.patientForm;

        scope.setActiveMenuItem = function(menuItem){
        };

        scope.isValidContactNumber = function (number, countryCode, requireLength) {
            var result = false;
            if( scope.isNullOrUndefined(number) ){
                result = false;
            }else {
                result = scope.isUnitedState(countryCode) && (  number.length === requireLength );
            }
            return result;
        };

        scope.isUnitedState = function(countryCode){
            return countryCode === "US";
        };

        scope.isNullOrUndefined = function(value){
            return angular.isUndefined(value) || value === null;
        };

        scope.isValidNumber = function (number) {
            var result = false;
            if(scope.isNullOrUndefined(number)){
                return result;
            }else {
                var trimmedNumber = number.trim();
                if ( !isNaN(trimmedNumber) && (trimmedNumber.length > 0)) {
                    result = true;
                }
            }
            return result;
        };

        scope.patient = {};

        $controller('DataElementsCtrl', {
            $scope: scope,
            DataElementsService: MockDataElementsService,
            loadedData: loadedData
        });


    }));

    xit("Should enable save button if validation pass", function(){
        expect(form ).toBeDefined();
        form.yearOfBirth.$setViewValue("2013");
        form.zipCode.$setViewValue("111");
        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    //To look at
    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('dataelements/111').respond(200);
        location.path('/dataelements');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('dataelements/dataElements.tpl.html');
        expect(route.current.controller).toEqual('DataElementsCtrl');
        //expect(route.current.resolve.loadedData).toBeDefined();
    });

    it("should be a an integer between 0 and 30 or X", function(){

        //Initially
        expect(scope.m6_days_medical_problem).toBeFalsy();
        expect(scope.e19_days_employment_problem ).toBeFalsy();
        expect(scope.l27_days_illegal ).toBeFalsy();
        expect(scope.f30_days_conflict_family ).toBeFalsy();

        //Should be true for numbers between 0 and 30 or X
        scope.patient.m6_days_medical_problem = 30;
        scope.validateNumberOrX('m6_days_medical_problem');
        expect(scope.m6_days_medical_problem).toBeFalsy(); // The boolean response in the code is negated

        scope.patient.m6_days_medical_problem = 'X';
        scope.validateNumberOrX('m6_days_medical_problem');
        expect(scope.m6_days_medical_problem).toBeFalsy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.m6_days_medical_problem = 'Y';
        scope.validateNumberOrX('m6_days_medical_problem');
        expect(scope.m6_days_medical_problem).toBeTruthy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.m6_days_medical_problem = 40;
        scope.validateNumberOrX('m6_days_medical_problem');
        expect(scope.m6_days_medical_problem).toBeTruthy(); // The boolean response in the code is negated

        //e19_days_employment_problem
        //Should be true for numbers between 0 and 30 or X
        scope.patient.e19_days_employment_problem = 30;
        scope.validateNumberOrX('e19_days_employment_problem');
        expect(scope.e19_days_employment_problem).toBeFalsy(); // The boolean response in the code is negated

        scope.patient.e19_days_employment_problem = 'X';
        scope.validateNumberOrX('e19_days_employment_problem');
        expect(scope.e19_days_employment_problem).toBeFalsy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.e19_days_employment_problem = 'Y';
        scope.validateNumberOrX('e19_days_employment_problem');
        expect(scope.e19_days_employment_problem).toBeTruthy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.e19_days_employment_problem = 40;
        scope.validateNumberOrX('e19_days_employment_problem');
        expect(scope.e19_days_employment_problem).toBeTruthy(); // The boolean response in the code is negated

        //l27_days_illegal
        //Should be true for numbers between 0 and 30 or X
        scope.patient.l27_days_illegal = 30;
        scope.validateNumberOrX('l27_days_illegal');
        expect(scope.l27_days_illegal).toBeFalsy(); // The boolean response in the code is negated

        scope.patient.l27_days_illegal = 'X';
        scope.validateNumberOrX('l27_days_illegal');
        expect(scope.l27_days_illegal).toBeFalsy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.l27_days_illegal = 'Y';
        scope.validateNumberOrX('l27_days_illegal');
        expect(scope.l27_days_illegal).toBeTruthy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.l27_days_illegal = 40;
        scope.validateNumberOrX('l27_days_illegal');
        expect(scope.l27_days_illegal).toBeTruthy(); // The boolean response in the code is negated

        //f30_days_conflict_family
        //Should be true for numbers between 0 and 30 or X
        scope.patient.f30_days_conflict_family = 30;
        scope.validateNumberOrX('f30_days_conflict_family');
        expect(scope.f30_days_conflict_family).toBeFalsy(); // The boolean response in the code is negated

        scope.patient.f30_days_conflict_family = 'X';
        scope.validateNumberOrX('f30_days_conflict_family');
        expect(scope.f30_days_conflict_family).toBeFalsy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.f30_days_conflict_family = 'Y';
        scope.validateNumberOrX('f30_days_conflict_family');
        expect(scope.f30_days_conflict_family).toBeTruthy(); // The boolean response in the code is negated

        // Should fail is a number not in the range 0 to 30 or leter X is enter
        scope.patient.f30_days_conflict_family = 40;
        scope.validateNumberOrX('f30_days_conflict_family');
        expect(scope.f30_days_conflict_family).toBeTruthy(); // The boolean response in the code is negated

        scope.patient.f30_days_conflict_family = null;
        scope.validateNumberOrX('f30_days_conflict_family');
        expect(scope.f30_days_conflict_family).toBeTruthy(); // The boolean response in the code is negated

    });


    it("should validate gender and other gender", function(){
        expect(scope.patient.genderCode).toBeUndefined();
        expect(scope.patient.otherDescription ).toBeUndefined();

        scope.patient.genderCode = "MA";
        scope.validateGender();
        expect(scope.disableOtherGender).toBeTruthy();
        expect(scope.isOtherGenderInvalid).toBeFalsy();
        expect(scope.patient.otherDescription).toBeNull();

        scope.patient.genderCode = "FE";
        scope.validateGender();
        expect(scope.disableOtherGender).toBeTruthy();
        expect(scope.isOtherGenderInvalid).toBeFalsy();
        expect(scope.patient.otherDescription).toBeNull();


        scope.patient.genderCode = "TG";
        scope.validateGender();
        expect(scope.disableOtherGender).toBeTruthy();
        expect(scope.isOtherGenderInvalid).toBeFalsy();
        expect(scope.patient.otherDescription).toBeNull();

        scope.patient.genderCode = "Other";
        scope.validateGender();
        expect(scope.disableOtherGender).toBeFalsy();


    });


    it("should validate other gender description", function(){
        expect(scope.isOtherGenderInvalid).toBeFalsy();

        scope.patient.otherDescription = "other gender";
        scope.ValidateOther();

        expect(scope.isOtherGenderInvalid).toBeFalsy();

        scope.patient.otherDescription = null;
        scope.ValidateOther();

        expect(scope.isOtherGenderInvalid).toBeTruthy();
    });


    it("should validate number of days", function(){
       expect(scope.isValidNumberOfDays(10)).toBeTruthy();
        expect(scope.isValidNumberOfDays(-1)).toBeFalsy();
        expect(scope.isValidNumberOfDays(31)).toBeFalsy();
    });

    it("should validate age", function(){

        expect(scope.patient.age).toBeUndefined();

        scope.patient.age = 40;
        scope.isValidAge();
        expect(scope.isAgeOk).toBeTruthy();
        expect(scope.isOverAge ).toBeFalsy();

        scope.patient.age = 90;
        scope.isValidAge();
        expect(scope.isAgeOk).toBeFalsy();
        expect(scope.isOverAge ).toBeTruthy();

        scope.patient.age = 90;
        scope.isValidAge();
        expect(scope.isAgeOk).toBeFalsy();
        expect(scope.isOverAge ).toBeTruthy();
    });


    it("should validate route value", function(){

      expect(scope.isValidRouteValue(1,-7)).toBeTruthy();
      expect(scope.isValidRouteValue(5,-8)).toBeTruthy();
      expect(scope.isValidRouteValue(12,-7)).toBeFalsy();
      expect(scope.isValidRouteValue(1,-10)).toBeFalsy();
    });


    it("should validate source of income", function(){

        scope.patient.e17_income_illegal = 100;
        scope.validateIllegalSourceOfIncome();
        expect(scope.illigalSource ).toBeFalsy();

        scope.patient.e17_income_illegal = -1;
        scope.validateIllegalSourceOfIncome();
        expect(scope.illigalSource ).toBeTruthy();

        scope.patient.e17_income_illegal = 99999 ;
        scope.validateIllegalSourceOfIncome();
        expect(scope.illigalSource ).toBeFalsy();
    });

    it("should  GPRA day count", function(){
        //heroinDayCount
        expect(scope.drugUse.heroinDayCount).toBeUndefined();
        expect(scope.isValidHeroinDayCount).toBeFalsy();
        scope.patient.heroinDayCount = 12;
        scope.onNumberOfDaysChange('heroinDayCount');
        expect(scope.drugUse.heroinDayCount).toEqual("");
        expect(scope.isValidHeroinDayCount).toBeFalsy();
        scope.patient.heroinDayCount = 42;
        scope.onNumberOfDaysChange('heroinDayCount');
        expect(scope.drugUse.heroinDayCount).toEqual("");
        expect(scope.isValidHeroinDayCount).toBeTruthy();

        //morphineCount
        expect(scope.drugUse.morphineCount).toBeUndefined();
        expect(scope.isValidMorphineCount ).toBeFalsy();
        scope.patient.morphineCount = 12;
        scope.onNumberOfDaysChange('morphineCount');
        expect(scope.drugUse.morphineCount).toEqual("");
        expect(scope.isValidMorphineCount ).toBeFalsy();
        scope.patient.morphineCount = 42;
        scope.onNumberOfDaysChange('morphineCount');
        expect(scope.drugUse.morphineCount).toEqual("");
        expect(scope.isValidMorphineCount ).toBeTruthy();

        //diluadidCount
        expect(scope.drugUse.diluadidCount).toBeUndefined();
        expect(scope.isValidDiluadidCount  ).toBeFalsy();
        scope.patient.diluadidCount = 12;
        scope.onNumberOfDaysChange('diluadidCount');
        expect(scope.drugUse.diluadidCount).toEqual("");
        expect(scope.isValidDiluadidCount  ).toBeFalsy();
        scope.patient.diluadidCount = 42;
        scope.onNumberOfDaysChange('diluadidCount');
        expect(scope.drugUse.diluadidCount).toEqual("");
        expect(scope.isValidDiluadidCount  ).toBeTruthy();

        //demerolCount
        expect(scope.drugUse.demerolCount).toBeUndefined();
        expect(scope.isValidDemerolCount   ).toBeFalsy();
        scope.patient.demerolCount = 12;
        scope.onNumberOfDaysChange('demerolCount');
        expect(scope.drugUse.demerolCount).toEqual("");
        expect(scope.isValidDemerolCount   ).toBeFalsy();
        scope.patient.demerolCount = 42;
        scope.onNumberOfDaysChange('demerolCount');
        expect(scope.drugUse.demerolCount).toEqual("");
        expect(scope.isValidDemerolCount  ).toBeTruthy();

        //percocetCount
        expect(scope.drugUse.percocetCount).toBeUndefined();
        expect(scope.isValidPercocetCount    ).toBeFalsy();
        scope.patient.percocetCount = 12;
        scope.onNumberOfDaysChange('percocetCount');
        expect(scope.drugUse.percocetCount).toEqual("");
        expect(scope.isValidPercocetCount    ).toBeFalsy();
        scope.patient.percocetCount = 42;
        scope.onNumberOfDaysChange('percocetCount');
        expect(scope.drugUse.percocetCount).toEqual("");
        expect(scope.isValidPercocetCount   ).toBeTruthy();

        //darvonCount
        expect(scope.drugUse.darvonCount).toBeUndefined();
        expect(scope.isValidDarvonCount ).toBeFalsy();
        scope.patient.darvonCount = 12;
        scope.onNumberOfDaysChange('darvonCount');
        expect(scope.drugUse.darvonCount).toEqual("");
        expect(scope.isValidDarvonCount     ).toBeFalsy();
        scope.patient.darvonCount = 42;
        scope.onNumberOfDaysChange('darvonCount');
        expect(scope.drugUse.darvonCount).toEqual("");
        expect(scope.isValidDarvonCount ).toBeTruthy();


        //codeineCount
        expect(scope.drugUse.codeineCount).toBeUndefined();
        expect(scope.isValidCodeineCount  ).toBeFalsy();
        scope.patient.codeineCount = 12;
        scope.onNumberOfDaysChange('codeineCount');
        expect(scope.drugUse.codeineCount).toEqual("");
        expect(scope.isValidCodeineCount      ).toBeFalsy();
        scope.patient.codeineCount = 42;
        scope.onNumberOfDaysChange('codeineCount');
        expect(scope.drugUse.codeineCount).toEqual("");
        expect(scope.isValidCodeineCount  ).toBeTruthy();

        //tylenolCount
        expect(scope.drugUse.tylenolCount).toBeUndefined();
        expect(scope.isValidTylenolCount).toBeFalsy();
        scope.patient.tylenolCount = 12;
        scope.onNumberOfDaysChange('tylenolCount');
        expect(scope.drugUse.tylenolCount).toEqual("");
        expect(scope.isValidTylenolCount ).toBeFalsy();
        scope.patient.tylenolCount = 42;
        scope.onNumberOfDaysChange('tylenolCount');
        expect(scope.drugUse.tylenolCount).toEqual("");
        expect(scope.isValidTylenolCount).toBeTruthy();

        //oxycontinCount
        expect(scope.drugUse.oxycontinCount).toBeUndefined();
        expect(scope.isValidOxycontinCount ).toBeFalsy();
        scope.patient.oxycontinCount = 12;
        scope.onNumberOfDaysChange('oxycontinCount');
        expect(scope.drugUse.oxycontinCount).toEqual("");
        expect(scope.isValidOxycontinCount  ).toBeFalsy();
        scope.patient.oxycontinCount = 42;
        scope.onNumberOfDaysChange('oxycontinCount');
        expect(scope.drugUse.oxycontinCount).toEqual("");
        expect(scope.isValidOxycontinCount ).toBeTruthy();

        //methadoneCount
        expect(scope.drugUse.methadoneCount).toBeUndefined();
        expect(scope.isValidMethadoneCount ).toBeFalsy();
        scope.patient.methadoneCount = 12;
        scope.onNumberOfDaysChange('methadoneCount');
        expect(scope.drugUse.methadoneCount).toEqual("");
        expect(scope.isValidMethadoneCount ).toBeFalsy();
        scope.patient.methadoneCount = 42;
        scope.onNumberOfDaysChange('methadoneCount');
        expect(scope.drugUse.methadoneCount).toEqual("");
        expect(scope.isValidMethadoneCount ).toBeTruthy();


        //depressionCount
        expect(scope.mentalPhysicalProblem.depressionCount).toBeUndefined();
        expect(scope.isInValidDepressionCount  ).toBeFalsy();
        scope.patient.depressionCount = 12;
        scope.onNumberOfDaysChange('depressionCount');
        expect(scope.mentalPhysicalProblem.depressionCount).toEqual("");
        expect(scope.isInValidDepressionCount  ).toBeFalsy();
        scope.patient.depressionCount = 42;
        scope.onNumberOfDaysChange('depressionCount');
        expect(scope.mentalPhysicalProblem.depressionCount).toEqual("");
        expect(scope.isInValidDepressionCount  ).toBeTruthy();



        ////anxietyCount
        expect(scope.mentalPhysicalProblem.anxietyCount).toBeUndefined();
        expect(scope.isInValidAnxietyCount  ).toBeFalsy();
        scope.patient.anxietyCount = 12;
        scope.onNumberOfDaysChange('anxietyCount');
        expect(scope.mentalPhysicalProblem.anxietyCount).toEqual("");
        expect(scope.isInValidAnxietyCount  ).toBeFalsy();
        scope.patient.anxietyCount = 42;
        scope.onNumberOfDaysChange('anxietyCount');
        expect(scope.mentalPhysicalProblem.anxietyCount).toEqual("");
        expect(scope.isInValidAnxietyCount  ).toBeTruthy();


        ////violentCount
        expect(scope.mentalPhysicalProblem.violentCount).toBeUndefined();
        expect(scope.isInValidViolentCount   ).toBeFalsy();
        scope.patient.violentCount = 12;
        scope.onNumberOfDaysChange('violentCount');
        expect(scope.mentalPhysicalProblem.violentCount).toEqual("");
        expect(scope.isInValidViolentCount   ).toBeFalsy();
        scope.patient.violentCount = 42;
        scope.onNumberOfDaysChange('violentCount');
        expect(scope.mentalPhysicalProblem.violentCount).toEqual("");
        expect(scope.isInValidViolentCount   ).toBeTruthy();


        //psychMedicationCount
        expect(scope.mentalPhysicalProblem.psychMedicationCount).toBeUndefined();
        expect(scope.isInValidPsychMedicationCount    ).toBeFalsy();
        scope.patient.psychMedicationCount = 12;
        scope.onNumberOfDaysChange('psychMedicationCount');
        expect(scope.mentalPhysicalProblem.psychMedicationCount).toEqual("");
        expect(scope.isInValidPsychMedicationCount    ).toBeFalsy();
        scope.patient.psychMedicationCount = 42;
        scope.onNumberOfDaysChange('psychMedicationCount');
        expect(scope.mentalPhysicalProblem.psychMedicationCount).toEqual("");
        expect(scope.isInValidPsychMedicationCount    ).toBeTruthy();

        //d2a_intoxication_30
        expect(scope.isInvalidAlcoholToxicationNumberOfDays).toBeFalsy();
        scope.patient.d2a_intoxication_30 = 12;
        scope.onNumberOfDaysChange('d2a_intoxication_30');
        expect(scope.isInvalidAlcoholToxicationNumberOfDays).toBeFalsy();
        scope.patient.d2a_intoxication_30 = 42;
        scope.onNumberOfDaysChange('d2a_intoxication_30');
        expect(scope.isInvalidAlcoholToxicationNumberOfDays).toBeTruthy();


        //d3a_heroin_30
        expect(scope.isInvalidHeroinNumberOfDays ).toBeFalsy();
        scope.patient.d3a_heroin_30 = 12;
        scope.onNumberOfDaysChange('d3a_heroin_30');
        expect(scope.isInvalidHeroinNumberOfDays ).toBeFalsy();
        scope.patient.d3a_heroin_30 = 42;
        scope.onNumberOfDaysChange('d3a_heroin_30');
        expect(scope.isInvalidHeroinNumberOfDays ).toBeTruthy();

        //d4a_methadone_30
        expect(scope.isInvalidMethadoneNumberOfDays  ).toBeFalsy();
        scope.patient.d4a_methadone_30 = 12;
        scope.onNumberOfDaysChange('d4a_methadone_30');
        expect(scope.isInvalidMethadoneNumberOfDays  ).toBeFalsy();
        scope.patient.d4a_methadone_30 = 42;
        scope.onNumberOfDaysChange('d4a_methadone_30');
        expect(scope.isInvalidMethadoneNumberOfDays  ).toBeTruthy();

        //d4a_methadone_30
        expect(scope.isInvalidOpiatesAnalgesicsNumberOfDays   ).toBeFalsy();
        scope.patient.d5a_opiates_30 = 12;
        scope.onNumberOfDaysChange('d5a_opiates_30');
        expect(scope.isInvalidOpiatesAnalgesicsNumberOfDays   ).toBeFalsy();
        scope.patient.d5a_opiates_30 = 42;
        scope.onNumberOfDaysChange('d5a_opiates_30');
        expect(scope.isInvalidOpiatesAnalgesicsNumberOfDays   ).toBeTruthy();

    });


    it("should have valid route value", function(){
        //heroinRouteCode
        expect(scope.drugUse.heroinRouteCode).toBeUndefined();
        expect(scope.isInvalidHeroinRoute ).toBeFalsy();
        scope.patient.heroinRouteCode = 5;
        scope.onRouteChange ('heroinRouteCode');
        expect(scope.drugUse.heroinRouteCode).toEqual("");
        expect(scope.isInvalidHeroinRoute  ).toBeFalsy();
        scope.patient.heroinRouteCode = 42;
        scope.onRouteChange('heroinRouteCode');
        expect(scope.drugUse.heroinRouteCode).toEqual("");
        expect(scope.isInvalidHeroinRoute ).toBeTruthy();

        //morphineRouteCode
        expect(scope.drugUse.morphineRouteCode).toBeUndefined();
        expect(scope.isInvalidMorphineRoute  ).toBeFalsy();
        scope.patient.morphineRouteCode = 5;
        scope.onRouteChange ('morphineRouteCode');
        expect(scope.drugUse.morphineRouteCode).toEqual("");
        expect(scope.isInvalidMorphineRoute   ).toBeFalsy();
        scope.patient.morphineRouteCode = 42;
        scope.onRouteChange('morphineRouteCode');
        expect(scope.drugUse.morphineRouteCode).toEqual("");
        expect(scope.isInvalidMorphineRoute  ).toBeTruthy();

        //diluadidRouteCode
        expect(scope.drugUse.diluadidRouteCode).toBeUndefined();
        expect(scope.isInvalidDiluadidRoute   ).toBeFalsy();
        scope.patient.diluadidRouteCode = 5;
        scope.onRouteChange ('diluadidRouteCode');
        expect(scope.drugUse.diluadidRouteCode).toEqual("");
        expect(scope.isInvalisInvalidDiluadidRoute).toBeFalsy();
        scope.patient.diluadidRouteCode = 42;
        scope.onRouteChange('diluadidRouteCode');
        expect(scope.drugUse.diluadidRouteCode).toEqual("");
        expect(scope.isInvalidDiluadidRoute   ).toBeTruthy();

        //demerolRouteCode
        expect(scope.drugUse.demerolRouteCode).toBeUndefined();
        expect(scope.isInvalidDemerolRoute    ).toBeFalsy();
        scope.patient.demerolRouteCode = 5;
        scope.onRouteChange ('demerolRouteCode');
        expect(scope.drugUse.demerolRouteCode).toEqual("");
        expect(scope.isInvalidDemerolRoute ).toBeFalsy();
        scope.patient.demerolRouteCode = 42;
        scope.onRouteChange('demerolRouteCode');
        expect(scope.drugUse.demerolRouteCode).toEqual("");
        expect(scope.isInvalidDemerolRoute    ).toBeTruthy();


        //percocetRouteCode
        expect(scope.drugUse.percocetRouteCode).toBeUndefined();
        expect(scope.isInvalidPercocetRoute ).toBeFalsy();
        scope.patient.percocetRouteCode = 5;
        scope.onRouteChange ('percocetRouteCode');
        expect(scope.drugUse.percocetRouteCode).toEqual("");
        expect(scope.isInvalidPercocetRoute  ).toBeFalsy();
        scope.patient.percocetRouteCode = 42;
        scope.onRouteChange('percocetRouteCode');
        expect(scope.drugUse.percocetRouteCode).toEqual("");
        expect(scope.isInvalidPercocetRoute).toBeTruthy();

        //darvonRouteCode
        expect(scope.drugUse.darvonRouteCode).toBeUndefined();
        expect(scope.isInvalidDarvonRoute  ).toBeFalsy();
        scope.patient.darvonRouteCode = 5;
        scope.onRouteChange ('darvonRouteCode');
        expect(scope.drugUse.darvonRouteCode).toEqual("");
        expect(scope.isInvalidDarvonRoute   ).toBeFalsy();
        scope.patient.darvonRouteCode = 42;
        scope.onRouteChange('darvonRouteCode');
        expect(scope.drugUse.darvonRouteCode).toEqual("");
        expect(scope.isInvalidDarvonRoute ).toBeTruthy();

        //codeineRouteCode
        expect(scope.drugUse.codeineRouteCode).toBeUndefined();
        expect(scope.isInvalidCodeineRoute   ).toBeFalsy();
        scope.patient.codeineRouteCode = 5;
        scope.onRouteChange ('codeineRouteCode');
        expect(scope.drugUse.codeineRouteCode).toEqual("");
        expect(scope.isInvalidCodeineRoute    ).toBeFalsy();
        scope.patient.codeineRouteCode = 42;
        scope.onRouteChange('codeineRouteCode');
        expect(scope.drugUse.codeineRouteCode).toEqual("");
        expect(scope.isInvalidCodeineRoute  ).toBeTruthy();

        //tylenolRouteCode
        expect(scope.drugUse.tylenolRouteCode).toBeUndefined();
        expect(scope.isInvalidTylenolRoute    ).toBeFalsy();
        scope.patient.tylenolRouteCode = 5;
        scope.onRouteChange ('tylenolRouteCode');
        expect(scope.drugUse.tylenolRouteCode).toEqual("");
        expect(scope.isInvalidTylenolRoute     ).toBeFalsy();
        scope.patient.tylenolRouteCode = 42;
        scope.onRouteChange('tylenolRouteCode');
        expect(scope.drugUse.tylenolRouteCode).toEqual("");
        expect(scope.isInvalidTylenolRoute   ).toBeTruthy();

        //oxycontinRouteCode
        expect(scope.drugUse.oxycontinRouteCode).toBeUndefined();
        expect(scope.isInvalidOxycontinRoute     ).toBeFalsy();
        scope.patient.oxycontinRouteCode = 5;
        scope.onRouteChange ('oxycontinRouteCode');
        expect(scope.drugUse.oxycontinRouteCode).toEqual("");
        expect(scope.isInvalidOxycontinRoute      ).toBeFalsy();
        scope.patient.oxycontinRouteCode = 42;
        scope.onRouteChange('oxycontinRouteCode');
        expect(scope.drugUse.oxycontinRouteCode).toEqual("");
        expect(scope.isInvalidOxycontinRoute).toBeTruthy();

        //methadoneRouteCode
        expect(scope.drugUse.methadoneRouteCode).toBeUndefined();
        expect(scope.isInvalidMethadoneRoute ).toBeFalsy();
        scope.patient.methadoneRouteCode = 5;
        scope.onRouteChange ('methadoneRouteCode');
        expect(scope.drugUse.methadoneRouteCode).toEqual("");
        expect(scope.isInvalidMethadoneRoute ).toBeFalsy();
        scope.patient.methadoneRouteCode = 42;
        scope.onRouteChange('methadoneRouteCode');
        expect(scope.drugUse.methadoneRouteCode).toEqual("");
        expect(scope.isInvalidMethadoneRoute).toBeTruthy();

    });

    it("should set GPrA day count to empty string when radio button is click ", function(){
        expect(scope.patient.heroinDayCount).toBeUndefined();
        expect(scope.isValidHeroinDayCount).toBeFalsy();
        scope.onRadioButtonChange('heroinDayCount');
        expect(scope.patient.heroinDayCount).toEqual('');
        expect(scope.isValidHeroinDayCount).toBeFalsy();

        expect(scope.patient.morphineCount).toBeUndefined();
        expect(scope.isValidMorphineCount).toBeFalsy();
        scope.onRadioButtonChange('morphineCount');
        expect(scope.patient.morphineCount).toEqual('');
        expect(scope.isValidMorphineCount).toBeFalsy();

        expect(scope.patient.diluadidCount).toBeUndefined();
        expect(scope.isValidDiluadidCount ).toBeFalsy();
        scope.onRadioButtonChange('diluadidCount');
        expect(scope.patient.diluadidCount).toEqual('');
        expect(scope.isValidDiluadidCount ).toBeFalsy();

        expect(scope.patient.demerolCount).toBeUndefined();
        expect(scope.isValidDemerolCount ).toBeFalsy();
        scope.onRadioButtonChange('demerolCount');
        expect(scope.patient.demerolCount).toEqual('');
        expect(scope.isValidDemerolCount ).toBeFalsy();

        expect(scope.patient.percocetCount).toBeUndefined();
        expect(scope.isValidPercocetCount  ).toBeFalsy();
        scope.onRadioButtonChange('percocetCount');
        expect(scope.patient.percocetCount).toEqual('');
        expect(scope.isValidPercocetCount  ).toBeFalsy();

        expect(scope.patient.darvonCount).toBeUndefined();
        expect(scope.isValidDarvonCount  ).toBeFalsy();
        scope.onRadioButtonChange('darvonCount');
        expect(scope.patient.darvonCount).toEqual('');
        expect(scope.isValidDarvonCount ).toBeFalsy();

        expect(scope.patient.tylenolCount).toBeUndefined();
        expect(scope.isValidTylenolCount  ).toBeFalsy();
        scope.onRadioButtonChange('tylenolCount');
        expect(scope.patient.tylenolCount).toEqual('');
        expect(scope.isValidTylenolCount  ).toBeFalsy();

        expect(scope.patient.oxycontinCount).toBeUndefined();
        expect(scope.isValidOxycontinCount  ).toBeFalsy();
        scope.onRadioButtonChange('oxycontinCount');
        expect(scope.patient.oxycontinCount).toEqual('');
        expect(scope.isValidOxycontinCount  ).toBeFalsy();

        expect(scope.patient.codeineCount).toBeUndefined();
        expect(scope.isValidCodeineCount  ).toBeFalsy();
        scope.onRadioButtonChange('codeineCount');
        expect(scope.patient.codeineCount).toEqual('');
        expect(scope.isValidCodeineCount  ).toBeFalsy();

        expect(scope.patient.methadoneCount).toBeUndefined();
        expect(scope.isValidMethadoneCount   ).toBeFalsy();
        scope.onRadioButtonChange('methadoneCount');
        expect(scope.patient.methadoneCount).toEqual('');
        expect(scope.isValidMethadoneCount   ).toBeFalsy();

        expect(scope.patient.heroinRouteCode).toBeUndefined();
        expect(scope.isInvalidHeroinRoute   ).toBeFalsy();
        scope.onRadioButtonChange('heroinRouteCode');
        expect(scope.patient.heroinRouteCode).toEqual('');
        expect(scope.isInvalidHeroinRoute   ).toBeFalsy();

        expect(scope.patient.morphineRouteCode).toBeUndefined();
        expect(scope.isInvalidMorphineRoute    ).toBeFalsy();
        scope.onRadioButtonChange('morphineRouteCode');
        expect(scope.patient.morphineRouteCode).toEqual('');
        expect(scope.isInvalidMorphineRoute    ).toBeFalsy();

        expect(scope.patient.diluadidRouteCode).toBeUndefined();
        expect(scope.isInvalidDiluadidRoute    ).toBeFalsy();
        scope.onRadioButtonChange('diluadidRouteCode');
        expect(scope.patient.diluadidRouteCode).toEqual('');
        expect(scope.isInvalidDiluadidRoute    ).toBeFalsy();

        expect(scope.patient.demerolRouteCode).toBeUndefined();
        expect(scope.isInvalidDemerolRoute     ).toBeFalsy();
        scope.onRadioButtonChange('demerolRouteCode');
        expect(scope.patient.demerolRouteCode).toEqual('');
        expect(scope.isInvalidDemerolRoute     ).toBeFalsy();

        expect(scope.patient.percocetRouteCode).toBeUndefined();
        expect(scope.isInvalidPercocetRoute     ).toBeFalsy();
        scope.onRadioButtonChange('percocetRouteCode');
        expect(scope.patient.percocetRouteCode).toEqual('');
        expect(scope.isInvalidPercocetRoute ).toBeFalsy();

        expect(scope.patient.codeineRouteCode).toBeUndefined();
        expect(scope.isInvalidCodeineRoute ).toBeFalsy();
        scope.onRadioButtonChange('codeineRouteCode');
        expect(scope.patient.codeineRouteCode).toEqual('');
        expect(scope.isInvalidCodeineRoute  ).toBeFalsy();

        expect(scope.patient.darvonRouteCode).toBeUndefined();
        expect(scope.isInvalidDarvonRoute ).toBeFalsy();
        scope.onRadioButtonChange('darvonRouteCode');
        expect(scope.patient.darvonRouteCode).toEqual('');
        expect(scope.isInvalidDarvonRoute  ).toBeFalsy();

        expect(scope.patient.tylenolRouteCode).toBeUndefined();
        expect(scope.isInvalidTylenolRoute ).toBeFalsy();
        scope.onRadioButtonChange('tylenolRouteCode');
        expect(scope.patient.tylenolRouteCode).toEqual('');
        expect(scope.isInvalidTylenolRoute).toBeFalsy();

        expect(scope.patient.oxycontinRouteCode).toBeUndefined();
        expect(scope.isInvalidOxycontinRoute  ).toBeFalsy();
        scope.onRadioButtonChange('oxycontinRouteCode');
        expect(scope.patient.oxycontinRouteCode).toEqual('');
        expect(scope.isInvalidOxycontinRoute   ).toBeFalsy();

        expect(scope.patient.methadoneRouteCode).toBeUndefined();
        expect(scope.isInvalidMethadoneRoute ).toBeFalsy();
        scope.onRadioButtonChange('methadoneRouteCode');
        expect(scope.patient.methadoneRouteCode).toEqual('');
        expect(scope.isInvalidMethadoneRoute ).toBeFalsy();

        expect(scope.patient.depressionCount).toBeUndefined();
        expect(scope.isInValidDepressionCount  ).toBeFalsy();
        scope.onRadioButtonChange('depressionCount');
        expect(scope.patient.depressionCount).toEqual('');
        expect(scope.isInValidDepressionCount  ).toBeFalsy();

        expect(scope.patient.anxietyCount).toBeUndefined();
        expect(scope.isInValidAnxietyCount   ).toBeFalsy();
        scope.onRadioButtonChange('anxietyCount');
        expect(scope.patient.anxietyCount).toEqual('');
        expect(scope.isInValidAnxietyCount   ).toBeFalsy();


        expect(scope.patient.violentCount).toBeUndefined();
        expect(scope.isInValidViolentCount   ).toBeFalsy();
        scope.onRadioButtonChange('violentCount');
        expect(scope.patient.violentCount).toEqual('');
        expect(scope.isInValidViolentCount   ).toBeFalsy();

        expect(scope.patient.psychMedicationCount).toBeUndefined();
        expect(scope.isInValidPsychMedicationCount   ).toBeFalsy();
        scope.onRadioButtonChange('psychMedicationCount');
        expect(scope.patient.psychMedicationCount).toEqual('');
        expect(scope.isInValidPsychMedicationCount   ).toBeFalsy();

    });

    it("should get data element field", function(){
        expect(scope.getComplexField(2,-7)).toEqual(-7);
        expect(scope.getComplexField(12,-8)).toEqual(-8);
        expect(scope.getComplexField(19, -1)).toEqual(19);
    });

    it("should create data elements object from patient data", function(){

        scope.patient = {
            genderCode:null,
            heroinDayCount: 2,
            morphineCount: "",
            diluadidCount: 2,
            demerolCount: "",
            percocetCount: 2,
            darvonCount: 2,
            codeineCount: 2,
            tylenolCount: "",
            oxycontinCount: 2,
            methadoneCount: 2,

            heroinRouteCode: 2,
            morphineRouteCode: 2,
            diluadidRouteCode: "",
            demerolRouteCode: 4,
            percocetRouteCode: 2,
            darvonRouteCode: 2,
            codeineRouteCode: 2,
            tylenolRouteCode: "",
            oxycontinRouteCode: 2,
            methadoneRouteCode: 2,

            depressionCount: 11,
            anxietyCount: "",
            violentCount: 12,
            psychMedicationCount: ""
        };

        scope.drugUse = {
            heroinDayCount: "",
            morphineCount: -7,
            diluadidCount: "",
            demerolCount: -8,
            percocetCount: "",
            darvonCount: "",
            codeineCount: "",
            tylenolCount: -7,
            oxycontinCount: "",
            methadoneCount: "",

            heroinRouteCode: 2,
            morphineRouteCode: 2,
            diluadidRouteCode: -7,
            demerolRouteCode: 2,
            percocetRouteCode: 2,
            darvonRouteCode: 2,
            codeineRouteCode: 2,
            tylenolRouteCode: -8,
            oxycontinRouteCode: 2,
            methadoneRouteCode: 2
        };

        scope.mentalPhysicalProblem = {
            depressionCount: "",
            anxietyCount: -7,
            violentCount: "",
            psychMedicationCount: -8
        };

        scope.updatePatientWithComplexFields();

        expect(scope.dataelements.genderCode).toEqual('UN');
        expect(scope.dataelements.heroinDayCount ).toEqual(scope.patient.heroinDayCount );
        expect(scope.dataelements.morphineCount ).toEqual(scope.drugUse.morphineCount );
        expect(scope.dataelements.diluadidCount).toEqual(scope.patient.diluadidCount);
        expect(scope.dataelements.demerolCount).toEqual(scope.drugUse.demerolCount);
        expect(scope.dataelements.percocetCount).toEqual(scope.patient.percocetCount);
        expect(scope.dataelements.darvonCount).toEqual(scope.patient.darvonCount);
        expect(scope.dataelements.codeineCount).toEqual(scope.patient.codeineCount);
        expect(scope.dataelements.tylenolCount).toEqual(scope.drugUse.tylenolCount);
        expect(scope.dataelements.oxycontinCount).toEqual(scope.patient.oxycontinCount);
        expect(scope.dataelements.methadoneCount).toEqual(scope.patient.methadoneCount);

        expect(scope.dataelements.heroinRouteCode).toEqual(scope.patient.heroinRouteCode);
        expect(scope.dataelements.morphineRouteCode).toEqual(scope.patient.morphineRouteCode);
        expect(scope.dataelements.diluadidRouteCode).toEqual(scope.drugUse.diluadidRouteCode);
        expect(scope.dataelements.demerolRouteCode).toEqual(scope.patient.demerolRouteCode);
        expect(scope.dataelements.percocetRouteCode).toEqual(scope.patient.percocetRouteCode);
        expect(scope.dataelements.darvonRouteCode).toEqual(scope.patient.darvonRouteCode);
        expect(scope.dataelements.codeineRouteCode).toEqual(scope.patient.codeineRouteCode);
        expect(scope.dataelements.tylenolRouteCode).toEqual(scope.drugUse.tylenolRouteCode);

        expect(scope.dataelements.oxycontinRouteCode).toEqual(scope.patient.oxycontinRouteCode);
        expect(scope.dataelements.methadoneRouteCode).toEqual(scope.patient.methadoneRouteCode);

        expect(scope.dataelements.depressionCount).toEqual(scope.patient.depressionCount);
        expect(scope.dataelements.anxietyCount).toEqual(scope.mentalPhysicalProblem.anxietyCount);
        expect(scope.dataelements.violentCount).toEqual(scope.patient.violentCount);
        expect(scope.dataelements.psychMedicationCount).toEqual(scope.mentalPhysicalProblem.psychMedicationCount);


    });

    it("should set day count to empty string and radio value to -7 or -8", function(){
        expect(scope.setCountOrRouteRadioValue(-7, 'heroinRouteCode')).toEqual(-7);
        expect(scope.setCountOrRouteRadioValue(-8, 'heroinRouteCode')).toEqual(-8);
        expect(scope.setCountOrRouteRadioValue(5, 'heroinDayCount')).toEqual("");
    });

    it("should create drugUse and mentalPhysicalProblem object from patient data", function(){
       var dataelements = {
            genderCode:'MA',
            heroinDayCount: 2,
            morphineCount: "",
            diluadidCount: 2,
            demerolCount: "",
            percocetCount: 2,
            darvonCount: 2,
            codeineCount: 2,
            tylenolCount: "",
            oxycontinCount: 2,
            methadoneCount: 2,

            heroinRouteCode: -7,
            morphineRouteCode: 2,
            diluadidRouteCode: "",
            demerolRouteCode: 4,
            percocetRouteCode: 2,
            darvonRouteCode: 2,
            codeineRouteCode: 2,
            tylenolRouteCode: "",
            oxycontinRouteCode: 2,
            methadoneRouteCode: 2,

            depressionCount: 11,
            anxietyCount: "",
            violentCount: 12,
            psychMedicationCount: "",
            asi: false,
            gpra: true
        };

        //For GPRA
        scope.createComplexFields(dataelements);

        expect(scope.drugUse.heroinDayCount).toEqual('');
        expect(scope.patient.genderCode).toEqual(dataelements.genderCode);
        expect(scope.disableOtherGender ).toBeTruthy();

        dataelements.genderCode = "";

        scope.createComplexFields(dataelements);
        expect(scope.disableOtherGender ).toBeFalsy();
        expect(scope.patient.otherDescription ).toEqual(dataelements.genderCode);

    });

    it("should verify for ASI", function(){
        var patient = {
            asi: true,
            gpra: false
        };
        expect(scope.isASI(patient)).toBeTruthy();
        patient.asi = false;
        expect(scope.isASI(patient)).toBeFalsy();
    });

    it("should verify for GPRA", function(){
        var patient = {
            asi: false,
            gpra: true
        };
        expect(scope.isGPRA(patient)).toBeTruthy();
        patient.gpra = false;
        expect(scope.isGPRA(patient)).toBeFalsy();
    });



    it("should claer all fields", function(){
        var data = {
            firstname: 'tomson',
            lastname:'ngassa'
        };

        var result = scope.clearAllFields(data);
        expect(result.firstname).toEqual(null);
        expect(result.lastname).toEqual(null);
    });

    it('should validate other gender', function(){
        scope.patient.genderCode="";
        scope.patient.otherDesciption="other";
        expect(scope.isValidOtherGender()).toBeTruthy();

        scope.patient.otherDesciption=null;
        expect(scope.isValidOtherGender()).toBeTruthy();

        scope.patient.otherDesciption=undefined;
        expect(scope.isValidOtherGender()).toBeTruthy();

        scope.patient.genderCode="MA";
        expect(scope.isValidOtherGender()).toBeFalsy();
    });

    xit("should be valid zipcode", function(){
        scope.validateZipCode("111");
        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();

        scope.validateZipCode("");
        expect(scope.validZipCodeLength).toBeFalsy();
        expect(scope.zipcodeIsANumber ).toBeFalsy();

        scope.validateZipCode();
        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();


    });

});

