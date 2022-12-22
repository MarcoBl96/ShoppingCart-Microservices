'use strict';

angular.module('userForm')
    .controller('UserFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {

        var self = this;

        var productId = $stateParams.productId || 0;

        self.submitUserForm = function () {
            var id = self.user.id;
                $http.post('api/user/users/registration', self.user).then(function () {
                    $state.go('welcome');
                });
        };
    }]);