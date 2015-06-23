'use strict';

angular.module('devSuspensionApp')
    .controller('ShockController', function ($scope, Shock, ShockSearch) {
        $scope.shocks = [];
        $scope.loadAll = function() {
            Shock.query(function(result) {
               $scope.shocks = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Shock.get({id: id}, function(result) {
                $scope.shock = result;
                $('#saveShockModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.shock.id != null) {
                Shock.update($scope.shock,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Shock.save($scope.shock,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Shock.get({id: id}, function(result) {
                $scope.shock = result;
                $('#deleteShockConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Shock.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShockConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ShockSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.shocks = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveShockModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.shock = {brand: null, model: null, hsc: null, lsc: null, rebound: null, bottomOut: null, type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
