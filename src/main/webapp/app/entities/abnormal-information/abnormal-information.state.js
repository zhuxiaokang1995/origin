(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('abnormal-information', {
            parent: 'entity',
            url: '/abnormal-information',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.abnormalInformation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/abnormal-information/abnormal-informations.html',
                    controller: 'AbnormalInformationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('abnormalInformation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('abnormal-information-detail', {
            parent: 'abnormal-information',
            url: '/abnormal-information/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.abnormalInformation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/abnormal-information/abnormal-information-detail.html',
                    controller: 'AbnormalInformationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('abnormalInformation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AbnormalInformation', function($stateParams, AbnormalInformation) {
                    return AbnormalInformation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'abnormal-information',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('abnormal-information-detail.edit', {
            parent: 'abnormal-information-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/abnormal-information/abnormal-information-dialog.html',
                    controller: 'AbnormalInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AbnormalInformation', function(AbnormalInformation) {
                            return AbnormalInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('abnormal-information.new', {
            parent: 'abnormal-information',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/abnormal-information/abnormal-information-dialog.html',
                    controller: 'AbnormalInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lineStationId: null,
                                abnormalCause: null,
                                abnormalTime: null,
                                remark: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('abnormal-information', null, { reload: 'abnormal-information' });
                }, function() {
                    $state.go('abnormal-information');
                });
            }]
        })
        .state('abnormal-information.edit', {
            parent: 'abnormal-information',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/abnormal-information/abnormal-information-dialog.html',
                    controller: 'AbnormalInformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AbnormalInformation', function(AbnormalInformation) {
                            return AbnormalInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('abnormal-information', null, { reload: 'abnormal-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('abnormal-information.delete', {
            parent: 'abnormal-information',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/abnormal-information/abnormal-information-delete-dialog.html',
                    controller: 'AbnormalInformationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AbnormalInformation', function(AbnormalInformation) {
                            return AbnormalInformation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('abnormal-information', null, { reload: 'abnormal-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
