'use strict';

describe('bham.contactModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.contactModule");
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
            dependencies = module.value('bham.contactModule').requires;
        });

        it("should have bhma.contactService as a dependency", function() {
            expect(hasModule('bham.contactService')).toEqual(true);
        });
    });
});


describe("bham.contactModule ContactCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.contactModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, location, rootScope, httpBackend;

    beforeEach(inject(function($rootScope, $controller, $location, $httpBackend){
        scope = $rootScope.$new();
        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;

        scope.setCurrentFooterPage = function(currentFooterPage){
            scope.currentFooterPage = currentFooterPage;
        };

        scope.setActiveMenuItem = function(menuItem){
            scope.activeMenuItem = menuItem;
            scope.activeSubMenuItem = false;
            scope.highlightMenu = false;
            scope.highlightPatientMenu = false;
            scope.activePatientSubMenuItem = false;
            scope.enableDropDownMenu = false;
        };

        spyOn(scope, 'setActiveMenuItem').andCallThrough();

        spyOn(scope, 'setCurrentFooterPage').andCallThrough();

        $controller('ContactCtrl', {
            $scope: scope
        });


    }));

    it("should calculate current footer path", function(){
        location.path('/aboutus');
        expect(scope.setActiveMenuItem).toHaveBeenCalledWith("contacts");
        expect(scope.setCurrentFooterPage).toHaveBeenCalled();
    });

});


describe("bham.contactModule ContactCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.contactModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, ContactService, location, rootScope, httpBackend;

    beforeEach(inject(function($rootScope, $controller, $location, $httpBackend, _ContactService_){
        scope = $rootScope.$new();
        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;
        ContactService = _ContactService_;

        scope.feedback = {comments: "sewqeqe", mail: "qwqewq@asdsadsd.com", type: "Technical problem  (something isn't working as expected)"};

        scope.setCurrentFooterPage = function(currentFooterPage){
            scope.currentFooterPage = currentFooterPage;
        };

        spyOn(scope, 'setCurrentFooterPage').andCallThrough();

        $controller('GeneralFeedbackCtrl', {
            $scope: scope,
            ContactService: ContactService
        });
    }));

    it("should reset feedback", function(){
        scope.reset();
        expect(scope.feedback ).toEqual({});
    });

    it("should save feedback", function(){
        httpBackend.expectPOST('feedback').respond({status: 200});
        scope.save();
        expect(scope.setCurrentFooterPage).toHaveBeenCalled();
    });


});

