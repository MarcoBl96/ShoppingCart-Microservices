'use strict';

angular.module('loginForm')
    .controller('LoginFormController', ["$http", '$state', '$stateParams', '$rootScope', '$scope', '$location', '$window',
    function($http, $state, $stateParams, $rootScope, $scope, $location, $window) {

  var vm = this;
          vm.login = login;

          (function initController() {
              $window.localStorage.setItem('token', '');
          })();
        function login() {
                $http({
                url: '/users/login',
                method: "POST",
                data: {
                    'username': vm.username,
                    'password': vm.password
                }
            }).then(function (response) {
                if (response.data) {

                                    console.log("setting rootScope")
                                    $rootScope.username = userData.name;

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


                    $window.sessionStorage.setItem(
                      'userData', JSON.stringify(userData)
                    );
                    $http.defaults.headers.common['Authorization']
                      = 'Basic ' + token;
                    $location.path('/');
                } else {
                    alert("Authentication failed.")
                }
            });
    }}]);