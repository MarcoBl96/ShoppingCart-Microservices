'use strict';

angular.module('productList', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('productList', {
                url: '/getAllProducts',
                template: '<product-list></product-list>'
            })
    }]);