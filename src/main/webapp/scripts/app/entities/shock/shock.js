'use strict';

angular.module('devSuspensionApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shock', {
                parent: 'entity',
                url: '/shock',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'devSuspensionApp.shock.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shock/shocks.html',
                        controller: 'ShockController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shock');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shockDetail', {
                parent: 'entity',
                url: '/shock/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'devSuspensionApp.shock.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shock/shock-detail.html',
                        controller: 'ShockDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shock');
                        return $translate.refresh();
                    }]
                }
            });
    });
