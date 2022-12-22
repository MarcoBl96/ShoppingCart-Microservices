'use strict';

angular.module('loginForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                template: '<login-form></login-form>'
            })
    }]);
