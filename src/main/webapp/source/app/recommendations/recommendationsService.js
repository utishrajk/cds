'use strict';

angular.module('bham.recommendationsService', ['ngResource'])
    .constant('RECOMMENDATIONS_URL', 'recommendations/:requestId')
    .factory('RecommendationsService', ['$resource','RECOMMENDATIONS_URL',
        function ($resource, RECOMMENDATIONS_URL) {

            var recommendationsResource = $resource(RECOMMENDATIONS_URL, {requestId: '@requestId'});

            return {
                retrieve: function () {
                    return recommendationsResource;
                }

            };
        }]);