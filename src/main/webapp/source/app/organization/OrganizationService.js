'use strict';

angular.module('bham.organizationService', ['ngResource'])
    .constant('ORGANIZATION_RESOURCE_URL', 'organizationalproviders' )
    .constant('STATE_RESOURCE_URL', 'statecodes' )
    .constant('COUNTRY_RESOURCE_URL', 'countrycodes' )
    .constant('SERVICE_RESOURCE_URL', 'servicecodes' )
    .constant('PREFIXES_RESOURCE_URL', 'prefixes' )
    .constant('ASSESSMENT_RESOURCE_URL', 'organizationalproviders/assessment' )

    .factory('OrganizationService', ['$resource','ORGANIZATION_RESOURCE_URL','STATE_RESOURCE_URL','COUNTRY_RESOURCE_URL', 'SERVICE_RESOURCE_URL','PREFIXES_RESOURCE_URL','ASSESSMENT_RESOURCE_URL', '$location',
        function($resource, ORGANIZATION_RESOURCE_URL, STATE_RESOURCE_URL, COUNTRY_RESOURCE_URL,SERVICE_RESOURCE_URL, PREFIXES_RESOURCE_URL, ASSESSMENT_RESOURCE_URL,$location){

            var OrganizationResource = $resource( ORGANIZATION_RESOURCE_URL);
            var StateResource = $resource(STATE_RESOURCE_URL);
            var CountryResource = $resource(COUNTRY_RESOURCE_URL);
            var ServiceResource = $resource(SERVICE_RESOURCE_URL);
            var PrefixResource = $resource(PREFIXES_RESOURCE_URL);
            var assessmentResource = $resource(ASSESSMENT_RESOURCE_URL);

            return {
                getOrganizationResource : function(){
                    return OrganizationResource;
                },

                getStateResource : function(){
                    return StateResource;
                },

                getCountryResource : function(){
                    return CountryResource;
                },

                getServiceResource : function(){
                    return ServiceResource;
                },

                getPrefixResource: function(){
                    return PrefixResource;
                },

                getAssessmentResource: function(){
                    return assessmentResource;
                },

                update : function(organizationProfile, successCb, errorCb) {
                    OrganizationResource.save(organizationProfile, successCb, errorCb );
                }
            };
    }]);