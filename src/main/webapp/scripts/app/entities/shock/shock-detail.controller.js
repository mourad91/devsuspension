'use strict';

angular.module('devSuspensionApp')
    .controller('ShockDetailController', function ($scope, $stateParams, Shock) {
        $scope.shock = {};
        $scope.load = function (id) {
            Shock.get({id: id}, function(result) {
              $scope.shock = result;
            });
        };
        $scope.load($stateParams.id);
    });
