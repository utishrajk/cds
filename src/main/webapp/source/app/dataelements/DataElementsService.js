'use strict';

angular.module('bham.dataElementsService', ['ngResource', 'bham.loggingModule'])
    .factory('DataElementsService', ['$resource', '$location','ErrorService',  function ($resource, $location, ErrorService) {

            var dataElementsResources = $resource('dataelements/:requestId', {requestId: '@requestId'},{'update': { method:'PUT' }});
            var dataElementsFlagResources = $resource('dataelements/flag/:requestId', {requestId: '@requestId'},{'update': { method:'PUT' }});

            var asiResourcesId = $resource('dataelements/asi/:id', {id: '@id'},{'update': { method:'PUT' }});
            var gpraResourcesId = $resource('dataelements/gpra/:id', {id : '@id'}, {'update': { method:'PUT' }});

            var route = $resource('routecodes');
            var asiroutecodes = $resource('asiroutecodes');
            var episodecodes = $resource('episodecodes');
            var statuscodes = $resource('statuscodes');
            var yesornocodes = $resource('yesornocodes');

            return {
                retrieve: function () {
                    return dataElementsResources;
                },

                retrieveWithFlags : function () {
                    return  dataElementsFlagResources;
                },

                create: function (id, dataelements, successCb, errorCb) {
                    gpraResourcesId.update( {id : id}, dataelements, successCb, errorCb);
                },

                createAsi: function(id, asi, successCb, errorCb) {
                    asiResourcesId.update( {id : id}, asi, successCb, errorCb);
                },

                get: function (id, successCb, errorCb) {
                    dataElementsResources.get({requestId:id }, successCb, errorCb);
                },

                getRouteCodes: function (successCb, errorCb) {
                    return route.query(successCb,errorCb);
                },

                getASIRouteCodes: function (successCb, errorCb) {
                    return asiroutecodes.query(successCb,errorCb);
                },

                getEpisodeCodes: function (successCb, errorCb){
                    return episodecodes.query(successCb,errorCb);
                },

                getStatusCodes: function (successCb, errorCb) {
                    return statuscodes.query(successCb,errorCb);
                },

                getYesNoCodes: function (successCb, errorCb) {
                    return yesornocodes.query(successCb,errorCb);
                },

                getRouteCodeResource: function(){
                    return route;
                },

                getASIRouteResource: function(){
                    return asiroutecodes;
                },

                getEpisodeCodeResource: function(){
                    return episodecodes;
                },

                getStatusCodeResource: function(){
                    return statuscodes;
                },

                getYesOrNoCodeResource: function(){
                    return yesornocodes;
                }
            };
        }]);