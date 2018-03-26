(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sn', {
            parent: 'entity',
            url: '/sn',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.sn.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sn/sns.html',
                    controller: 'SnController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sn');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sn-detail', {
            parent: 'sn',
            url: '/sn/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.sn.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sn/sn-detail.html',
                    controller: 'SnDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sn');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sn', function($stateParams, Sn) {
                    return Sn.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sn',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sn-detail.edit', {
            parent: 'sn-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sn/sn-dialog.html',
                    controller: 'SnDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sn', function(Sn) {
                            return Sn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sn.new', {
            parent: 'sn',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sn/sn-dialog.html',
                    controller: 'SnDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serialNumber: null,
                                hutID: null,
                                orderID: null,
                                isBinding: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sn', null, { reload: 'sn' });
                }, function() {
                    $state.go('sn');
                });
            }]
        })
        .state('sn.edit', {
            parent: 'sn',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sn/sn-dialog.html',
                    controller: 'SnDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sn', function(Sn) {
                            return Sn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sn', null, { reload: 'sn' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sn.delete', {
            parent: 'sn',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sn/sn-delete-dialog.html',
                    controller: 'SnDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sn', function(Sn) {
                            return Sn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sn', null, { reload: 'sn' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
