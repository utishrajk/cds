'use strict';

angular.module('bham.caremanagerService', ['ngResource'])

    .constant('CARE_MANAGER_RESOURCE_URL', 'individualproviders')
    .constant('CARE_MANAGER_STATE_RESOURCE_URL', 'statecodes' )
    .constant('CARE_MANAGER_COUNTRY_RESOURCE_URL', 'countrycodes' )
    .constant('CARE_MANAGER_PREFIXES_RESOURCE_URL', 'prefixes' )
    .constant('CARE_MANAGER_TRAINING_RESOURCE_URL', 'individualproviders/training' )

    .factory('CareManagerService', ['$resource','CARE_MANAGER_RESOURCE_URL', 'CARE_MANAGER_STATE_RESOURCE_URL', 'CARE_MANAGER_COUNTRY_RESOURCE_URL', 'CARE_MANAGER_PREFIXES_RESOURCE_URL', 'CARE_MANAGER_TRAINING_RESOURCE_URL', '$location',
        function($resource, CARE_MANAGER_RESOURCE_URL, CARE_MANAGER_STATE_RESOURCE_URL, CARE_MANAGER_COUNTRY_RESOURCE_URL,CARE_MANAGER_PREFIXES_RESOURCE_URL, CARE_MANAGER_TRAINING_RESOURCE_URL, $location){

            var CareManagerResource = $resource( CARE_MANAGER_RESOURCE_URL);
            var CareManagerStateResource = $resource( CARE_MANAGER_STATE_RESOURCE_URL);
            var CareManagerCountryResource = $resource( CARE_MANAGER_COUNTRY_RESOURCE_URL);
            var CareManagerPrefixResource = $resource( CARE_MANAGER_PREFIXES_RESOURCE_URL);
            var CareManagerTrainingResource = $resource( CARE_MANAGER_TRAINING_RESOURCE_URL);

            return {
                update : function(caremanager, successCb, errorCb) {
                    CareManagerResource.save(caremanager,successCb, errorCb );
                },

                getStateResource: function(successCb, errorCb) {
                    return CareManagerStateResource;
                },

                getCareManagerResource : function(){
                    return CareManagerResource;
                },
                getCareManagerCountryResource : function(){
                    return CareManagerCountryResource;
                },
                updateTrainingVideoOption : function(data, successCb, errorCb){
                     CareManagerTrainingResource.save(data,successCb, errorCb );
                }
            };
        }]);