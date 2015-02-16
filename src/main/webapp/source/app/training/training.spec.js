/**
 * Created by tomson.ngassa on 3/10/14.
 */

'use strict';

describe("bham.training:", function() {
    var rootScope, scope;

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.training'));

    beforeEach(inject(function( $rootScope, $controller){
        rootScope = $rootScope;
        scope = $rootScope.$new();

        scope.activeSubMenuItem = "blababa";

        rootScope.toggleToolsMenuItem = function(menuItem){};

        rootScope.selectToolsSubMenu = function(menuItem){};

        spyOn(rootScope, "toggleToolsMenuItem");
        spyOn(rootScope, "selectToolsSubMenu");

        $controller('TrainingCtrl', {
            $scope: scope
        });

    }));

    it('should test training controller', function(){

        rootScope.toggleToolsMenuItem('toolsandresources');
        rootScope.selectToolsSubMenu('training');

        expect(rootScope.toggleToolsMenuItem).toHaveBeenCalled();
        expect(rootScope.selectToolsSubMenu).toHaveBeenCalled();
    });
});