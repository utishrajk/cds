'use strict';


describe('bham.contactService', function () {
    var module;

    beforeEach(function () {
        module = angular.module("bham.contactService");
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
            dependencies = module.value('bham.contactService').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });
    });
});

describe('ContactService', function(){
    var mockContactService, $httpBackend, caremanager;

    beforeEach(module('bham.contactService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockContactService = $injector.get('ContactService');
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

    it('should save feedback', inject(function () {
        var feedback = {comments: "sewqeqe", mail: "qwqewq@asdsadsd.com", type: "Technical problem  (something isn't working as expected)"};
        $httpBackend.expectPOST('feedback').respond({status: 200});
        var feedbackResource = mockContactService.getFeedbackResource();
        var status = feedbackResource.save(feedback,
            function(data ){
                status = data.status;
            },
            function(error){});
        $httpBackend.flush();
        expect(status).toEqual(200);
    }));

});
