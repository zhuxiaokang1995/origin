(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transport-task', {
            parent: 'entity',
            url: '/transport-task',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.transportTask.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transport-task/transport-tasks.html',
                    controller: 'TransportTaskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transportTask');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('transport-task-detail', {
            parent: 'transport-task',
            url: '/transport-task/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.transportTask.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transport-task/transport-task-detail.html',
                    controller: 'TransportTaskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transportTask');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TransportTask', function($stateParams, TransportTask) {
                    return TransportTask.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transport-task',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transport-task-detail.edit', {
            parent: 'transport-task-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transport-task/transport-task-dialog.html',
                    controller: 'TransportTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransportTask', function(TransportTask) {
                            return TransportTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transport-task.new', {
            parent: 'transport-task',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transport-task/transport-task-dialog.html',
                    controller: 'TransportTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                funID: null,
                                serialID: null,
                                taskID: null,
                                taskType: null,
                                taskPrty: null,
                                taskFlag: null,
                                lPN: null,
                                frmPos: null,
                                frmPosType: null,
                                toPos: null,
                                toPosType: null,
                                opFlag: null,
                                remark: null,
                                issuedTaskTime: null,
                                completionTime: null,
                                storeType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transport-task', null, { reload: 'transport-task' });
                }, function() {
                    $state.go('transport-task');
                });
            }]
        })
        .state('transport-task.edit', {
            parent: 'transport-task',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transport-task/transport-task-dialog.html',
                    controller: 'TransportTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransportTask', function(TransportTask) {
                            return TransportTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transport-task', null, { reload: 'transport-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transport-task.delete', {
            parent: 'transport-task',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transport-task/transport-task-delete-dialog.html',
                    controller: 'TransportTaskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TransportTask', function(TransportTask) {
                            return TransportTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transport-task', null, { reload: 'transport-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
