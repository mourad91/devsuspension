'use strict';

angular.module('devSuspensionApp')
    .factory('Fork', function ($resource, DateUtils) {
        return $resource('api/forks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
