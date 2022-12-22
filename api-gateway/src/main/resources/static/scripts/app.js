'use strict';
/* App Module */
var shoppingCartApp = angular.module('shoppingCartApp', ['layoutNav', 'layoutWelcome', 'layoutFooter', 'layoutSlider', 'layoutAboutus', 'layoutContactus',
'infrastructure', 'ui.router', 'productDetails', 'productList', 'productForm', 'userForm', 'loginForm', 'cartForm', 'collectCustomerInfo', 'orderConfirmationForm', 'thankCustomer']);

shoppingCartApp
.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function(
    $stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

    // safari turns to be lazy sending the Cache-Control header
    $httpProvider.defaults.headers.common["Cache-Control"] = 'no-cache';
    $httpProvider.interceptors.push('HttpErrorHandlingInterceptor');

    $stateProvider
        .state('app', {
            abstract: true,
            url: '',
            template: '<ui-view></ui-view>'
        })
        .state('welcome', {
            parent: 'app',
            url: '/welcome',
            template: '<layout-welcome></layout-welcome>'
        })
        .state('aboutus', {
            parent: 'app',
            url: '/aboutus',
            template: '<layout-aboutus></layout-aboutus>'
        })
        .state('contactus', {
            parent: 'app',
            url: '/contactus',
            template: '<layout-contactus></layout-contactus>'
        });
}]);


['welcome', 'footer', 'slider', 'aboutus', 'contactus'].forEach(function(c) {
    var mod = 'layout' + c.toUpperCase().substring(0, 1) + c.substring(1);
    angular.module(mod, []);
    angular.module(mod).component(mod, {
        templateUrl: "scripts/fragments/" + c + ".html"
    });
});
angular.module('layoutNav', []);


angular.module('layoutNav', []) // ... omitted code
.controller('layoutNavController',

  function($rootScope, $scope, $http, $location) {

  var authenticate = function(credentials, callback) {
    var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
    } : {};
    $http.get('/users/user').then(function(data) {
      if (data.data.authenticated) {
        $scope.authenticated = true;
        $scope.username = data.data.name;
        $rootScope.username = data.data.name;
        console.log('Auth is true');
        console.log(data);

        $http.get('/users/GetCustomerId').then(function(customerresponse)
        {
          if (customerresponse.data) {

              console.log(customerresponse.data);
              $rootScope.customerId = customerresponse.data;
              console.log($rootScope);

              } else {
                alert("Error - No Customer Data Found");
                console.log('Error - No Customer Data Found');
              }
          });

      } else {
        $scope.authenticated = false;
        console.log('Auth is false');
      }
      callback && callback();
    });
  }
  $scope.logout = function() {
    $http.post('logout', {}).then(function() {
      $scope.authenticated = false;
      $location.path("/");
    });
    }
  authenticate();
});

angular.module('layoutNav')
    .component('layoutNav', {
        templateUrl: 'scripts/fragments/nav.html',
        Controller: 'layoutNavController'
    });







