'use strict';

angular.module('devSuspensionApp')
    .controller('ForkController', function ($scope, Fork, ForkSearch) {
        $scope.forks = [];
        $scope.loadAll = function() {
            Fork.query(function(result) {
               $scope.forks = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Fork.get({id: id}, function(result) {
                $scope.fork = result;
                $('#saveForkModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.fork.id != null) {
                Fork.update($scope.fork,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Fork.save($scope.fork,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Fork.get({id: id}, function(result) {
                $scope.fork = result;
                $('#deleteForkConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Fork.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteForkConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ForkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.forks = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveForkModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fork = {brand: null, model: null, hsc: null, lsc: null, rebound: null, bottomOut: null, type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
