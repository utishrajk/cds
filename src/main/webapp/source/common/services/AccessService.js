/**
 * Created by tomson.ngassa on 9/17/2014.
 */

'use strict';

angular.module('bham.accessService', ['ngResource', 'bham.loggingModule'])

    .constant('ACCESS_RESOURCE_URL', 'access' )

    .factory('AccessService', ['$resource','ACCESS_RESOURCE_URL','ErrorService',
        function($resource, ACCESS_RESOURCE_URL, ErrorService){

            var AccessResource = $resource( ACCESS_RESOURCE_URL );

            return {
                save : function(data){
                    AccessResource.save(data,
                        function(response){ErrorService.success(response);},
                        function(response){ErrorService.error(response);});
                }
            };
    }]);