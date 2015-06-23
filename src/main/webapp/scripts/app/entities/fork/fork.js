'use strict';

angular.module('devSuspensionApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fork', {
                parent: 'entity',
                url: '/fork',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'devSuspensionApp.fork.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fork/forks.html',
                        controller: 'ForkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fork');
                        return $translate.refresh();
                    }]
                }
            })
            .state('forkDetail', {
                parent: 'entity',
                url: '/fork/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'devSuspensionApp.fork.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fork/fork-detail.html',
                        controller: 'ForkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fork');
                        return $translate.refresh();
                    }]
                }
            });
    });
