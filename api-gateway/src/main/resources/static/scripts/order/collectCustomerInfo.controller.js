'use strict';

angular.module('collectCustomerInfo')
    .controller('CollectCustomerInfoController', ["$http", '$state', '$stateParams', '$rootScope', function ($http, $state, $stateParams, $rootScope) {

    var self = this;

    console.log("Collect Customer Info Controller");

    $http.get('api/user/users/getCustomer/' + $rootScope.customerId).then(function (resp) {
        self.customer = resp.data;

        console.log(self.customer);



    });

            self.submitCollectCustomerInfoForm = function () {
                var id = self.customer.customerId;

                if (id) {
                    $http.put('api/user/users/editCustomer/' + id, self.customer).then(function () {
                        $state.go('orderConfirmation');
                    });
                } else {
                    alert("no Customer ID");
                }
            };
    }]);