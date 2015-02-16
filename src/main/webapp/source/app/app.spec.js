/**
 * Created by tomson.ngassa on 3/3/14.
 */

'use strict';

describe('bham module', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham");
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
            dependencies = module.value('bham').requires;
        });

        it("should have templates-app as a dependency", function() {
            expect(hasModule('templates-app')).toEqual(true);
        });

        it("should have templates-common as a dependency", function() {
            expect(hasModule('templates-common')).toEqual(true);
        });

        it("should have ngRoute as a dependency", function() {
            expect(hasModule('ngRoute')).toEqual(true);
        });

        it("should have bham.caremanagerModul as a dependency", function() {
            expect(hasModule('bham.caremanagerModule')).toEqual(true);
        });

        it("should have bham.organizationModule as a dependency", function() {
            expect(hasModule('bham.organizationModule')).toEqual(true);
        });

        it("should have bham.directives as a dependency", function() {
            expect(hasModule('bham.directives')).toEqual(true);
        });

        it("should have bham.breadcrumbsModule as a dependency", function() {
            expect(hasModule('bham.breadcrumbsModule')).toEqual(true);
        });

        xit("should have security module as a dependency", function() {
            expect(hasModule('security')).toEqual(true);
        });
    });
});

describe("App route:", function() {
    var $route, $location, $rootScope, AppCtrl, scope, $controller;

    beforeEach(module('bham'));

    beforeEach(inject(function(_$route_, _$location_, _$rootScope_, _$controller_){
        $route = _$route_;
        $location = _$location_;
        $rootScope = _$rootScope_;
        scope = $rootScope.$new();
        $controller = _$controller_;

        AppCtrl = $controller('AppCtrl', {
            $scope: scope
        });
    }));

    it('should contain index page template', function(){
        expect(scope.headnavbar).toEqual('../head-navbar.tpl.html');
        expect(scope.breadcrums).toEqual('../breadcrums.tpl.html');
        expect(scope.sidenavbar).toEqual('../side-navbar.tpl.html');
    });

    //TODO
    it('should get breadcrumbs', function(){
        expect(scope.breadcrumbs()).toEqual([]);
//        $location.path('/otherwise');
    });


});

