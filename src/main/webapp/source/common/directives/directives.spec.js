/**
 * Created by tomson.ngassa on 3/3/14.
 */

'use strict';

describe('bham.directives module', function(){
    var module;

    beforeEach(function() {
        module = angular.module("bham.directives");
    });

    it("should be registered", function() {
        expect(module).not.toEqual(null);
    });
});

describe('Datepicker directives', function(){
    var scope, aDate, element;

    beforeEach(module('bham.directives'));

    beforeEach(inject(function($compile, $rootScope) {
        scope = $rootScope;
        aDate = new Date(2013, 3, 12);
        element = angular.element('<input type="text" date-picker ng-model="x"  >');
        $compile(element)(scope);
        }
    ));

   xit('should be able to get the date form the model', function(){
//       element.datepicker('setDate', aDate);
       // affectation de la date de test au scope
       scope.$apply(function() {
           scope.x = aDate;
           return scope;
       });
       scope.$apply();
//       expect(element.datepicker('getDate')).toEqual(aDate);
       element.find("input").click();
       expect(scope.x).toEqual(aDate);
   });

});

