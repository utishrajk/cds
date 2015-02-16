'use strict';

//angular.module("bham.activityDirectives", [])
//
//    .directive("showActivity", function () {
//        return {
//            restrict: 'E',
//            scope: {
//                activity: '='
//            },
//            template: '<a class="blue" href="#">' +
//                             '<i class="icon-zoom-in bigger-130"></i>' +
//                      '</a>',
//            link: function (scope, element, attrs) {
//
//                element.on('click', function (e) {
//                    e.preventDefault();
//
//                    var selectedActivity = scope.activity;
//
//                    var username = selectedActivity.username !== null ? selectedActivity.username : '';
//                    var description = selectedActivity.description !== null ? selectedActivity.description : '';
//                    var ipAddress = selectedActivity.ipAddress !== null ? selectedActivity.ipAddress : '';
//                    var timestamp = selectedActivity.timestamp !== null ? selectedActivity.timestamp : '';
//
//
//                    var activityDetails = '<div class="row">' +
//                        '<div class="col-xs-12">' +
//                        '<div class="table-responsive">' +
//                        '<div class="modal-body no-padding" >' +
//                        ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
//                        '  <tbody>' +
//                        ' <tr > <td  class="captionID" >  <b>Activity ID</b> </td> <td class="dataID"> '+ username + ' </td></tr>' +
//                        ' <tr > <td  class="captionID" >  <b>Description</b> </td> <td class="dataID"> '+ description + ' </td></tr>' +
//                        ' <tr > <td  class="captionID" >  <b>IP Address</b> </td> <td class="dataID"> '+ ipAddress + ' </td></tr>' +
//                        ' <tr > <td  class="captionID" >  <b>Date</b> </td> <td class="dataID"> '+ timestamp + ' </td></tr>' +
//                        '  </tbody>' +
//                        ' </table>' +
//                        ' </div>' +
//                        ' </div>' +
//                        ' </div>' +
//                        ' </div>' ;
//                    $('#dialog-activity-details').html(activityDetails);
//                    $("#dialog-activity-details").removeClass('hide').dialog({
//                        resizable: false,
//                        modal: true,
//                        title: 'Activity details',
//                        title_html: true,
//                        id:"close-btn",
//                        width:400
//                    });
//                });
//            }
//        };
//    })  ;



