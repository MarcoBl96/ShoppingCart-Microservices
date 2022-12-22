'use strict';

angular.module('orderConfirmationForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('orderConfirmation', {
                parent: 'app',
                url: '/order/orderConfirmation',
                templateUrl: 'scripts/order/orderConfirmation-details.template.html',
                controller: 'OrderConfirmationFormController',
                controllerAs: '$ctrl'
            })
    }]);
