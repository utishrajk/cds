'use strict';

angular.module('bham.breadcrumbsModule', [])

//Keep a key value pair of menu items to be shown in browser
.constant('menuItemsMap' ,{
			training: 'Training Video',
			myaccount: 'My Account',
            organization: 'Organization Profile',
            patients: 'Patient List',
            reports: 'Reports',
            documentlibrary: 'Document Library',
            feedback: 'Feedback', 
            activity: 'Activity',
            error:'Error',
            treatmentplan: 'Treatment Plan Wizard',
            conditions: 'Conditions',
            socialhistories: 'Social History',
            patientprofile: 'Summary Care Record',
            demographics: 'Demographics',
            procedures: 'Procedures',
            patient: 'Patient',
            reminders: 'Reminders',
            reminder: 'Reminders',
            recommendations: 'Recommendations',
            edit: 'Edit',
            add : 'Add',
            dataelements: 'Patient Data Elements',
            aboutus: 'About Us',
            termofuse: 'Terms of Use',
            privacypolicy : 'Privacy Policy',
            contactus: 'Contact Us',
            generalfeedback: 'Feedback',
            faqs: 'FAQS',
            generalfeedbackerror : 'Error',
            invalidurl: 'Error'

    })

.factory('BreadcrumbsService', ['$rootScope', '$location', 'menuItemsMap', function($rootScope, $location, menuItemsMap){

  var breadcrumbs = [];
  
  $rootScope.$on('$routeChangeSuccess', function(event, current){

    var pathElements = $location.path().split('/'), result = [], i;
    var pathElementsLength = pathElements.length;

     //Remove id at the end of the path
    if( pathElementsLength > 0) {
        var lastElement = pathElements[pathElementsLength - 1];
        if(! isNaN(lastElement)) {
            var pathElementsWithoutId = pathElements.slice(0 , pathElementsLength - 1);
            pathElements = pathElementsWithoutId;
        }
    }

    var breadcrumbPath = function (index) {
      return '/' + (pathElements.slice(0, index + 1)).join('/');
    };

    pathElements.shift();
    var toolsAndResources =   "Tools & Resources";

    for (i=0; i<pathElements.length; i++) {
        //Get the name of the menu items that will be shown in the browser
        //if it exist or use the name in the path

        if(isNaN(pathElements[i])){
            var menuName = menuItemsMap[ pathElements[i]] || pathElements[i] ;

            if(pathElements[i] === "reminder"){
                result.push({name: menuName , path: "/reminders" });
            }else if(pathElements[i]  === "documentlibrary"){
                result.push({name: toolsAndResources , path: "/documentlibrary" });
                result.push({name: menuName , path: "" });
            }else if(pathElements[i]  === "training"){
                result.push({name: toolsAndResources , path: "/training" });
                result.push({name: menuName , path: "" });
            }else if(pathElements[i]  === "recommendations"){
                result.push({name: menuName , path: breadcrumbPath(i+1) });
            }else{
                result.push({name: menuName , path: breadcrumbPath(i)});
            }
        }
    }

    breadcrumbs = result;
  });

   return {
       getAll : function(){
           return breadcrumbs;
       },

       getFirst: function(){
           return breadcrumbs[0] || {};
       }
   };
}]);
