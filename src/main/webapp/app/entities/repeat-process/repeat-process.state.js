(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('repeat-process', {
            parent: 'entity',
            url: '/repeat-process',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.repeatProcess.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/repeat-process/repeat-processes.html',
                    controller: 'RepeatProcessController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('repeatProcess');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('repeat-process-detail', {
            parent: 'repeat-process',
            url: '/repeat-process/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.repeatProcess.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/repeat-process/repeat-process-detail.html',
                    controller: 'RepeatProcessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('repeatProcess');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RepeatProcess', function($stateParams, RepeatProcess) {
                    return RepeatProcess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'repeat-process',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('repeat-process-detail.edit', {
            parent: 'repeat-process-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repeat-process/repeat-process-dialog.html',
                    controller: 'RepeatProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RepeatProcess', function(RepeatProcess) {
                            return RepeatProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('repeat-process.new', {
            parent: 'repeat-process',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repeat-process/repeat-process-dialog.html',
                    controller: 'RepeatProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                processNum: null,
                                processName: null,
                                descriple: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('repeat-process', null, { reload: 'repeat-process' });
                }, function() {
                    $state.go('repeat-process');
                });
            }]
        })
        .state('repeat-process.edit', {
            parent: 'repeat-process',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repeat-process/repeat-process-dialog.html',
                    controller: 'RepeatProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RepeatProcess', function(RepeatProcess) {
                            return RepeatProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('repeat-process', null, { reload: 'repeat-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('repeat-process.delete', {
            parent: 'repeat-process',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/repeat-process/repeat-process-delete-dialog.html',
                    controller: 'RepeatProcessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RepeatProcess', function(RepeatProcess) {
                            return RepeatProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('repeat-process', null, { reload: 'repeat-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
