'use strict';

angular.module('orderConfirmationForm')
    .controller('OrderConfirmationFormController', ["$http", '$state', '$stateParams', '$rootScope', '$scope', function ($http, $state, $stateParams, $rootScope, $scope) {

    var self = this;
    var cartId = 0;
    let order = {customerOrderId:0, cartId:0, customerId:0};

    $http.get('api/user/users/getCustomer/' + $rootScope.customerId).then(function (resp) {
        self.customer = resp.data;
    });

    $http.get('api/cart/carts/getCartId/' + $rootScope.customerId).then(function (resp) {
        cartId = resp.data;
        console.log("CartId:");
        console.log(cartId);

        $http.get('api/cart/carts/' + cartId).then(function (resp) {
                self.cart = resp.data;
                console.log("self.cart:");
                console.log(self.cart);
            });
    });

            $scope.submitOrderConfirmationForm = function () {
                console.log("SubmitOrderConfirmation");
                console.log(self.customer);
                console.log(self.cart);
                order.customerId = self.customer.customerId;
                order.cartId = self.cart.cartId;
                console.log(order);

                    $http.post('api/order/orders/newOrder/', order).then(function () {
                        $state.go('thankCustomer');
                    });

            };
    }]);