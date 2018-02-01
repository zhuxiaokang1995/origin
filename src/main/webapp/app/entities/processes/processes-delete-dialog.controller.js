(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessesDeleteController',ProcessesDeleteController);

    ProcessesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Processes'];

    function ProcessesDeleteController($uibModalInstance, entity, Processes) {
        var vm = this;

        vm.processes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Processes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
