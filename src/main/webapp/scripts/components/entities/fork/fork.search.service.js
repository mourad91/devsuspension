'use strict';

angular.module('devSuspensionApp')
    .factory('ForkSearch', function ($resource) {
        return $resource('api/_search/forks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
