'use strict';

angular.module('devSuspensionApp')
    .factory('Shock', function ($resource, DateUtils) {
        return $resource('api/shocks/:id', {}, {
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
