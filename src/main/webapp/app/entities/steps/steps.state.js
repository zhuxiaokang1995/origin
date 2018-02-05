(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('steps', {
            parent: 'entity',
            url: '/steps',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.steps.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/steps/steps.html',
                    controller: 'StepsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('steps');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('steps-detail', {
            parent: 'steps',
            url: '/steps/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.steps.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/steps/steps-detail.html',
                    controller: 'StepsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('steps');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Steps', function($stateParams, Steps) {
                    return Steps.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'steps',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('steps-detail.edit', {
            parent: 'steps-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/steps/steps-dialog.html',
                    controller: 'StepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Steps', function(Steps) {
                            return Steps.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('steps.new', {
            parent: 'steps',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/steps/steps-dialog.html',
                    controller: 'StepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                stepID: null,
                                stepName: null,
                                sequence: null,
                                stepAttrID: null,
                                stationID: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('steps', null, { reload: 'steps' });
                }, function() {
                    $state.go('steps');
                });
            }]
        })
        .state('steps.edit', {
            parent: 'steps',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/steps/steps-dialog.html',
                    controller: 'StepsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Steps', function(Steps) {
                            return Steps.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('steps', null, { reload: 'steps' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('steps.delete', {
            parent: 'steps',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/steps/steps-delete-dialog.html',
                    controller: 'StepsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Steps', function(Steps) {
                            return Steps.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('steps', null, { reload: 'steps' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
