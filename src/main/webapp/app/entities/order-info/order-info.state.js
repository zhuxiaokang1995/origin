(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-info', {
            parent: 'entity',
            url: '/order-info',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.orderInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-info/order-infos.html',
                    controller: 'OrderInfoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-info-detail', {
            parent: 'order-info',
            url: '/order-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.orderInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-info/order-info-detail.html',
                    controller: 'OrderInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderInfo', function($stateParams, OrderInfo) {
                    return OrderInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-info-detail.edit', {
            parent: 'order-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-info/order-info-dialog.html',
                    controller: 'OrderInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderInfo', function(OrderInfo) {
                            return OrderInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-info.new', {
            parent: 'order-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-info/order-info-dialog.html',
                    controller: 'OrderInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderID: null,
                                defID: null,
                                defDescript: null,
                                lineID: null,
                                bOPID: null,
                                pPRName: null,
                                departID: null,
                                quantity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-info', null, { reload: 'order-info' });
                }, function() {
                    $state.go('order-info');
                });
            }]
        })
        .state('order-info.edit', {
            parent: 'order-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-info/order-info-dialog.html',
                    controller: 'OrderInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderInfo', function(OrderInfo) {
                            return OrderInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-info', null, { reload: 'order-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-info.delete', {
            parent: 'order-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-info/order-info-delete-dialog.html',
                    controller: 'OrderInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderInfo', function(OrderInfo) {
                            return OrderInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-info', null, { reload: 'order-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
