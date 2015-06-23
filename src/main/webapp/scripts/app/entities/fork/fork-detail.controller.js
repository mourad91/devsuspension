'use strict';

angular.module('devSuspensionApp')
    .controller('ForkDetailController', function ($scope, $stateParams, Fork) {
        $scope.fork = {};
        $scope.load = function (id) {
            Fork.get({id: id}, function(result) {
              $scope.fork = result;
            });
        };
        $scope.load($stateParams.id);
    });
