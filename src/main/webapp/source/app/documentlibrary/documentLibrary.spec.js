/**
 * Created by tomson.ngassa on 3/10/14.
 */

'use strict';

describe("bham.documentLibraryModule:", function() {
    var $route, $location, $rootScope;

    beforeEach(module('ngRoute'));
    beforeEach(module('bham.documentLibraryModule'));
//    beforeEach(module('bham.accessService'));

    var scope, window, location, AccessService;

    beforeEach(inject(function( $rootScope, $controller, _$window_, $location){
        scope = $rootScope.$new();
        window = _$window_;
        location = $location;
        AccessService = {
            save :  function(){

            }
        };

        scope.activeSubMenuItem = "blabla";

        scope.toggleToolsMenuItem = function(menuItem){};

        scope.selectToolsSubMenu = function(menuItem){};

        $controller('DocumentLibraryCtrl', {
            $scope: scope,
            AccessService: AccessService
        });
    }));

    it('should have default values', function(){
        spyOn(scope, "toggleToolsMenuItem");
        spyOn(scope, "selectToolsSubMenu");

        scope.toggleToolsMenuItem('toolsandresources');
        scope.selectToolsSubMenu('documentLibrary');

        expect(scope.toggleToolsMenuItem).toHaveBeenCalled();
        expect(scope.selectToolsSubMenu).toHaveBeenCalled();
    });


    xit('should open document', function(){
        var selectedNode = {
                name: "/path/to/my/document.pdf"
        };
        scope.openDocument (selectedNode);
    });

});