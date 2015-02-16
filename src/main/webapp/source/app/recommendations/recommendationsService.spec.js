/**
 * Created by tomson.ngassa on 6/12/14.
 */

'use strict';


describe('bham.recommendationsService', function () {
    var module;

    beforeEach(function () {
        module = angular.module("bham.recommendationsService");
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
            dependencies = module.value('bham.recommendationsService').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });
    });
});

describe('RecommendationsService', function(){
    var mockRecommendationsService, $httpBackend;

    beforeEach(module('bham.recommendationsService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockRecommendationsService = $injector.get('RecommendationsService');
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

    it('should query recommendations ', inject(function () {
        var resourceObject = mockRecommendationsService.retrieve();
        expect(resourceObject).not.toBeNull();

    }));

});
