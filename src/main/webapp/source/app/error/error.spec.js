'use strict';

describe('bham.errorModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.errorModule");
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
            dependencies = module.value('ngResource').requires;
        });

        it("should have bham.security as a dependency", function() {
            expect(hasModule('bham.security')).toEqual(true);
        });
    });
});


describe("bham.errorModule ErrorCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.errorModule'));

    var scope, location, rootScope, httpBackend;

    beforeEach(inject(function($rootScope, $controller, $location){
        scope = $rootScope.$new();
        rootScope = $rootScope;
        location = $location;


        scope.setActiveMenuItem = function(menuItem){
            scope.activeMenuItem = menuItem;
            scope.activeSubMenuItem = false;
            scope.highlightMenu = false;
            scope.highlightPatientMenu = false;
            scope.activePatientSubMenuItem = false;
            scope.enableDropDownMenu = false;
        };

        spyOn(scope, 'setActiveMenuItem').andCallThrough();

        $controller('ErrorCtrl', {
            $scope: scope
        });


    }));

    it("should calculate current footer path", function(){
        location.path('/error');
        expect(scope.setActiveMenuItem).toHaveBeenCalledWith("error");
    });

});


describe("bham.errorModule InvalidUrlCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.errorModule'));

    var scope, location, rootScope, httpBackend;

    beforeEach(inject(function($rootScope, $controller, $location){
        scope = $rootScope.$new();
        rootScope = $rootScope;
        location = $location;


        scope.setActiveMenuItem = function(menuItem){
            scope.activeMenuItem = menuItem;
            scope.activeSubMenuItem = false;
            scope.highlightMenu = false;
            scope.highlightPatientMenu = false;
            scope.activePatientSubMenuItem = false;
            scope.enableDropDownMenu = false;
        };

        spyOn(scope, 'setActiveMenuItem').andCallThrough();

        $controller('InvalidUrlCtrl', {
            $scope: scope
        });


    }));

    it("should calculate current footer path", function(){
        location.path('/invalidurl');
        expect(scope.setActiveMenuItem).toHaveBeenCalledWith("invalidurl");
    });

});



