'use strict';

angular.module('productForm')
    .controller('ProductFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;

        var productId = $stateParams.productId || 0;

        if (!productId) {
            self.product = {};
        } else {
            $http.get("api/product/products/" + productId).then(function (resp) {
                self.product = resp.data;
            });
        }

        self.submitProductForm = function () {
            var id = self.product.id;

            if (id) {
                $http.put('api/product/products/admin/product/editProduct/' + id, self.product).then(function () {
                    $state.go('productDetails', {productId: productId});
                });
            } else {
                $http.post('api/product/products/admin/product/addProduct', self.product).then(function () {
                    $state.go('productList');
                });
            }
        };
    }]);
