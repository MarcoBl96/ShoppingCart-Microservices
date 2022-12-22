'use strict';

angular.module('collectCustomerInfo', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('collectCustomerInfo', {
                parent: 'app',
                url: '/order/collectCustomerInfo/',
                templateUrl: 'scripts/order/collectCustomerInfo-form.template.html',
                controller: 'CollectCustomerInfoController',
                controllerAs: '$ctrl'
            })
    }]);
