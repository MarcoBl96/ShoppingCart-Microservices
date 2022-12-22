'use strict';

angular.module('userForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('userNew', {
                parent: 'app',
                url: '/users/new',
                template: '<user-form></user-form>'
            })
            .state('userEdit', {
                parent: 'app',
                url: '/users/:userId/edit',
                template: '<user-form></user-form>'
            })
    }]);