describe("bham module AppCTrl", function() {
    var $route, $location, $rootScope, AppCtrl, scope, $controller, breadcrumbsService, entityList, patient, form, $compile ;

    beforeEach(module('bham'));


    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function(_$route_, _$location_, _$rootScope_, _$controller_, _BreadcrumbsService_, _$compile_){
        $route = _$route_;
        $location = _$location_;
        $rootScope = _$rootScope_;
        scope = $rootScope.$new();
        $controller = _$controller_;
        breadcrumbsService = _BreadcrumbsService_;
        $compile = _$compile_;


        var element = angular.element(
            '<form name="myForm">' +
                '<input ng-model="abstractObject.firstName" name="firstName" required type="text" />' +
            '</form>'
        );
        scope.abstractObject = { firstName:null};

        $compile(element)(scope);
        scope.$digest();
        form = scope.myForm;

        var Session = {
            userRoles: ["ROLE_ADMIN", "ROLE_USER"]
        };

        AppCtrl = $controller('AppCtrl', {
            $scope: scope,
            BreadcrumbsService: breadcrumbsService,
            Session : Session

        });

        entityList = [ {id:0, name:'Tomson', code:"001"}, {id:1, name:'Himalay', code:"002"}, {id:2, name:'Utish', code:"003"}];

        patient = {id:0, fullName:"Tomson Ngassa"};



    }));

    it('should route to the login page', function(){
        expect($route.current).toBeUndefined();
        $location.path('/blablabla');
        $rootScope.$digest();

        expect($route.current.templateUrl).toBe('login/login.tpl.html');
        expect($location.path()).toBe('/login');

        $location.path('/otherwise');
        $rootScope.$digest();

        expect($location.path()).toBe('/login');
    });

    it('should have default values', function(){
        expect(scope.headnavbar).toEqual('../head-navbar.tpl.html');
        expect(scope.breadcrums).toEqual('../breadcrums.tpl.html');
        expect(scope.sidenavbar).toEqual('../side-navbar.tpl.html');

        expect(scope.datePattern).toEqual(/(0[1-9]|1[012])[ \/.](0[1-9]|[12][0-9]|3[01])[ \/.](19|20)\d\d/);

        expect(scope.openCustomMenu).toBeFalsy();
        expect(scope.isDemographics).toBeFalsy();

        expect(scope.selectedPatient).toBeUndefined();
        expect(scope.selectedPatientId).toBeUndefined();
        expect(scope.selectedPatientFullName).toBeUndefined();
        expect(scope.collapseDemographicsAccordion).toBeUndefined();
        expect(scope.toggleDemographicsAccordionClass).toBeUndefined();
        expect(scope.collapseConditionsAccordion).toBeUndefined();
        expect(scope.toggleConditionsAccordionClass).toBeUndefined();
        expect(scope.collapseSocialhistoryAccordion).toBeUndefined();
        expect(scope.toggleSocialhistoryAccordionClass).toBeUndefined();
        expect(scope.collapseProcedureAccordion).toBeUndefined();
        expect(scope.toggleProcedureAccordionClass).toBeUndefined();

        expect(scope.trainingMenuitem).toBeUndefined();
        expect(scope.careManagerMenuitem).toBeUndefined();
        expect(scope.organizationMenuitem).toBeUndefined();
        expect(scope.patientListMenuitem).toBeUndefined();

        expect(scope.reportsMenuitem).toBeUndefined();
        expect(scope.toolsResourceMenuitem).toBeUndefined();
        expect(scope.conditionsMenuitem).toBeUndefined();
        expect(scope.socialHistoryMenuitem).toBeUndefined();
        expect(scope.procedureMenuitem).toBeUndefined();
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        expect(scope.demographicsMenuitem).toBeUndefined();
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();

    });

    //TODO
    xit('should get breadcrumbs', function(){
        expect(scope.breadcrumbs()).toEqual([]);
        $location.path('/dataelements');
        expect(scope.breadcrumbs()).toEqual(['']);
    });

    it('should get entity by Id', function(){
        expect(scope.getEntityById(entityList, 0)).toEqual({id:0, name:'Tomson', code:"001"});
        expect(scope.getEntityById(entityList, 1)).toEqual({id:1, name:'Himalay', code:"002"});
        expect(scope.getEntityById(entityList, 2)).toEqual({id:2, name:'Utish', code:"003"});

    });

    it('should get entity by code', function(){
        expect(scope.getEntityByCode (entityList, "001")).toEqual({id:0, name:'Tomson', code:"001"});
        expect(scope.getEntityByCode (entityList, "002")).toEqual({id:1, name:'Himalay', code:"002"});
        expect(scope.getEntityByCode (entityList, "003")).toEqual({id:2, name:'Utish', code:"003"});
    });

    xit('should set selected patient', function(){
        expect(scope.selectedPatient).toBeUndefined();
        scope.setSelectedPatient (entityList[0]);
        expect(scope.selectedPatient).toBeDefined();
    });

    it('should redirect', function(){
        expect($route.current).toBeUndefined();
        scope.redirect("/dataelements");
        expect($location.path()).toBe('/dataelements');
    });

    it('should delete entity by Id', function(){
        expect(entityList.length).toEqual(3);
        scope.deleteEntityById(entityList, 0);
        expect(entityList.length).toEqual(2);
        scope.deleteEntityById(entityList, 1);
        expect(entityList.length).toEqual(1);
        scope.deleteEntityById(entityList, 2);
        expect(entityList.length).toEqual(0);
    });

    it('should select menu', function(){

        scope.setActiveMenuItem('patientdataelements');
        expect( scope.activeMenuItem ).toEqual('patientdataelements');
        expect( scope.highlightMenu ).toBeFalsy();
        expect( scope.highlightPatientMenu ).toBeFalsy();
        expect( scope.activePatientSubMenuItem ).toBeFalsy();
        expect( scope.enableDropDownMenu ).toBeFalsy();

    });

    it('should select tools and resources submenu menu', function(){

        expect( scope.highlightMenu  ).toBeFalsy();
        expect( scope.activeSubMenuItem  ).toBeFalsy();

        scope.toggleToolsMenuItem('toolsandresources');
        expect(scope.activeMenuItem ).toEqual('toolsandresources');

        expect( scope.highlightPatientMenu  ).toBeFalsy();
        expect( scope.activePatientSubMenuItem  ).toBeFalsy();
        expect( scope.highlightMenu  ).toBeTruthy();
        expect( scope.activeSubMenuItem  ).toBeTruthy();

        scope.selectToolsSubMenu('documentLibrary');
        expect(scope.activeMenuItem ).toEqual('documentLibrary');
        expect( scope.activeSubMenuItem   ).toBeTruthy();
        expect( scope.enableDropDownMenu  ).toBeFalsy();


    });


    it('should convert and object to a JSON', function(){
        var obj = [{id:0, name:"Tomson0"}, {id:1, name:"Tomson1"}, {id:2, name:"Tomson2"}];
        var jsonObject = scope.toJSON(obj);
        var expectedJsonObject =  JSON.stringify(obj, null, 2);
        expect(angular.equals(expectedJsonObject, jsonObject)).toBeTruthy();
    });

    it('should be future date', function(){
        var today = new Date();
        var futureDate = today.setDate(today.getDate() + 10);
        expect(scope.isFutureDate( futureDate )).toBeTruthy();

        var today1 = new Date();
        var futureDate1 = today1.setDate(today1.getDate() - 10);
        expect(scope.isFutureDate( futureDate1 )).toBeFalsy();

        expect(scope.isFutureDate( null )).toBeFalsy();
    });

    it('should end date not proceed start date', function(){
        var startDate = new Date();
        var today = new Date();
        var endDate = today.setDate(today.getDate() - 10);
        expect(scope.isEndDateBeforeStartDate(startDate, endDate )).toBeTruthy();

        var startDate1 = new Date();
        var today1 = new Date();
        var endDate1 = today1.setDate(today1.getDate() + 10);
        expect(scope.isEndDateBeforeStartDate(startDate1, endDate1 )).toBeFalsy();

        expect(scope.isEndDateBeforeStartDate(null, endDate1 )).toBeFalsy();
    });



    it("should be a valid number", function(){
        expect(scope.isValidNumber("1233")).toBeTruthy();
        expect(scope.isValidNumber("12333ee")).toBeFalsy();
    });

    it("should be a valid US phone/fax number", function(){
        expect(scope.isValidContactNumber("1223222222", "US", 10)).toBeTruthy();
        expect(scope.isValidContactNumber("1223222", "", "10")).toBeFalsy();
        expect(scope.isValidContactNumber(null, "", "10")).toBeFalsy();
    });

    it("should be a valid number", function(){
        expect(scope.isValidNumber ("1223222222")).toBeTruthy();
        expect(scope.isValidNumber ("1223222ee")).toBeFalsy();
        expect(scope.isValidNumber  (null)).toBeFalsy();
    });

    it('should be Adminnistrator', function(){
        expect(scope.isAdmin()).toBeTruthy();
    });

    xit('should get Age from date string', function(){
        var d = new Date("July 21, 1983 01:15:00");
        var today = new Date();
        var n = (today.getFullYear() - d.getFullYear());
        expect(scope.getAge ('06/21/1983')).toEqual(n);
    });

    it("form should be dirty", function(){
        expect(form).toBeDefined();
        form.firstName.$setViewValue("Tomson");
        scope.$digest();
        expect(scope.isDirty(form.firstName)).toBeTruthy();
    });

    it("should set forget password user", function(){
        expect(scope.user).toBeUndefined();
        var user = {firstname : "Tomson", lastname: "ngassa"};
        scope.forgotPasswordUser (user);
        expect(scope.user).toEqualData(user);
    });


    it("should set current footer page", function(){
        expect(scope.currentFooterPage).toBeUndefined();
        scope.setCurrentFooterPage ("currentPage");
        expect(scope.currentFooterPage).toEqual("currentPage");
    });


    // Old test that might be needed

    it("should show error in form", function(){
        expect(form ).toBeDefined();
        form.firstName.$setViewValue("Tomson");
        scope.$digest();
        expect(scope.showError(form.firstName, 'required')).toBeFalsy();

        form.firstName.$setViewValue("");
        scope.$digest();
        expect(scope.showError(form.firstName, 'required')).toBeTruthy();
    });

    it("should scroll to the top", function(){
        expect($location.hash()).toEqual("");
        scope.gotoTop();
        expect($location.hash()).toEqual("top");
    });

    it("should show patient profile", function(){
        expect($route.current).toBeUndefined();
        scope.selectedPatientId = 213;
        scope.showPatientProfile();
        expect($location.path()).toBe('/patient/'+scope.selectedPatientId  +'/patientprofile');
    });

    it("should toggle dropdown menu", function(){
        expect(scope.enableDropDownMenu).toBeFalsy();
        scope.toggleDropDownMenu();
        expect(scope.enableDropDownMenu).toBeTruthy();
    });

    it('should populate custom patient menu', function(){
        scope.populateCustomPatientMenu(patient);

        expect( scope.selectedPatient ).toEqual(patient);
        expect( scope.selectedPatientId ).toEqual(patient.id);
        expect( scope.selectedPatientFullName ).toEqual(patient.fullName);

        expect( scope.collapseDemographicsAccordion ).toEqual('');
        expect( scope.toggleDemographicsAccordionClass ).toBeFalsy();

        expect( scope.collapseConditionsAccordion ).toEqual('');
        expect( scope.toggleConditionsAccordionClass ).toBeFalsy();

        expect( scope.collapseSocialhistoryAccordion ).toEqual('');
        expect( scope.toggleSocialhistoryAccordionClass ).toBeFalsy();

        expect( scope.collapseProcedureAccordion ).toEqual('');
        expect( scope.toggleProcedureAccordionClass ).toBeFalsy();

        expect(scope.openCustomMenu).toBeTruthy();

        expect(scope.reportsMenuitem).toBeFalsy();
        expect(scope.toolsResourceMenuitem).toBeFalsy();
        expect(scope.conditionsMenuitem).toBeFalsy();
        expect(scope.socialHistoryMenuitem).toBeFalsy();
        expect(scope.procedureMenuitem).toBeFalsy();
        expect(scope.treatmentPlanMenuitem).toBeFalsy();
        expect(scope.demographicsMenuitem).toBeFalsy();
        expect(scope.summaryCareRecordMenuitem).toBeFalsy();

    });

    it('should select treatment menu', function(){
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        scope.selectTreatmentPlanMenu();
        expect(scope.treatmentPlanMenuitem).toBeTruthy();
    });

    it('should select summary record menu', function(){
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();
        scope.selectSummaryRecordMenu();
        expect(scope.summaryCareRecordMenuitem).toBeTruthy();
    });

    it('should toggle demographics accordion', function(){
        scope.collapseDemographicsAccordion = "";
        scope.toggleDemographicsAccordionClass = false;

        scope.onToggledDemographicsAccordion();

        expect(scope.collapseDemographicsAccordion).toEqual('collapse');
        expect(scope.toggleDemographicsAccordionClass).toBeTruthy();
    });

    it('should toggle conditions accordion', function(){
        scope.collapseConditionsAccordion = "";
        scope.toggleConditionsAccordionClass = false;

        scope.onToggledConditionsAccordion();

        expect(scope.collapseConditionsAccordion).toEqual('collapse');
        expect(scope.toggleConditionsAccordionClass).toBeTruthy();
    });

    it('should toggle social history accordion', function(){
        scope.collapseSocialhistoryAccordion = "";
        scope.toggleSocialhistoryAccordionClass = false;

        scope.onToggledSocialHistoryAccordion();

        expect(scope.collapseSocialhistoryAccordion).toEqual('collapse');
        expect(scope.toggleSocialhistoryAccordionClass).toBeTruthy();
    });

    it('should toggle procedure accordion', function(){
        scope.collapseProcedureAccordion = "";
        scope.toggleProcedureAccordionClass = false;

        scope.onToggledProcedureAccordion();

        expect(scope.collapseProcedureAccordion).toEqual('collapse');
        expect(scope.toggleProcedureAccordionClass).toBeTruthy();
    });

    it('should remove active CSS class in side navigation bar', function(){

        expect(scope.trainingMenuitem).toBeUndefined();
        expect(scope.careManagerMenuitem).toBeUndefined();
        expect(scope.organizationMenuitem).toBeUndefined();
        expect(scope.patientListMenuitem).toBeUndefined();
        expect(scope.reportsMenuitem).toBeUndefined();
        expect(scope.toolsResourceMenuitem).toBeUndefined();
        expect(scope.conditionsMenuitem).toBeUndefined();
        expect(scope.socialHistoryMenuitem).toBeUndefined();
        expect(scope.procedureMenuitem).toBeUndefined();
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        expect(scope.demographicsMenuitem).toBeUndefined();
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();

        scope.removeActiveClassInSideNavBar();

        expect(scope.trainingMenuitem).toBeFalsy();
        expect(scope.careManagerMenuitem).toBeFalsy();
        expect(scope.organizationMenuitem).toBeFalsy();
        expect(scope.patientListMenuitem).toBeFalsy();
        expect(scope.reportsMenuitem).toBeFalsy();
        expect(scope.toolsResourceMenuitem).toBeFalsy();
        expect(scope.conditionsMenuitem).toBeFalsy();
        expect(scope.socialHistoryMenuitem).toBeFalsy();
        expect(scope.procedureMenuitem).toBeFalsy();
        expect(scope.treatmentPlanMenuitem).toBeFalsy();
        expect(scope.demographicsMenuitem).toBeFalsy();
        expect(scope.summaryCareRecordMenuitem).toBeFalsy();
    });

    it('should add active CSS class in side navigation bar', function(){

        expect(scope.trainingMenuitem).toBeUndefined();
        expect(scope.careManagerMenuitem).toBeUndefined();
        expect(scope.organizationMenuitem).toBeUndefined();
        expect(scope.patientListMenuitem).toBeUndefined();
        expect(scope.reportsMenuitem).toBeUndefined();
        expect(scope.toolsResourceMenuitem).toBeUndefined();
        expect(scope.conditionsMenuitem).toBeUndefined();
        expect(scope.socialHistoryMenuitem).toBeUndefined();
        expect(scope.procedureMenuitem).toBeUndefined();
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        expect(scope.demographicsMenuitem).toBeUndefined();
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();

        scope.addActiveClassInSideNavBar('training');
        expect(scope.trainingMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('organization');
        expect(scope.organizationMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('patientList');
        expect(scope.patientListMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('reports');
        expect(scope.reportsMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('feedback');
        expect(scope.feedbackMenuitem ).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('toolsAndResource');
        expect(scope.toolsResourceMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('reminders');
        expect(scope.remindersMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeFalsy();

        scope.addActiveClassInSideNavBar('conditions');
        expect(scope.conditionsMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();

        scope.addActiveClassInSideNavBar('socialHistory');
        expect(scope.socialHistoryMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();

        scope.addActiveClassInSideNavBar('procedure');
        expect(scope.procedureMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();

        scope.addActiveClassInSideNavBar('treatmentPlan');
        expect(scope.treatmentPlanMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();

        scope.addActiveClassInSideNavBar('demographics');
        expect(scope.demographicsMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();

        scope.addActiveClassInSideNavBar('summaryCareRecord');
        expect(scope.summaryCareRecordMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();
    });

    it('should handle onSelectmenu event on top menu', function(){

        expect(scope.trainingMenuitem).toBeUndefined();
        expect(scope.careManagerMenuitem).toBeUndefined();
        expect(scope.organizationMenuitem).toBeUndefined();
        expect(scope.patientListMenuitem).toBeUndefined();
        expect(scope.reportsMenuitem).toBeUndefined();
        expect(scope.toolsResourceMenuitem).toBeUndefined();
        expect(scope.conditionsMenuitem).toBeUndefined();
        expect(scope.socialHistoryMenuitem).toBeUndefined();
        expect(scope.procedureMenuitem).toBeUndefined();
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        expect(scope.demographicsMenuitem).toBeUndefined();
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();

        scope.onSelectmenu('patients');

        expect(scope.trainingMenuitem).toBeFalsy();
        expect(scope.careManagerMenuitem).toBeFalsy();
        expect(scope.organizationMenuitem).toBeFalsy();
        expect(scope.patientListMenuitem).toBeFalsy();
        expect(scope.reportsMenuitem).toBeFalsy();
        expect(scope.toolsResourceMenuitem).toBeFalsy();
        expect(scope.conditionsMenuitem).toBeFalsy();
        expect(scope.socialHistoryMenuitem).toBeFalsy();
        expect(scope.procedureMenuitem).toBeFalsy();
        expect(scope.treatmentPlanMenuitem).toBeFalsy();
        expect(scope.demographicsMenuitem).toBeFalsy();
        expect(scope.summaryCareRecordMenuitem).toBeFalsy();

        expect(scope.openCustomMenu).toBeFalsy();
    });

    it('should handle onSelectmenu event on submenu', function(){
        expect(scope.trainingMenuitem).toBeUndefined();
        expect(scope.careManagerMenuitem).toBeUndefined();
        expect(scope.organizationMenuitem).toBeUndefined();
        expect(scope.patientListMenuitem).toBeUndefined();
        expect(scope.reportsMenuitem).toBeUndefined();
        expect(scope.toolsResourceMenuitem).toBeUndefined();
        expect(scope.conditionsMenuitem).toBeUndefined();
        expect(scope.socialHistoryMenuitem).toBeUndefined();
        expect(scope.procedureMenuitem).toBeUndefined();
        expect(scope.treatmentPlanMenuitem).toBeUndefined();
        expect(scope.demographicsMenuitem).toBeUndefined();
        expect(scope.summaryCareRecordMenuitem).toBeUndefined();

        scope.onSelectmenu('summaryCareRecord');

        expect(scope.trainingMenuitem).toBeFalsy();
        expect(scope.careManagerMenuitem).toBeFalsy();
        expect(scope.organizationMenuitem).toBeFalsy();
        expect(scope.patientListMenuitem).toBeFalsy();
        expect(scope.reportsMenuitem).toBeFalsy();
        expect(scope.toolsResourceMenuitem).toBeFalsy();
        expect(scope.conditionsMenuitem).toBeFalsy();
        expect(scope.socialHistoryMenuitem).toBeFalsy();
        expect(scope.procedureMenuitem).toBeFalsy();
        expect(scope.treatmentPlanMenuitem).toBeFalsy();
        expect(scope.demographicsMenuitem).toBeFalsy();

        expect(scope.summaryCareRecordMenuitem).toBeTruthy();
        expect(scope.openCustomMenu).toBeTruthy();
    });

    it('should enable custom menu', function(){
        expect(scope.openCustomMenu).toBeFalsy();
        scope.enableCustomPatientMenu();
        expect(scope.openCustomMenu).toBeTruthy();
    });

    it('should disenable custom menu', function(){
        scope.openCustomMenu = true;
        scope.disableCustomPatientMenu();
        expect(scope.openCustomMenu).toBeFalsy();
    });

    it('should toggle demographic Mode', function(){
        expect(scope.isDemographics).toBeFalsy();
        scope.toggleDemographicMode(true);
        expect(scope.isDemographics).toBeTruthy();
    });

    it('should set selected patient', function(){
        var selectedPatient = { firstname: 'Tomson', lastName: 'Ngassa'};
        expect(scope.selectedPatient ).toBeFalsy();
        scope.setSelectedPatient(selectedPatient);
        expect(scope.selectedPatient).toEqualData(selectedPatient);
    });


});
