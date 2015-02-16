/**
 * Created by tomson.ngassa on 6/12/14.
 */

'use strict';


describe('bham.userService', function () {
    var module;

    beforeEach(function () {
        module = angular.module("bham.userService");
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
            dependencies = module.value('bham.userService').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });
    });
});

describe('UserService', function(){
    var mockUserService, $httpBackend;

    beforeEach(module('bham.userService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockUserService = $injector.get('UserService');
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


    it('should create an individual provider', inject(function () {
        var provider = {firstName:"tommy"};
        $httpBackend.expectPOST("app/public/register").respond({status: 201});
        var status = mockUserService.create(
            provider,
            function(data ){
                status = data.status;
            },
            function(error){});
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

    it('should identify an individual provider', inject(function(){
        var provider = {userName:"tommy"};
        $httpBackend.expectPOST('app/public/identifyuser').respond({status: 201});
        var status = mockUserService.identifyUser(
            provider,
            function(data) {
                status = data.status;
            },
            function(error) {});
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

    it('should query Security Questions Code', inject(function () {
        var securityQuestionCodes =  [{code: "1", displayName: "Display1" }, {code: "2", displayName: "Display2" }];
        $httpBackend.whenGET("securityquestions").respond(securityQuestionCodes);
        var result = mockUserService.getSecurityQuestionsCodes(
            function(data) {
                result = data;
            },
            function(error) {}
        );
        $httpBackend.flush();
        expect(result).toEqualData(securityQuestionCodes);
    }));

    it('should retrieve questions', inject(function () {
        var questions =  {question1: "1", question2: "2"};
        var provider = {userName: "tommy"};
        $httpBackend.whenGET("app/public/retrieveSecurityQuestions/tommy/dob").respond(questions);
        var result = mockUserService.retrieveQuestions(
            provider,
            function(data) {
                result = data;
            },
            function(error) {}
        );
        $httpBackend.flush();
        expect(result).toEqualData(questions);
    }));

    it('should verify answers', inject(function () {
        var provider = {userName: "tommy"};
        provider.securityAnswers = {answer1: 'answer1', answer2: 'answer2'};
        $httpBackend.expectPOST("app/public/verifyAnswers").respond({status: 201});
        var status = mockUserService.verifyAnswers(
            provider,
            function(data) {
                status = data.status;
            },
            function(error) {}
        );
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

    it('should reset password', inject(function() {
        var passwordDto = {password1: "1", password2: "2"};
        $httpBackend.expectPOST("app/public/resetpassword").respond({status: 201});
        var status = mockUserService.resetPassword(
            passwordDto,
            function(data) {
                status = data.status;
            },
            function(error) {}
        );
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

    it('should test forgot security questions', inject(function() {
        // In case security questions are defined
        var user = {userName: "tommy", securityAnswers : []};
        $httpBackend.expectPOST("app/public/sendVerificationEmail").respond({status: 201});
        var status = mockUserService.forgotSecurityQuestions(
            user,
            function(data) {
                status = data.status;
            }
        );
        $httpBackend.flush();
        expect(status).toEqualData(201);

        // In case of undefined security questions
        var user1 = {userName: "tommy"};
        $httpBackend.expectPOST("app/public/sendVerificationEmail").respond({status: 201});
        var status1 = mockUserService.forgotSecurityQuestions(
            user1,
            function(data) {
                status1 = data.status;
            }
        );
        $httpBackend.flush();
        expect(status1).toEqualData(201);
    }));

    it('should test forgot password and security questions', inject(function() {
        var user = {userName: "tommy"};
        $httpBackend.expectPOST("app/public/resetpasswordandsecurityquestions").respond({status: 201});
        var status = mockUserService.resetPasswordAndSecurityQuestions(
            user,
            function(data) {
                status = data.status;
            }
        );
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

    it('should verify identity', inject(function() {
        var data = {userName: "tommy", credential: "blablabla"};
        $httpBackend.expectPOST("app/public/verifyIdentity").respond({status: 201});
        var status = mockUserService.verifyIdentity(
            data,
            function(data) {
                status = data.status;
            }
        );
        $httpBackend.flush();
        expect(status).toEqualData(201);
    }));

});
