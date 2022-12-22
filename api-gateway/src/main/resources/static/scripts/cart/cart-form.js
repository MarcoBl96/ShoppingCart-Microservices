'use strict';

angular.module('cartForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('cartForm', {
                url: '/cart',
                template: '<cart-form></cart-form>'
            })
    }])