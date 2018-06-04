(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('AbnormalInformationDeleteController',AbnormalInformationDeleteController);

    AbnormalInformationDeleteController.$inject = ['$uibModalInstance', 'entity', 'AbnormalInformation'];

    function AbnormalInformationDeleteController($uibModalInstance, entity, AbnormalInformation) {
        var vm = this;

        vm.abnormalInformation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AbnormalInformation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
