/**
 * Created by tomson.ngassa on 4/29/14.
 */

'use strict';

xdescribe('bham.reminderModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.reminderModule");
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
            dependencies = module.value('bham.reminderModule').requires;
        });

        it("should have ngResource as a dependency", function() {
            expect(hasModule('ngResource')).toEqual(true);
        });

        it("should have bham.reminderService as a dependency", function() {
            expect(hasModule('bham.reminderService')).toEqual(true);
        });

        it("should have bham.naturalSort as a dependency", function() {
            expect(hasModule('bham.naturalSort')).toEqual(true);
        });
    });
});

describe("bham.reminderModule ReminderListCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.reminderModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, ReminderService;

    beforeEach(inject(function($rootScope, $controller){
        scope = $rootScope.$new();
        var reminders = [
            {"id":0, "date":"04/12/2014", "from":"BHCDS", "subject":"Illicit Drug use Screening", "patient":"Joe Bloggs", "messageType":"USPSTF Screening", "priority":"2", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"}
        ];

        var successCb = function(){console.log('Success');};
        var errorCb =  function (){ console.log('Error');};

        ReminderService = {
            query: function(successCb, errorCb) {
                return reminders ;
            },
            get : function(id, successCb, errorCb) {
                for(var i = 0; i < reminders.length ; i++ ){
                    if(reminders[i].id === id){
                        return reminders[i];
                    }
                }
            }
        };

        scope.reminders = reminders;
        scope.filteredReminderss = reminders;
        scope.totalRecords = reminders.length;

        scope.setActiveMenuItem = function(item){};

        $controller('ReminderListCtrl', {
            $scope: scope,
            ReminderService: ReminderService
        });
    }));

    it('should initialize default values', function(){
        expect(scope.pageSize).toEqual(10);
        expect(scope.showPageSize).toEqual(6);
        expect(scope.startRecord).toEqual(1);

        expect(scope.sortField).toBeUndefined();
        expect(scope.reverse).toBeFalsy();

        expect(scope.pages).toEqualData([]);
        expect(scope.pageNo).toEqual(0);

        expect(scope.firstPage).toEqual(0);
        expect(scope.lastPage).toEqual(0);
    });

    it('should sort by firstName column', function(){
        scope.sortField = 'firstName';
        scope.sort('firstName');
        expect(scope.reverse).toBeTruthy();
        scope.sort('firstName');
        expect(scope.reverse).toBeFalsy();

        scope.sort("");
        expect(scope.reverse).toBeFalsy();
        expect(scope.sortField).toEqual("");
    });

    it('should sort in ascending order', function(){
        scope.sortField = 'firstName';
        expect(scope.isSortUp(scope.sortField)).toBeTruthy();
        scope.sort(scope.sortField);
        expect(scope.isSortUp(scope.sortField)).toBeFalsy();
    });

    it('should sort in descending order', function(){
        scope.sortField = 'firstName';
        expect(scope.isSortDown(scope.sortField)).toBeFalsy();
        scope.sort(scope.sortField);
        expect(scope.isSortDown(scope.sortField)).toBeTruthy();
    });

    it('should change showPageSize on page size change', function(){
        scope.pageSize = 20;
        scope.onPageSizeChange();
        expect(scope.showPageSize).toEqual(6);  // Since the list of reminder is 6
        expect(scope.startRecord).toEqual(1);
        expect(scope.pageNo).toEqual(0);

        scope.pageSize = 2;
        scope.onPageSizeChange();
        expect(scope.showPageSize).toEqual(2);  // Since the list of reminder is 6
        expect(scope.startRecord).toEqual(1);
        expect(scope.pageNo).toEqual(0);
    });

    it('should calculate pagination pages', function(){
        scope.pages = [];
        //Create 5 pages
        for (var i=0; i<5; i++) {
            scope.pages.push(i);
        }
        scope.setActivePage(2);
        expect(scope.pageNo).toEqual(2);
    });

    it('should seach reminder table', function(){
        expect(scope.pageNo).toEqual(0);
        scope.searchBy = 'subject';
        scope.onSearch();
        expect(scope.composedCriteria).toEqual({subject : "" + scope.criteria});

        scope.criteria = 'messageType';
        scope.searchBy = 'patient';
        scope.onSearch();
        expect(scope.composedCriteria).toEqual({patient : scope.criteria});

        scope.criteria = 'messageType';
        scope.searchBy = '';
        scope.onSearch();
        expect(scope.composedCriteria).toEqual( scope.criteria);
    });

    it("should update the showpage variable when the filter patient length changes", function(){
        expect(scope.pages).toEqual([]);
        //In case the filter size is less than the page size
        scope.filteredReminders =[
            {"id":0, "date":"04/12/2014", "from":"BHCDS", "subject":"Illicit Drug use Screening", "patient":"Joe Bloggs", "messageType":"USPSTF Screening", "priority":"2", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"}
        ];
        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(2);

        //In case the filter size is more than the page size
        scope.filteredReminders =[
            {"id":0, "date":"04/12/2014", "from":"BHCDS", "subject":"Illicit Drug use Screening", "patient":"Joe Bloggs", "messageType":"USPSTF Screening", "priority":"2", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"},
            {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"}
        ];
        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(6);
    });

    it("should update pagination pages when page size changes", function(){
        //If page size is greater than total patients
        expect(scope.pageSize).toEqual(10);
        scope.pageSize = 25;
        scope.$apply();
        expect(scope.pages).toEqual([0]);

        //If page size is less than total patients
        scope.pageSize = 4;
        scope.$apply();
        expect(scope.pages).toEqual([0, 1]);

    });
});


describe("bham.reminderModule ReminderRecommendationCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.reminderModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, ReminderService, reminder;

    beforeEach(inject(function($rootScope, $controller){
        reminder =  {"id":1, "date":"04/25/2014", "from":"BHCDS", "subject":"Depression Screening", "patient":"Linda Collins", "messageType":"USPSTF Screening", "priority":"1", "status":"Accepted"};

        scope = $rootScope.$new();
        var routeParams = {id:1};

        var successCb = function(){console.log('Success');};
        var errorCb =  function (){ console.log('Error');};

        ReminderService = {
            get : function(id, successCb, errorCb) {
                return reminder ;
            }
        };

        scope.setActiveMenuItem = function(item){};

        $controller('ReminderRecommendationCtrl', {
            $scope: scope,
            CareManagerService: ReminderService,
            $routeParams : routeParams
        });
    }));

    it("should have default values", function() {
        expect(scope.activeTab).toEqual('general');
    });

    it("should switch tab", function() {
        expect(scope.activeTab).toEqual('general');
        scope.switchTabTo('tab1');
        expect(scope.activeTab).toEqual('tab1');
    });

    it("should get reminder", function() {
        var newReminder = ReminderService.get(1);
        expect(scope.reminder).toEqualData(newReminder);
    });
});
