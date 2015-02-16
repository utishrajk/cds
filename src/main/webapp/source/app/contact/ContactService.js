/**
 * Created by tomson.ngassa on 8/27/2014.
 */
'use strict';

angular.module('bham.contactService', ['ngResource'])

    .constant('FEEDBACK_RESOURCE_URL', 'feedback' )

    .factory('ContactService', ['$resource','FEEDBACK_RESOURCE_URL', '$location',
        function($resource, FEEDBACK_RESOURCE_URL, $location){

            var FeedbackResource = $resource( FEEDBACK_RESOURCE_URL );

            return {
                getFeedbackResource: function(){
                    return FeedbackResource;
                }
            };
    }]);