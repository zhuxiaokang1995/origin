(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessControlDeleteController',ProcessControlDeleteController);

    ProcessControlDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProcessControl'];

    function ProcessControlDeleteController($uibModalInstance, entity, ProcessControl) {
        var vm = this;

        vm.processControl = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProcessControl.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
