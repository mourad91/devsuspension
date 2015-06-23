'use strict';

angular.module('devSuspensionApp')
    .factory('ShockSearch', function ($resource) {
        return $resource('api/_search/shocks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
