(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ScanningRegistrationDeleteController',ScanningRegistrationDeleteController);

    ScanningRegistrationDeleteController.$inject = ['$uibModalInstance', 'entity', 'ScanningRegistration'];

    function ScanningRegistrationDeleteController($uibModalInstance, entity, ScanningRegistration) {
        var vm = this;

        vm.scanningRegistration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ScanningRegistration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
