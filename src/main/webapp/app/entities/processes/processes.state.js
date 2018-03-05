(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('processes', {
            parent: 'entity',
            url: '/processes',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.processes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/processes/processes.html',
                    controller: 'ProcessesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('processes-detail', {
            parent: 'processes',
            url: '/processes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.processes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/processes/processes-detail.html',
                    controller: 'ProcessesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Processes', function($stateParams, Processes) {
                    return Processes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'processes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('processes-detail.edit', {
            parent: 'processes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/processes/processes-dialog.html',
                    controller: 'ProcessesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Processes', function(Processes) {
                            return Processes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('processes.new', {
            parent: 'processes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/processes/processes-dialog.html',
                    controller: 'ProcessesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subBopID: null,
                                processID: null,
                                generalSopPath: null,
                                subBopName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('processes', null, { reload: 'processes' });
                }, function() {
                    $state.go('processes');
                });
            }]
        })
        .state('processes.edit', {
            parent: 'processes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/processes/processes-dialog.html',
                    controller: 'ProcessesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Processes', function(Processes) {
                            return Processes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('processes', null, { reload: 'processes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('processes.delete', {
            parent: 'processes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/processes/processes-delete-dialog.html',
                    controller: 'ProcessesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Processes', function(Processes) {
                            return Processes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('processes', null, { reload: 'processes' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
