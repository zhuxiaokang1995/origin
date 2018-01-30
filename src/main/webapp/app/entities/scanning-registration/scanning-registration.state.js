(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scanning-registration', {
            parent: 'entity',
            url: '/scanning-registration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.scanningRegistration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scanning-registration/scanning-registrations.html',
                    controller: 'ScanningRegistrationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scanningRegistration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scanning-registration-detail', {
            parent: 'scanning-registration',
            url: '/scanning-registration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'holleyImsApp.scanningRegistration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scanning-registration/scanning-registration-detail.html',
                    controller: 'ScanningRegistrationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scanningRegistration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ScanningRegistration', function($stateParams, ScanningRegistration) {
                    return ScanningRegistration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scanning-registration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scanning-registration-detail.edit', {
            parent: 'scanning-registration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scanning-registration/scanning-registration-dialog.html',
                    controller: 'ScanningRegistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScanningRegistration', function(ScanningRegistration) {
                            return ScanningRegistration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scanning-registration.new', {
            parent: 'scanning-registration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scanning-registration/scanning-registration-dialog.html',
                    controller: 'ScanningRegistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pk: null,
                                serialNumber: null,
                                stationID: null,
                                scanType: null,
                                defectCode: null,
                                result: null,
                                subSn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scanning-registration', null, { reload: 'scanning-registration' });
                }, function() {
                    $state.go('scanning-registration');
                });
            }]
        })
        .state('scanning-registration.edit', {
            parent: 'scanning-registration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scanning-registration/scanning-registration-dialog.html',
                    controller: 'ScanningRegistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScanningRegistration', function(ScanningRegistration) {
                            return ScanningRegistration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scanning-registration', null, { reload: 'scanning-registration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scanning-registration.delete', {
            parent: 'scanning-registration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scanning-registration/scanning-registration-delete-dialog.html',
                    controller: 'ScanningRegistrationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ScanningRegistration', function(ScanningRegistration) {
                            return ScanningRegistration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scanning-registration', null, { reload: 'scanning-registration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
