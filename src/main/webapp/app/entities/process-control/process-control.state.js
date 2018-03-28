(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('process-control', {
            parent: 'entity',
            url: '/process-control',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.processControl.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-control/process-controls.html',
                    controller: 'ProcessControlController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processControl');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('process-control-detail', {
            parent: 'process-control',
            url: '/process-control/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.processControl.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-control/process-control-detail.html',
                    controller: 'ProcessControlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processControl');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProcessControl', function($stateParams, ProcessControl) {
                    return ProcessControl.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'process-control',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('process-control-detail.edit', {
            parent: 'process-control-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-control/process-control-dialog.html',
                    controller: 'ProcessControlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProcessControl', function(ProcessControl) {
                            return ProcessControl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('process-control.new', {
            parent: 'process-control',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-control/process-control-dialog.html',
                    controller: 'ProcessControlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serialNumber: null,
                                hutID: null,
                                orderID: null,
                                stationID: null,
                                result: null,
                                mountGuardTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('process-control', null, { reload: 'process-control' });
                }, function() {
                    $state.go('process-control');
                });
            }]
        })
        .state('process-control.edit', {
            parent: 'process-control',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-control/process-control-dialog.html',
                    controller: 'ProcessControlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProcessControl', function(ProcessControl) {
                            return ProcessControl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-control', null, { reload: 'process-control' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('process-control.delete', {
            parent: 'process-control',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-control/process-control-delete-dialog.html',
                    controller: 'ProcessControlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProcessControl', function(ProcessControl) {
                            return ProcessControl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-control', null, { reload: 'process-control' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
