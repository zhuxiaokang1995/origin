(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ScanningRegistrationDetailController', ScanningRegistrationDetailController);

    ScanningRegistrationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ScanningRegistration'];

    function ScanningRegistrationDetailController($scope, $rootScope, $stateParams, previousState, entity, ScanningRegistration) {
        var vm = this;

        vm.scanningRegistration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:scanningRegistrationUpdate', function(event, result) {
            vm.scanningRegistration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
