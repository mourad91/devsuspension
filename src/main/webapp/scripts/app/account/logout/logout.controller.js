'use strict';

angular.module('devSuspensionApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
