'use strict';

angular.module('devSuspensionApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


