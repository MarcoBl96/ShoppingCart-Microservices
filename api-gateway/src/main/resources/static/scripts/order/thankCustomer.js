'use strict';

angular.module('thankCustomer', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('thankCustomer', {
                parent: 'app',
                url: '/order/thankCustomer',
                templateUrl: 'scripts/order/thankCustomer.html'

            })
    }]);
