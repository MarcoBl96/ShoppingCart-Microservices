'use strict';

/**
 * Global HTTP errors handler.
 */
angular.module('infrastructure')
    .factory('HttpErrorHandlingInterceptor', function ($q) {
        return {
            responseError: function (response) {
                var error = response.data;
                console.log("Request ERror");
                console.log(error);
                alert(error.error + "\r\n" + error.path + "\r\n" );
                //+ error.errors.map(function (e) {
                //console.log("return");
                //    return e.field + ": " + e.defaultMessage;
                //}).join("\r\n"));
                console.log("response");
                console.log(response);
                return $q.reject(response);
            }
        }
    });
